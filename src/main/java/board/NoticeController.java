package board;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

//MVC의 Controller의 모든 역할을 이 하나의 파일에! 
//원래는 Controller도 분리할 수 있으면 하는게 좋긴 하다

//사용 method 요약
//void init(): 이 servlet 주소로 요청 들어오면 최초 1회 자동 실행. 싱글톤 DAO, servletcontext 객체 만듦
//void service(): 이 servlet 주소로 요청 들어오면 1회 자동 실행. 파라미터 파싱해서 아래 중 해당되는 Controller method 실행해줌
//String addNotice(request)  : 파라미터 파싱해서 Notice n 객체로 만들어줌. dao.addNotice(n); 실행해서 db에서 해당 객체 데이터 추가하도록.
   // 문제 발생시 예외 발생시킴. 성공시 목록화면(boardlist.jsp)으로 이동
//String fixNews(request) : addNotice와 매우 유사한데  dao.fixNotice(n) 만 다름
//String listNotice(request) :  dao.getAll() 실행해서 db에서 모든 Notice 객체 받아와서 List<Notice>에 저장해서 request에 담음. 
   // 문제 발생 시 에러 화면 뜸으로 예상. 성공시 목록화면(boardlist.jsp)으로 이동
//String getNotice(request) : listNotice와 매우 유사한데, dao.getNotice() 실행해서 Notice 객체 1개만 받아오는게 다름.
   // 문제 발생 시 에러 화면 뜸으로 예상. 성공시 ☆수정화면(edit.jsp)으로 이동

@WebServlet("/upload_notice")
public class NoticeController extends HttpServlet {

	private static final long serialVersionUID = 1L; //java data를 내보낼 때 직렬화를 하게 됨. 직렬화/역직렬화 때 사용할 UID를 지정해줘야함
	
	private NoticeDAO dao;
	private ServletContext ctx; //서블릿 컨테이너와 통신하기 위해서 사용되는 메소드를 지원하는 인터페이스

	// 웹 리소스 기본 경로 지정
	private final String START_PAGE = "board/boardslists.jsp";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new NoticeDAO();
		ctx = getServletContext();		
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		dao = new NoticeDAO();
		
		// 자바 리플렉션을 사용해 if, switch 없이 요청에 따라 구현 메서드가 실행되도록 함.
		Method m;
		String view = null;
		
		// action 파라미터 없이 접근한 경우
		if (action == null) {
			action = "listNotice";
		}
		
		try {
			// 현재 클래스에서 action 이름과 HttpServletRequest 를 파라미터로 하는 메서드 찾음
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			
			// 메서드 실행후 리턴값 받아옴
			view = (String)m.invoke(this, request);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			// 에러 로그를 남기고 view 를 로그인 화면으로 지정, 앞에서와 같이 redirection 사용도 가능.
			ctx.log("요청 action 없음!!");
			request.setAttribute("error", "action 파라미터가 잘못 되었습니다!!");
			view = START_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// POST 요청 처리후에는 리디렉션 방법으로 이동 할 수 있어야 함.
		if(view.startsWith("redirect:/")) {
			// redirect/ 문자열 이후 경로만 가지고 옴
			String rview = view.substring("redirect:/".length());
			response.sendRedirect(rview);
		} else {
			// 지정된 뷰로 포워딩, 포워딩시 컨텍스트경로는 필요없음.
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);	
		}
	}
	
	public String addNotice(HttpServletRequest request) {
		Notice n = new Notice();
		try {						
	        // 입력값을 Notice 객체로 매핑
			BeanUtils.populate(n, request.getParameterMap());
			dao.addNotice(n);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("방명록 추가 과정에서 문제 발생!");
			request.setAttribute("error", "방명록이 정상적으로 등록되지 않았습니다!!");
			return listNotice(request);
		}
		
		return "redirect:/upload_notice?action=listNotice"; //등록 완료했으면 목록으로 이동 listNotice
	}
	 
	 
	 public String fixNews(HttpServletRequest request) {
		 	Notice n = new Notice();
		 	try {						
		        // 입력값을 Notice 객체로 매핑
				BeanUtils.populate(n, request.getParameterMap());
				
				dao.fixNotice(n);
			} catch (Exception e) {
				e.printStackTrace();
				ctx.log("방명록 수정 과정에서 문제 발생!");
				request.setAttribute("error", "방명록이 정상적으로 수정되지 않았습니다!!");
				return listNotice(request);
			}
			
			return "redirect:/upload_notice?action=listNotice"; //등록 완료했으면 목록으로 이동 listNotice
			
	} 
	 
	
	 public String listNotice(HttpServletRequest request) {
		 	List<Notice> list;
			try {
				list = dao.getAll();
		    	request.setAttribute("noticelist", list); //jsp에서 사용할 이름은 noticelist
			} catch (Exception e) {
				e.printStackTrace();
				ctx.log("방명록 목록 생성 과정에서 문제 발생!!");
				request.setAttribute("error", "방명록 목록이 정상적으로 처리되지 않았습니다!!");
			}
		 
	    	return "board/boardlists.jsp"; //모든 Notice 목록은 request에 담겨서 boardlist 목록으로 전달됨
	 }
	 
	 public String getNotice(HttpServletRequest request) {
	    
		 int aid = Integer.parseInt(request.getParameter("aid"));
		 
	        try {
	        	Notice n = dao.getNotice(aid);
				request.setAttribute("notice", n); //jsp에서 사용할 이름은 notice
			} catch (SQLException e) {
				e.printStackTrace();
				ctx.log("방명록 1개를 가져오는 과정에서 문제 발생!!");
				request.setAttribute("error", "방명록 1개를 정상적으로 가져오지 못했습니다!!");
			}

		 
		 return "board/edit.jsp"; //개별 방명록 클릭하면 수정 화면으로 이동함
	 }
}