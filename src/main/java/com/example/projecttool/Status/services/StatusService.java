package com.example.projecttool.Status.services;

import com.example.projecttool.Backlog.model.Backlog;
import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Project.repositories.ProjectRepository;
import com.example.projecttool.Project.services.ProjectService;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Status.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    public Status addStatus(Status status,String project_id,String username){
        status.setProject(projectRepository.findByProjectIdentifier(project_id));
        status.setProjectIdentifier(project_id);
        return statusRepository.save(status);
    }

//    public String getStatusByProjectIdentifier(String projectId){
//        return statusRepository.findByProjectIdentifier(projectId);
//    }

}

//    SELECT status FROM status
//    join project on status.project_identifier=project.project_identifier;
