package com.example.projecttool.Status.controller;

import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Project.services.MapValidationErrorService;
import com.example.projecttool.ProjectTask.model.ProjectTask;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Status.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/status")
@CrossOrigin
public class StatusController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{project_id}")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Status status, BindingResult result, @PathVariable String project_id, Principal principal){


        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        Status newStatus = statusService.addStatus(status,project_id);
        return new ResponseEntity<Status>(newStatus, HttpStatus.CREATED);
    }

    @GetMapping("/{project_id}")
    public  Iterable<Status> getAllStatuses(@PathVariable String project_id){
        return statusService.getStatusByProjectIdentifier(project_id);
    }
}
