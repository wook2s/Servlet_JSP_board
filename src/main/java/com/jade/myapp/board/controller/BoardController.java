package com.jade.myapp.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jade.myapp.board.VO.BoardVO;
import com.jade.myapp.board.service.BoardService;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {

	BoardService boardService;
	private static final String FILE_PATH = "C:\\file_repo";

	@Override
	public void init() throws ServletException {
		boardService = new BoardService();
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

		if (action == null || action.equals("/list") || action.equals("/")) {
			List<BoardVO> boardList = boardService.getAllBoardList();
			System.out.println("리스트 길이 : " + boardList.size());
			request.setAttribute("boardList", boardList);
			nextPage = "/view/boardList.jsp";
		}

		else if (action.equals("/detail")) {
			String _boardNO = request.getParameter("boardNO");
			int boardNO = Integer.parseInt(_boardNO);
			BoardVO board = boardService.getBoard(boardNO);

			request.setAttribute("board", board);
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
						System.out.println("변수 이름 : " + fileItem.getFieldName());
						System.out.println("파일 이름 : " + fileItem.getName());
						if (fileItem.getName().length() > 0) {
							fileName = fileItem.getName();
						}
						System.out.println("파일 크기 : " + fileItem.getSize());
						File fileForPathAndName = new File(filePath + File.separator + fileItem.getName());
						fileItem.write(fileForPathAndName);
					} else {
						if (fileItem.getFieldName().equals("id")) {
							id = fileItem.getString("UTF-8");
						} else if (fileItem.getFieldName().equals("title")) {
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

			BoardVO board = new BoardVO(-1, maxNum, 0, title, content, fileName, id);
			boardService.insertBoard(board);
			nextPage = "/board/list";

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
						System.out.println("변수 이름 : " + fileItem.getFieldName());
						System.out.println("파일 이름 : " + fileItem.getName());
						System.out.println("파일 크기 : " + fileItem.getSize());
						
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

			boardService.deleteBoard(boardNO);
			nextPage = "/board/list";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
