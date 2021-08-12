package com.jade.myapp.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jade.myapp.board.VO.BoardVO;

public class BoardDAO {

	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<BoardVO> getAllBoardList() {
		
		List<BoardVO> boardList = new ArrayList<>();
		String sql="SELECT LEVEL, BOARD_NO, PARENT_NO, TITLE, CONTENT, IMAGE_NAME, ID, WRITE_DATE FROM T_BOARD "
				+ " START WITH  PARENT_NO=0" + " CONNECT BY PRIOR BOARD_NO=PARENT_NO "
				+ " ORDER SIBLINGS BY BOARD_NO DESC";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO board = new BoardVO(
						rs.getInt("LEVEL"), 
						rs.getInt("BOARD_NO"),
						rs.getInt("PARENT_NO"),
						rs.getString("TITLE"), 
						rs.getString("CONTENT"),
						rs.getString("IMAGE_NAME"), 
						rs.getString("ID"),
						rs.getDate("WRITE_DATE")
						);
				
				boardList.add(board);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("getAllBoardList() 에러");
			e.printStackTrace();
		}
		return boardList;
	}

	public BoardVO getBoard(int boardNO) {
		BoardVO board = null;
		String sql="SELECT * FROM T_BOARD WHERE BOARD_NO=?";
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNO);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new BoardVO(
						-1, 
						rs.getInt("BOARD_NO"), 
						rs.getInt("PARENT_NO"), 
						rs.getString("TITLE"), 
						rs.getString("CONTENT"), 
						rs.getString("IMAGE_NAME"), 
						rs.getString("ID"),
						rs.getDate("WRITE_DATE")
						);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("getBoard() 에러");
			e.printStackTrace();
		}
		
		return board;
	}

	public int getMaxBoardNO() {
		int maxNum = -1;
		String sql="SELECT NVL(COUNT(*),0) FROM T_BOARD";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				maxNum = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("getMaxBoardNO() 에러");
		}
		
		return maxNum;
	}

	public void insertBoard(BoardVO board) {
		String sql = "INSERT INTO T_BOARD (BOARD_NO, PARENT_NO, TITLE, CONTENT, IMAGE_NAME, ID) "
				+ "VALUES "
				+ "(?,?,?,?,?,?)";
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,board.getBoardNO());
			pstmt.setInt(2,board.getParentNO());
			pstmt.setString(3, board.getTitle());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getImageName());
			pstmt.setString(6, board.getId());
			
			pstmt.executeUpdate();

			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println(" insertBoard(BoardVO board) 에러");
			e.printStackTrace();
		}
	}

	public void updateBoard(BoardVO board) {
		String sql = "UPDATE T_BOARD SET BOARD_NO=?, PARENT_NO=?, TITLE=?, CONTENT=?, IMAGE_NAME=?, ID=? "
				+ "WHERE BOARD_NO=?";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,board.getBoardNO());
			pstmt.setInt(2,board.getParentNO());
			pstmt.setString(3, board.getTitle());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getImageName());
			pstmt.setString(6, board.getId());
			pstmt.setInt(7,board.getBoardNO());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.err.println(" updateBoard(BoardVO board) 에러");
			e.printStackTrace();
		}
	}

	public void deleteBoard(int boardNO) {
		String sql="DELETE T_BOARD WHERE BOARD_NO = ?";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,boardNO);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("deleteBoard(int boardNO) 에러");
			e.printStackTrace();
		}
	}

}
















