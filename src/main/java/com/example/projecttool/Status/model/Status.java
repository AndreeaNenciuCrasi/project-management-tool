package com.example.projecttool.Status.model;

import com.example.projecttool.Project.model.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String projectIdentifier;

    @NotBlank(message = "Please include a status name.")
    private String statusName;

//    Many to One with Project
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id", updatable = false, nullable = false)
    @JsonIgnore
    private Project project;

}
