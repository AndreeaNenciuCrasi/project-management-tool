package com.example.projecttool.Team.services;

import com.example.projecttool.Project.repositories.ProjectRepository;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Team.model.TeamMember;
import com.example.projecttool.Team.repositories.TeamMemberRepository;
import com.example.projecttool.User.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TeamMember addTeamMemberToProject(TeamMember teamMember, String projectId, String username){
        Long userId = userRepository.findByUsername(username).getId();

        teamMember.setProject(projectRepository.findByProjectIdentifier(projectId));
        teamMember.setUserId(userId);
        return teamMemberRepository.save(teamMember);
    }

    public Iterable<TeamMember> getTeamMembersByProjectId(String projectId){
        return teamMemberRepository.findTeamMembersByProjectId(projectRepository.findByProjectIdentifier(projectId).getId());
    }

    public void deleteTeamMember(Long userId){
        TeamMember teamMember=teamMemberRepository.findTeamMemberByUserId(userId);
        teamMemberRepository.delete(teamMember);
    }
}
