package com.example.projecttool.Status.services;

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

    public Status addStatus(Status status,String project_id){
        status.setProject(projectRepository.findByProjectIdentifier(project_id));
        status.setProjectIdentifier(project_id);
        return statusRepository.save(status);
    }


    public Iterable<Status> getStatusByProjectIdentifier(String projectId){
        return statusRepository.findStatusesByProjectIdentifier(projectId);
    }

}

