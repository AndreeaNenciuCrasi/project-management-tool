package com.example.projecttool.ProjectTask.services;

import com.example.projecttool.Backlog.repositories.BacklogRepository;
import com.example.projecttool.ProjectTask.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;
}
