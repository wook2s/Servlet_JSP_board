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
	
	public List<BoardVO> getAllBoardList(int page) {
		
		List<BoardVO> boardList = new ArrayList<>();
		String sql="    select * from( "
				+ "    select * from "
				+ "        (select rownum rnum, board_no, parent_no, title, image_name, write_date, id, content from "
				+ "            (select * from t_board order by board_no desc) order by board_no desc) b "
				+ "            left join \r\n"
				+ "            (select board_no, count(*) recnt from t_reply group by board_no) r"
				+ "    on b.board_no = r.board_no "
				+ "    order by b.board_no desc) "
				+ "    where rnum between ? and ?";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1+((page-1)*10));
			pstmt.setInt(2, page*10);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardVO board = new BoardVO(
						rs.getInt("BOARD_NO"),
						rs.getInt("PARENT_NO"),
						rs.getString("TITLE"), 
						rs.getString("CONTENT"),
						rs.getString("IMAGE_NAME"), 
						rs.getString("ID"),
						rs.getDate("WRITE_DATE"),
						rs.getInt("RECNT")
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
		String sql="SELECT NVL(MAX(BOARD_NO),0) FROM T_BOARD";
		
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

	public int getCountBoardNO() {
		int cnt = -1;
		String sql="SELECT NVL(COUNT(BOARD_NO),0) FROM T_BOARD";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("getMaxBoardNO() 에러");
		}
		
		return cnt;
	}

}
















