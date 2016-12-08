package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Drop_tbl {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
		Statement stmt = conn.createStatement(); 
		
		stmt.execute("drop table gls_tbl_t cascade constraint");
		stmt.execute("drop table pnl_tbl_t cascade constraint");
		stmt.execute("drop table dft_tbl_t cascade constraint");
		stmt.execute("drop sequence dft_tbl_t_seq");
		
		stmt.close();
		conn.close();
		

	}

}