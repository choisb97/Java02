package jdbc02; 
// Table 안 Data에 직접 접근하는 곳(청와대) - 굉장히 중요

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc01.DBConnection;

/*
 < DAO(Data Access Object) >
 - SQL구문 처리
 - CRUD 구현
   Create(insert), Read(select), Update, Delete 
 */

/*
  < Execute, ExecuteQuery, ExecuteUpdate 메서드 >
  1) Execute
  => Boolean Type의 값 return
     -> true : result / false : update count or no result
  => 모든 구문을 수행(Select, Insert, Update, Delete, DDL 문을 모두 실행)
  => 리턴값이 ResultSet인 경우에는 true, 이 외의 경우에는 false로 출력
  => 리턴값이 ResultSet라고 하여 ResultSet 객체에 결괏값을 담을 수 없음

  2) ExecuteQuery
  => ResultSet 객체의 값 return
     -> ResultSet : object that contains the data produced by the given query
  => SELECT 구문을 수행할 때 사용되는 함수
  => ResultSet 객체에 결괏값을 담을 수 있음


  3) ExecuteUpdate
  => Int Type의 값 return
     -> int : row count / 없는 경우 : 0
  => SELECT 구문을 제외한 다른 구문을 수행할 때 사용되는 함수
  => INSERT / DELETE / UPDATE 관련 구문에서는 반영된 레코드(Row)의 건수를 반환
  => CREATE / DROP 관련 구문에서는 -1 을 반환
 */

public class StudentDAO {

	// ** 전역변수 정의
	private static Connection cn = DBConnection.getConnection();
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String sql;

//=======================================================================================================

	// 1. Select(Read)

	// 1-1) Student List 출력하기

	public List<StudentVO> selectList() {

		sql = "select * from student"; // 모든 학생

		// ** 결과를 담을 컬렉션(데이터 담아놓는 클래스) 정의(ArrayList가 가장 적합)
		// 좌측은 List로 우측 ArrayList의 조상 Type으로 설정.
		List<StudentVO> list = new ArrayList<>();
		// StudentVO Type의 ArrayList 생성 -> return ArrayList<StudentVO>

		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);

			// => select문 결과가 있는지 확인
			if (rs.next()) {

				// 결과를 모아 담아서 요청한 곳(서비스 -> 컨트롤러)로 전달
				// StudentVO vos = new StudentVO(); -> 컴파일 에러는 아니지만 데이터 출력 오류
				// 반복문 밖에 사용하면 추가되는 데이터들이 동일한 주솟값을 가지며, 마지막에 추가된 데이터만 반복문만큼 출력된다.
				do {
					StudentVO vo = new StudentVO();

					// 위 sql구문의 Column 순서와 타입이 동일해야 함.
					vo.setSnum(rs.getInt(1));
					vo.setSname(rs.getString("sname"));
					vo.setAge(rs.getInt(3));
					vo.setInfo(rs.getString("info"));
					vo.setJno(rs.getInt("jno"));
					vo.setHeight(rs.getDouble(6));
					vo.setId(rs.getString(7));
					
					list.add(vo);

				} while(rs.next()); // rs에 next()가 있을 때 까지, 없으면 반복문 종료

				return list; // do ~ while 반복문 끝나면 list return

			} else {
				System.out.println("< selectList : 출력자료가 한 건도 없음 >");
				return null;
			} // if

		} catch (Exception e) {
			System.out.println("\n** selectList Exception => " + e.toString());
			return null;
			// catch 안에 return 써야 try return이 없는 경우 컴파일 에러 발생 확인가능.
		} // t~c

		// return null; => t~c 밖에 사용해서 else이거나 catch로 넘어가는 경우 return null 한 번에 사용 가능
		// -> 초보의 경우 각각의 구문 안에 return 사용하는 것이 안전
		
	} // selectList

//---------------------------------------------------------------------------------------------------------

	// 1-2) Student Jo List 출력하기
	// ** PreparedStatement Test
	// => 매개변수, return값 적용하기

	public List<StudentVO> joListPST(StudentVO vo) {

		sql = "select * from student where jno = ?";

		List<StudentVO> list = new ArrayList<>();

		try {
			pst = cn.prepareStatement(sql); // 현재 - ?(바인딩변수)가 포함된 미완성의 sql구문
			pst.setInt(1, vo.getJno()); // ?의 값을 할당하므로 sql구문 완성(? 처리)
			// set타입(? 자리의 index, ?의 value)
			rs = pst.executeQuery(); // 위에서 ?처리한 pst 출력

			// => select문 결과가 있는지 확인
			if (rs.next()) { // next값(다음 행)이 있으면 true - 출력

				// 3) 결과출력
				// StudentVO vos = new StudentVO(); -> 컴파일 에러는 아니지만 데이터 출력 오류
				// 반복문 밖에 사용하면 추가되는 데이터들이 동일한 주솟값을 가지며, Scanner로 입력한 데이터만 반복문만큼 출력된다.

				do {
					StudentVO vos = new StudentVO();
					// 반복문 안에 인스턴스를 생성해야 추가되는 데이터마다 다른 주솟값을 가지며 정상적인 데이터가 출력된다.

					vos.setSnum(rs.getInt(1));
					vos.setSname(rs.getString("sname"));
					vos.setAge(rs.getInt(3));
					vos.setInfo(rs.getString("info"));
					vos.setJno(rs.getInt("jno"));
					vos.setHeight(rs.getDouble(6));
					vos.setId(rs.getString(7));

					list.add(vos);

				} while(rs.next()); // rs에 next()가 있을 때 까지, 없으면 반복문 종료

				return list; // 반복문 문제없이 종료 시 list return

			} else {
				System.out.println("< joListPST : 출력자료가 한 건도 없음 >");
				return null;
			} // if

		} catch (Exception e) {
			System.out.println("\n** joListPST Exception => " + e.toString());
			return null;
		} // t~c

	} // joListPST

