package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Data Access Object
//DB에 접근하는 로직 분리
//DAO는 MVC 중 Model에 해당 

//사용 method 요약
//Connection open() :  DB 연결을 가져오는 메서드, DB 정보 담은 Connection 객체 반환
//List<Notice> getAll() : 모든 Notice들를 Notice 객체에 담아 List<Notice>형태로 가져옴
//Notice getNotice (int aid) : aid와 일치하는 Notice 1개를 Notice 객체로 반환함
//void addNotice (Notice n) : News 객체 n을 Db에 넣음

//미사용
//void delNotice(int aid) : aid와 일치하는 Notice를 삭제하는 sql문 실행, 일치하는 aid가 DB에 없으면 에러 발생 

public class NoticeDAO {
	
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";
	
	// DB 연결을 가져오는 메서드, DBCP를 사용하는 것이 좋음
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL,"jwbookdb","1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List<Notice> getAll() throws Exception {
		Connection conn = open();
		List<Notice> NoticeList = new ArrayList<>();
		
		//TODO 
		//그냥 date써도 문제없어 보이는데 / PARSEDATETIME는 string to Date 라고 함
		//형식 0000-00-00로 변경하려면 오른쪽 sql 사용 : select aid, name,email,FORMATDATETIME(DATE, 'yyyy-MM-dd') as cdate  ,title,pwd,content  from notices
		String sql = "select aid, name,email,PARSEDATETIME(date,'yyyy-MM-dd hh:mm:ss') as cdate ,title,pwd,content  from notices";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs) {
			while(rs.next()) {
				//TODO "방명록 목록"에서 필요한 데이터는 aid, name, email, date, title임. 일단 다 넣어줌
				Notice n = new Notice();
				n.setAid(rs.getInt("aid"));
				n.setName(rs.getString("name"));
				n.setEmail(rs.getString("email")); 
				n.setDate(rs.getString("cdate")); 
				n.setTitle(rs.getString("title"));
				n.setPwd(rs.getString("pwd")); //불필요할지도
				n.setContent(rs.getString("content")); //불필요할지도
				
				NoticeList.add(n);
			}
			return NoticeList;			
		}
	}
	
	public Notice getNotice(int aid) throws SQLException {
		Connection conn = open();
		
		Notice n = new Notice();
		
		//마찬가지로 date 0000-00-00로 받아오려면 FORMATDATETIME(DATE, 'yyyy-MM-dd') 사용
		String sql = "select aid, name,email,PARSEDATETIME(date,'yyyy-MM-dd hh:mm:ss') as cdate ,title,pwd,content  from notices where aid=?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid); //?에 aid 넣기
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		
		try(conn; pstmt; rs) {
			//"게시글 수정"할 때 필요한 데이터 (전부 다 필요)
			n.setAid(rs.getInt("aid"));
			n.setName(rs.getString("name"));
			n.setEmail(rs.getString("email")); 
			n.setDate(rs.getString("cdate")); 
			n.setTitle(rs.getString("title"));
			n.setPwd(rs.getString("pwd")); 
			n.setContent(rs.getString("content")); 
			pstmt.executeQuery();
			return n;
		}
	}
	
	public void addNotice(Notice n) throws Exception {
		Connection conn = open();
		
		String sql = "insert into Notices(name,email,date,title,pwd,content) values(?,?,CURRENT_TIMESTAMP(),?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try(conn; pstmt) {
			pstmt.setString(1, n.getName());
			pstmt.setString(2, n.getEmail());
			pstmt.setString(3, n.getTitle());
			pstmt.setString(4, n.getPwd());
			pstmt.setString(5, n.getContent());
			
			pstmt.executeUpdate();
		}
	}
	
	public void fixNotice(Notice n) throws Exception {
		Connection conn = open();
		
		String sql = "update notices set EMAIL=?, date=FORMATDATETIME(CURRENT_TIMESTAMP(),'yyyy-MM-dd'), title=?, pwd=?, content=? where aid=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
		try(conn; pstmt) {
			
			Notice orinN= getNotice(n.getAid());
			String orinPwd= orinN.getPwd();
			String newPwd= n.getPwd();
			
			if (! orinPwd.equals(newPwd)) throw new Exception("비밀번호가 맞지 않습니다!");
			
			pstmt.setString(1, n.getEmail());
			pstmt.setString(2,  n.getTitle());
			pstmt.setString(3,n.getPwd());
			pstmt.setString(4, n.getContent());
			pstmt.setInt(5, n.getAid());
			
			pstmt.executeUpdate();
		}
	}
	 
			
}