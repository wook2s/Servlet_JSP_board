package com.jade.myapp.board.service;

import java.util.List;

import com.jade.myapp.board.VO.BoardVO;
import com.jade.myapp.board.dao.BoardDAO;

public class BoardService {

	BoardDAO boardDAO;
	
	public BoardService() {
		boardDAO = new BoardDAO();
	}
	
	public List<BoardVO> getAllBoardList(int page) {
		List<BoardVO> boardList = boardDAO.getAllBoardList(page);
		return boardList;
	}

	public BoardVO getBoard(int boardNO) {
		BoardVO board = boardDAO.getBoard(boardNO);
		return board;
	}

	public int getMaxBoardNO() {
		int maxNum = boardDAO.getMaxBoardNO();
		return maxNum;
	}

	public void insertBoard(BoardVO board) {
		boardDAO.insertBoard(board);
	}

	public void updateBoard(BoardVO board) {
		boardDAO.updateBoard(board);
	}

	public void deleteBoard(int boardNO) {
		boardDAO.deleteBoard(boardNO);
	}

	public int getCountBoardNO() {
		int cnt = boardDAO.getCountBoardNO() ;
		return cnt;
	}

}
