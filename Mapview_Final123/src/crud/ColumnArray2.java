package crud;

public class ColumnArray2 {
	
////////////////////////////////////////////////////////////////////////////
// Title : DEFECT MAPVIEW ������Ʈ�� COLUMN�� TYPE ARRAY.
// char(14) : 2016.10.06
// Author : ������
// Comment : CREATE TABLE�� INSERT TABLE�� �ڵ��� ����ȭ�ϰ� Į������ ������ ���̼��� ���� ARRAY�� ��ӹ޾� ����Ѵ�.
/////////////////////////////////////////////////////////////////////////////

	static String gls_column [] ={ //���� ������ field count�� DB�� ���� �ʿ䰡 ���� ������ �Ǵ��Ͽ� ����. ��17�� Į��
			"lot_id",
			"gls_id", // primary key�� ���� Į��
			"slot_id", 
			"product_id", 
			"step_id", 
			"cst_id", 
			"eqp_id", // index�� ���� Į��
			"unit_id", 
			"auto_mode", 
			"recipe", // index�� ���� Į��
			"gls_judgement", 
			"align_1", 
			"align_2", 
			"repeat_defect", 
			"start_time", // index�� ���� Į��
			"end_time", // index�� ���� Į��
			"site1"
			};
	
	static String gls_type [] ={ //����ŬDB�� ����ϹǷ�, ����Ŭ ������ type ����
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
	
	static String pnl_column [] ={ //�� 29�� Į��
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
			"p_gls_id" // ���������� ���� �߰� Į��, gls_dat ���̺��� gls_id�� �����Ѵ�. (foreign key)
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
	
	static String dft_column [] ={ //�� 30�� Į��
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
			"d_pnl_id" // ���������� ���� �߰�Į��, pnl_dat ���̺��� pnl_id�� �����Ѵ�. (foreign key)
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
			"number(7,6)", //���弭���󿡴� number(5)�� ��ϵǾ�����, ���� data�� (7,6)
			"number(7,6)", //���弭���󿡴� number(5)�� ��ϵǾ�����, ���� data�� (7,6)
			"number(7,6)", //���弭���󿡴� number(5)�� ��ϵǾ�����, ���� data�� (7,6)
			"number(7,6)", //���弭���󿡴� number(5)�� ��ϵǾ�����, ���� data�� (7,6)
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
	