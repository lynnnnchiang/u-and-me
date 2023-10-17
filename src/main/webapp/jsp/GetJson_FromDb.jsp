<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="tw.idv.cha102.g7.schedule.service.impl.*"%>
<%@page contentType="text/html;charset=utf-8"  language="java" import="java.sql.*" errorPage=""%>
<%@page import="org.json.JSONObject"%>

<%!
Connection conn = null;
PreparedStatement stmt = null;
ResultSet rs = null;
%>

<%
//--------------------------------------------連線資訊
Class.forName("com.mysql.cj.jdbc.Driver");
String dbname = "main";  //-----記得改
String user = "root";      //-----記得改
String password = "root";  //-----記得改

String dataSource = "jdbc:mysql://localhost:3306/" + dbname + "?useUnicode=yes&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Taipei";
//--------------------------------------------取得前端送來的資料
//int schId = Integer.parseInt(request.getParameter("schId")); // 直接執行此程式會跑出500，因為request物件沒有收到傳進來的參數
//int schId = 11; // schedules不存在此員工編號的物件，直接執行此程式查出來會是空集合{}，不會跑出500
//int schId = 1; // schedules有此員工編號物件，直接執行此程式查出來會是此員工編號JSON物件，不會跑出500

System.our.print("aaa");

try{
	//---------------------------------------連線下指令並取回資料
	conn = DriverManager.getConnection(dataSource, user, password);

	String table = "schedules"; //-----記得改
	String sql = "select * from " + table + " where sch_id = ?";

	//建立PreparedStatement物件
	stmt = conn.prepareStatement(sql);

	//代入資料
	stmt.setInt(1, schId);

	//執行PreparedStatement
	rs = stmt.executeQuery();

	//準備將資料打包進JSONObject
	JSONObject sch = new JSONObject(); //{}

	//如果有資料取回一筆資料
	if(rs.next()){
		sch.put("schName", rs.getString("sch_name"));
		sch.put("memId", rs.getInt("mem_id"));
		sch.put("schStart", rs.getDate("sch_start"));
		sch.put("schEnd", rs.getDate("sch_end"));
		sch.put("schPub", rs.getInt("sch_pub"));
		sch.put("schCopy", rs.getInt("sch_copy"));
		sch.put("schCost", rs.getInt("sch_cost"));
	}
	//輸出JSONObject
	out.print(sch);
	//--------------------------------------------
} catch (SQLException se) {
	System.out.println(se);
} finally {
	if (rs != null) {
		try {
			rs.close();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	if (stmt != null) {
		try {
			stmt.close();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}

	if (conn != null) {
		try {
			conn.close();
		} catch (SQLException se) {
			System.out.println(se);
		}
	}
}
%>
