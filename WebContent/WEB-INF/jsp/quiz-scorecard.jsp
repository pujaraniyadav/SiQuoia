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
    <title>SiQuoia :: ScoreCard</title>

</head>
<body class="Main">
	<jsp:include page="html/header.html" />
	
     <div class="Form">
      	<h1 class="FormHeading">Score Card</h1>
      	<div class="FieldDiv">
	 		<div class="QAndA">
	 			<table>
	 				<tr>
	 					<td width="30%"><u><b>Q.no</b></u></td>
	 					<td width="30%"><u><b>Pass/Fail</b></u></td>
	 					<td width="30%"><u><b>Points</b></u></td>
	 				</tr>
	 				<c:forEach var="result" items="${results}">
  						<tr>
  							<td>${result.key}</td>
  							<td>
  								<c:if test="${result.value.result}">
  									<label style="color: green;">Correct</label>
  								</c:if>
  							 	<c:if test="${not result.value.result}">
  									<label style="color: red;">Incorrect/skipped</label>
  								</c:if>
  							</td>
  							<td>${result.value.points}</td>
  						</tr>
					</c:forEach>
	 			</table>
	 		</div>
	 		
	 		<c:if test="${mode != 'Learner'}">
	 			<br/>
	 			<div class="QAndA">
	 				<h2>Score is ${score}</h2>
	 			</div>
	 		</c:if>
	 	 	
	 	 	<br/>
	 		<a class="SaveButton" href="home.q">Go Home</a>
	 	</div>
	 </div>
	 		
</body>
</html>

