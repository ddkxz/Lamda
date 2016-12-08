<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.sql.*, javax.sql.*, javax.servlet.*"%>
<%@ page import="org.json.simple.JSONArray, org.json.simple.JSONObject, org.json.simple.parser.JSONParser" %>
<%

String glass_id= request.getParameter("glass_id").trim();
String recipe= request.getParameter("recipe").trim();
String panel_count= request.getParameter("panel_count").trim();

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
	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
	PreparedStatement pstmt =null;
	String query = "";
	
if(flag==1){
	
	query = "select P.p_gls_id, P.pnl_id, D.defect_no, D.defect_code, D.coor_x, D.coor_y, P.pnl_x_pos, P.pnl_y_pos, P.pnl_x_size, P.pnl_y_size from (select * from dft_tbl_t where defect_code in (?)) D join pnl_tbl_t P on D.d_pnl_id = P.pnl_id where P.p_gls_id = ?";
	pstmt = conn.prepareStatement(query);
		
		for(i=0; i<defect_code_array.length; i++){
			pstmt.setString(i+1, defect_code_array[i]);
		}
		
		pstmt.setString(i+1, glass_id);
		
}else{

	query = "select P.p_gls_id, P.pnl_id, D.defect_no, D.defect_code, D.coor_x, D.coor_y, P.pnl_x_pos, P.pnl_y_pos, P.pnl_x_size, P.pnl_y_size from dft_tbl_t D join pnl_tbl_t P on D.d_pnl_id = P.pnl_id where P.p_gls_id = ?";
	pstmt = conn.prepareStatement(query);
	
		pstmt.setString(1, glass_id);
}
	
	ResultSet rs = pstmt.executeQuery();
	JSONArray jsonArray = new JSONArray();

	while (rs.next()) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("p_glass_id", rs.getString(1));
		jsonObj.put("panel_id", rs.getString(2));
		jsonObj.put("defect_id", rs.getString(3));
		jsonObj.put("defect_code", rs.getString(4));
		jsonObj.put("coord_x", rs.getDouble(5));
		jsonObj.put("coord_y", rs.getDouble(6));
// 		jsonObj.put("pnl_x_pos", rs.getInt(7));
// 		jsonObj.put("pnl_y_pos", rs.getInt(8));
// 		jsonObj.put("pnl_x_size", rs.getInt(9));
// 		jsonObj.put("pnl_y_size", rs.getInt(10));
		jsonArray.add(jsonObj);
	}
	
	query = "select * from(select pnl_x_pos, pnl_y_pos, pnl_x_size, pnl_y_size, pnl_id, recipe from pnl_tbl_t join gls_tbl_t on p_gls_id=gls_id where gls_id not in (select p_gls_id from pnl_tbl_t where pnl_x_size = 0) order by gls_id) where recipe=? and rownum<=?";
	pstmt = conn.prepareStatement(query);
	
	pstmt.setString(1, recipe);
	pstmt.setString(2, panel_count);
	
	ResultSet rs_p = pstmt.executeQuery();
	JSONArray jsonArray_p = new JSONArray();

	while (rs_p.next()) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("pnl_x_pos", rs_p.getInt(1));
		jsonObj.put("pnl_y_pos", rs_p.getInt(2));
		jsonObj.put("pnl_x_size", rs_p.getInt(3));
		jsonObj.put("pnl_y_size", rs_p.getInt(4));
		jsonObj.put("pnl_id", rs_p.getString(4));
		jsonArray_p.add(jsonObj);
	}
	
	rs.close();
	pstmt.close();
	conn.close();
	out.println("{\"result1\":"+ jsonArray+", \"result2\":"+jsonArray_p+"}");
//	out.println(jsonArray_p);
%>