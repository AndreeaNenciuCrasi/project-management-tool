package com.example.projecttool.Team.controller;

import com.example.projecttool.Project.services.MapValidationErrorService;
import com.example.projecttool.Team.model.TeamMember;
import com.example.projecttool.Team.services.TeamMemberService;
import com.example.projecttool.User.model.User;
import com.example.projecttool.User.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/team")
@CrossOrigin
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{projectIdentifier}/{username}")
    public ResponseEntity<?> addNewTeamMember(@RequestBody TeamMember teamMember,
                                              @PathVariable String projectIdentifier, @PathVariable String username, Principal principal){

        TeamMember newTeamMember = teamMemberService.addTeamMemberToProject(teamMember, projectIdentifier, username);
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
}
