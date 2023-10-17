<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="tw.idv.cha102.g7.schedule.service.impl.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>


<%-- <%

             AttrService attrService = new AttrService();
             List<Attr> list = attrService.getAll();
             request.setAttribute("list",list);
     %> --%>



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
		 <h4><a href="index"><img src="jsp/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
		<th>修改</th>
        <th>刪除</th>
	</tr>

	<tr>
		<%@ include file="/jsp/page1.file" %>
	    <c:forEach items="${schedules}" var="schedule">

    			<td>${schedule.schId}</td>
    			<td>${schedule.schName}</td>
    			<td>${schedule.memId}</td>
    			<td>${schedule.schStart}</td>
    			<td>${schedule.schEnd}</td>
    			<td>${schedule.schPub}</td>
    			<td>${schedule.schCopy}</td>
    			<td>${schedule.schCost}</td>
                <td>
    			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/attr/attr.do" style="margin-bottom: 0px;">
    			     <input type="submit" value="修改">
    			     <input type="hidden" name="id"  value="${schedule.schId}">
    			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
    			</td>
    			<td>
    			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/attr/attr.do" style="margin-bottom: 0px;">
    			     <input type="submit" value="刪除">
    			     <input type="hidden" name="id"  value="${schedule.schId}">
    			     <input type="hidden" name="action" value="delete"></FORM>
    			</td>

    		</tr>
    		</c:forEach>
    		<input type="button" id="target" value="SUCCESS"></input>
</table>
<%@ include file="/jsp/page2.file" %>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
window.addEventListener('load', function (e){
console.log("Hello my test");
const btn_el = document.getElementById('target');
btn_el.addEventListener('click',function(){
    alert('success');
});


});



</script>
</html>