package com.example.projecttool.Status.repositories;

import com.example.projecttool.Project.model.Project;
import com.example.projecttool.Status.model.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {

    Iterable<Status> findStatusesByProjectIdentifier(String projectIdentifier);
    
    Status findStatusById(Long id);

    @Modifying
    @Query("update Status s set s.statusName = :status where s.id = :id")
    void updateStatus(@Param(value = "id") Long id, @Param(value = "status") String status);
}
