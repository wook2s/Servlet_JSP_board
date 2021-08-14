package com.jade.myapp.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jade.myapp.board.VO.ReplyVO;

public class ReplyDAO {

	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	
	public ReplyDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ReplyVO> getReplyList(int boardNO) {

		List<ReplyVO> replyList = new ArrayList<>();
		
		String sql="SELECT * FROM T_REPLY WHERE BOARD_NO = ? ";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNO);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyVO reply = new ReplyVO(
						rs.getInt("REPLY_NO"),
						rs.getInt("BOARD_NO"),
						rs.getString("ID"),
						rs.getString("CONTENT"),
						rs.getDate("WRITE_DATE")
						);
				
				replyList.add(reply);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("getReplyList(int boardNO) 에러");
			e.printStackTrace();
		}
		return replyList;
	}


	public int getMaxReplyNO() {
		int maxNum = -1;
		String sql="SELECT NVL(MAX(REPLY_NO),0) FROM T_REPLY";
		
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
			System.err.println("getMaxReplyNO() 에러");
		}
		return maxNum;
	}

	public void addReply(ReplyVO reply) {
		String sql = "INSERT INTO T_REPLY (REPLY_NO, ID, CONTENT, BOARD_NO) "
				+ "VALUES "
				+ "(?,?,?,?)";
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,reply.getReplyNO());
			pstmt.setString(2, reply.getId());
			pstmt.setString(3, reply.getContent());
			pstmt.setInt(4,reply.getBoardNO());
			
			pstmt.executeUpdate();

			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println(" insertBoard(BoardVO board) 에러");
			e.printStackTrace();
		}
		
	}

	public void deleteReply(Integer replyNO) {
		String sql="DELETE T_REPLY WHERE REPLY_NO = ?";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,replyNO);
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("deleteReply(Integer replyNO) 에러");
			e.printStackTrace();
		}
		
	}

	public ReplyVO getReply(int prevReplyNO) {
		ReplyVO reply = null;
		String sql="SELECT * FROM T_REPLY WHERE REPLY_NO=?";
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, prevReplyNO);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				reply = new ReplyVO(
						rs.getInt("REPLY_NO"),
						rs.getInt("BOARD_NO"),
						rs.getString("ID"),
						rs.getString("CONTENT"),
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
		
		return reply;
	}

	public void updateReply(ReplyVO reply) {
		String sql = "UPDATE T_REPLY SET REPLY_NO =?, BOARD_NO = ?, ID = ?, CONTENT = ?, WRITE_DATE = ? WHERE REPLY_NO= ? ";
		
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, reply.getReplyNO());
			pstmt.setInt(2, reply.getBoardNO());
			pstmt.setString(3, reply.getId());
			pstmt.setString(4, reply.getContent());
			pstmt.setDate(5, reply.getWriteDate());
			pstmt.setInt(6,reply.getReplyNO());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.err.println("updateReply(ReplyVO reply) 에러");
			e.printStackTrace();
		}
	}
	
	
}
