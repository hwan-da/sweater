package com.ssafy.sweater.model.dao;

import java.util.List;

import com.ssafy.sweater.model.dto.Comment;
import com.ssafy.sweater.model.dto.Message;

public interface CommentDao {

	public List<Comment> selectAll(int commentId);
	
	public int insertComment(Comment comment);
	
	public int updateComment(Comment comment);
	
	public int deleteComment(int commentId);
	
	public int invite(Message Msessage);
	
}
