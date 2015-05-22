/* Enter file contents here
 * 2015.05.22 IngredientTable.java
 * CREATE & MANIPULATE IngredientTable DB
 * by Kim
*/


import java.sql.Connection;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientTable {
	
	public static void main (String[] args) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int action = 0;
		int num = 1;
		String ingre = null;
		String sql = null;


		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:yours", "your_id", "your_pwd");
			// Connection 얻어오기 (Connection 객체 획득)
			System.out.println("CONNECTION SUCCESS...");

			while (true) {
				
				System.out.println("1. 테이블 생성\n2. 데이터 삽입\n3. 데이터 출력\n4. 테이블 삭제\n");
				Scanner scan = new Scanner(System.in);
				action = scan.nextInt(); // 메뉴 선택
				scan.nextLine();
				
				if (action==1) {
					
					sql = "CREATE TABLE INGREDIENT ("
							+ "INGRE_NUM INTEGER PRIMARY KEY NOT NULL,"
							+ "INGRE_NAME VARCHAR(30) NOT NULL)";
				         
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					System.out.println("CREATE TABLE SUCCESS...\n\n");
				}

				else if (action==2) {

					System.out.println("재료 이름을 입력하세요 : ");
					ingre = scan.next(); // 이름 입력
					scan.nextLine();

					// SQL Query를 생성, 실행하며, 반환된 결과를 가져오게 할 작업 영역을 제공
					sql = "INSERT into INGREDIENT values ("+num+",'"+ingre+"')";
					num++;
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					
					System.out.println("INSERT SUCCESS...\n\n");
				}

				else if (action==3) {
					
					sql = "select * from INGREDIENT";
					stmt = conn.prepareStatement(sql);
					rs = stmt.executeQuery(sql);
					// PreparedStatement 객체의 executeQuery() 메소드로 Query 수행
					// executeQuery() : Select문에서 사용, ResultSet 반환
					
					// 결과 출력
					System.out.println("\nINGRE_NUM\tINGRE_NAME\n--------------------------------------------");
					while (rs.next()) // rs가 한 개 이상이라면 true, 0개라면 false 반환
						System.out.println(rs.getString(1)+"\t\t"+rs.getString(2));
					System.out.println("");
					
					System.out.println("SELECT SUCCESS...\n\n");
				}
				
				else if (action==4) {
					
					sql = "DROP TABLE INGREDIENT";
				         
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					System.out.println("DROP TABLE SUCCESS...\n\n");
				}
				
				else {		
					
					System.out.println("NO ACTION...\n\n");
					break;
				}
			} // while문 종료
			
		} catch (SQLException e) { // Query에서 에러 발생 시 동작
			// TODO Auto-generated catch block
			e.printStackTrace(); // 에러 메시지의 발생 근원지를 찾아서 단계별로 에러를 출력
		
		} finally {	
			try {
				if (rs!=null)
					rs.close();
				if (stmt!=null)
					stmt.close();
				if (conn!=null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		
	} // main 종료
}
