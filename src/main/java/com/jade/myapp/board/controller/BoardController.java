package com.jade.myapp.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jade.myapp.board.VO.BoardVO;
import com.jade.myapp.board.service.BoardService;

@WebServlet("/board/*")
public class BoardController extends HttpServlet{

	BoardService boardService;
	
	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String action = request.getPathInfo();
		String nextPage = "/view/boardList.jsp";
		System.out.println("요청 주소 : "+action);
		
		if(action == null || action.equals("/list") || action.equals("/")) {
			List<BoardVO> boardList = boardService.getAllBoardList();
			System.out.println("리스트 길이 : "+boardList.size());
			request.setAttribute("boardList", boardList);
			nextPage = "/view/boardList.jsp";
		}
		
		else if(action.equals("/detail")) {
			String _boardNO = request.getParameter("boardNO");
			int boardNO = Integer.parseInt(_boardNO);
			BoardVO board = boardService.getBoard(boardNO);
			request.setAttribute("board", board);
			nextPage="/view/boardDetail.jsp";
		}
		
		else if(action.equals("/insertForm")) {
			nextPage = "/view/boardInsert.jsp";
		}
		else if(action.equals("/insertBoard")) {
			String title = request.getParameter("title");
			String id =request.getParameter("id");
			String content =request.getParameter("content");
			int maxNum = boardService.getMaxBoardNO()+1;
			BoardVO board = new BoardVO(-1, maxNum, 0, title, content, null, id);
			boardService.insertBoard(board);
			nextPage = "/board/list";
			
		}
		else if(action.equals("/modifyForm")) {
			String _boardNO = request.getParameter("boardNO");
			System.out.println(_boardNO);
			int boardNO = Integer.parseInt(_boardNO);
			BoardVO board = boardService.getBoard(boardNO);
			request.setAttribute("board", board);
			nextPage = "/view/boardModify.jsp";
		}
		else if(action.equals("/modifyBoard")) {
			String _boardNO = request.getParameter("boardNO");
			int boardNO = Integer.parseInt(_boardNO);
			String title = request.getParameter("title");
			String content =request.getParameter("content");

			BoardVO board = boardService.getBoard(boardNO);

			board.setTitle(title);
			board.setContent(content);

			boardService.updateBoard(board);
			nextPage = "/board/list";
		}
		else if(action.equals("/deleteBoard")) {
			String _boardNO = request.getParameter("boardNO");
			int boardNO = Integer.parseInt(_boardNO);
			boardService.deleteBoard(boardNO);
			nextPage = "/board/list";
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
		
	}
	
}

















