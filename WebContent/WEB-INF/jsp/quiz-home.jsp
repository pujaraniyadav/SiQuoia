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
	
<div id="main" class="Form">

	<!--  Play menus -->

      		<h1 class="FormHeading">Player Menu</h1>
			<form:form name="PlayForm" method="post" action="submit-play.q" commandName="config">
				<div class="FieldDiv"><form:errors cssClass="Error" element="div" path="*" /></div>

				<div class="FieldLabel">Category</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="category" style="width:80%;" onchange="this.form.submit()">
						<form:options items="${categories}" />
					</form:select>
				</div>

				<div class="FieldLabel">Game</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="game" style="width:80%;">
						<form:options items="${games}" />
					</form:select>
				</div>

				<div class="FieldLabel">Play mode</div>
				<div class="FieldDiv">
					<form:select class="FieldInput" path="mode" style="width:80%;">
						<form:options items="${modes}" />
					</form:select>
				</div>
								
				<br/>
				<div class="FieldDiv">
					<input type="submit" class="SaveButton" value="Play !" />
				</div>
				<br/>
			</form:form>
		
	<!--  Manage menus -->
		
			<hr></hr>
			<h1 class="FormHeading">Config Menu</h1>
			<form:form name="PlayForm" action="submit-play.q">
				<div class="FieldDiv"><form:errors cssClass="Error" element="div" path="*" /></div>
												
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="this.form.submit()">Buy Game</a>
				</div>
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="document.LoginForm.submit()">Submit Questions</a>
				</div>
				<br/>
				<div class="FieldDiv">
					<a class="SaveButton" href="javascript:" onclick="document.LoginForm.submit()">Logout</a>
				</div>
			</form:form>
			
</div>

</body>
</html>

