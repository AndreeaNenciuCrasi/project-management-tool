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

    private StatusService statusService;
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    public StatusController(StatusService statusService, MapValidationErrorService mapValidationErrorService) {
        this.statusService = statusService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

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

    @PatchMapping("/{status_id}")
    public ResponseEntity<?> updateStatus(@Valid @RequestBody Status status, BindingResult result,
                                               @PathVariable String status_id, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        Long id = Long.valueOf(status_id);
        statusService.updateStatus(status.getStatusName(), id);
        return new ResponseEntity<String>("Status was updated",HttpStatus.OK);
    }

    @DeleteMapping("/{status_id}")
    public ResponseEntity<?> deleteProject(@PathVariable String status_id, Principal principal){
        Long id = Long.valueOf(status_id);
        statusService.deleteStatus(id);
        return new ResponseEntity<String>("Status with ID '"+ status_id +"' was deleted", HttpStatus.OK);
    }
}
