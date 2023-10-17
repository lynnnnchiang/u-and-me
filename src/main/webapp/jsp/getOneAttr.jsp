<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="tw.idv.cha102.g7.attr.service.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%--
    AttractionService attractionService = (AttractionService) request.getAttribute("attractionService");
--%>



<html>
<head>
<title>所有員工資料 - listAllEmp.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有員工資料 - listAllEmp.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>景點編號</th>
		<th>景點審核狀態</th>
		<th>景點上下架狀態</th>
		<th>景點名稱</th>
		<th>地址</th>
		<th>經度</th>
		<th>緯度</th>
		<th>說明</th>
		<th>類別編號</th>
		<th>營業時間</th>
		<th>消費</th>
		<th>檢舉原因</th>
	</tr>

	<tr>

    			<td>${attraction.id}</td>
    			<td>${attraction.veri_sta}</td>
    			<td>${attraction.sta}</td>
    			<td>${attraction.name}</td>
    			<td>${attraction.addr}</td>
    			<td>${attraction.lon}</td>
    			<td>${attraction.lat}</td>
    			<td>${attraction.illa}</td>
    			<td>${attraction.type_id}</td>
    			<td>${attraction.buss_time}</td>
    			<td>${attraction.cost_range}</td>
    			<td>${attraction.rep}</td>
    			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/attr/attr.do" style="margin-bottom: 0px;">
    			     <input type="submit" value="修改">
    			     <input type="hidden" name="id"  value="${attraction.id}">
    			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
    			</td>
    			<td>
    			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/attr/attr.do" style="margin-bottom: 0px;">
    			     <input type="submit" value="刪除">
    			     <input type="hidden" name="id"  value="${attraction.id}">
    			     <input type="hidden" name="action" value="delete"></FORM>
    			</td>
    		</tr>
</table>


</body>
</html>