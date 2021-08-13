
package com.jade.myapp.board.VO;

import java.sql.Date;

public class BoardVO {
	
	private int boardNO;
	private int parentNO;
	private String title;
	private String content;
	private String imageName;
	private String id;
	private Date writeDate;

	public BoardVO() {
		// TODO Auto-generated constructor stub
	}


	public BoardVO(int boardNO, int parentNO, String title, String content, String imageName, String id) {
		this.boardNO = boardNO;
		this.parentNO = parentNO;
		this.title = title;
		this.content = content;
		this.imageName = imageName;
		this.id = id;
	}

	public BoardVO(int boardNO, int parentNO, String title, String content, String imageName, String id,
			Date writeDate) {
		this.boardNO = boardNO;
		this.parentNO = parentNO;
		this.title = title;
		this.content = content;
		this.imageName = imageName;
		this.id = id;
		this.writeDate = writeDate;
	}

	public int getBoardNO() {
		return boardNO;
	}

	public void setBoardNO(int boardNO) {
		this.boardNO = boardNO;
	}

	public int getParentNO() {
		return parentNO;
	}

	public void setParentNO(int parentNO) {
		this.parentNO = parentNO;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

}
