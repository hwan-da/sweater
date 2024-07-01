package com.ssafy.sweater.model.service;

import java.util.List;

import com.ssafy.sweater.model.dto.Comment;
import com.ssafy.sweater.model.dto.Message;

public interface CommentService {

	public List<Comment> getCommentList(int commentId);

	public int writeComment(Comment comment);

	public int modifyComment(Comment comment);

	public int removeComment(int commentId);

	public int invite(Message Msessage);

}
