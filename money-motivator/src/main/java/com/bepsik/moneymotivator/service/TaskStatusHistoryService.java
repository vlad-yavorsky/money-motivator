package com.bepsik.moneymotivator.service;

import com.bepsik.moneymotivator.entity.Task;
import com.bepsik.moneymotivator.entity.TaskStatusHistory;
import com.bepsik.moneymotivator.enumeration.TaskStatus;
import com.bepsik.moneymotivator.repository.TaskStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskStatusHistoryService {

    private final TaskStatusHistoryRepository taskStatusHistoryRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public void create(Task task, TaskStatus taskStatus) {
        TaskStatusHistory taskStatusHistory = TaskStatusHistory
                .builder()
                .task(task)
                .status(taskStatus)
                .createdBy(currentUserService.getEmail())
                .build();
        taskStatusHistoryRepository.save(taskStatusHistory);
    }

}
