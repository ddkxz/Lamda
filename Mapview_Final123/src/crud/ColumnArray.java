package crud;

public class ColumnArray {
	
////////////////////////////////////////////////////////////////////////////
// Title : DEFECT MAPVIEW 프로젝트의 COLUMN과 TYPE ARRAY.
// char(14) : 2016.10.06
// Author : 김남호
// Comment : CREATE TABLE과 INSERT TABLE의 코딩을 간소화하고 칼럼정보 수정의 용이성을 위해 ARRAY를 상속받아 사용한다.
/////////////////////////////////////////////////////////////////////////////

	static String gls_column [] ={ //원본 파일의 field count는 DB에 넣을 필요가 없는 것으로 판단하여 삭제.
			"lot_id",
			"gls_id", // primary key로 만들 칼럼
			"slot_id", 
			"product_id", 
			"step_id", 
			"cst_id", 
			"eqp_id", // index로 만들 칼럼
			"unit_id", 
			"auto_mode", 
			"recipe", // index로 만들 칼럼
			"gls_judgement", 
			"align_1", 
			"align_2", 
			"repeat_defect", 
			"start_time", // index로 만들 칼럼			
			"end_time", // index로 만들 칼럼			
			"site1"
			};
	
	static String gls_type [] ={ //오라클DB를 사용하므로, 오라클 데이터 type 설정
			"char(12)",
			"varchar2(14)", 
			"number(2)", // char -> number
			"char(15)", 
			"char(7)", 
			"char(10)", 
			"varchar2(9)", //실제필드값은 7로 일정하지만, 일반적인 검색조건의 용이성을 위해 가변길이 타입설정
			"char(12)", 
			"char(3)", 
			"varchar2(20)",  //실제 필드값의 길이가 다양함. 검색의 용이성을 위해 오라클의 가변길이타입으로 수정. 
			"char(1)", 
			"char(20)", 
			"char(20)", 
			"char(2)",
			"timestamp(0)", // start_date, 소숫점이하의 밀리세컨드는 표시하지 않는다.따라서 소숫점이하 자릿수 (0).	 	
			"timestamp(0)", // end_date			
			"char(100)"
			};
	
	static String pnl_column [] ={
			"pnl_id", // primary key
			"gate_pnl_grade", 
			"sd_pnl_grade", 
			"r_pnl_judge", 
			"at_pnl_grade", 
			"at_pxl_grade", 
			"pnl_judgement", 
			"repeat_defect", 
			"defect_qty", 
			"modulator", 
			"pnl_voltage", 
			"ebeam", 
			"gray_avg", 
			"gray_std", 
			"threshold", 
			"test_mode", 
			"ess_result",
			"test_mode_1",
			"test_mode_2",
			"test_mode_3",
			"pnl_x_pos",
			"pnl_y_pos",
			"pnl_x_size",
			"pnl_y_size",
			"ess_channel",
			"ess_value",
			"start_time",
			"end_time",
			"p_gls_id" // 조인조건을 위한 추가 칼럼, gls_tbl 테이블의 gls_id를 참조한다. (foreign key)
			};

	static String pnl_type [] ={
			"char(16)",			
			"char(1)", 
			"char(1)", 
			"char(1)", 
			"char(1)", 
			"char(1)", 
			"char(1)", 
			"char(2)", 
			"number(5,0)", 
			"char(5)", 
			"char(5)", // char->number 
			"char(13)", 
			"char(25)", 
			"char(25)", 
			"char(5)", // char->number
			"char(8)", 
			"char(3)",
			"char(10)",
			"char(10)",
			"char(10)",
			"number(11,3)",
			"number(11,3)",
			"number(11,3)",
			"number(11,3)",
			"varchar2(400)",
			"varchar2(400)",
			"char(14)",
			"char(14)",
			"varchar2(14)" //검색의 용이성을 위해서.
			};	
	
	static String dft_column [] ={
			"seq", //primary key, 시퀀스 생성하여 primary key로 둔다.
			"defect_no",			
			"defect_code", // index
			"x_defect_size", 
			"y_defect_size", 
			"defect_length", 
			"defect_area", 
			"repeat_defect", 
			"coor_x", 
			"coor_y", 
			"pr_data", 
			"pr_gate", 
			"se_data", 
			"se_gate", 
			"te_data", 
			"te_gate", 
			"modulator", 
			"ebeam", 
			"condition", 
			"v_start", 
			"v_end", 
			"v_site", 
			"v_mod", 
			"def_v1", 
			"def_v2", 
			"image1", 
			"image2", 
			"image3", 
			"image4", 
			"image5",
			"d_pnl_id" // 조인조건을 위한 추가칼럼, pnl_dat 테이블의 pnl_id를 참조한다. (foreign key)
			};
	
	static String dft_type [] ={
			"number(20)",
			"number(5,0)",			
			"varchar2(5)", // char타입일 경우 pstmt문장내에서 바인드변수값으로 길이를 맞추어 공백을 추가해주어야 검색가능하다. 이런 불편을 막기위해 가변길이 타입(varchar2)으로 설정한다.
			"number(8,1)", 
			"number(8,1)", 
			"number(8,1)", 
			"number(8,1)", 
			"char(2)", 
			"number(10,2)", 
			"number(10,2)", 
			"number(6)", 
			"number(6)", 
			"number(6)", 
			"number(6)", 
			"number(6)", 
			"number(6)", 
			"number(1)", 
			"number(1)", 
			"char(10)", 
			"number(5)", 
			"number(5)",
			"number(5)",
			"number(5)",
			"number(5)", 
			"number(5)", 
			"char(55)", 
			"char(55)", 
			"char(55)", 
			"char(55)", 
			"char(55)",
			"char(16)"
			};
	
}
	