//--------------------------------------------------------------------------------------------------

	// 1-3) 내 정보(한 사람) 보기 - PST(PreparedStatement)

	public StudentVO selectOne(StudentVO vo) {

		sql = "select * from student where snum = ?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, vo.getSnum());
			rs = pst.executeQuery();

			if (rs.next()) {
				vo.setSnum(rs.getInt(1));
				vo.setSname(rs.getString("sname"));
				vo.setAge(rs.getInt(3));
				vo.setInfo(rs.getString("info"));
				vo.setJno(rs.getInt("jno"));
				vo.setHeight(rs.getDouble(6));
				vo.setId(rs.getString(7));

				return vo;

			} else {
				System.out.println("** snum에 해당하는 학생이 존재하지 않습니다.");
				return null;
			} // if

		} catch (Exception e) {
			System.out.println("\n** selectOne Exception => " + e.toString());
			return null;
		} // t~c

	} // selectOne

//========================================================================================================

	// executeUpdate()의 return Type이 Int이기 때문에 public int 사용

	// 2. Insert(Create)
	// - Insert의 성공여부는 실행된 레코드(Row)개수로 확인 (0이면 실패)

	public int insert(StudentVO vo) {
		sql = "insert into student(sname, age, info, jno, height, id)values(?, ?, ?, ?, ?, ?)";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getSname());
			pst.setInt(2, vo.getAge());
			pst.setString(3, vo.getInfo());
			pst.setInt(4, vo.getJno());
			pst.setDouble(5, vo.getHeight());
			pst.setString(6, vo.getId());

			return pst.executeUpdate(); // 실행된 레코드(Row) 개수(int) return

		} catch (Exception e) {
			System.out.println("\n** Insert Exception => " + e.toString());
			return 0; // 0이냐 1이상이 나오냐에 따라 Insert 성공(실행된 레코드 개수), 실패(0) 여부 확인 가능.
			// catch 안에 return 써야 try return이 없는 경우 컴파일 에러 발생 확인가능.
		} // t~c

		// return 0; -> t~c 밖에 쓰면 try return이 없는 경우와 catch 모두 0 출력됨 - 위험

	} // insert

//========================================================================================================

	// 3. Update

	// => sname으로 찾고, age와 ID 수정
	public int update(StudentVO vo) {

		sql = "update student set age = ?, id = ? where sname = ?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, vo.getAge());
			pst.setString(2, vo.getId());
			pst.setString(3, vo.getSname());

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("\n** Update Exception => " + e.toString());
			return 0;
		} // t~c

	} // update

//========================================================================================================

	// 4. Delete

	// => sname으로 찾아서 삭제
	public int delete(StudentVO vo) {

		sql = "delete from student where sname = ?";

		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getSname());

			return pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("\n** Delete Exception => " + e.toString());
			return 0;
		} // t~c

	} // delete

//========================================================================================================

	// 5. 실습
	// => 조별 인원수, sum(age), avg(age), max(age), min(age) 출력하기
	//   -> VO 클래스 대신 DTO 클래스 따로 생성
	
	// ** Group 함수
	public List<GroupDTO> groupTest() {

		// SQL함수는 ?(바인딩변수) 사용 불가 / 값이 아닌 컬럼명은 ? 사용 불가
		sql = "select jno, count(*), sum(age), avg(age), max(age), min(age) "
				+ "from student group by jno order by jno";

		List<GroupDTO> list = new ArrayList<>();

		try {
			// ?(바인딩변수) 없어도 PreparedStatement 사용 가능
			pst = cn.prepareStatement(sql);
			rs = pst.executeQuery(); // 위에서 sql문을 넣었기 때문에 executeQuery는 비워둔다.
			// 모든 select문의 결과는 ResultSet으로 출력해야 한다.

			if (rs.next()) {

				do {
					GroupDTO dto = new GroupDTO();
					
					dto.setJno(rs.getInt(1));
					dto.setCount(rs.getInt(2));
					dto.setSum(rs.getInt(3));
					dto.setAvg(rs.getDouble(4));
					dto.setMax(rs.getInt(5));
					dto.setMin(rs.getInt(6));

					list.add(dto);
					
				} while (rs.next());

				return list;

			} else {
				System.out.println("< groupTest : 출력자료가 한 건도 없음 >");
				return null;
			} // if

		} catch (Exception e) {
			System.out.println("\n** groupTest Exception => " + e.toString());
			return null;
		} // t~c

	} // groupTest

