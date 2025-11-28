package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.dto.ProjectDto;
import com.bepsik.moneymotivator.entity.Project;
import com.bepsik.moneymotivator.exception.NotFoundException;
import com.bepsik.moneymotivator.mapper.ProjectMapper;
import com.bepsik.moneymotivator.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final CurrentUserService currentUserService;
    private final UserService userService;

    public List<ProjectDto> findByCurrentUser() {
        log.info("Find projects by current user");
        var projects = projectRepository.findByCurrentUserEmail(currentUserService.getEmail());
        return projectMapper.toDto(projects);
    }

    public ProjectDto findById(Long id) {
        log.info("Find project by id {}", id);
        verifyAccess(id);
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project " + id + " not found"));
        return projectMapper.toDto(project);
    }

    private void verifyAccess(Long projectId) {
        if (!projectRepository.existsByProjectIdAndUserEmail(currentUserService.getEmail(), projectId)) {
            throw new NotFoundException("Project " + projectId + " not found or access denied");
        }
    }

    @Transactional
    public ProjectDto create(ProjectDto projectDto) {
        log.info("Create project {}", projectDto);
        Project project = Project.builder()
                .name(projectDto.getName())
                .description(projectDto.getDescription())
                .owner(userService.findByCurrentUser())
                .build();
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }
}
