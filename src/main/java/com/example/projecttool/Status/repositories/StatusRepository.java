package com.example.projecttool.Status.repositories;

import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Status.model.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {

    Iterable<Status> findByProjectIdentifier(String projectId);
}
