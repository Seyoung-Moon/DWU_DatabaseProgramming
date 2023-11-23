package lab3;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Lab3 {

	public Lab3() {		// 생성자
		// JDBC 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}	
	}
	
	private static Connection getConnection() {
		String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";		
		String user = "scott2";
		String passwd = "TIGER";

		// DBMS와의 연결 생성
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}	 
		return conn;
	}
	
	public static void printDeptInfo(String deptName) {
		Connection conn = getConnection();
		String query = "SELECT d.deptno, manager, COUNT(empno)"
						+ "FROM EMP0753 e JOIN DEPT0753 d ON(e.deptno = d.deptno) "
						+ "WHERE DNAME = ? "
						+ "GROUP BY d.deptno, manager";
		try {
			PreparedStatement pStmt = conn.prepareStatement(query);
			pStmt.setString(1, deptName);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()) {
				System.out.println("<부서정보>");
				System.out.println("부서번호: " + rs.getInt("deptno"));
				System.out.println("부서명: " + deptName);
				System.out.println("관리자사번: " + rs.getInt("manager"));
				System.out.println("사원 수: " + rs.getInt("COUNT(empno)"));
			}
			System.out.println();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printAllEmpsInDept(String deptName) {
		Connection conn = getConnection();
		String query = "SELECT e.empno, ename, job, sal, NVL(comm, 0.0) AS comm_alias "
						+ "FROM EMP0753 e JOIN DEPT0753 d ON(e.deptno = d.deptno) "
						+ "WHERE DNAME = ?";
		try {
			PreparedStatement pStmt = conn.prepareStatement(query);
			pStmt.setString(1, deptName);
			ResultSet rs = pStmt.executeQuery();
			
			String[] column = {"사번", "이름", "직무", "급여", "수당"};
			for(int i = 0; i < column.length; i++) {
				System.out.printf("%13s", column[i]);
			}
			System.out.println();
			System.out.println("-----------------------------------------------------------------------");
	
			while (rs.next()) {
				System.out.printf("%14s", rs.getInt("empno"));
				System.out.printf("%14s", rs.getString("ename"));
				System.out.printf("%14s", rs.getString("job"));
				System.out.printf("%14.2f", rs.getDouble("sal"));
				System.out.printf("%14.2f\n", rs.getDouble("comm_alias"));
			}
			System.out.println();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int replaceManagerOfDept(String deptName, int newMgrNo, double newMgrComm) {
		int oldMgrNo = 0;
		Connection conn = getConnection();
		int recordCount = 0;
		String query1 = "SELECT manager "
						+ "FROM DEPT0753 "
						+ "WHERE dname = ?";
		
		String dml1 = "UPDATE DEPT0753 SET manager = ? " 
					+ "WHERE dname = ?";
		
		String dml2 = "UPDATE EMP0753 SET job = RTRIM(job, '(M)') "
					+ "WHERE empno = ?";

		String dml3 = "UPDATE EMP0753 SET comm = NVL(comm, 0.0) + ?, job = job || '(M)'"
					+ "WHERE empno = ?";
		
		try {
			
			PreparedStatement pStmt1 = conn.prepareStatement(query1);
			pStmt1.setString(1, deptName);
			ResultSet rs1 = pStmt1.executeQuery();
			while(rs1.next()) {
				oldMgrNo = rs1.getInt("manager");
			}

			PreparedStatement pStmt2 = conn.prepareStatement(dml1);	
			pStmt2.setInt(1, newMgrNo);
			pStmt2.setString(2, deptName);
			recordCount = pStmt2.executeUpdate();
			
			PreparedStatement pStmt3 = conn.prepareStatement(dml2);	
			pStmt3.setInt(1, oldMgrNo);
			recordCount = pStmt3.executeUpdate();
			
			PreparedStatement pStmt4 = conn.prepareStatement(dml3);	
			pStmt4.setDouble(1, newMgrComm);
			pStmt4.setInt(2, newMgrNo);
			recordCount = pStmt4.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oldMgrNo; 
	}
	
	public static void printEmpInfo(int empNo) {
		Connection conn = getConnection();
		String query = "SELECT ename, job, hiredate, sal, NVL(comm, 0.0) AS comm_alias, deptno "
						+ "FROM EMP0753 " 
						+ "WHERE empno = ?";
		
		try {
			PreparedStatement pStmt = conn.prepareStatement(query);
			pStmt.setInt(1, empNo);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()) {
				System.out.printf("%d ", empNo);
				System.out.printf("%s ", rs.getString("ename"));
				System.out.printf("%s ", rs.getString("job"));
				System.out.printf("%s ", rs.getString("hiredate"));
				System.out.printf("%.2f ", rs.getDouble("sal"));
				System.out.printf("%.2f ", rs.getDouble("comm_alias"));
				System.out.printf("%d\n", rs.getInt("deptno"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		/*  참고: DB 검색 결과 중 DATE 타입의 컬럼 값을 읽어서 특정 형식의 String 객체를 생성하는 방법:
			Date sqlDate = rs.getDate("...");				// import java.sql.Date
			LocalDate localDate = sqlDate.toLocalDate();  	// import java.time.LocalDate 
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");   // import java.time.format.DateTimeFormatter
			String DateStr = localDate.format(formatter);  
		*/
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);	
		
		System.out.print("부서명을 입력하시오: ");
		String deptName = scanner.next();
		System.out.println();
		
		// printDeptInfo 호출
		printDeptInfo(deptName);
		// printAllEmpsInDept 호출
		printAllEmpsInDept(deptName);
		System.out.print("새 관리자 사번과 보직수당을 입력하시오: ");
		int managerNo  = scanner.nextInt();
		double commission = scanner.nextDouble();
			
		// replaceManagerOfDept 호출 (기존 관리자 사번 return)
		replaceManagerOfDept(deptName, managerNo, commission);
		System.out.println("기존 관리자: ");		
		// printEmpInfo 호출		
		printEmpInfo(7698);
		System.out.println("새 관리자: ");
		// printEmpInfo 호출		
		printEmpInfo(7499);
		
		scanner.close();
	}
}
