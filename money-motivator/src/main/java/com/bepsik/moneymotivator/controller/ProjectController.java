package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.ProjectDto;
import com.bepsik.moneymotivator.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> findByCurrentUser() {
        log.info("Request to find all projects");
        return projectService.findByCurrentUser();
    }

    @GetMapping("/{id}")
    public ProjectDto findById(@PathVariable Long id) {
        log.info("Request to find project by id {}", id);
        return projectService.findById(id);
    }

    @PostMapping
    public ProjectDto create(@RequestBody @Valid ProjectDto projectDto) {
        log.info("Request to create new project {}", projectDto);
        return projectService.create(projectDto);
    }

}
