package pl.mkowsky.jirawannabedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkowsky.jirawannabedemo.dto.CommentDTO;
import pl.mkowsky.jirawannabedemo.model.Comment;
import pl.mkowsky.jirawannabedemo.model.Task;
import pl.mkowsky.jirawannabedemo.repository.TaskRepository;
import pl.mkowsky.jirawannabedemo.services.CommentService;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;


    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping(value = "/get-commnet/{commentID}")
    Comment getCommentByID(@PathVariable("commentID") Long commentID) {
        return commentService.findCommentByID(commentID);
    }

    @GetMapping(value = "/get-comments-for-task/{taskID}")
    List<Comment> getCommentsForTask(@PathVariable("taskID") Long taskID) {
        return commentService.findCommentsForTask(taskID);
    }

    @PostMapping(value = "/new-comment")
    void addCommentToTask(@RequestBody CommentDTO commentDTO) {
        commentService.createNewComment(commentDTO);
    }

    @PostMapping(value = "/delete-comment")
    void deleteComment(@RequestParam ("commentID") Long commentID){
        commentService.deleteComment(commentID);
    }

}
