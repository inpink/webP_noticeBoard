package boardMC;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;


//MVC의 Controller의 모든 역할을 이 하나의 파일에! 
//원래는 Controller도 분리할 수 있으면 하는게 좋긴 하다

@WebServlet("/upload_notice")
public class NoticeController extends HttpServlet {

	
	 public String addNews(HttpServletRequest request) {
			
			
			return "redirect:/upload_notice?action=listNews";
			
	}
	 
	 
	 public String fixNews(HttpServletRequest request) {
			
			
			return "redirect:/upload_notice?action=listNews";
			
	} 
	 
	
	 public String listNews(HttpServletRequest request) {
	    	
		 
	    	return "board/boardlists.jsp";
	    }
	    
}