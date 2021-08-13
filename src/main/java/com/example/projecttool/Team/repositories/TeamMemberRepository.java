package com.example.projecttool.Team.repositories;

import com.example.projecttool.Status.model.Status;
import com.example.projecttool.Team.model.TeamMember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember,Long> {

    Iterable<TeamMember> findTeamMembersByProjectId(Long id);

    TeamMember findTeamMemberByUserIdAndProjectId(Long userId, Long projectId);

//    @Query("select count(*) from TeamMember tm where tm.userId= :userId and tm.projectId= :projectId"))
    Integer countTeamMemberByUserIdAndProjectId(Long userId, Long projectId);


    @Query("SELECT tm.project.id FROM TeamMember tm where tm.userId= :id")
    Iterable<Long> findProjectIdByUserId(@Param(value = "id") Long id);
}
