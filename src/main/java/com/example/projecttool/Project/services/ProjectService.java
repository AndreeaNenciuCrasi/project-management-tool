package com.example.projecttool.Project.services;

import com.example.projecttool.Backlog.model.Backlog;
import com.example.projecttool.Backlog.repositories.BacklogRepository;
import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Team.repositories.TeamMemberRepository;
import com.example.projecttool.Team.services.TeamMemberService;
import com.example.projecttool.User.model.User;
import com.example.projecttool.User.repositories.UserRepository;
import com.example.projecttool.User.services.UserService;
import com.example.projecttool.exceptions.projectIDException.ProjectIdException;
import com.example.projecttool.Project.repositories.ProjectRepository;
import com.example.projecttool.exceptions.projectNotFoundException.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;
    private UserRepository userRepository;
    private TeamMemberRepository teamMemberRepository;
    private UserService userService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, BacklogRepository backlogRepository, UserRepository userRepository, TeamMemberRepository teamMemberRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
        this.userRepository = userRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.userService = userService;
    }

    public Project saveOrUpdateProject(Project project, String username){
        String identifier = project.getProjectIdentifier().toUpperCase();

        if(project.getId() !=null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
//            List<String> teammatesUsernames = teamMemberRepository.findTeamMembersUsernamesOnProjectId(project.getId());
//            && !teammatesUsernames.contains(username)
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

    public Project findProjectByIdentifier(String projectIdentifier, String username){
        Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectIdentifier + "' does not exist");
        }
        List<String> teammatesUsernames = teamMemberRepository.findTeamMembersUsernamesOnProjectId(project.getId());
            if(!project.getProjectLeader().equals(username) && !teammatesUsernames.contains(username) ){
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
