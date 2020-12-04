package pl.mkowsky.jirawannabedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import pl.mkowsky.jirawannabedemo.model.Task;


import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
        Task getTaskById(Long taskID);



        @Transactional
        @Query(value = "SELECT * FROM task where project_id = :projectID", nativeQuery = true)
        List<Task> getTasksForProject(@Param("projectID") Long projectID);


}
