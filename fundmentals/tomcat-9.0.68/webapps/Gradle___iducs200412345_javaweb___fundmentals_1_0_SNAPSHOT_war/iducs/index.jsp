<%--
  Created by IntelliJ IDEA.
  User: minwook
  Date: 2022/10/31
  Time: 2:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--지시자 정보 제공 <%@ page ~ %> 페이지 include 인클루드 // taglib 태그 라이브러리 --%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    // 자바 문법에 따라 작성함. JSP 기본 객체를 사용할 수 있음 : out -JSP 기본 객체중 하나
    // 객체를 생성해서 사용해야 함.
    String name = new String("인덕");
    String param = request.getParameter("iducs"); // request 내장객체 or 기본객체
    out.println("스크립틀릿 (scriptlet");

    String[] arrStr = {"인덕", "컴소", "Java", "Web", "2A"};
    request.setAttribute("attr", arrStr); //Attribute 방식은 EL, JSTL로 처리하면 효과적임
    for(String str : arrStr) {
        out.println ("<h1>" + str + "</h1>");
    }
%>

</h1>
<%= "표현식 (Expression) : 스크립틀릿에서 출력 편리성 향상 "+ param %>
<c:out value="EL & JSTL 활용 : EL은 Expression의 기능을 보강, attribute 접근 편리성 제공, 다양한 연산 제공"/>
<c:forEach items="${attr}" var="item">
    <img src="../img/마이콜.png"/>
    <h2>${item}</h2>
</c:forEach>
<%@ include file="../about.jsp"%>
</body>
</html>
