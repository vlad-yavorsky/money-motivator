package com.bepsik.moneymotivator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_project")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_balance_history_id_seq")
    @SequenceGenerator(name = "t_balance_history_id_seq", sequenceName = "t_balance_history_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @Column(insertable = false, updatable = false)
    private LocalDateTime created;

}
