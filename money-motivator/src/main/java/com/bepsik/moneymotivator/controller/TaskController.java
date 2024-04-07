package com.bepsik.moneymotivator.controller;

import com.bepsik.moneymotivator.dto.TaskDto;
import com.bepsik.moneymotivator.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    public TaskDto findById(@PathVariable Long id) {
        return taskService.findByIdAndConvert(id);
    }

    @GetMapping("/project/{projectId}")
    public List<TaskDto> findAllByProjectId(@PathVariable Long projectId) {
        return taskService.findAllByProjectId(projectId);
    }

    @GetMapping("/to-do")
    public List<TaskDto> findAllAssignedToCurrentUser() {
        return taskService.findAllAssignedToCurrentUser();
    }

    @GetMapping("/to-verify")
    public List<TaskDto> findAllOwnedByCurrentUser() {
        return taskService.findAllOwnedByCurrentUser();
    }

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{id}/verify")
    public TaskDto verifyTask(@PathVariable Long id) {
        return taskService.verifyTask(id);
    }

}
