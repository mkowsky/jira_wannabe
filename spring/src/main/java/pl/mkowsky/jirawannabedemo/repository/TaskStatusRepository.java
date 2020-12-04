package pl.mkowsky.jirawannabedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mkowsky.jirawannabedemo.model.TaskStatusChange;

import java.util.List;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusChange, Long> {


    @Transactional
    //@Query(value = "select tasks_changes.change_description from tasks_changes join task on task.id = tasks_changes.task_id where task.project_id = :projectID", nativeQuery = true)
    @Query(value = "select tc.id, tc.change_date, tc.change_description, tc.change_type, tc.task_id from tasks_changes as tc join task on task.id = tc.task_id where task.project_id = :projectID", nativeQuery = true)
    List<TaskStatusChange> getAllTaskChangesForProject(@Param("projectID") Long projectID);


    @Transactional
    @Query(value = "select tc.id, tc.change_description, tc.change_date, tc.change_type, tc.task_id from tasks_changes as tc join task on task.id = tc.task_id where task.project_id = :projectID", nativeQuery = true)
    List<Object[]> testQuery(@Param("projectID") Long projectID);

}
