<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.sql.*, javax.sql.*, javax.servlet.*"%>
<%@ page import="org.json.simple.JSONArray, org.json.simple.JSONObject, org.json.simple.parser.JSONParser" %>
<%

	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
	String query1 = "select distinct(recipe) from gls_tbl_t order by 1";	
	String query2 = "select distinct(eqp_id) from gls_tbl_t order by 1";	
	String query3 = "select distinct(defect_code) from dft_tbl_t order by 1";
	

	PreparedStatement pstmt = conn.prepareStatement(query1);
	ResultSet rs1 = pstmt.executeQuery();
	pstmt = conn.prepareStatement(query2);
	ResultSet rs2 = pstmt.executeQuery();
	pstmt = conn.prepareStatement(query3);
	ResultSet rs3 = pstmt.executeQuery();
	
	JSONArray jsonArray1 = new JSONArray();
	JSONArray jsonArray2 = new JSONArray();
	JSONArray jsonArray3 = new JSONArray();
	
	
	while (rs1.next()) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("recipe", rs1.getString(1));
		jsonArray1.add(jsonObj);
	}
	
	while (rs2.next()) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("eqp_id", rs2.getString(1));
		jsonArray1.add(jsonObj);
	}
	
	while (rs3.next()) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("defect_code", rs3.getString(1));
		jsonArray1.add(jsonObj);
	}

	rs1.close();
	rs2.close();
	rs3.close();
	pstmt.close();
	conn.close();
	out.println(jsonArray1);

%>
