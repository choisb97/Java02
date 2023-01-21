package jdbc02; 
// DAO와 Controller의 완충지대(아무런 일을 하지 않아도 Service를 무조건 넣는다)
// DAO보호, DAO와 Controller는 직접 연결하지 않는다. 
// DAO의 수정으로 Controller가 셧다운되는 일을 막는다.

import java.util.List;

public class StudentService {
	
	// ** 전역 인스턴스 생성 **
	StudentDAO dao = new StudentDAO();

	// 1. Select(Read)
	// 1-1) Student List 출력하기
	public List<StudentVO> selectList() { 
		return dao.selectList();
	}

	
	// 1-2) Student JoList_PST 출력하기
	public List<StudentVO> joListPST(StudentVO vo) {
		return dao.joListPST(vo);
	}
	
	
	// 1-3) 내 정보(한 사람) 보기 - PST(PreparedStatement)
	public StudentVO selectOne(StudentVO vo) {
		return dao.selectOne(vo);
	}
	
//----------------------------------------------------------------------------
	
	// 2. Insert(Create)
	public int insert(StudentVO vo) {
		return dao.insert(vo);
	}
	
//----------------------------------------------------------------------------

	// 3. Update
	public int update(StudentVO vo) {
		return dao.update(vo);
	}
	
//----------------------------------------------------------------------------
	
	// 4. Delete
	public int delete(StudentVO vo) {
		return dao.delete(vo);
	}
	
//----------------------------------------------------------------------------

	// 5. 실습
	// => 조별 인원수, sum(age), avg(age), max(age), min(age) 출력하기
	public List<GroupDTO> groupTest() {
		return dao.groupTest();
	}

//----------------------------------------------------------------------------

	// 6. Transaction Test
	public void transactionTest() {
		dao.transactionTest();
	}

} // class
