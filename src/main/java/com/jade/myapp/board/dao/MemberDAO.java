package com.jade.myapp.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.jade.myapp.board.VO.MemberVO;

public class MemberDAO {

	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public boolean checkMember(String id, String pwd) {
		String sql="select * from t_member where id like ? ";
		boolean result = false;
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String _pwd = rs.getString("PWD");
				if(pwd.equals(_pwd)) {
					result = true;
				}
			}
			
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.err.println("checkMember(String id, String pwd) 에러");
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean addMember(MemberVO memberVO) {
		String sql="INSERT INTO T_MEMBER (ID, PWD, NAME, EMAIL) VALUES (?,?,?,?)";
		boolean result = false;
		try {
			conn = dataFactory.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberVO.getId());
			pstmt.setString(2, memberVO.getPwd());
			pstmt.setString(3, memberVO.getName());
			pstmt.setString(4, memberVO.getEmail());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
			result = true;
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

}
