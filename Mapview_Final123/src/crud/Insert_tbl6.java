package crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Insert_tbl6 extends ColumnArray {
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Title : 			DEFECT MAPVIEW ������Ʈ - DATA INSERTING.
// Date : 		2016.10.08
// Author : 		�賲ȣ
// Comment : 	INSERT �۾��� �ڵ��� ����ȭ �� ����Ʈ�ڵ� �ϱ� ���� PREPAREDSTATEMENT���
//					���� ���α׷��� ������ ���� ��������� �����ϰ�, �ڹٿ��� ��� �� ���Ͽ���, DATA���� �� �з� ����. 
//					�� ������ 0, 1���ʵ�(DATA ����, FIELD_COUNT)�� DB�� �Է��� �ʿ䰡 ���� ������ �Ǵ��Ͽ� �Է�����.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
				
		//�����͸� �з��Ͽ� �� ���̺� insert
		
		String sql_1 = "Insert Into gls_tbl_t values(";
		int i=0;
		int j = gls_column.length;
		while (i < j) {
			String Append = (i < j - 1) ? "?," //������ ���� ������ ���� �ѹ��� ����, ���� dbms error�� ����� ���ؼ�.
					: "?)log errors into err$_gls_tbl('insert error') reject limit unlimited"; 
			sql_1 = sql_1 + Append;
			i++;
		}
		
		String sql_2 = "Insert Into pnl_tbl_t values(";
		i = 0;
		j = pnl_column.length;
		while (i < j) {
			String Append = (i < j - 1) ? "?,"
					: "?)log errors into err$_pnl_tbl('insert error') reject limit unlimited";
			sql_2 = sql_2 + Append;
			i++;
		}
		
		String sql_3 = "Insert Into dft_tbl_t values(dft_tbl_t_seq.nextval,"; // sequence�� ù��° Į���� �Է�.
		i = 1; // �ι�° Į������ ����.
		j = dft_column.length;
		while (i < j) {
			String Append = (i < j - 1) ? "?,"
					: "?)log errors into err$_dft_tbl('insert error') reject limit unlimited";
			sql_3 = sql_3 + Append;
			i++;
		}
		
		
		
		String path="i:\\Defect MapView\\data\\";
		File dir=new File(path);
		File [] fileList=dir.listFiles(); int m=1;
		for(File dataFile : fileList) {
			System.out.println("*******************************");
			System.out.println(m+" ��° ���Ͽ���");
			System.out.println("*******************************");
			PreparedStatement pstmt_1 = null;
			PreparedStatement pstmt_2 = null;
			PreparedStatement pstmt_3 = null;
			
		    String dataFileName=dataFile.getName();
		    
		    File f = new File(path + dataFileName); // path��� dir(c:\\Maview\\data)�� ������ �����߻��Ѵ�.
			BufferedReader br = new BufferedReader(new FileReader(f));
			String readtxt;

			if ((readtxt = br.readLine()) == null) { //ù°������ INF�̹Ƿ� ���⼭ READ�� �� ������ �ʴ´�.
				System.out.printf("Empty\n");
				br.close();
				return;
			}
			int LineCnt = 2; 
			String gls_id=""; String pnl_id="";
			
			while ((readtxt = br.readLine()) != null) { // ��°���κ��� READ
				String field[] = readtxt.split("\\^"); //�齽����������, ^�� ����ǥ���ı�ȣ�� ���ֵȴ�.
								
								
				ArrayList<String> fieldarray = new ArrayList<String>();				
				
				
				if(field[0].equals("GLS_DAT")){
					
					gls_id=field[3]; // pnl_tbl ���̺� �߰��Ͽ� �������ǿ� ����� �ʵ�
					
					field[16]=field[16].replace("_","");
					field[17]=field[17].replace("_","");
					
//					String start[]=field[16].split("_"); // start��¥�ð� �����͸� ��¥�� �ð����� �����Ѵ�.
//					String end[]=field[17].split("_"); // end��¥�ð� �����͸� ��¥�� �ð����� �����Ѵ�.
//					
//					field[16] = start[0]; //start��¥�� �ٽ� 16���� ����
//					field[17] = end[0]; //end��¥�� �ٽ� 16���� ����
//					String start_time= start[1]; 
//					String end_time= end[1]; 
					
					for (i = 2; i < field.length; i++) fieldarray.add(field[i]); //1���ʵ�(FIELD_COUNT)�� �����Ѵ�.
//					fieldarray.add(start_time);
//					fieldarray.add(end_time);
					
					pstmt_1 = conn.prepareStatement(sql_1);
					
					for (i=0; i<fieldarray.size();i++){
						String value = (fieldarray.get(i).equals("***"))? "": fieldarray.get(i);
						pstmt_1.setString(i+1, value);
					}
				
					pstmt_1.executeUpdate();
					pstmt_1.clearParameters();
					pstmt_1.close(); //close�� ���ϸ��� �ѹ��� �ϰ� �Ǹ�, �ִ뿭�� Ŀ�������� ��ӹ߻�.
					
				
				}else if(field[0].equals("PNL_DAT")){
				
					pnl_id=field[2]; // dft_tbl ���̺� �߰��Ͽ� �������ǿ� ����� �ʵ�
					field[28]=field[28].replace("_",""); // ���߿� date����ȯ�� ���ؼ� �̸� "_"�� �����Ѵ�.
					field[29]=field[29].replace("_","");	
					for (i = 2; i < field.length; i++) fieldarray.add(field[i]); 
					fieldarray.add(gls_id); // �������� Į�� �߰�
					pstmt_2 = conn.prepareStatement(sql_2);
					
					for (i=0; i<fieldarray.size();i++){
						String value = (fieldarray.get(i).equals("***"))? "": fieldarray.get(i);
						pstmt_2.setString(i+1, value);
					}
				
					pstmt_2.executeUpdate();
					pstmt_2.clearParameters();	
					pstmt_2.close();
					
				
				}else if(field[0].equals("DFT_DAT")){
				
					for (i = 2; i < field.length; i++) fieldarray.add(field[i]); 
					fieldarray.add(pnl_id); // �������� Į�� �߰�
					pstmt_3 = conn.prepareStatement(sql_3);
					
					String value = "";
					for (i=0; i<fieldarray.size();i++){
						value = (fieldarray.get(i).equals("***"))? "": fieldarray.get(i);
						pstmt_3.setString(i+1, value);
					}
				
					pstmt_3.executeUpdate();
					pstmt_3.clearParameters();
					pstmt_3.close();
					
				
				}else {continue;}
				
									
				System.out.printf("%d ���� READING\n", LineCnt);					
				LineCnt++;							
				}	
			
						
			m++;			
			br.close();			
			
		}					
		conn.close();
		System.out.println("INSERTING �Ϸ�");
}
}
