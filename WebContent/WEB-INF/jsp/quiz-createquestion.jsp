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
      		<h1 class="FormHeading">Create Question</h1>
			<form:form name="CreateQuestionForm" method="post" action="create-question.q" commandName="question">
				<div class="FieldDiv"><form:errors cssClass="Error" element="div" path="*" /></div>

				<div class="FieldLabel">Question ${number}</div>
				<div class="FieldDiv"><form:input class="FieldInput" path="question" style="width:90%;"/></div>

				<div class="FieldLabel">Answer (Separate by comma)</div>
				<div class="FieldDiv"><form:input class="FieldInput" path="answers" style="width:90%;"/></div>
								
				<div class="FieldLabel">Correct Answer</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="answer" style="width:90%;">
						<form:option value="0">1</form:option>
						<form:option value="1">2</form:option>
						<form:option value="2">3</form:option>
						<form:option value="3">4</form:option>
					</form:select>
				</div>

				<div class="FieldLabel">Points</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="points" style="width:90%;">
						<form:option value="1">Easy</form:option>
						<form:option value="2">Medium</form:option>
						<form:option value="3">Hard</form:option>
					</form:select>
				</div>
				
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="document.CreateQuestionForm.submit()">Next</a>
					<a class="SaveButton" href="home.q">Cancel</a>
				</div>
				<br/>
			</form:form>
		</div>
		
</body>
</html>

