<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- bootstrap start -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title> page title </title>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-3.3.1.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<!-- bootstrap end -->
</HEAD>

<style tyle="text/css">

html,

body {

    margin:0;

    padding:0;

    height:100%;

}

#wrapper {

	position:relative;

    min-height:100%;

}

#header {

	height:70px;

    background:#ccc;

}

#container {

    padding:20px;

}

#footer {

    position:absolute;

    bottom:0;

    width:100%;

    height:70px;   

    background:#ccc;

}

</style>
</head>

<BODY>

<div id="wrapper">

    <div id="header">header</div>

    <div id="container">
    나는 container입니다.

	</div>

    <div id="footer">footer</div>

</div>

</BODY>
</html>