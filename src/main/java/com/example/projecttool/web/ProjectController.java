package com.example.projecttool.web;

import com.example.projecttool.domain.Project;
import com.example.projecttool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<String>("Invalid Project Object", HttpStatus.BAD_REQUEST);
        }
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