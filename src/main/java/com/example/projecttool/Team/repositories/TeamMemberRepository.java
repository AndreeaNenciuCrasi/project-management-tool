package com.example.projecttool.Team.repositories;

import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Team.model.TeamMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember,Long> {
    Iterable<TeamMember> findTeamMembersByProjectId(Long id);
}
