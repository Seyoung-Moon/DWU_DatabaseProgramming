package persistence.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import persistence.dao.DeptDAO;
import persistence.util.JDBCUtil;
import service.dto.DeptDTO;

public class DeptDAOImpl implements DeptDAO {

	private JDBCUtil jdbcUtil = null;
	
	public DeptDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}	
	
	private static String query = "SELECT DEPARTMENT.D_NO AS DEPT_NO, " +
			  "       DEPARTMENT.FACULTY AS DEPT_FAC, " +
			  "       DEPARTMENT.COLLEGE AS DEPT_COL, " +
			  "       DEPARTMENT.D_NAME AS DEPT_NAME " +
			  "FROM DEPARTMENT ";

	@Override
	public DeptDTO getDeptByName(String dName) {
		String searchQuery = query + "WHERE DEPARTMENT.D_NAME = ?";
		Object[] param = new Object[] {dName};

		jdbcUtil.setSql(searchQuery);
		jdbcUtil.setParameters(param);
	
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			DeptDTO dto = new DeptDTO();
			while (rs.next()) {
				dto.setDeptNo(rs.getString("DEPT_NO"));
				dto.setFaculty(rs.getString("DEPT_FAC"));
				dto.setCollege(rs.getString("DEPT_COL"));
				dto.setDName(rs.getString("DEPT_NAME"));
			}
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return null;
	}

	@Override
	public List<DeptDTO> getDeptList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertDept(DeptDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDept(DeptDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteDept(int dNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DeptDTO getDeptByNo(String dNo) {
		// TODO Auto-generated method stub
		return null;
	}	
}
