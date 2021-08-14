package com.jade.myapp.board.service;

import com.jade.myapp.board.dao.MemberDAO;

public class MemberService {

	MemberDAO memberDAO;
	
	public MemberService() {
		memberDAO = new MemberDAO();
	}
	
	public boolean checkMember(String id, String pwd) {
		boolean checkMember = memberDAO.checkMember(id, pwd);
		return checkMember;
	}

}
