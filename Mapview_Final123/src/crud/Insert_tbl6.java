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
// Title : 			DEFECT MAPVIEW 프로젝트 - DATA INSERTING.
// Date : 		2016.10.08
// Author : 		김남호
// Comment : 	INSERT 작업의 코딩을 간소화 및 소프트코딩 하기 위해 PREPAREDSTATEMENT사용
//					순수 프로그래밍 구현을 위해 엑셀사용을 지양하고, 자바에서 목록 및 파일열기, DATA정제 및 분류 수행. 
//					각 라인의 0, 1번필드(DATA 구분, FIELD_COUNT)는 DB에 입력할 필요가 없는 것으로 판단하여 입력제외.
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.23.100:1522:orcl2", "scott", "1234");
				
		//데이터를 분류하여 각 테이블에 insert
		
		String sql_1 = "Insert Into gls_tbl_t values(";
		int i=0;
		int j = gls_column.length;
		while (i < j) {
			String Append = (i < j - 1) ? "?," //무시할 만한 오류로 인한 롤백을 막고, 또한 dbms error를 남기기 위해서.
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
		
		String sql_3 = "Insert Into dft_tbl_t values(dft_tbl_t_seq.nextval,"; // sequence를 첫번째 칼럼에 입력.
		i = 1; // 두번째 칼럼부터 시작.
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
			System.out.println(m+" 번째 파일열기");
			System.out.println("*******************************");
			PreparedStatement pstmt_1 = null;
			PreparedStatement pstmt_2 = null;
			PreparedStatement pstmt_3 = null;
			
		    String dataFileName=dataFile.getName();
		    
		    File f = new File(path + dataFileName); // path대신 dir(c:\\Maview\\data)을 넣으면 에러발생한다.
			BufferedReader br = new BufferedReader(new FileReader(f));
			String readtxt;

			if ((readtxt = br.readLine()) == null) { //첫째라인은 INF이므로 여기서 READ된 후 쓰이지 않는다.
				System.out.printf("Empty\n");
				br.close();
				return;
			}
			int LineCnt = 2; 
			String gls_id=""; String pnl_id="";
			
			while ((readtxt = br.readLine()) != null) { // 둘째라인부터 READ
				String field[] = readtxt.split("\\^"); //백슬래쉬없으면, ^가 정규표현식기호로 간주된다.
								
								
				ArrayList<String> fieldarray = new ArrayList<String>();				
				
				
				if(field[0].equals("GLS_DAT")){
					
					gls_id=field[3]; // pnl_tbl 테이블에 추가하여 조인조건에 사용할 필드
					
					field[16]=field[16].replace("_","");
					field[17]=field[17].replace("_","");
					
//					String start[]=field[16].split("_"); // start날짜시간 데이터를 날짜와 시간으로 구분한다.
//					String end[]=field[17].split("_"); // end날짜시간 데이터를 날짜와 시간으로 구분한다.
//					
//					field[16] = start[0]; //start날짜는 다시 16번에 저장
//					field[17] = end[0]; //end날짜는 다시 16번에 저장
//					String start_time= start[1]; 
//					String end_time= end[1]; 
					
					for (i = 2; i < field.length; i++) fieldarray.add(field[i]); //1번필드(FIELD_COUNT)는 제외한다.
//					fieldarray.add(start_time);
//					fieldarray.add(end_time);
					
					pstmt_1 = conn.prepareStatement(sql_1);
					
					for (i=0; i<fieldarray.size();i++){
						String value = (fieldarray.get(i).equals("***"))? "": fieldarray.get(i);
						pstmt_1.setString(i+1, value);
					}
				
					pstmt_1.executeUpdate();
					pstmt_1.clearParameters();
					pstmt_1.close(); //close를 파일마다 한번씩 하게 되면, 최대열기 커서오류가 계속발생.
					
				
				}else if(field[0].equals("PNL_DAT")){
				
					pnl_id=field[2]; // dft_tbl 테이블에 추가하여 조인조건에 사용할 필드
					field[28]=field[28].replace("_",""); // 나중에 date형변환을 위해서 미리 "_"를 삭제한다.
					field[29]=field[29].replace("_","");	
					for (i = 2; i < field.length; i++) fieldarray.add(field[i]); 
					fieldarray.add(gls_id); // 조인조건 칼럼 추가
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
					fieldarray.add(pnl_id); // 조인조건 칼럼 추가
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
				
									
				System.out.printf("%d 라인 READING\n", LineCnt);					
				LineCnt++;							
				}	
			
						
			m++;			
			br.close();			
			
		}					
		conn.close();
		System.out.println("INSERTING 완료");
}
}
