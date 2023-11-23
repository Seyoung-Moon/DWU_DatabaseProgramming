package persistence;

import persistence.dao.*;
import persistence.dao.impl.*;

// DAO 를 구현한 Implementation 객체를 생성하는 클래스
public class DAOFactory {
	
	// StudentDAO 를 위한 RDB 용 DAO Implementation 객체를 반환
	public StudentDAO getStudentDAO() {
		return new StudentDAOImpl();		 
	}
	
	// DeptDAO 를 위한 RDB 용 DAO Implementation 객체를 반환
	public DeptDAO getDeptDAO() {
		return new DeptDAOImpl();		
	}
	
	// ProfDAO 를 위한 RDB 용 DAO Implementation 객체를 반환
	public ProfDAO getProfDAO() {
		return new ProfDAOImpl();		
	}
}
