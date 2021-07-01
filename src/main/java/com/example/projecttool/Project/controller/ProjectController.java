package com.example.projecttool.Project.controller;

import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Project.services.MapValidationErrorService;
import com.example.projecttool.Project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){
        System.out.println(project);
        System.out.println(principal);
        System.out.println(principal.getName());

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
        Project project = projectService.findProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public  Iterable<Project> getAllProjects(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
        projectService.deleteProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<String>("Project with ID '"+ projectId +"' was deleted", HttpStatus.OK);
    }

    @GetMapping("/statusList/{projectId}")
    public  String getAllStatuses(@PathVariable String projectId){
        return projectService.getTypesOfStatus(projectId);
    }

    @PatchMapping("/newStatus/{projectId}/{column}")
    public ResponseEntity<?> addNewColumnStatus(@PathVariable String projectId, @PathVariable String column){
        projectService.addNewStatusColumnNameInList(column,projectId);
        return new ResponseEntity<String>("New status '" +column + "' was added to project with ID '"+ projectId +"'.", HttpStatus.OK);
    }

}

