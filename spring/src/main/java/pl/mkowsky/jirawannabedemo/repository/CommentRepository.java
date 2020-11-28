package pl.mkowsky.jirawannabedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.mkowsky.jirawannabedemo.model.Comment;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {


    Comment findCommentById(Long commentID);

    @Transactional
    @Query(value = "SELECT * FROM comment where task_id = :taskID", nativeQuery = true)
    List<Comment> getAllCommentsForTask(@Param("taskID") Long taskID);


}
