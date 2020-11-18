package pl.mkowsky.jirawannabedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mkowsky.jirawannabedemo.model.TaskStatusChange;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusChange, Long> {
}
