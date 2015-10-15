<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<base href="<%=basePath%>">