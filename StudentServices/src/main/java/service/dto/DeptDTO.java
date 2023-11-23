package service.dto;

// 학과 정보를 위한 DTO
public class DeptDTO {
	private String deptNo;			// 학과번호
	private String faculty;			// 학부
	private String college;			// 소속대학
	private String dName;			// 학과명
	
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String dNo) {
		this.deptNo = dNo;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getDName() {
		return dName;
	}
	public void setDName(String name) {
		this.dName = name;
	}	
}
