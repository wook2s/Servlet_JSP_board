package com.jade.myapp.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jade.myapp.board.VO.BoardVO;
import com.jade.myapp.board.VO.ReplyVO;
import com.jade.myapp.board.service.BoardService;
import com.jade.myapp.board.service.MemberService;
import com.jade.myapp.board.service.ReplyService;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {

	BoardService boardService;
	ReplyService replyService;
	MemberService memberService;
	
	private static final String FILE_PATH = "C:\\file_repo";

	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
		replyService = new ReplyService();
		memberService = new MemberService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getPathInfo();
		String nextPage = "/view/boardList.jsp";
		System.out.println("요청 주소 : " + action);

		HttpSession session = request.getSession();
		
		//----------------로그인-------------------
		
		if(action.equals("/loginForm")) {
			nextPage = "/member/signin.jsp";
		}
		
		else if(action.equals("/login")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			boolean checkMember = memberService.checkMember(id, pwd);
			
			System.out.println("로그인 결과 : "+checkMember);
			if(checkMember == true) {
				session.setAttribute("id", id);
				response.sendRedirect("/Servlet_JSP_Board/board/list");
				return;
			}else if(checkMember == false){
				response.sendRedirect("/Servlet_JSP_Board/board/loginForm");
				return;
			}
		}else if(action.equals("/logout")) {
			session.removeAttribute("id");
			response.sendRedirect("/Servlet_JSP_Board/board/list");
			return;
		}
		//----------------로그인-------------------
		else if (action == null || action.equals("/list") || action.equals("/")) {

			Integer page = 1;
			
			String _page = request.getParameter("page");
			System.out.println("_page : "+_page);
			if(_page !=null) {
				page = Integer.valueOf(_page);
			}
			System.out.println("page : "+page);
			
			Integer boardCount = boardService.getCountBoardNO();
			Integer lastPage =0;
			if(boardCount %10 ==0) {
				lastPage = boardCount/10;
			}else {
				lastPage = boardCount/10 +1;
			}
			System.out.println("lastPage : "+lastPage);
			System.out.println("boardCount : "+boardCount);
			String id = (String) session.getAttribute("id");
			
			request.setAttribute("id", id);
			request.setAttribute("page", page);
			request.setAttribute("lastPage", lastPage);
			request.setAttribute("boardCount", boardCount);
			
			
			List<BoardVO> boardList = boardService.getAllBoardList(page);
			request.setAttribute("boardList", boardList);
			nextPage = "/view/boardList.jsp";
		}

		else if (action.equals("/detail")) {
			
			String id = (String)session.getAttribute("id");
			if( id == null || id.equals("")) {
				response.sendRedirect("/Servlet_JSP_Board/board/loginForm");
				return;
			}
			request.setAttribute("id",id);
			
			String _boardNO = request.getParameter("boardNO");
			int boardNO = Integer.parseInt(_boardNO);
			BoardVO board = boardService.getBoard(boardNO);

			List<ReplyVO> replyList = replyService.getReplyList(boardNO);
			
			request.setAttribute("board", board);
			request.setAttribute("replyList", replyList);
			nextPage = "/view/boardDetail.jsp";
			
		} else if (action.equals("/downloadImage")) {
			String imageName = request.getParameter("imageName");
			String boardNO = request.getParameter("boardNO");
			System.out.println("imageName : " + imageName);
			System.out.println("boardNO : " + boardNO);

			File filedownPath = new File(FILE_PATH + File.separator + boardNO + File.separator + imageName);
			System.out.println("file path : " + filedownPath.getName());

			response.setHeader("Cache=Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; fileName=" + imageName);

			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(filedownPath);
			byte[] buffer = new byte[1024];

			while (true) {
				int cnt = in.read(buffer);
				if (cnt == -1) {
					break;
				}
				out.write(buffer, 0, cnt);
			}
			in.close();
			out.close();
			return;
		}

		else if (action.equals("/insertForm")) {
			String id = (String)session.getAttribute("id");
			if( id == null || id.equals("")) {
				response.sendRedirect("/Servlet_JSP_Board/board/loginForm");
				return;
			}
			request.setAttribute("id",id);
			nextPage = "/view/boardInsert.jsp";
			
		} else if (action.equals("/insertBoard")) {

			String id = "";
			String content = "";
			String title = "";
			String fileName = "";

			int maxNum = boardService.getMaxBoardNO() + 1;
			System.out.println("maxNum : " + maxNum);
			String filePath = FILE_PATH + File.separator + maxNum;
			File fileForPath = new File(filePath); // 경로 폴더
			if (!fileForPath.exists()) { // 경로 생성
				fileForPath.mkdirs();
			}
			DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024, fileForPath);
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				List items = upload.parseRequest(request);
				for (int i = 0; i < items.size(); i++) {
					FileItem fileItem = (FileItem) items.get(i);
					if (!fileItem.isFormField() && fileItem.getName().length() > 0) {
						if (fileItem.getName().length() > 0) {
							fileName = fileItem.getName();
						}
						File fileForPathAndName = new File(filePath + File.separator + fileItem.getName());
						fileItem.write(fileForPathAndName);
					} else {
						if (fileItem.getFieldName().equals("id")) {
							id = fileItem.getString("UTF-8");
							System.out.println("id : " +id);
						} else if (fileItem.getFieldName().equals("title")) {
							title = fileItem.getString("UTF-8");
							System.out.println("title : " +title);
						} else if (fileItem.getFieldName().equals("content")) {
							content = fileItem.getString("UTF-8");
							System.out.println("content : " +content);
						}

					}
				}
			} catch (Exception e) {
				System.err.println("파일 업로드 실패");
				e.printStackTrace();
			}

			BoardVO board = new BoardVO(maxNum, 0, title, content, fileName, id);
			boardService.insertBoard(board);
			response.sendRedirect("/Servlet_JSP_Board/board/list");
			return;

		} else if (action.equals("/modifyForm")) {
			String _boardNO = request.getParameter("boardNO");
			System.out.println(_boardNO);
			int boardNO = Integer.parseInt(_boardNO);
			BoardVO board = boardService.getBoard(boardNO);
			request.setAttribute("board", board);
			request.getSession().setAttribute("board", board); // 수정 정보를 세션이 임시 저장...
			nextPage = "/view/boardModify.jsp";
			
		} else if (action.equals("/modifyBoard")) {
			
			BoardVO preBoard = (BoardVO) request.getSession().getAttribute("board");
			request.getSession().removeAttribute("board");
			
			String content = preBoard.getContent();
			String title = preBoard.getTitle();
			String imageName = preBoard.getImageName();
			if(imageName == null) {
				imageName ="";
			}
			int boardNO = preBoard.getBoardNO();
			
			String filePath = FILE_PATH + File.separator + boardNO;
			File fileForPath = new File(filePath); // 경로 폴더
			if (!fileForPath.exists()) { // 경로 생성
				fileForPath.mkdirs();
			}
			DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 1024, fileForPath);
			ServletFileUpload upload = new ServletFileUpload(factory);

			try {
				List items = upload.parseRequest(request);
				for (int i = 0; i < items.size(); i++) {
					FileItem fileItem = (FileItem) items.get(i);
					
					if (!fileItem.isFormField() && fileItem.getName().length() > 0) {
						String tmpImageName = "";
						if (fileItem.getName().length() > 0) {
							tmpImageName = fileItem.getName();
						}
						System.out.println("기존 파일 이름 : "+imageName);
						System.out.println("신규 파일 이름 : "+tmpImageName);
						
						if(!imageName.equals(tmpImageName)) { // 기존파일과 다르면 기존파일 삭제 후 새파일 추가
							File preFile = new File(filePath);
							if(preFile.isDirectory()) {
								File[] files = preFile.listFiles();
								for(File f : files) {
									f.delete();
								}
							}
							File fileForPathAndName = new File(filePath + File.separator + fileItem.getName());//신규파일 경로 지정 후
							fileItem.write(fileForPathAndName); //신규파일 추가
							imageName = tmpImageName; // 신규파일로 파일명 변경
						}
						
					} else {
						if (fileItem.getFieldName().equals("title")) {
							title = fileItem.getString("UTF-8");
						} else if (fileItem.getFieldName().equals("content")) {
							content = fileItem.getString("UTF-8");
						}
					}
				}
			} catch (Exception e) {
				System.err.println("파일 업로드 실패");
				e.printStackTrace();
			}
			BoardVO board = boardService.getBoard(boardNO); //기존파일 호출

			board.setTitle(title);
			board.setContent(content);
			board.setImageName(imageName); // 기존파일 수정
			boardService.updateBoard(board);
			nextPage = "/board/list";
			
		} else if (action.equals("/deleteBoard")) {
			String _boardNO = request.getParameter("boardNO");
			int boardNO = Integer.parseInt(_boardNO);

			File deleteFilePath = new File(FILE_PATH + File.separator + boardNO); //  폴더 경로

			if (deleteFilePath.exists()) {
				if (deleteFilePath.isDirectory()) {
					File[] files = deleteFilePath.listFiles();
					for (int i = 0; i < files.length; i++) {
						if (files[i].delete()) {
							System.out.println(files[i].getName() + " 삭제성공");
						} else {
							System.out.println(files[i].getName() + " 삭제실패");
						}
					}
				}
				if (deleteFilePath.delete()) {
					System.out.println("파일삭제 성공");
				} else {
					System.out.println("파일삭제 실패");
				}
			} else {
				System.out.println("파일이 존재하지 않습니다.");
			}
			replyService.deleteReplyByBoardNO(boardNO);
			boardService.deleteBoard(boardNO);
			nextPage = "/board/list";
		}
		else if(action.equals("/addReply")) {
			if((String)session.getAttribute("id") == null) {
				response.sendRedirect("/Servlet_JSP_Board/board/loginForm");
				return;
			}
			String _boardNO = request.getParameter("boardNO");
			Integer boardNO = Integer.valueOf(_boardNO);
			String id = request.getParameter("id");
			String content = request.getParameter("content");
			int replyNO = replyService.getMaxReplyNO()+1;
			
			ReplyVO reply = new ReplyVO(replyNO, boardNO, id, content);
			replyService.addReply(reply);
			
			nextPage = "/board/detail?boardNO="+_boardNO;
			
		}
		else if(action.equals("/deleteReply")) {
			String _boardNO = request.getParameter("boardNO");
			String _replyNO = request.getParameter("replyNO");
			Integer replyNO = Integer.valueOf(_replyNO);
			
			replyService.deleteReply(replyNO);
			
			nextPage = "/board/detail?boardNO="+_boardNO;
			
		}else if(action.equals("/modifyReplyForm")) {
			String _boardNO = request.getParameter("boardNO");
			int boardNO = Integer.parseInt(_boardNO);

			String _replyNO = request.getParameter("replyNO");
			int replyNO = Integer.parseInt(_replyNO);
			
			BoardVO board = boardService.getBoard(boardNO);
			List<ReplyVO> replyList = replyService.getReplyList(boardNO);
			
			request.setAttribute("board", board);
			request.setAttribute("replyList", replyList);
			request.setAttribute("replyNO", replyNO);
			System.out.println(replyNO);
			System.out.println(boardNO);
			
			session = request.getSession(); 
			session.setAttribute("replyNO", replyNO);
			session.setAttribute("boardNO", boardNO);
			
			nextPage = "/view/boardModifyForReply.jsp";
		}
		else if(action.equals("/modifyReply")) {
			
			System.out.println(request.getParameter("content"));
			session = request.getSession();
			
			Integer replyNO = (Integer) session.getAttribute("replyNO");
			Integer boardNO = (Integer) session.getAttribute("boardNO");
			
			System.out.println(replyNO);
			System.out.println(boardNO);
			
			request.getSession().removeAttribute("boardNO");
			request.getSession().removeAttribute("replyNO");
			
			ReplyVO reply = replyService.getReply(replyNO);
			reply.setContent(request.getParameter("content"));
			
			System.out.println(reply.toString());
			
			replyService.updateReply(reply);
			
			nextPage = "/board/detail?boardNO="+boardNO;
			
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
