package pl.mkowsky.jirawannabedemo.services;

import pl.mkowsky.jirawannabedemo.dto.CommentDTO;
import pl.mkowsky.jirawannabedemo.model.Comment;

import java.util.List;

public interface CommentService {


    Comment findCommentByID(Long commentID);
    void createNewComment(CommentDTO commentDTO);
    void saveNewComment(Comment comment);
    List<Comment>  findCommentsForTask(Long taskID);
    void deleteComment(Long commentID);
}
