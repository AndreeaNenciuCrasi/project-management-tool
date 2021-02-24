package com.example.projecttool.ProjectTask.repositories;


import com.example.projecttool.ProjectTask.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
}
