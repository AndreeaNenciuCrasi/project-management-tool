package com.example.projecttool.Status.model;

import com.example.projecttool.Project.model.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String status) {
        this.statusName = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
