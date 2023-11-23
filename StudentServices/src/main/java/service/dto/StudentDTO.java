package service.dto;

// 학생과 관련한 정보를 저장하기 위한 DTO(Data Transition Object)
public class StudentDTO {
	private String stuNo = null; 		// 학번
	private String stuName = null; 		// 성명
	private String pwd = null; 			// 암호
	private String stuPhoneNo = null; 	// 연락처
	private String year = null; 		// 학년
	private String profNo = null;		// 지도교수 번호
	private String detpNo = null;	 	// 학과번호
	private String profName = null; 	// 지도교수 성명
	private String deptName = null; 	// 학과명

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getStuPhoneNo() {
		return stuPhoneNo;
	}

	public void setStuPhoneNo(String stuPhoneNo) {
		this.stuPhoneNo = stuPhoneNo;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getProfNo() {
		return profNo;
	}

	public void setProfNo(String pNo) {
		this.profNo = pNo;
	}

	public String getDeptNo() {
		return detpNo;
	}

	public void setDeptNo(String dNo) {
		this.detpNo = dNo;
	}

	public String getProfName() {
		return profName;
	}

	public void setProfName(String profName) {
		this.profName = profName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDept(String deptName) {
		this.deptName = deptName;
	}

}
