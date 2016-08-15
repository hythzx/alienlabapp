<%--
  Created by IntelliJ IDEA.
  User: 橘
  Date: 2016/8/3
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.removeAttribute("user");
    response.sendRedirect("index.jsp");
%>
