package crud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Create_tbl5 extends ColumnArray {
	
//////////////////////////////////////////////////////////////////////////////////////
// Title : 			DEFECT MAPVIEW 프로젝트의 TABLE생성 및 PRIMARY KEY설정.
// Date : 		2016.10.08
// Author : 		김남호
// Comment : 	COLUMN ARRAY를 상속받아서 CREATE TABLE 코딩을 간소화.
//////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
		Statement stmt = conn.createStatement(); 
		int i=0;
		
		//gls_dat 정보를 담을 테이블 생성
		
		String Qtxt1="create table gls_tbl_t(";
		while(i<gls_column.length){
			Qtxt1=Qtxt1+gls_column[i]+" "+gls_type[i]+", "; i++;
		}
		Qtxt1=Qtxt1+"constraint gls_tbl_t_pk primary key(gls_id))";
		
		stmt.execute(Qtxt1);
		
		//pnl_dat 정보를 담을 테이블 생성
		i = 0;
		Qtxt1="create table pnl_tbl_t(";
		while(i<pnl_column.length){
			Qtxt1=Qtxt1+pnl_column[i]+" "+pnl_type[i]+", "; i++;
		}
		Qtxt1=Qtxt1+"constraint pnl_tbl_t_pk primary key(pnl_id))"; 
		//foreign key를 미리 설정하니까 insert시에 데이터가 아무것도 입력되지 않는 문제발생
		//error log도 남지 않는 문제 발생... 그래서 foreign key는 데이터 입력 완료후 설정예정.
		
		stmt.execute(Qtxt1);
		
		
		//dft_dat 정보를 담을 테이블 생성
		i = 0;
		Qtxt1="create table dft_tbl_t(";
		while(i<dft_column.length){
			Qtxt1=Qtxt1+dft_column[i]+" "+dft_type[i]+", "; i++;
		}
		Qtxt1=Qtxt1+"constraint dft_tbl_t_pk primary key(seq))";
		//foreign key를 미리 설정하니까 insert시에 데이터가 아무것도 입력되지 않는 문제발생
		//error log도 남지 않는 문제 발생... 그래서 foreign key는 데이터 입력 완료후 설정예정.
		
		stmt.execute(Qtxt1);
		
		Qtxt1="create sequence dft_tbl_t_seq start with 1 increment by 1 nocycle nocache"; 
		stmt.execute(Qtxt1); //dft_tbl의 P.K.로 설정할 sequence생성.
		
		stmt.close();
		conn.close();
	}
}
	