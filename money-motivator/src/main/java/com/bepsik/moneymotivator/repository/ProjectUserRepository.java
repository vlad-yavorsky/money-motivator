package com.bepsik.moneymotivator.repository;

import com.bepsik.moneymotivator.entity.ProjectUser;
import com.bepsik.moneymotivator.entity.ProjectUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, ProjectUserId> {

}
