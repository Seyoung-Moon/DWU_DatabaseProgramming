package jdbc.examples;

import java.sql.*;									

public class MetaDataEx {
	public static void main(String args[]) 
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String url = "jdbc:oracle:thin:@dblab.dongduk.ac.kr:1521/orclpdb";		
		String user = "scott";
		String passwd = "TIGER";
			
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		String query =  "SELECT * FROM dept";						 
	
		try {			
			conn = DriverManager.getConnection(url, user, passwd);	
			
			// Test DatabaseMetaData
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String productName = dbMetaData.getDatabaseProductName();
			System.out.println("Database: " + productName);
			String productVersion =dbMetaData.getDatabaseProductVersion();
			System.out.println("Version: " + productVersion);
			String username =dbMetaData.getUserName();
			System.out.println("UserName: " + username);
			System.out.println("List of tables:");
			ResultSet  tbls = dbMetaData.getTables(null, username, "%", new String[] {"TABLE"});
			while (tbls.next()) {
				String tableName = tbls.getString("TABLE_NAME");
				System.out.println(tableName);
			}						
			System.out.println();

			stmt = conn.createStatement();				
			rs = stmt.executeQuery(query);				
			
			
			// Test ResultSetMetaData
			ResultSetMetaData rsMetaData = rs.getMetaData();
			System.out.println("Field \tsize\tDataType");
			int columnCount = rsMetaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				System.out.print(rsMetaData.getColumnName(i) + " \t");
				System.out.print(rsMetaData.getColumnDisplaySize(i) + "\t");
				System.out.println(rsMetaData.getColumnTypeName(i));
			}
			System.out.println();
			for(int i=1; i<=columnCount; i++)  
				System.out.print(rsMetaData.getColumnName(i) + " \t");
			System.out.println();
			while (rs.next()) {
				for(int i=1; i<=columnCount; i++) 
					System.out.print(rs.getObject(rsMetaData.getColumnName(i)) + " \t");
				System.out.println();
			}			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {										
			if (rs != null) 
				try { 
					rs.close(); 
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