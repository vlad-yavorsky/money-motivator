package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.ProjectDto;
import com.bepsik.moneymotivator.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> findByCurrentUser() {
        return projectService.findByCurrentUser();
    }

    @GetMapping("/{id}")
    public ProjectDto findById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @PostMapping
    public ProjectDto create(@RequestBody @Valid ProjectDto projectDto) {
        return projectService.create(projectDto);
    }

}
