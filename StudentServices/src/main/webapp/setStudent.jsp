<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="service.*, service.dto.*" %>
<jsp:useBean id="stu" class="service.dto.StudentDTO" scope="page" />
<jsp:setProperty name="stu" property="*" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>학생 정보 등록</title>
</head>

<body>
<%
	StudentService studentService = new StudentServiceImpl();
//	StudentDTO stu = new StudentDTO();
//	stu.setStuNo(request.getParameter("stuNo"));
//	stu.setStuName(request.getParameter("stuName"));
//	stu.setPwd(request.getParameter("pwd"));
//	stu.setStuPhoneNo(request.getParameter("stuPhoneNo"));
//	stu.setYear(request.getParameter("year"));
//	stu.setProfName(request.getParameter("profName"));
//	stu.setDept(request.getParameter("dept"));

	int result = studentService.insertStudent(stu);
	System.out.println(result + "개의 학생정보가 추가되었습니다.");
	response.sendRedirect("index.jsp");
%>

</body>
</html>