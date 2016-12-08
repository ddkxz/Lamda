<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@ page import="java.io.*, java.sql.*, javax.sql.*, javax.servlet.*"%>
<%@ page import="org.json.simple.JSONArray, org.json.simple.JSONObject, org.json.simple.parser.JSONParser" %>
<%

String fromTime= request.getParameter("fromTime").replaceAll("\\W","").trim(); // 정규식을 사용한 replaceAll : 영어알파벳과 숫자만 남기고 전부 없애라. 
String toTime= request.getParameter("toTime").replaceAll("\\W","").trim();
String recipe= request.getParameter("recipe").trim();


int i=0; int flag=0;
String eqp_array[] ={}; String defect_code_array[] ={};
String Q_eqp = ""; String Q_defect = "";

//flag를 사용하는 이유는, 해당 flag에 따라서 검색조건구성이 달라지고, 그에 따라 쿼리문도 다르게 적용할 거니까.

if (request.getParameter("eqp_id").equals("")){
	flag=0; 	
}else{
	flag=1;
	String eqp_id= request.getParameter("eqp_id").trim();
	//앞선 파일로부터 ajax를 통해 전달된 eqp_id 검색조건값들은 모두 쉼표에 의해 맵핑되어 있다. 따라서 쉼표를 구분자로 하여 나누고, 각각의 값을 array로 만든다.
	eqp_array = eqp_id.split(","); 
	
	for( ; i<eqp_array.length; i++){ //array에 원소개수만큼 ?를 생성한다. 마지막 물음표만 쉼표를 안붙인다.
		Q_eqp=(i<eqp_array.length-1)? Q_eqp+"?,": Q_eqp+"?"; // 여기서 만들어진 물음표묶음(Q_eqp)은 쿼리문 중간에 포함된다.
	}
}

if (request.getParameter("defect_code").equals("")){
	//이 공간에는 위에서 입력된 flas값이 그대로 전달된다. 즉 위에서 flag 0값이 내려온다면, eqp, defect code 둘 다 검색조건제외라는 뜻.
	// 만일 위에서 flag 1값이 내려온다면, eqp은 검색하지만, defect는 검색조건제외.
}else{
	flag = (flag==1)? 3: 2;
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
	
if (flag==3){
	query = "select distinct(G2.gls_id),G2.eqp_id, G2.lot_id,PC.panel_count, G2.gls_judgement, G2.step_id, G2.unit_id from gls_tbl G2,(select count(*) as panel_count, p_gls_id from pnl_tbl group by p_gls_id) PC, (select P.p_gls_id from pnl_tbl P,(select d_pnl_id from dft_tbl where defect_code in ("+Q_defect+")) D where D.d_pnl_id = P.pnl_id) PD where PC.p_gls_id = G2.gls_id and PD.p_gls_id = G2.gls_id and G2.start_time >= ? and G2.end_time <= ? and G2.recipe = ? and G2.eqp_id in ("+Q_eqp+")";
	pstmt = conn.prepareStatement(query);

	for(i=0; i<defect_code_array.length; i++){ 
		pstmt.setString(i+1, defect_code_array[i]); //pstmt는 물음표를 찾을 때 1번부터 시작한다. 0번 물음표는 없다. 따라서 i+1.
	}

		pstmt.setString(i+1, fromTime); //위의 for문이 끝날 때, i++상태로 끝난다. 따라서 여기서는 i+2를 하면 안 된다. 
		pstmt.setString(i+2, toTime);
		pstmt.setString(i+3, recipe);

		for(int j=0; j<eqp_array.length; j++){
			pstmt.setString(j+i+4, eqp_array[j]); 
		}
		
}else if(flag==2){

	query = "select distinct(G2.gls_id),G2.eqp_id, G2.lot_id,PC.panel_count, G2.gls_judgement, G2.step_id, G2.unit_id from gls_tbl G2,(select count(*) as panel_count, p_gls_id from pnl_tbl group by p_gls_id) PC, (select P.p_gls_id from pnl_tbl P,(select d_pnl_id from dft_tbl where defect_code in ("+Q_defect+")) D where D.d_pnl_id = P.pnl_id) PD where PC.p_gls_id = G2.gls_id and PD.p_gls_id = G2.gls_id and G2.start_time >= ? and G2.end_time <= ? and G2.recipe = ?";
	pstmt = conn.prepareStatement(query);

	for(i=0; i<defect_code_array.length; i++){ 
		pstmt.setString(i+1, defect_code_array[i]);
	}
		pstmt.setString(i+1, fromTime);
		pstmt.setString(i+2, toTime);
		pstmt.setString(i+3, recipe);
		
}else if(flag==1){
	
	query = "select distinct(G2.gls_id),G2.eqp_id, G2.lot_id,PC.panel_count, G2.gls_judgement, G2.step_id, G2.unit_id from gls_tbl G2,(select count(*) as panel_count, p_gls_id from pnl_tbl group by p_gls_id) PC where PC.p_gls_id = G2.gls_id and G2.start_time >= ? and G2.end_time <= ? and G2.recipe = ? and G2.eqp_id in ("+Q_eqp+")";
	pstmt = conn.prepareStatement(query);

		pstmt.setString(1, fromTime);
		pstmt.setString(2, toTime);
		pstmt.setString(3, recipe);
		
		for(int j=0; j<eqp_array.length; j++){
			pstmt.setString(j+4, eqp_array[j]);
		}
}else{

	query = "select distinct(G2.gls_id),G2.eqp_id, G2.lot_id,PC.panel_count, G2.gls_judgement, G2.step_id, G2.unit_id from gls_tbl G2,(select count(*) as panel_count, p_gls_id from pnl_tbl group by p_gls_id) PC where PC.p_gls_id = G2.gls_id and G2.start_time >= ? and G2.end_time <= ? and G2.recipe = ?";
	pstmt = conn.prepareStatement(query);

		pstmt.setString(1, fromTime);
		pstmt.setString(2, toTime);
		pstmt.setString(3, recipe); 
}
	
	ResultSet rs = pstmt.executeQuery();
	JSONArray jsonArray = new JSONArray(); //json array를 만드는 방법 1 : 먼저 빈 jsonarray를 생성.

	while (rs.next()) {
		JSONObject jsonObj = new JSONObject(); //jsonarray의 원소는 json object다. 반복문의 첫머리에서 계속 새 object가 재생성된다. 
		jsonObj.put("glass_id", rs.getString(1)); // object에 데이터를 넣을 때는 put(키값, 밸류값) 형태를 취한다.
		jsonObj.put("eqp_id", rs.getString(2));
		jsonObj.put("lot_id", rs.getString(3));
		jsonObj.put("panel_count", rs.getInt(4));
		jsonObj.put("glass_grade", rs.getString(5));
		jsonObj.put("step_id", rs.getString(6));
		jsonObj.put("unit_id", rs.getString(7));
		jsonArray.add(jsonObj); // object에 데이터를 다 넣었으면, 그 object를 jsonarray에 add한다. 그리고 다시 첫머리로 가서 object는 새로 태어난다.
	}
	
	rs.close();
	pstmt.close();
	conn.close();
	out.println(jsonArray);
%>