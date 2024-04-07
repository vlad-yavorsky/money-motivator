package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.dto.TaskDto;
import com.bepsik.moneymotivator.entity.Task;
import com.bepsik.moneymotivator.enumeration.ProjectRole;
import com.bepsik.moneymotivator.enumeration.TaskStatus;
import com.bepsik.moneymotivator.exception.BadRequestException;
import com.bepsik.moneymotivator.exception.NotFoundException;
import com.bepsik.moneymotivator.mapper.TaskMapper;
import com.bepsik.moneymotivator.repository.ProjectRepository;
import com.bepsik.moneymotivator.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CurrentUserService currentUserService;
    private final UserService userService;
    private final TaskStatusHistoryService taskStatusHistoryService;
    private final ProjectRepository projectRepository;

    private void verifyAccessToProject(Long projectId) {
        if (!projectRepository.existsByProjectIdAndUserEmail(currentUserService.getEmail(), projectId)) {
            throw new NotFoundException("Task not exist or access denied");
        }
    }

    private void verifySupervisorAccessToProject(Long projectId) {
        if (!projectRepository.existsByProjectIdAndUserEmailAndRole(currentUserService.getEmail(), projectId, ProjectRole.SUPERVISOR)) {
            throw new NotFoundException("Task not exist or access denied");
        }
    }

    public TaskDto findByIdAndConvert(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task " + id + " not found"));
        verifyAccessToProject(task.getProject().getId());
        return taskMapper.toDto(task);
    }

    private Task findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task " + id + " not found"));
        verifyAccessToProject(task.getProject().getId());
        return task;
    }

    public List<TaskDto> findAllByProjectId(Long projectId) {
        verifyAccessToProject(projectId);
        List<Task> task = taskRepository.findAllByProjectId(projectId);
        return taskMapper.toDto(task);
    }

    public List<TaskDto> findAllAssignedToCurrentUser() {
        List<Task> task = taskRepository.findAllByAssigneeEmailAndStatusIn(currentUserService.getEmail(), List.of(TaskStatus.NEW, TaskStatus.IN_PROGRESS, TaskStatus.DONE));
        return taskMapper.toDto(task);
    }

    public List<TaskDto> findAllOwnedByCurrentUser() {
        List<Task> task = taskRepository.findAllByAuthorEmailAndStatusIn(currentUserService.getEmail(), List.of(TaskStatus.NEW, TaskStatus.IN_PROGRESS, TaskStatus.DONE));
        return taskMapper.toDto(task);
    }

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        verifyAccessToProject(taskDto.getProjectId());
        Task task = taskMapper.toEntity(taskDto);
        task.setAuthor(userService.findByCurrentUser());
        if (StringUtils.isNotBlank(taskDto.getAssigneeEmail())) {
            task.setAssignee(userService.findByEmail(taskDto.getAssigneeEmail()));
        }
        task.setStatus(TaskStatus.NEW);
        taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TaskDto verifyTask(Long id) {
        Task task = findById(id);
        verifySupervisorAccessToProject(task.getProject().getId());
        if (List.of(TaskStatus.VERIFIED, TaskStatus.PAID).contains(task.getStatus())) {
            throw new BadRequestException("Task already verified");
        }
        task.setStatus(TaskStatus.VERIFIED);
        taskRepository.save(task);
        taskStatusHistoryService.create(task, TaskStatus.VERIFIED);
        userService.addToBalance(task.getAssignee(), task.getPrice());
        return taskMapper.toDto(task);
    }

}
