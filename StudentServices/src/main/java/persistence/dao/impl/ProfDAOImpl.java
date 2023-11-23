package persistence.dao.impl;

import java.sql.ResultSet;
import java.util.List;
import persistence.dao.ProfDAO;
import persistence.util.JDBCUtil;
import service.dto.ProfDTO;

public class ProfDAOImpl implements ProfDAO {

	private JDBCUtil jdbcUtil = null;
	
	public ProfDAOImpl() {
		jdbcUtil = new JDBCUtil();
	}
	
	private static String query = 	"SELECT PROFESSOR.P_NO, " +
	  								"       PROFESSOR.P_NAME, " +
	  								"       PROFESSOR.P_PHONE_NO, " +
	  								"       PROFESSOR.ROOM_NO, " +
	  								"       PROFESSOR.D_NO " +
	  								"FROM PROFESSOR ";
	
	@Override
	public ProfDTO getProfByName(String name) {
		String searchQuery = query + "WHERE PROFESSOR.P_NAME = ?";
		Object[] param = new Object[] {name};
		
		jdbcUtil.setSql(searchQuery);
		jdbcUtil.setParameters(param);
	
		try {
			ResultSet rs = jdbcUtil.executeQuery();
			ProfDTO dto = new ProfDTO();
			while (rs.next()) {
				dto.setProfNo(rs.getString("P_NO"));
				dto.setName(rs.getString("P_NAME"));
				dto.setPhoneNo(rs.getString("P_PHONE_NO"));
				dto.setRoomNo(rs.getString("ROOM_NO"));
				dto.setDeptNo(rs.getString("D_NO"));
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
	public List<ProfDTO> getProfList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertProf(ProfDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProf(ProfDTO dept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteProf(int pNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ProfDTO getProfByNo(String pNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
