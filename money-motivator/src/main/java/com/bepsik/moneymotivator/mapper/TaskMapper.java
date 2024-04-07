package com.bepsik.moneymotivator.mapper;

import com.bepsik.moneymotivator.dto.TaskDto;
import com.bepsik.moneymotivator.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = ProjectMapper.class)
public interface TaskMapper {

    @Mapping(source = "author.email", target = "authorEmail")
    @Mapping(source = "assignee.email", target = "assigneeEmail")
    TaskDto toDto(Task entity);
    List<TaskDto> toDto(List<Task> entity);

    Task toEntity(TaskDto dto);

}
