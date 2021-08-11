package com.example.projecttool.Backlog.model;

import com.example.projecttool.Project.model.Project;
import com.example.projecttool.ProjectTask.model.ProjectTask;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.ToString.Exclude;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence =0;
    private String projectIdentifier;


    //One to One with project
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id", nullable = false)
    @JsonIgnore
    private Project project;

    //One to Many with projecttasks
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, mappedBy = "backlog", orphanRemoval = true)
    private List<ProjectTask> projectTasks = new ArrayList<>();


}
