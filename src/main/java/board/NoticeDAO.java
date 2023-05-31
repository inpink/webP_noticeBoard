package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Data Access Object
//DB�� �����ϴ� ���� �и�
//DAO�� MVC �� Model�� �ش� 

//��� method ���
//Connection open() :  DB ������ �������� �޼���, DB ���� ���� Connection ��ü ��ȯ
//List<Notice> getAll() : ��� Notice�鸦 Notice ��ü�� ��� List<Notice>���·� ������
//Notice getNotice (int aid) : aid�� ��ġ�ϴ� Notice 1���� Notice ��ü�� ��ȯ��
//void addNotice (Notice n) : News ��ü n�� Db�� ����

//�̻��
//void delNotice(int aid) : aid�� ��ġ�ϴ� Notice�� �����ϴ� sql�� ����, ��ġ�ϴ� aid�� DB�� ������ ���� �߻� 

public class NoticeDAO {
	
	final String JDBC_DRIVER = "org.h2.Driver";
	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/jwbookdb";
	
	// DB ������ �������� �޼���, DBCP�� ����ϴ� ���� ����
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
		//�׳� date�ᵵ �������� ���̴µ� / PARSEDATETIME�� string to Date ��� ��
		//���� 0000-00-00�� �����Ϸ��� ������ sql ��� : select aid, name,email,FORMATDATETIME(DATE, 'yyyy-MM-dd') as cdate  ,title,pwd,content  from notices
		String sql = "select aid, name,email,PARSEDATETIME(date,'yyyy-MM-dd hh:mm:ss') as cdate ,title,pwd,content  from notices";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs) {
			while(rs.next()) {
				//TODO "���� ���"���� �ʿ��� �����ʹ� aid, name, email, date, title��. �ϴ� �� �־���
				Notice n = new Notice();
				n.setAid(rs.getInt("aid"));
				n.setName(rs.getString("name"));
				n.setEmail(rs.getString("email")); 
				n.setDate(rs.getString("cdate")); 
				n.setTitle(rs.getString("title"));
				n.setPwd(rs.getString("pwd")); //���ʿ�������
				n.setContent(rs.getString("content")); //���ʿ�������
				
				NoticeList.add(n);
			}
			return NoticeList;			
		}
	}
	
	public Notice getNotice(int aid) throws SQLException {
		Connection conn = open();
		
		Notice n = new Notice();
		
		//���������� date 0000-00-00�� �޾ƿ����� FORMATDATETIME(DATE, 'yyyy-MM-dd') ���
		String sql = "select aid, name,email,PARSEDATETIME(date,'yyyy-MM-dd hh:mm:ss') as cdate ,title,pwd,content  from notices where aid=?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid); //?�� aid �ֱ�
		System.out.println("getNotice 원래 aid"+aid+sql);
		ResultSet rs = pstmt.executeQuery();
		
		rs.next();
		
		try(conn; pstmt; rs) {
			//"�Խñ� ����"�� �� �ʿ��� ������ (���� �� �ʿ�)
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
			System.out.println("DAO addNotice n.getAid()"+n.getAid()+sql);
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
			System.out.println("fixNotice n.getAid()"+n.getAid()+sql);
			//비밀번호 같은지 검사하는 기능
			/*Notice orinN= getNotice(n.getAid());
			String orinPwd= orinN.getPwd();
			String newPwd= n.getPwd();
			
			if (! orinPwd.equals(newPwd)) throw new Exception("��й�ȣ�� ���� �ʽ��ϴ�!");*/
			
			pstmt.setString(1, n.getEmail());
			pstmt.setString(2,  n.getTitle());
			pstmt.setString(3,n.getPwd());
			pstmt.setString(4, n.getContent());
			pstmt.setInt(5, n.getAid());
			
			pstmt.executeUpdate();
		}
	}
	 
			
}