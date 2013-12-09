<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="proj.webapp.*" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="css/quiz.css" media="screen" rel="stylesheet" type="text/css"/>
    <title>SiQuoia :: Login</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />

      <div class="Form">
      		<h1 class="FormHeading">Login</h1>
			<form:form name="LoginForm" method="post" action="submit-login.q" commandName="user">
				<div class="FieldDiv"><form:errors cssClass="Error" element="div" path="*" /></div>

				<div class="FieldLabel">Username:</div>
				<div class="FieldDiv"><form:input class="FieldInput" path="username"	/></div>
								
				<div class="FieldLabel">Password:</div>
				<div class="FieldDiv"><form:password class="FieldInput" path="password"/></div>
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="document.LoginForm.submit()">Login</a>
				</div>
				<br/>
				<div class="FieldDiv">
					<a href="signup.q">Create account</a>
				</div>
				<br/>
			</form:form>
		</div>
		
</body>

<jsp:include page="html/footer.html" />

</html>

