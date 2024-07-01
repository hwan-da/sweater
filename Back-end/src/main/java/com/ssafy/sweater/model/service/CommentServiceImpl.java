package com.ssafy.sweater.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.sweater.model.dao.CommentDao;
import com.ssafy.sweater.model.dto.Comment;
import com.ssafy.sweater.model.dto.Message;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentDao commentDao;
	
	public CommentServiceImpl(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	@Override
	public List<Comment> getCommentList(int commentId) {
		return commentDao.selectAll(commentId);
	}

	@Override
	public int writeComment(Comment comment) {
		return commentDao.insertComment(comment);
	}

	@Override
	public int modifyComment(Comment comment) {
		return commentDao.updateComment(comment);
	}

	@Override
	public int removeComment(int commentId) {
		return commentDao.deleteComment(commentId);
	}

	@Override
	public int invite(Message Msessage) {
		return commentDao.invite(Msessage);
	}

}
