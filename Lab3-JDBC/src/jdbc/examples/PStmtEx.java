package jdbc.examples;
import java.sql.*;

public class PStmtEx {
	public static void main(String args[]) 		
	{
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pStmt = null;			
		ResultSet rs = null;
		String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";		
		String user = "scott";
		String passwd = "TIGER";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// JDBC Driver 로딩 및 등록
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}	
		
		String pattern = "%AR%";				

		try {
			conn = DriverManager.getConnection(url, user, passwd);	
			
			String query1 = "SELECT ename, job, dname "
						+ "FROM emp JOIN dept USING (deptno) "
						+ "WHERE ename like " + pattern;		// 작은 따옴표 누락!
			System.out.println(query1);

			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query1);				// Statement 객체를 사용하여 SQL문 실행
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		// �ڿ� �ݳ�
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (stmt != null) 
				try { 
					stmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}

		try {
			String query2 = "SELECT ename, job, dname "
					  	+ "FROM emp JOIN dept USING (deptno) "
					  	+ "WHERE ename like '" + pattern + "'";	// 작은 따옴표 추가
			System.out.println(query2);
			
			stmt = conn.createStatement();	
			rs = stmt.executeQuery(query2);				// Statement 객체를 사용하여 SQL문 실행
			System.out.println();
			while (rs.next()) {							// 실행 결과 레코드 fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + ", " + job + ", " + dname);
			}
			System.out.println();
			rs.close();

			//String query3 = "SELECT ename, job, dname "
			//	  	+ "FROM emp JOIN dept USING (deptno) "
			//	  	+ "WHERE ename like ?";			// parameter가 포함된 SQL 질의 정의
			
			// String concatenation(+)보다 StringBuilder/StringBuffer 사용이 바람직함
			StringBuilder query3 = new StringBuilder();
			query3.append("SELECT ename, job, dname ");
			query3.append("FROM emp JOIN dept USING (deptno) ");
			query3.append("WHERE ename like ?");
						
			System.out.println(query3);

			pStmt = conn.prepareStatement(query3.toString());	// Connection으로부터 PreparedStatement 생성
			pStmt.setString(1, pattern);			// PreparedStatement에 파라미터 값 설정
			rs = pStmt.executeQuery();				// 질의 실행 (주의: parameter 없음!)			
			System.out.println();
			while (rs.next()) {						// 실행 결과 레코드 fetch
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				String dname = rs.getString("dname");
				System.out.println(ename + ", " + job + ", " + dname);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		// �ڿ� �ݳ�
			if (rs != null) 
				try { 
					rs.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (pStmt != null) 
				try { 
					pStmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (stmt != null) 
				try { 
					stmt.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) 
				try { 
					conn.close(); 
				} catch (SQLException ex) { ex.printStackTrace(); }
		}	
	}	
}