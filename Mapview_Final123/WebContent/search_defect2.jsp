<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.sql.*, javax.sql.*, javax.servlet.*"%>
<%@ page import="org.json.simple.JSONArray, org.json.simple.JSONObject, org.json.simple.parser.JSONParser" %>
<%

String glass_id= request.getParameter("glass_id").trim();
//String glass_id="5AAB4Y0483A0";


int i=0; int flag=0;
String defect_code_array[] ={};
String Q_defect = "";

if (request.getParameter("defect_code").equals("")){
	flag=0;
}else{
	flag = 1;
	String defect_code= request.getParameter("defect_code").trim();
	defect_code_array = defect_code.split(",");
	
	for(i=0; i<defect_code_array.length; i++){
		Q_defect=(i<defect_code_array.length-1)? Q_defect+"?,": Q_defect+"?";
	}
}





	Class.forName("oracle.jdbc.driver.OracleDriver");
	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "scott","Manager33");
	PreparedStatement pstmt =null;
	String query = "";
	
if(flag==1){
	
	query = "select P.p_gls_id, P.pnl_id, D.defect_no, D.defect_code, D.coor_x, D.coor_y from pnl_tbl P,(select * from dft_tbl where defect_code in ("+Q_defect+")) D where D.d_pnl_id = P.pnl_id and P.p_gls_id = ?";
	pstmt = conn.prepareStatement(query);
		
		for(i=0; i<defect_code_array.length; i++){
			pstmt.setString(i+1, defect_code_array[i]);
		}
		
		pstmt.setString(i+1, glass_id);
		
}else{

	query = "select P.p_gls_id, P.pnl_id, D.defect_no, D.defect_code, D.coor_x, D.coor_y from pnl_tbl P, dft_tbl D where D.d_pnl_id = P.pnl_id and P.p_gls_id = ?";
	pstmt = conn.prepareStatement(query);
	
		pstmt.setString(1, glass_id);
}
	
	ResultSet rs = pstmt.executeQuery();
	JSONArray jsonArray = new JSONArray();

	while (rs.next()) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("glass_id", rs.getString(1));
		jsonObj.put("panel_id", rs.getString(2));
		jsonObj.put("defect_id", rs.getString(3));
		jsonObj.put("defect_code", rs.getString(4));
		jsonObj.put("coord_x", rs.getDouble(5));
		jsonObj.put("coord_y", rs.getDouble(6));
		jsonArray.add(jsonObj);
	}
	
	rs.close();
	pstmt.close();
	conn.close();
	out.println(jsonArray);
%>