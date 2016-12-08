package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Truncate_tbl {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
		Statement stmt = conn.createStatement(); 
		
		stmt.execute("truncate table gls_tbl_t"); 
		//stmt.execute("truncate table pnl_tbl cascade"); //오라클 12c에서만 가능함
		//stmt.execute("truncate table dft_tbl cascade");
		
		stmt.close();
		conn.close();
		

	}

}
