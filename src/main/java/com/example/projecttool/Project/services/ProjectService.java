package com.example.projecttool.Project.services;

import com.example.projecttool.Backlog.model.Backlog;
import com.example.projecttool.Backlog.repositories.BacklogRepository;
import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Status.repositories.StatusRepository;
import com.example.projecttool.Status.services.StatusService;
import com.example.projecttool.User.model.User;
import com.example.projecttool.User.repositories.UserRepository;
import com.example.projecttool.exceptions.projectIDException.ProjectIdException;
import com.example.projecttool.Project.repositories.ProjectRepository;
import com.example.projecttool.exceptions.projectNotFoundException.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private StatusRepository statusRepository;

    public Project saveOrUpdateProject(Project project, String username){
        String identifier = project.getProjectIdentifier().toUpperCase();

        if(project.getId() !=null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if(existingProject != null &&(!existingProject.getProjectLeader().equals(username))){
                throw new ProjectNotFoundException("Project not found in your account");
            }else if(existingProject == null){
                throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier() + "' cannot be updated because  it doesn't exist");
            }
        }
        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(identifier);

            if(project.getId()==null){
                Backlog backlog = new Backlog();
//                Status statusToDo =new Status();
//                statusToDo.setProject(project);
//                statusToDo.setStatus("TO DO");
//                statusToDo.setProjectIdentifier(identifier);
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(identifier);
            }

            if(project.getId()!=null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(identifier));
            }

            return projectRepository.save(project);

        }catch (Exception e){
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase()+ "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId, String username){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
        }

        if(!project.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project not found in your account");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {

        projectRepository.delete(findProjectByIdentifier(projectId,username));
    }

}
