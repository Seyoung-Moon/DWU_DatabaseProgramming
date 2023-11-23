<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="service.*" %>
<%@ page import="service.dto.StudentDTO" %>
<jsp:useBean id="stu" class="service.dto.StudentDTO" scope="page" />
<jsp:setProperty name="stu" property="*" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>학생 정보 조회</title>
</head>
<body>
<%
StudentService studentService = new StudentServiceImpl();
	StudentDTO student = studentService.getStudent(stu.getStuName());
	if (student != null ) {
		out.print("학번: " + student.getStuNo()+ "<br>");
		out.print("이름: " + student.getStuName()+ "<br>");
		out.print("학년: " + student.getYear()+ "<br>");
		out.print("학과: " + student.getDeptName() + "<br>");
		out.print("전화번호: " + student.getStuPhoneNo()+ "<br>");
		out.print("지도교수: " + student.getProfName()+ "<br>");
	}
%>
<br>
<a href="index.jsp">Go back</a>
</body>
</html>