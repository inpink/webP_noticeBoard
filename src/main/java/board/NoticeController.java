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

//MVC�� Controller�� ��� ������ �� �ϳ��� ���Ͽ�! 
//������ Controller�� �и��� �� ������ �ϴ°� ���� �ϴ�

//��� method ���
//void init(): �� servlet �ּҷ� ��û ������ ���� 1ȸ �ڵ� ����. �̱��� DAO, servletcontext ��ü ����
//void service(): �� servlet �ּҷ� ��û ������ 1ȸ �ڵ� ����. �Ķ���� �Ľ��ؼ� �Ʒ� �� �ش�Ǵ� Controller method ��������
//String addNotice(request)  : �Ķ���� �Ľ��ؼ� Notice n ��ü�� �������. dao.addNotice(n); �����ؼ� db���� �ش� ��ü ������ �߰��ϵ���.
   // ���� �߻��� ���� �߻���Ŵ. ������ ���ȭ��(boardlist.jsp)���� �̵�
//String fixNews(request) : addNotice�� �ſ� �����ѵ�  dao.fixNotice(n) �� �ٸ�
//String listNotice(request) :  dao.getAll() �����ؼ� db���� ��� Notice ��ü �޾ƿͼ� List<Notice>�� �����ؼ� request�� ����. 
   // ���� �߻� �� ���� ȭ�� ������ ����. ������ ���ȭ��(boardlist.jsp)���� �̵�
//String getNotice(request) : listNotice�� �ſ� �����ѵ�, dao.getNotice() �����ؼ� Notice ��ü 1���� �޾ƿ��°� �ٸ�.
   // ���� �߻� �� ���� ȭ�� ������ ����. ������ �ټ���ȭ��(edit.jsp)���� �̵�

@WebServlet("/upload_notice")
public class NoticeController extends HttpServlet {

	private static final long serialVersionUID = 1L; //java data�� ������ �� ����ȭ�� �ϰ� ��. ����ȭ/������ȭ �� ����� UID�� �����������
	
	private NoticeDAO dao;
	private ServletContext ctx; //���� �����̳ʿ� ����ϱ� ���ؼ� ���Ǵ� �޼ҵ带 �����ϴ� �������̽�

	// �� ���ҽ� �⺻ ��� ����
	private final String START_PAGE = "board/boardlists.jsp";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new NoticeDAO();
		ctx = getServletContext();		
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		
		dao = new NoticeDAO();
		
		// �ڹ� ���÷����� ����� if, switch ���� ��û�� ���� ���� �޼��尡 ����ǵ��� ��.
		Method m;
		String view = null;
		
		// action �Ķ���� ���� ������ ���
		if (action == null) {
			action = "listNotice";
		}
		
		try {
			// ���� Ŭ�������� action �̸��� HttpServletRequest �� �Ķ���ͷ� �ϴ� �޼��� ã��
			m = this.getClass().getMethod(action, HttpServletRequest.class);
			
			// �޼��� ������ ���ϰ� �޾ƿ�
			view = (String)m.invoke(this, request);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			// ���� �α׸� ����� view �� �α��� ȭ������ ����, �տ����� ���� redirection ��뵵 ����.
			ctx.log("��û action ����!!");
			request.setAttribute("error", "action �Ķ���Ͱ� �߸� �Ǿ����ϴ�!!");
			view = START_PAGE;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		// POST ��û ó���Ŀ��� ���𷺼� ������� �̵� �� �� �־�� ��.
		if(view.startsWith("redirect:/")) {
			// redirect/ ���ڿ� ���� ��θ� ������ ��
			String rview = view.substring("redirect:/".length());
			response.sendRedirect(rview);
		} else {
			// ������ ��� ������, �������� ���ؽ�Ʈ��δ� �ʿ����.
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);	
		}
	}
	
	public String addNotice(HttpServletRequest request) {
		Notice n = new Notice();
		try {						
	        // �Է°��� Notice ��ü�� ����
			BeanUtils.populate(n, request.getParameterMap());
			System.out.println("Controller addNotice n.getAid()"+n.getAid());
			dao.addNotice(n);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("���� �߰� �������� ���� �߻�!");
			request.setAttribute("error", "������ ���������� ��ϵ��� �ʾҽ��ϴ�!!");
			return listNotice(request);
		}
		
		return "redirect:/upload_notice?action=listNotice"; //��� �Ϸ������� ������� �̵� listNotice
	}
	 
	 
	 public String fixNotice(HttpServletRequest request) {
		 	Notice n = new Notice();
		 	try {						
		        // �Է°��� Notice ��ü�� ����
				BeanUtils.populate(n, request.getParameterMap());
				System.out.println("Controller fixNotice n.getAid()"+n.getAid());
				dao.fixNotice(n);
			} catch (Exception e) {
				e.printStackTrace();
				ctx.log("���� ���� �������� ���� �߻�!");
				request.setAttribute("error", "������ ���������� �������� �ʾҽ��ϴ�!!");
				return listNotice(request);
			}
			
			return "redirect:/upload_notice?action=listNotice"; //��� �Ϸ������� ������� �̵� listNotice
			
	} 
	 
	
	 public String listNotice(HttpServletRequest request) {
		 	List<Notice> list;
			try {
				list = dao.getAll();
		    	request.setAttribute("noticelist", list); //jsp���� ����� �̸��� noticelist
			} catch (Exception e) {
				e.printStackTrace();
				ctx.log("���� ��� ���� �������� ���� �߻�!!");
				request.setAttribute("error", "���� ����� ���������� ó������ �ʾҽ��ϴ�!!");
			}
		 
	    	return "board/boardlists.jsp"; //��� Notice ����� request�� ��ܼ� boardlist ������� ���޵�
	 }
	 
	 public String getNotice(HttpServletRequest request) {
	    
		 int aid = Integer.parseInt(request.getParameter("aid"));
		 
	        try {
	        	Notice n = dao.getNotice(aid);
				request.setAttribute("notice", n); //jsp���� ����� �̸��� notice
			} catch (SQLException e) {
				e.printStackTrace();
				ctx.log("���� 1���� �������� �������� ���� �߻�!!");
				request.setAttribute("error", "���� 1���� ���������� �������� ���߽��ϴ�!!");
			}

		 
		 return "board/edit.jsp"; //���� ���� Ŭ���ϸ� ���� ȭ������ �̵���
	 }
}