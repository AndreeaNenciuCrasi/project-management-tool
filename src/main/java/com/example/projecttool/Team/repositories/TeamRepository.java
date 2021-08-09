package com.example.projecttool.Team.repositories;

import com.example.projecttool.Team.model.TeamMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<TeamMember,Long> {
}
