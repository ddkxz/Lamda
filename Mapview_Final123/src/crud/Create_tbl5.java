package crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_tbl5 extends ColumnArray {
	
//////////////////////////////////////////////////////////////////////////////////////
// Title : 			DEFECT MAPVIEW ������Ʈ�� TABLE���� �� PRIMARY KEY����.
// Date : 		2016.10.08
// Author : 		�賲ȣ
// Comment : 	COLUMN ARRAY�� ��ӹ޾Ƽ� CREATE TABLE �ڵ��� ����ȭ.
//////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
		Statement stmt = conn.createStatement(); 
		int i=0;
		
		//gls_dat ������ ���� ���̺� ����
		
		String Qtxt1="create table gls_tbl_t(";
		while(i<gls_column.length){
			Qtxt1=Qtxt1+gls_column[i]+" "+gls_type[i]+", "; i++;
		}
		Qtxt1=Qtxt1+"constraint gls_tbl_t_pk primary key(gls_id))";
		
		stmt.execute(Qtxt1);
		
		//pnl_dat ������ ���� ���̺� ����
		i = 0;
		Qtxt1="create table pnl_tbl_t(";
		while(i<pnl_column.length){
			Qtxt1=Qtxt1+pnl_column[i]+" "+pnl_type[i]+", "; i++;
		}
		Qtxt1=Qtxt1+"constraint pnl_tbl_t_pk primary key(pnl_id))"; 
		//foreign key�� �̸� �����ϴϱ� insert�ÿ� �����Ͱ� �ƹ��͵� �Էµ��� �ʴ� �����߻�
		//error log�� ���� �ʴ� ���� �߻�... �׷��� foreign key�� ������ �Է� �Ϸ��� ��������.
		
		stmt.execute(Qtxt1);
		
		
		//dft_dat ������ ���� ���̺� ����
		i = 0;
		Qtxt1="create table dft_tbl_t(";
		while(i<dft_column.length){
			Qtxt1=Qtxt1+dft_column[i]+" "+dft_type[i]+", "; i++;
		}
		Qtxt1=Qtxt1+"constraint dft_tbl_t_pk primary key(seq))";
		//foreign key�� �̸� �����ϴϱ� insert�ÿ� �����Ͱ� �ƹ��͵� �Էµ��� �ʴ� �����߻�
		//error log�� ���� �ʴ� ���� �߻�... �׷��� foreign key�� ������ �Է� �Ϸ��� ��������.
		
		stmt.execute(Qtxt1);
		
		Qtxt1="create sequence dft_tbl_t_seq start with 1 increment by 1 nocycle nocache"; 
		stmt.execute(Qtxt1); //dft_tbl�� P.K.�� ������ sequence����.
		
		stmt.close();
		conn.close();
	}
}
	