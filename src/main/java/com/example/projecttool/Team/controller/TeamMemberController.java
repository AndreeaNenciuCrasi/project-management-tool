package com.example.projecttool.Team.controller;

import com.example.projecttool.Project.services.MapValidationErrorService;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Team.model.TeamMember;
import com.example.projecttool.Team.services.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/team")
@CrossOrigin
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{project_id}/{username}")
    public ResponseEntity<?> addNewTeamMember(@RequestBody TeamMember teamMember,
                                              @PathVariable String project_id, @PathVariable String username,  Principal principal){

        TeamMember newTeamMember = teamMemberService.addTeamMemberToProject(teamMember,project_id, username);
        return new ResponseEntity<TeamMember>(newTeamMember, HttpStatus.CREATED);
    }
}
