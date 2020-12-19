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

    @Transactional
    @Query(value = "SELECT \n" +
            "  COUNT(IF(state = 'IN_PROGRESS' , 1, NULL)) as inprog,\n" +
            "    COUNT(IF(state = 'DONE' , 1, NULL)) as done,\n" +
            "    COUNT(IF(state = 'CODE_REVIEW' , 1, NULL)) as review,\n" +
            "    COUNT(IF(state = 'TO_DO' , 1, NULL)) as todo\n" +
            "FROM \n" +
            "  task\n" +
            ";", nativeQuery = true)
    List<Long[]> getTasksLength();

    @Transactional
    @Query(value = "SELECT \n" +
            "  COUNT(IF(state = 'IN_PROGRESS' , 1, NULL)) as inprog,\n" +
            "    COUNT(IF(state = 'DONE' , 1, NULL)) as done,\n" +
            "    COUNT(IF(state = 'CODE_REVIEW' , 1, NULL)) as review,\n" +
            "    COUNT(IF(state = 'TO_DO' , 1, NULL)) as todo\n" +
            "FROM \n" +
            "  task\n" +
            "where task.user_id = :userID", nativeQuery = true)
    List<Long[]> getTasksLength(@Param("userID") Long userID);


    @Transactional
    @Query(value = "SELECT \n" +
            "  COUNT(IF(state = 'IN_PROGRESS' , 1, NULL)) as inprog,\n" +
            "    COUNT(IF(state = 'DONE' , 1, NULL)) as done,\n" +
            "    COUNT(IF(state = 'CODE_REVIEW' , 1, NULL)) as review,\n" +
            "    COUNT(IF(state = 'TO_DO' , 1, NULL)) as todo\n" +
            "FROM \n" +
            "  task\n" +
            "where task.project_id = :projectID", nativeQuery = true)
    List<Long[]> getTasksLengthForProjectWithProjectID(@Param("projectID") Long projectID);




}
