package com.bepsik.moneymotivator.entity;

import com.bepsik.moneymotivator.enumeration.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_task_status_history")
@Builder
@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_task_status_history_id_seq")
    @SequenceGenerator(name = "t_task_status_history_id_seq", sequenceName = "t_task_status_history_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Task task;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(insertable = false, updatable = false)
    private LocalDateTime date;

    private String createdBy;

}
