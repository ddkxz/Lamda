package crud;

public class ColumnArray2 {
	
////////////////////////////////////////////////////////////////////////////
// Title : DEFECT MAPVIEW 프로젝트의 COLUMN과 TYPE ARRAY.
// char(14) : 2016.10.06
// Author : 김현수
// Comment : CREATE TABLE과 INSERT TABLE의 코딩을 간소화하고 칼럼정보 수정의 용이성을 위해 ARRAY를 상속받아 사용한다.
/////////////////////////////////////////////////////////////////////////////

	static String gls_column [] ={ //원본 파일의 field count는 DB에 넣을 필요가 없는 것으로 판단하여 삭제. 총17개 칼럼
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
			"char(14)", 
			"char(2)", 
			"char(15)", 
			"char(7)", 
			"char(10)", 
			"char(7)", 
			"char(12)", 
			"char(3)", 
			"char(20)", 
			"char(1)", 
			"char(20)", 
			"char(20)", 
			"char(2)", 
			"char(14)", 
			"char(14)", 
			"char(100)"
			};
	
	static String pnl_column [] ={ //총 29개 칼럼
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
			"p_gls_id" // 조인조건을 위한 추가 칼럼, gls_dat 테이블의 gls_id를 참조한다. (foreign key)
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
			"char(5)", 
			"char(13)", 
			"char(25)", 
			"char(25)", 
			"char(5)", 
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
			"char(14)"
			};	
	
	static String dft_column [] ={ //총 30개 칼럼
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
			"number(5,0)",			
			"char(5)",
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
			"number(7,6)", //스펙서류상에는 number(5)로 기록되었으나, 실제 data는 (7,6)
			"number(7,6)", //스펙서류상에는 number(5)로 기록되었으나, 실제 data는 (7,6)
			"number(7,6)", //스펙서류상에는 number(5)로 기록되었으나, 실제 data는 (7,6)
			"number(7,6)", //스펙서류상에는 number(5)로 기록되었으나, 실제 data는 (7,6)
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
	