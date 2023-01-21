package jdbc01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class DBMain {
	
	// ** 전역변수 정의
	private static Connection cn = DBConnection.getConnection();
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;
	
	// < Student List 출력하기 >
	public static void selectList() {
		
		// 1) DB Connection
		// - 전역변수로 전달받음
		
		// 2) SQL 구문 실행
		// 세미콜론(;) 사용 없이 문장만 넣어주면 됨.
		sql = "select * from student"; // 모든 학생
		//sql = "select * from student where MOD(snum, 2) = 0";
		// -> snum 짝수인 학생만 출력
		
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			
			// => select문 결과가 있는지 확인
			if (rs.next()) { // next값(다음 행)이 있으면 true - 출력
				// 3) 결과출력
				System.out.println("< Student List >");
				System.out.println(" sname | age |  info  | jno | height | ID |");
				
				// for, while문을 사용하게 되면 next()를 계속 써야한다.
				do {
					// get컬럼타입(인덱스(1부터 시작) or 컬럼명)
					System.out.print(rs.getInt(1) + " ");
					System.out.print(rs.getString("sname") + " ");
					System.out.print(rs.getInt(3) + " ");
					System.out.print(rs.getString(4) + " ");
					System.out.print(rs.getInt(5) + " ");
					System.out.print(rs.getDouble(6) + " ");
					System.out.print(rs.getString("ID") + "\n");
				} while(rs.next()); // rs에 next()가 있을 때 까지
				
			} else {
				System.out.println("< selectList : 출력자료가 한 건도 없음 >");
			} // if
			
		} catch (Exception e) {
			System.out.println("** selectList Exception => " + e.toString());
		} // t~c
		
	} // selectList
	
//----------------------------------------------------------------------------------------------------------------
	
	public static void joList(int jno) {

		// 1) DB Connection
		// - 전역변수로 전달받음

		// 2) SQL 구문 실행
		sql = "select * from student where jno = " + jno;
		// select * from student where jno = 1

		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			
			// => select문 결과가 있는지 확인
			if (rs.next()) { // next값(다음 행)이 있으면 true - 출력
				// 3) 결과출력
				System.out.println("< Jo List >");
				System.out.println(" sname | age |  info  | jno | height | ID |");
				// for, while문을 사용하게 되면 next()를 계속 써야한다.
				do {
					// get컬럼타입(인덱스(1부터 시작) or 컬럼명)
					System.out.print(rs.getInt(1) + " ");
					System.out.print(rs.getString("sname") + " ");
					System.out.print(rs.getInt(3) + " ");
					System.out.print(rs.getString(4) + " ");
					System.out.print(rs.getInt(5) + " ");
					System.out.print(rs.getDouble(6) + " ");
					System.out.print(rs.getString(7) + "\n");
				} while(rs.next()); // rs에 next()가 있을 때 까지

			} else {
				System.out.println("< joList : 출력자료가 한 건도 없음 >");
			} // if

		} catch (Exception e) {
			System.out.println("** joList Exception => " + e.toString());
		} // t~c

	} // joList
	
//----------------------------------------------------------------------------------------------------------------
	/*
	 < 매개변수 Test >
	 - insert 구문
	   insert into student(sname, age, info, jno, height, ID) values('김길동', 22, 'Test Data', 6, 150.258, 'cherry');
	 -> Statement 객체 이용
	   "insert into student(sname, age, info, jno, height, ID) values('" + name + "', " + age + ", '" + info + "', " + jno + ", " + height + ", '" + ID + "')";
	 => 위 문자열 더하기의 불편함을 개선한 것이 "PreparedStatement"이다.
	    PreparedStatement에서는 "바인딩변수(실행 시 값을 받아 처리하는 변수)" ?(물음표)를 이용함.
	   insert into student(sname, age, info, jno, height, ID) values(?, ?, ?, ?, ?, ?);
	*/
	
	// ** PreparedStatement Test
	public static void joListPST(int jno) {

		sql = "select * from student where jno = ?";
		// select * from student where jno = (int jno)

		try {
			pst = cn.prepareStatement(sql); // 현재 - ?(바인딩변수)가 포함된 미완성의 sql구문
			pst.setInt(1, jno); // ?의 값을 할당하므로 sql구문 완성(? 처리)
			// set타입(? 자리의 index, ?의 value)
			rs = pst.executeQuery(); // 위에서 ?처리한 pst 출력

			// => select문 결과가 있는지 확인
			if (rs.next()) { // next값(다음 행)이 있으면 true - 출력
				// 3) 결과출력
				System.out.println("< Jo List PST >");
				System.out.println(" sname | age |  info  | jno | height | ID |");
				// for, while문을 사용하게 되면 next()를 계속 써야한다.
				do {
					// get컬럼타입(인덱스(1부터 시작) or 컬럼명)
					System.out.print(rs.getInt(1) + " ");
					System.out.print(rs.getString("sname") + " ");
					System.out.print(rs.getInt(3) + " ");
					System.out.print(rs.getString(4) + " ");
					System.out.print(rs.getInt(5) + " ");
					System.out.print(rs.getDouble(6) + " ");
					System.out.print(rs.getString(7) + "\n");
				} while(rs.next()); // rs에 next()가 있을 때 까지

			} else {
				System.out.println("< joListPST : 출력자료가 한 건도 없음 >");
			} // if

		} catch (Exception e) {
			System.out.println("** joListPST Exception => " + e.toString());
		} // t~c

	} // joListPST

//==================================================================================================================

	public static void main(String[] args) {

		// Test 1) Connection
		System.out.println("** main, Connection => " + cn);
		// === JDBC Connection 실패 => java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver
		// 실행을 위한 JDBC 드라이버(.jar)를 찾지 못 함.
		/*
		 * JDBC 드라이브 넣기
		 Project 우클릭 - Build Path - Configure Build Path - Libraries 
		    - Classpath - Add External JARs - IDEset - Connector J
		 */

	//--------------------------------------------------------------------

		// Test 2) Student List
		selectList();

	//--------------------------------------------------------------------

		// Test 3) Jo List
		// - Scanner로 jno 입력받아 출력하기
		Scanner sc = new Scanner(System.in);

		System.out.println("\n** 출력하려는 jno을 입력하세요 : ");

		joList(sc.nextInt());
	
	//--------------------------------------------------------------------
		
		// Test 4) Jo List PST
		System.out.println("\n** PST Test : jno을 입력하세요 : ");

		joListPST(sc.nextInt());

		sc.close();

	} // main

} // class
