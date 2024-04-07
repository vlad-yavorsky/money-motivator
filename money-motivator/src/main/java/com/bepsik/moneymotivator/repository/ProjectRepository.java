package com.bepsik.moneymotivator.repository;

import com.bepsik.moneymotivator.entity.Project;
import com.bepsik.moneymotivator.enumeration.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
        select distinct project
        from ProjectUser projectUser
        join projectUser.project project
        join projectUser.user user
        where user.email = :email
        """)
    List<Project> findByCurrentUserEmail(String email);

    @Query("""
        select case when count(project) > 0 then true else false end
        from ProjectUser projectUser
        join projectUser.user user
        join projectUser.project project
        where (user.email = :email and project.id = :projectId)
        or project.owner.email = :email
        """)
    boolean existsByProjectIdAndUserEmail(String email, Long projectId);

    @Query("""
        select case when count(project) > 0 then true else false end
        from ProjectUser projectUser
        join projectUser.user user
        join projectUser.project project
        where (user.email = :email and project.id = :projectId and projectUser.role = :role)
        or project.owner.email = :email
        """)
    boolean existsByProjectIdAndUserEmailAndRole(String email, Long projectId, ProjectRole role);

}
