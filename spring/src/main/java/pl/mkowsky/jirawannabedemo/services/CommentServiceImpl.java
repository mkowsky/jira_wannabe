package pl.mkowsky.jirawannabedemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkowsky.jirawannabedemo.dto.CommentDTO;
import pl.mkowsky.jirawannabedemo.model.Comment;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.repository.CommentRepository;
import pl.mkowsky.jirawannabedemo.repository.TaskRepository;
import pl.mkowsky.jirawannabedemo.repository.UserRepository;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void saveNewComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findCommentByID(Long commentID) {
        return commentRepository.findCommentById(commentID);
    }

    @Override
    public List<Comment> findCommentsForTask(Long taskID) {
        return commentRepository.getAllCommentsForTask(taskID);
    }

    @Override
    public void deleteComment(Long commentID) {
        commentRepository.delete(findCommentByID(commentID));
    }

    public void createNewComment(CommentDTO commentDTO) {

        /* I sposób
           Comment comment = new Comment(commentDTO.getComment(), new Date(), userRepository.findUserById(commentDTO.getUserID()));
        saveNewComment(comment);

        Task task = taskRepository.getTaskById(commentDTO.getTaskID());
        task.addComment(comment);
        taskRepository.save(task);
         */
        // II SPOSOB
        Comment comment = new Comment(commentDTO.getComment(),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                userRepository.findUserById(commentDTO.getUserID()),
                taskRepository.getTaskById(commentDTO.getTaskID()));

        saveNewComment(comment);
    }


}
