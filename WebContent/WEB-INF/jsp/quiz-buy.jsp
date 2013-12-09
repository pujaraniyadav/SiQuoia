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
    <title>SiQuoia :: Buy</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />

      <div class="Form">
      		<h1 class="FormHeading">Buy Game</h1>
			<form:form name="BuyForm" method="post" action="submit-buy.q" commandName="config">
				<c:if test="${error != null}">
					<div class="FieldDiv">
						<div class="Error">${error}</div>
					</div>
				</c:if>

				<div class="FieldLabel">Games</div>
				<div class="FieldDiv"><form:select class="FieldInput" path="quiz" items="${quizes}" style="width:80%;" /></div>

				<div class="FieldDiv">
					<div class="QAndA">
						<form:input type="radio" path="type" value="points"/>Buy with points
					</div>
					<br />
					<div class="QAndA">
						<form:input type="radio" path="type" value="cc" />Buy with Credit Card
						<br />
						<br />
						<div class="FieldLabel">Full Name</div>
						<div class="FieldDiv"><form:input class="FieldInput" path="fullname" style="width:80%;" /></div>
						<div class="FieldLabel">Credit Card</div>
						<div class="FieldDiv"><form:input class="FieldInput" path="cc" style="width:80%;" /></div>
						<div class="FieldLabel">Expiry Date</div>
						<div class="FieldDiv"><form:input class="FieldInput" path="edate" style="width:80%;" /></div>
					</div>
				</div>
				
								
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="document.BuyForm.submit()">Buy</a>
					<a class="SaveButton" href="home.q">Cancel</a>
				</div>
			</form:form>
		</div>
		
</body>
</html>

