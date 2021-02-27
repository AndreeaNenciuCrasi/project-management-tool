package com.example.projecttool.ProjectTask.services;

import com.example.projecttool.Backlog.domain.Backlog;
import com.example.projecttool.Backlog.repositories.BacklogRepository;
import com.example.projecttool.ProjectTask.domain.ProjectTask;
import com.example.projecttool.ProjectTask.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        projectTask.setBacklog(backlog);
        Integer backlogSequence = backlog.getPTSequence();
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);

        projectTask.setProjectSequence(projectIdentifier +"-"+backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if(projectTask.getPriority() == null){
            projectTask.setPriority(3);
        }
        if(projectTask.getStatus()=="" || projectTask.getStatus() == null){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }
}