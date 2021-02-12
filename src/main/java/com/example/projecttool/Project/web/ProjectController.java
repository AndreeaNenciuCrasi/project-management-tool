package com.example.projecttool.Project.web;

import com.example.projecttool.Project.domain.Project;
import com.example.projecttool.Project.services.MapValidationErrorService;
import com.example.projecttool.Project.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;


    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

}

//@RequestBody
//        Annotation indicating a method parameter should be bound to the body of the web request.
//        The body of the request is passed through an HttpMessageConverter to resolve the method argument
//        depending on the content type of the request. Optionally, automatic validation can be applied by annotating the argument with @Valid.


//public interface BindingResult
//        extends Errors
//General interface that represents binding results. Extends the interface for error registration capabilities, allowing
// for a Validator to be applied, and adds binding-specific analysis and model building.


//@Valid
//Another handy aspect of @Valid not mentioned above is that (ie: using Postman to test an endpoint) @Valid will format the output
//        of an incorrect REST call into formatted JSON instead of a blob of barely readable text. This is very useful if you are
//        creating a commercially consumable API for your users.


//@RequestMapping.
//        Simply put, the annotation is used to map web requests to Spring Controller methods.