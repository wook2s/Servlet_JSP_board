package com.jade.myapp.board.VO;

import java.sql.Date;

public class ReplyVO {

	private int replyNO;
	private int boardNO;
	
	private String id;
	private String content;
	private Date writeDate;
	
	public ReplyVO() {
	}

	public ReplyVO(int replyNO, int boardNO, String id, String content, Date writeDate) {
		this.replyNO = replyNO;
		this.boardNO = boardNO;
		this.id = id;
		this.content = content;
		this.writeDate = writeDate;
	}

	public ReplyVO(int replyNO, int boardNO, String id, String content) {
		this.replyNO = replyNO;
		this.boardNO = boardNO;
		this.id = id;
		this.content = content;
	}

	public int getReplyNO() {
		return replyNO;
	}

	public void setReplyNO(int replyNO) {
		this.replyNO = replyNO;
	}

	public int getBoardNO() {
		return boardNO;
	}

	public void setBoardNO(int boardNO) {
		this.boardNO = boardNO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	@Override
	public String toString() {
		return "ReplyVO [replyNO=" + replyNO + ", boardNO=" + boardNO + ", id=" + id + ", content=" + content
				+ ", writeDate=" + writeDate + "]";
	}
	
	
	
}
