package lab4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;

public class CompanyDao {
	private JDBCUtil jdbcUtil = null;	// JDBCUtil 필드 선언
		
	public CompanyDao() {				// 생성자 
		jdbcUtil = new JDBCUtil();		// JDBCUtil 객체 생성
	}
	
    public Department findDeptByName(String deptName) {
    	/* 실습 #3의 printDeptInfo()를 변형. 
    	 * 부서정보 검색 후 Department DTO를 생성하고 검색 결과를 저장해서 return함 
    	 */
        StringBuilder query = new StringBuilder();
        query.append("SELECT deptno, manager, COUNT(empno) AS numOfEmps ");
        query.append("FROM EMP0000 JOIN DEPT0000 USING (deptno) ");
        query.append("WHERE dname = ? ");
        query.append("GROUP BY deptno, manager");
        
    	jdbcUtil.setSqlAndParameters(query.toString(), new Object[]{deptName});	// JDBCUtil에 질의문과 파라미터 설정	
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			
			if (rs.next()) {		// 검색 결과 존재
				Department dept = new Department();				
				dept.setDeptNo(rs.getInt("deptno"));
				dept.setMgrNo(rs.getInt("manager"));
				dept.setNumOfEmps(rs.getInt("numOfEmps"));
				return dept;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 등 해제
		}
    	return null;
    } 

    public List<Employee> getAllEmpsInDept(int deptNo) {
    	/* 실습 #3의 printAllEmpsInDept()를 변형.
    	 * ArrayList<Employee> 타입 객체를 생성한 후, 검색된 각 사원 정보에 대해
    	 * Employee DTO를 생성 및 저장하고 List 객체에 추가함. List 객체를 반환함
    	 */    	
    	StringBuilder query = new StringBuilder();
        query.append("SELECT empno, ename, job, sal, comm ");
        query.append("FROM EMP0000 WHERE deptno = ?");                
        
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[]{deptNo});	// JDBCUtil에 질의문과 파라미터 설정	
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			
            List<Employee> empList = new ArrayList<Employee>();            
			while (rs.next()) {		// 커서를 통해 한 행씩 fetch
				Employee emp = new Employee(
						rs.getInt("empno"),
						rs.getString("ename"),
						rs.getString("job"),
						null,
						rs.getDouble("sal"),
						rs.getDouble("comm"), 
						null);
				empList.add(emp);
			}			
            return empList;		// 질의 결과 반환 (질의 결과가 없을 경우 빈 리스트 반환)
            
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 등 해제
		}
		return null;
    }

    public Employee replaceManagerOfDept(Appointment appo) {
    	/* 실습 #3의 replaceManagerOfDept()와 유사하나, 매개변수 전달을 위해 Appointment
    	 * DTO를 이용하고, 실행 결과로 기존 관리자 사원 정보를 포함하는 Employee DTO를 반환함
    	 */    	    	
        try {                	
        	// 기존 부서 관리자 사번 조회
        	String query = "SELECT manager FROM DEPT0000 WHERE deptno = ?";
            jdbcUtil.setSqlAndParameters(query.toString(), new Object[]{appo.getDeptNo()});	// JDBCUtil에 질의문과 파라미터 설정	
			ResultSet rs = jdbcUtil.executeQuery();			
            if (rs.next() == false)   // 부서가 존재하지 않음                  
            	return null;                  
            int oldMgrNo = rs.getInt("manager");            
        	
            // 부서 관리자 변경
            String update1 = "UPDATE DEPT0000 SET manager = ? WHERE deptno = ?"; 
            jdbcUtil.setSqlAndParameters(update1, 
            		new Object[]{appo.getMgrNo(), appo.getDeptNo()});	// JDBCUtil에 질의문과 파라미터 설정	
            jdbcUtil.executeUpdate();  

            // 기존 관리자 사원의 직무명 변경
            StringBuilder update2 = new StringBuilder();
            update2.append("UPDATE EMP0000 ");
            update2.append("SET job = SUBSTR(job, 0, LENGTH(job)-3) "); // 또는 REPLACE(job, '(M)', '') 이용
            update2.append("WHERE empno = ?");              
            jdbcUtil.setSqlAndParameters(update2.toString(), new Object[]{oldMgrNo});
            jdbcUtil.executeUpdate();  
            
            // 새 관리자 사원의 직무명과 수당 변경
            StringBuilder update3 = new StringBuilder();
            update3.append("UPDATE EMP0000 ");
            update3.append("SET job = job || '(M)', comm = NVL(comm,0) + ? "); // comm은 null이 가능 -> 0으로 바꿔서 계산
            update3.append("WHERE empno = ?");                        
            jdbcUtil.setSqlAndParameters(update3.toString(), 
            		new Object[]{appo.getComm(), appo.getMgrNo()});
            jdbcUtil.executeUpdate();
        	
            // 기존 관리자 사원 정보 조회 및 반환     
            StringBuilder query2 = new StringBuilder();
        	query2.append("SELECT ename, job, hiredate, sal, comm, dname "); 
            query2.append("FROM EMP0000 JOIN DEPT0000 USING (deptno) ");
            query2.append("WHERE empno = ?");
            
            jdbcUtil.setSqlAndParameters(query2.toString(), new Object[]{oldMgrNo});	// JDBCUtil에 질의문과 파라미터 설정	 		
   			rs = jdbcUtil.executeQuery();    			
    		if (rs.next()) {		// 검색 결과 존재         	
    			return new Employee(
    						oldMgrNo,
    						rs.getString("ename"),
    						rs.getString("job"),
    						rs.getDate("hiredate").toLocalDate(),
    						rs.getDouble("sal"),
    						rs.getDouble("comm"), 
    						rs.getString("dname"));
    		}
        	
        } catch (Exception ex) {
        	jdbcUtil.rollback();	// 트랜잭션 rollback 실행
        	ex.printStackTrace();
        } finally {
            jdbcUtil.commit();		// 트랜잭션 commit 실행
            jdbcUtil.close();
        }
    	return null;
    }
    
    public Employee findEmployee(int empNo) {
    	/* 실습 #3의 printEmpInfo()를 변형
    	 * 주어진 사번에 해당하는 사원 정보를 검색 후 Employee DTO를 생성 및 저장하고 반환함
    	 */ 
    	StringBuilder query = new StringBuilder();
    	query.append("SELECT ename, job, hiredate, sal, comm, dname "); 
        query.append("FROM EMP0000 JOIN DEPT0000 USING (deptno) ");
        query.append("WHERE empno = ?");
        
        jdbcUtil.setSqlAndParameters(query.toString(), new Object[]{empNo});	// JDBCUtil에 질의문과 파라미터 설정	
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			
			if (rs.next()) {		// 검색 결과 존재         	
	            return new Employee(
							empNo,
							rs.getString("ename"),
							rs.getString("job"),
							rs.getDate("hiredate").toLocalDate(),
							rs.getDouble("sal"),
							rs.getDouble("comm"), 
							rs.getString("dname"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// ResultSet, PreparedStatement, Connection 등 해제
		}
    	return null;        
    }
} 

