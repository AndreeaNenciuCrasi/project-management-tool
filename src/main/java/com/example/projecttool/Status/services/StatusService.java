package com.example.projecttool.Status.services;

import com.example.projecttool.Project.repositories.ProjectRepository;
import com.example.projecttool.Project.services.ProjectService;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Status.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StatusService {

    private StatusRepository statusRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository, ProjectRepository projectRepository) {
        this.statusRepository = statusRepository;
        this.projectRepository = projectRepository;
    }

    public Status addStatus(Status status, String project_id){
        status.setProject(projectRepository.findByProjectIdentifier(project_id));
        status.setProjectIdentifier(project_id);
        return statusRepository.save(status);
    }


    public Iterable<Status> getStatusByProjectIdentifier(String projectId){
        return statusRepository.findStatusesByProjectIdentifier(projectId);
    }

    @Transactional
    public void updateStatus(String name, Long statusId){
        statusRepository.updateStatus(statusId,name);
    }

    public void deleteStatus(Long statusId){
        Status status=statusRepository.findStatusById(statusId);
        statusRepository.delete(status);
    }

}

