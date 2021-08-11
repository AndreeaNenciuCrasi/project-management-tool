package com.example.projecttool.Project.model;


import com.example.projecttool.Backlog.model.Backlog;
import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Team.model.TeamMember;
import com.example.projecttool.User.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message= "Project name is required")
    private String projectName;

    @NotBlank(message = "Project Identifier is required")
    @Size(min=4, max=5, message = "Please use 4 to 5 characters")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description is not required")
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date start_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date end_date;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date created_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updated_At;


    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    //One to Many with status
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "project", orphanRemoval = true)
    private List<Status> taskStatus = new ArrayList<>();

    //One to Many with teamMembers
    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.REFRESH, mappedBy = "project", orphanRemoval = true)
    private List<TeamMember> teamMembers = new ArrayList<>();

    private String projectLeader;


    //    @PrePersist and @PreUpdate JPA entity listeners

    @PrePersist
    protected void onCreate(){
        this.created_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_At = new Date();
    }


}
