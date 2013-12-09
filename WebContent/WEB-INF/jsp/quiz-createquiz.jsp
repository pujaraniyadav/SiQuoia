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
    <title>SiQuoia :: Create Quiz</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />

      <div class="Form">
      		<h1 class="FormHeading">Create Quiz</h1>
			<form:form name="CreateQuizForm" method="post" action="create-quiz.q" commandName="quiz">
				<div class="FieldDiv"><form:errors cssClass="Error" element="div" path="*" /></div>

				<div class="FieldLabel">QuizName</div>
				<div class="FieldDiv"><form:input class="FieldInput" path="name" style="width:90%;"/></div>
								
				<div class="FieldLabel">Category:</div>
				<div class="FieldDiv"><form:input class="FieldInput" path="category" style="width:90%;"/></div>
				
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="document.CreateQuizForm.submit()">Next</a>
				</div>
			</form:form>
		</div>
		
</body>
</html>

