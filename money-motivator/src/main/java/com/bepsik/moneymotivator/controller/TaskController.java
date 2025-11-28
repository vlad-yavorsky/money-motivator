package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.TaskDto;
import com.bepsik.moneymotivator.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public TaskDto findById(@PathVariable Long id) {
        log.info("Request to find task by id {}", id);
        return taskService.findByIdAndConvert(id);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDto> findAllByProjectId(@PathVariable Long projectId) {
        log.info("Request to find all tasks by project id {}", projectId);
        return taskService.findAllByProjectId(projectId);
    }

    @GetMapping("/to-do")
    public List<TaskDto> findAllAssignedToCurrentUser() {
        log.info("Request to find all tasks assigned to current user");
        return taskService.findAllAssignedToCurrentUser();
    }

    @GetMapping("/to-verify")
    public List<TaskDto> findAllOwnedByCurrentUser() {
        log.info("Request to find all tasks owned by current user");
        return taskService.findAllOwnedByCurrentUser();
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        log.info("Request to create task {}", taskDto);
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{id}/verify")
    public TaskDto verifyTask(@PathVariable Long id) {
        log.info("Request to verify task by id {}", id);
        return taskService.verifyTask(id);
    }

}
