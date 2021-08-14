package com.jade.myapp.board.service;

import java.util.List;

import com.jade.myapp.board.VO.ReplyVO;
import com.jade.myapp.board.dao.ReplyDAO;

public class ReplyService {

	ReplyDAO replyDAO;
	
	public ReplyService() {
		replyDAO = new ReplyDAO();
	}

	public List<ReplyVO> getReplyList(int boardNO) {
		List<ReplyVO> replyList = replyDAO.getReplyList(boardNO);
		return replyList;
	}

	public int getMaxReplyNO() {
		int maxNum = replyDAO.getMaxReplyNO();
		return maxNum;
	}

	public void addReply(ReplyVO reply) {
		replyDAO.addReply(reply);
	}

	public void deleteReply(Integer replyNO) {
		replyDAO.deleteReply(replyNO);
	}

	public ReplyVO getReply(int prevReplyNO) {
		ReplyVO reply =  replyDAO.getReply(prevReplyNO);
		return reply;
	}

	public void updateReply(ReplyVO reply) {
		replyDAO.updateReply(reply);
	}

	public void deleteReplyByBoardNO(int boardNO) {
		replyDAO.deleteReplyByBoardNO(boardNO);
	}
}
