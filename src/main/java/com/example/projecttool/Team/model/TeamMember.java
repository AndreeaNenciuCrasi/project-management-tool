package com.example.projecttool.Team.model;

import com.example.projecttool.Project.model.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    Many to One with Project
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id", updatable = false, nullable = false)
    @JsonIgnore
    private Project project;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
