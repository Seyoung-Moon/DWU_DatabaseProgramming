<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="service.*" %>
<%@ page import="service.dto.StudentDTO" %>
<jsp:useBean id="stu" class="service.dto.StudentDTO" scope="page" />
<jsp:setProperty name="stu" property="*" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>학생 정보 변경</title>
</head>
<body>
<%
	StudentService studentService = new StudentServiceImpl();
//	StudentDTO stu = new StudentDTO();
//	stu.setStuNo(request.getParameter("stuNo"));
//	stu.setYear(request.getParameter("pwd"));
	int result = studentService.updateStudent(stu);
	System.out.println(result + "개의 학생정보가 수정되었습니다.");
	response.sendRedirect("index.jsp");
%>
</body>
</html>