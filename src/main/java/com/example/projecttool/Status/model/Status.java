package com.example.projecttool.Status.model;

import com.example.projecttool.Backlog.model.Backlog;
import com.example.projecttool.Project.model.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private String projectIdentifier;
    private String status;

//    Many to One with Project
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id", updatable = false, nullable = false)
    @JsonIgnore
    private Project project;
}
