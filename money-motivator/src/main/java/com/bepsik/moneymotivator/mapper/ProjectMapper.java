package com.bepsik.moneymotivator.mapper;

import com.bepsik.moneymotivator.dto.ProjectDto;
import com.bepsik.moneymotivator.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ProjectMapper {

    @Mapping(source = "owner.email", target = "ownerEmail")
    ProjectDto toDto(Project entity);
    List<ProjectDto> toDto(List<Project> entity);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }
        Project project = new Project();
        project.setId(id);
        return project;
    }

}
