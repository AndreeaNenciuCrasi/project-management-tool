package com.example.projecttool.Team.services;

import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Project.repositories.ProjectRepository;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Team.model.TeamMember;
import com.example.projecttool.Team.repositories.TeamMemberRepository;
import com.example.projecttool.User.model.User;
import com.example.projecttool.User.repositories.UserRepository;
import com.example.projecttool.exceptions.UserNotFoundException.UserNotFoundException;
import com.example.projecttool.exceptions.projectIDException.ProjectIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamMemberService {

    private TeamMemberRepository teamMemberRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public TeamMemberService(TeamMemberRepository teamMemberRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public TeamMember addTeamMemberToProject(TeamMember teamMember, String projectIdentifier, String username){
        try {
            User newTeammate = userRepository.findByUsername(username);
            Long userId = newTeammate.getId();

            teamMember.setProject(projectRepository.findByProjectIdentifier(projectIdentifier));
            teamMember.setUserId(userId);
            Long projectId = projectRepository.findByProjectIdentifier(projectIdentifier).getId();
            Integer count=teamMemberRepository.countTeamMemberByUserIdAndProjectId(userId,projectId);

            return count ==0 ? teamMemberRepository.save(teamMember): null;
        }
        catch (Exception e){
            System.out.println("User '" + username+ "' doesn't exists");
            throw new UserNotFoundException("User '" + username+ "' doesn't exists");
        }
    }

    public Iterable<TeamMember> getTeamMembersByProjectId(String projectId){
        return teamMemberRepository.findTeamMembersByProjectId(projectRepository.findByProjectIdentifier(projectId).getId());
    }

    public void deleteTeamMember(Long userId, Long projectId){
        TeamMember teamMember=teamMemberRepository.findTeamMemberByUserIdAndProjectId(userId, projectId);

        teamMemberRepository.delete(teamMember);
    }

    public List<Project> getProjectsWhereUserIsTeamMember(Long userId){
        Iterable<Long> projectIdList = teamMemberRepository.findProjectIdByUserId(userId);
        List<Project> teamMemberProjects = new ArrayList<>();
        for(Long projectId: projectIdList){
            teamMemberProjects.add(projectRepository.findProjectById(projectId));
        }
        return teamMemberProjects;
    }
}
