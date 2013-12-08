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
    <title>${journal.title}</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />

      <div class="Form" id="start_div">
      		<h1 class="FormHeading">Create Account</h1>
			<form:form name="createAccountForm" method="post" action="createAccount.q" commandName="user">
				<div class="FieldDiv"><form:errors cssClass="Error" element="div" path="*" /></div>

				<div class="FieldLabel">Username:</div>
				<div class="FieldDiv"><form:input class="FieldInput" path="username"/></div>
				
				<div class="FieldLabel">Fullname:</div>
				<div class="FieldDiv"><form:input class="FieldInput" style="width:80%;" path="fullname"/></div>
				
				<div class="FieldLabel">Email:</div>
				<div class="FieldDiv"><form:input class="FieldInput" style="width:80%;" path="email"/></div>
				
				<div class="FieldLabel">Date of birth:</div>
				<div class="FieldDiv"><form:input class="FieldInput" path="dob"/></div>
				
				<div class="FieldLabel">Password:</div>
				<div class="FieldDiv"><form:password class="FieldInput" path="password"/></div>
				
				<div class="FieldLabel">Re-Type Password:</div>
				<div class="FieldDiv"><input class="FieldInput" type="password" name="password2"/></div>	
				
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="document.createAccountForm.submit()">create account</a>
				</div>
				<br/>
				<div class="FieldDiv">
					<a href="login.q">Goto Login</a>
				</div>
			</form:form>
		</div>
		
</body>
</html>

