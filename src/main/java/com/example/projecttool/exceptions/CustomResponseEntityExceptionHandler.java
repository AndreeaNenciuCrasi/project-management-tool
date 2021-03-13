package com.example.projecttool.exceptions;

import com.example.projecttool.exceptions.projectIDException.ProjectIdException;
import com.example.projecttool.exceptions.projectIDException.ProjectIdExceptionResponse;
import com.example.projecttool.exceptions.projectNotFoundException.ProjectNotFoundException;
import com.example.projecttool.exceptions.projectNotFoundException.ProjectNotFoundExceptionResponse;
import com.example.projecttool.exceptions.userNameAlreadyExistsException.UsernameAlreadyExistsException;
import com.example.projecttool.exceptions.userNameAlreadyExistsException.UsernameAlreadyExistsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex, WebRequest request){
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request){
        ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, WebRequest request){
        UsernameAlreadyExistsResponse existsResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity(existsResponse, HttpStatus.BAD_REQUEST);
    }
}
