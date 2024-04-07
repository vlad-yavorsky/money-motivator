package com.bepsik.moneymotivator.entity;

import com.bepsik.moneymotivator.enumeration.TaskStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "t_task")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_task_id_seq")
    @SequenceGenerator(name = "t_task_id_seq", sequenceName = "t_task_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignee;

    @OneToMany(mappedBy = "task")
    private List<TaskStatusHistory> statusHistory;

}
