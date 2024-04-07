package com.bepsik.moneymotivator.entity;

import com.bepsik.moneymotivator.enumeration.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_project_user")
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUser {

    @EmbeddedId
    private ProjectUserId id;

    @ManyToOne(optional = false)
    @MapsId("projectId")
    private Project project;

    @ManyToOne(optional = false)
    @MapsId("userId")
    private User user;

    @Enumerated(EnumType.STRING)
    private ProjectRole role;

    @Column(insertable = false, updatable = false)
    private LocalDateTime invited;

}
