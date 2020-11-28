package pl.mkowsky.jirawannabedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mkowsky.jirawannabedemo.model.Project;

import java.util.List;


@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findProjectById(Long projectID);

    @Query(value = "SELECT user_id FROM  projects_users where project_id = :projectID", nativeQuery = true)
    List<Long> getAllProjectUsers(@Param("projectID") Long projectID);


}