//========================================================================================================

	// 6. Transaction Test
	/*
		 - Connection 객체가 관리
		 - 기본값은 AutoCommit true.
		 - setAutoCommit(false) -> commit 또는 rollback
	 */
	/*
	 6-1) Test 1
	 => Transaction 적용하지 않고 오류 유발
	 => 동일 자료 2회 입력 : 첫 번째는 입력되고, 두 번째 입력에서 Exception 발생
	 => Data의 무결성에 영향을 주게 됨.

	 6-2) Test 2
	 => Transaction 적용하고 오류 유발
	 => 동일 자료 2회 입력 : 첫 번째 입력 후, 두 번째 입력에서 Exception 발생하지만 모두 rollback 됨.
	 => 즉, 모두 입력되지 않음
	*/
//---------------------------------------------------------------------------------------------

	/*
		// ** Transaction Test와 Insert 메서드 두 개로 Test

		public void transactionTest() {
			// 6-1) Test 1
			// => 동일 자료 2회 입력 : 첫 번째는 입력되고, 두 번째 입력에서 Key 중복으로 Exception 발생
			//    my통장 -10000은 처리되고, u통장 +10000에서 오류 발생 -> Data 무결성 오류 발생

			//transactionInsert(); // insert OK
			//transactionInsert(); // Exception : Duplicate entry '77'...

		//------------------------------------------------------------------------------

			// Test 2 하려면 Test 1 transactionInsert(); 두 줄 주석

			// 6-2) Test 2
			// => Transaction 적용하고 오류 유발
			// => 동일 자료 2회 입력 : 첫 번째 입력 후, 두 번째 입력에서 Exception 발생하지만 모두 rollback 됨.

			try {
				cn.setAutoCommit(false); // false : autoCommit 안 됨. default : true

				transactionInsert(); // OK
				transactionInsert(); // Exception -> RollBack

				// 정상실행 => commit
				cn.commit();

			} catch (Exception e) {
				System.out.println("\n** transactionTest Exception => " + e.toString());

				// ** Exception 발생 => RollBack
				try { // ** try ~ catch문도 중첩 가능 **
					cn.rollback(); // rollback()은 try 안에 작성해야 됨.
					System.out.println("\n< transactionTest RollBack 성공 >");
				} catch (Exception e2) {
					System.out.println("\n** transactionTest RollBack 실패 => " + e2.toString());
				} // t~c_rollback
			} // t~c_commit

		} // transactionTest

	//------------------------------------------------------------------------------------------------------------	

		// 6. Transaction Test Insert

		// ** Transaction Insert 구문을 별도 메서드로 작성하는 경우
		// => Exception을 직접 처리하지 않고 호출메서드인 transactionTest에서 처리하도록
		//    throws 해줘야 함.
		public int transactionInsert() throws Exception {
			sql = "insert into student values(99, '가나다', 77, 'Transaction Test', 7, 123.45, 'IDIDID')";

			pst = cn.prepareStatement(sql);

			return pst.executeUpdate();

		} // transactionInsert
		
		// ** Transaction Test와 Insert 메서드 두 개로 Test
	
	*/ 
	
//========================================================================================================

	public void transactionTest() {
		sql = "insert into student values(, '가나다', 77, 'Transaction Test', 7, 123.45, 'IDIDID')";

		// 6-1) Test 1
		// => 동일 자료 2회 입력 : 첫 번째는 입력되고, 두 번째 입력에서 Key 중복으로 Exception 발생
		//    my통장 -10000은 처리되고, u통장 +10000에서 오류 발생 -> Data 무결성 오류 발생

		try {
			pst = cn.prepareStatement(sql);
			pst.executeUpdate();
			pst.executeUpdate();

		} catch (Exception e) {
			System.out.println("** transactionTest 1 Exception => " + e.toString());
		}

		//---------------------------------------------------------------------------------------

		// 6-2) Test 2
		// => Transaction 적용하고 오류 유발
		// => 동일 자료 2회 입력 : 첫 번째 입력 후, 두 번째 입력에서 Exception 발생하지만 모두 rollback 됨.

		try {
			cn.setAutoCommit(false);

			pst = cn.prepareStatement(sql);
			pst.executeUpdate(); // OK
			pst.executeUpdate(); // Exception -> RollBack

			// ** 정상실행 => commit
			cn.commit();

		} catch (Exception e) {
			System.out.println("\n** transactionTest 2 Exception => " + e.toString());

			// ** Exception 발생 => RollBack
			try { // try~catch 중첩 가능
				cn.rollback();
				System.out.println("\n< transactionTest RollBack 성공 >");

			} catch (Exception e2) {
				System.out.println("\n** transactionTest RollBack 실패 => " + e2.toString());
			} // t~c_rollback
		} // t~c_commit

	} // transactionTest

	
} // class
