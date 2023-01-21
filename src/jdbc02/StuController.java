package jdbc02; 
// 클라이언트가 들락날락 하는 곳(전방) - 위험요소가 많음
// 1년 365일 24시간 서비스가 가능해야하는 영역으로, 최대한 셧다운 같은 일을 줄여야 한다.

import java.util.*;
/*
import java.util.List;
import java.util.Scanner;
*/

public class StuController {
	
	// ** 전역 인스턴스 생성 **
	StudentService service = new StudentService();
	
	// 1. Select(Read)
	
	// 1-1) Student List 출력하기
	public void selectList() {
		
		List<StudentVO> list = service.selectList();
		System.out.println("\n< Student List >");

		for(StudentVO s : list) {
			// StudentVO Type의 s.getBlah를 list(배열)에 넣는다.
			System.out.println(s);
		} // eachfor
		
	} // selectList
	
//--------------------------------------------------------------------------
	
	// 1-2) Student JoList_PST 출력하기
	public void joListPST(StudentVO vo) {
		
		List<StudentVO> list = service.joListPST(vo);
		System.out.println("\n< Jo List PST >");

		for(StudentVO s : list) {
			System.out.println(s);
		} // eachfor
		
		// < for each 문 >
		// - 배열의 값만 순차적으로 사용가능하며 read만 가능. write 불가능 -> index 없기 때문에 접근(수정) 불가능.
		// for ( 변수타입 변수명 : 배열 이름 ) { 실행부 }
		
	} // joListPST
	
//--------------------------------------------------------------------------

	// 1-3) 내 정보(한 사람) 보기 - PST(PreparedStatement)
	public void selectOne(StudentVO vo) {
		
		System.out.println("< Student Info >");
		System.out.println(service.selectOne(vo));
		
	} // selectOne
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// 2. Insert(Create)
	public void insert(StudentVO vo) {
		
		if (service.insert(vo) > 0) { // 출력되는 레코드(row)의 개수가 0보다 많으면
			System.out.println("\n< Insert 성공 >");
			System.out.println(vo);
		} else {
			System.out.println("\n< Insert 실패 >");
		} // if
		
	} // insert
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// 3. Update
	
	public void update(StudentVO vo) {
		
		int count = service.update(vo); // count변수 생성
		
		if (count > 0)
			System.out.println("\n< Update 성공 : " + count + "개 수정 >");
		else
			System.out.println("\n< Update 실패 >");
		
	} // update
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// 4. Delete
	
	public void delete(StudentVO vo) {
		
		int count = service.delete(vo);
		
		if (count > 0)
			System.out.println("\n < Delete 성공 : " + count + "개 삭제 >");
		else
			System.out.println("\n< Delete 실패 >");
		
	} // delete
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// 5. 실습 : Group Test
	// => 조별 인원수, sum(age), avg(age), max(age), min(age) 출력하기
	public void groupTest() {

		List<GroupDTO> list = service.groupTest();
		System.out.println("\n< GroupTest(조별) List >");

		for(GroupDTO g : list) {
			// GroupDTO Type의 g.getBlah를 list(배열)에 넣는다.
			System.out.println(g);
		} // eachfor

	} // groupTest
	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	// 6. Transaction Test
	public void transactionTest() {
		service.transactionTest();
	} // transactionTest
	
	
//==============================================================================================	

	public static void main(String[] args) {
		
		StuController student = new StuController();
		/*
		// 1. Select(Read)
		
		// 1-1) Student List
		StudentVO vo = new StudentVO();
		// => Controller : 요청, 요청결과 View 처리 -> Service Class -> DAO Class : DB 처리
		
		student.selectList();
		
	//---------------------------------------------------------------------------------
		
		// 1-2) Student JOList_PST
		// => Scanner로 jno 입력받아 출력하기
		Scanner sc = new Scanner(System.in);
		System.out.println("\n** 출력하려는 jno을 입력하세요 : ");
		
		vo.setJno(sc.nextInt());
		student.joListPST(vo);
		
	//---------------------------------------------------------------------------------

		// 1-3) 내 정보(한 사람) 보기
		System.out.println("\n**출력하려는 snum을 입력하세요 : ");
		
		vo.setSnum(sc.nextInt());
		student.selectOne(vo);
		
		sc.close();
		
//====================================================================================================
		
		// 2. Insert(Create)
		// - Data를 변경하고 Insert 성공하면 출력할 때 마다 auto_increment로 인해 레코드(Row) 계속 추가됨
		
		vo.setSnum(0); // snum은 auto_increment(자동 숫자증가)이므로 알 수 없음
		vo.setSname("가나다");
		vo.setAge(33);
		vo.setInfo("Insert Test");
		vo.setJno(6);
		vo.setHeight(123.45);
		vo.setId("black");
		//vo.setId("blackblackblack"); // Insert 실패 Test 
		// ID는 varchar(10)이므로 -> Exception - Data too long for column 'ID'
		
		// 값 변경하고 Insert 성공하면 출력 시 auto_increment로 자꾸 추가됨
		
		student.insert(vo);
		// Update Test 위해 주석
		
//====================================================================================================
	
		// 3. Update
		
		vo.setSname("가나다");
		vo.setAge(99);
		vo.setId("Yellow");
		//vo.setId("YellowYellow"); // Update 실패 Test
		
		student.update(vo);
		
		// Update 결과 확인
		student.selectList();
		
//====================================================================================================

		// 4. Delete
		
		vo.setSname("가나다");
		
		student.delete(vo);
		
		// Delete 후에 Data 추가 시 auto_increment 때문에 snum 삭제된 다음 번호로 새로 추가 됨
	
//====================================================================================================
		
		// 5. 실습 - Group Test
		// => 조별 인원수, sum(age), avg(age), max(age), min(age) 출력하기
		
		student.groupTest();
		*/
//====================================================================================================

		// 6. Transaction Test
		student.transactionTest();
	
	} // main

} // class
