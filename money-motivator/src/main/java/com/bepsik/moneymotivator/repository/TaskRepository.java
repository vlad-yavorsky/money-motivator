package com.bepsik.moneymotivator.repository;

import com.bepsik.moneymotivator.dto.TaskStatisticDto;
import com.bepsik.moneymotivator.entity.Task;
import com.bepsik.moneymotivator.enumeration.TaskStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph(attributePaths = {"author", "assignee"})
    List<Task> findAllByProjectId(Long projectId);

    @EntityGraph(attributePaths = {"author", "assignee"})
    List<Task> findAllByAssigneeEmailAndStatusIn(String assigneeEmail, List<TaskStatus> taskStatuses);

    @EntityGraph(attributePaths = {"author", "assignee"})
    List<Task> findAllByAuthorEmailAndStatusIn(String email, List<TaskStatus> taskStatuses);

    @Query("""
        select distinct new com.bepsik.moneymotivator.dto.TaskStatisticDto(
                extract(year FROM statusHistory.date),
                extract(month FROM statusHistory.date),
                count(*))
        from Task task
        join task.assignee assignee
        join task.statusHistory statusHistory
        where statusHistory.status = com.bepsik.moneymotivator.enumeration.TaskStatus.VERIFIED
        and assignee.email = :assigneeEmail
        group by
            extract(year FROM statusHistory.date),
            extract(month FROM statusHistory.date)
        order by
            extract(year FROM statusHistory.date),
            extract(month FROM statusHistory.date)
        """)
    List<TaskStatisticDto> countByYearAndMonth(String assigneeEmail);

}
