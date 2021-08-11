package com.example.projecttool.Team.controller;

import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Project.services.MapValidationErrorService;
import com.example.projecttool.Team.model.TeamMember;
import com.example.projecttool.Team.repositories.TeamMemberRepository;
import com.example.projecttool.Team.services.TeamMemberService;
import com.example.projecttool.User.model.User;
import com.example.projecttool.User.repositories.UserRepository;
import com.example.projecttool.User.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project/team")
@CrossOrigin
public class TeamMemberController {

    private TeamMemberService teamMemberService;
    private TeamMemberRepository teamMemberRepository;
    private UserRepository userRepository;

    @Autowired
    public TeamMemberController(TeamMemberService teamMemberService, TeamMemberRepository teamMemberRepository, UserRepository userRepository) {
        this.teamMemberService = teamMemberService;
        this.teamMemberRepository = teamMemberRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{projectIdentifier}/{newTeamMemberUsername}")
    public ResponseEntity<?> addNewTeamMember(@RequestBody TeamMember teamMember,
                                              @PathVariable String projectIdentifier, @PathVariable String newTeamMemberUsername, Principal principal){

        TeamMember newTeamMember = teamMemberService.addTeamMemberToProject(teamMember, projectIdentifier, newTeamMemberUsername);
        return new ResponseEntity<TeamMember>(newTeamMember, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public  List<Optional<User>> getAllTeamMembers(@PathVariable String projectIdentifier){

        List<Optional<User>> listWithTeamMembers = new ArrayList<>();
        Iterable<TeamMember> teamMembers = teamMemberService.getTeamMembersByProjectId(projectIdentifier);

        for(TeamMember member: teamMembers){
            listWithTeamMembers.add(userRepository.findById(member.getUserId()));
        }
        return listWithTeamMembers;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteProject(@PathVariable String userId, Principal principal){
        Long id = Long.valueOf(userId);
        String username = userRepository.getById(id).getUsername();
        String projectId = teamMemberRepository.findTeamMemberByUserId(id).getProject().getProjectIdentifier();
        teamMemberService.deleteTeamMember(id);
        return new ResponseEntity<String>("Team member with username '"+ username +"' was removed from project '" + projectId +"'.", HttpStatus.OK);
    }

    @GetMapping("/teamProjects")
    public  List<Project> getProjectListWhereUserIsTeamMember(Principal principal){
        User user = userRepository.findByUsername(principal.getName());
        Long userId = user.getId();

        return teamMemberService.getProjectsWhereUserIsTeamMember(userId);
    }
}
