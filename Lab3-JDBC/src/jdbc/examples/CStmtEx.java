package jdbc.examples;

import java.sql.*;

public class CStmtEx {
	public static void main(String args[]) 
	{
		Connection conn = null;
		CallableStatement cStmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";		
		String user = "scott";
		String passwd = "TIGER";
		String query = "{call cs_proc(?, ?, ?)}";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection(url, user, passwd);	 
			cStmt = conn.prepareCall(query);
			cStmt.setInt(1, 10);
			cStmt.setString(2, "item01");
			cStmt.setString(3, "item01 is the best one.");
			cStmt.execute();
			System.out.println ("Insertion completes.");
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery("SELECT * FROM CS_TABLE");
			while (rs.next()) {							
				int no = rs.getInt("no");
				String item = rs.getString("item");
				String dsc = rs.getString("dsc");			
				System.out.printf("%d %10s %15s\n", no, item, dsc);
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {		
			if (cStmt != null) try { cStmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
			if (stmt != null) try { stmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
			if (conn != null) try { conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
		}	
	}	
}