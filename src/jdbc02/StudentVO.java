package jdbc02;

/*
 < VO(Value Object), DTO(Data Transfer Object) >
 - 자료의 구조를 표현하는 클래스이며, 자료의 전달에 이용됨.
 - 대부분 Table별로 만들며, Table과 동일한 필드명(컬럼명)을 사용한다.
 - Table과 무관하게 자료전달용으로만 정의한 경우 DTO라고 함. 
*/

public class StudentVO {

	private int snum;
	private String sname;
	private int age;
	private String info;
	private int jno;
	private double height;
	private String id;
	
	
	public int getSnum() {
		return snum;
	}
	
	public void setSnum(int snum) {
		this.snum = snum;
	}
	
	public String getSname() {
		return sname;
	}
	
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public int getJno() {
		return jno;
	}
	
	public void setJno(int jno) {
		this.jno = jno;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "StudentVO [snum = " + snum + ", sname = " + sname + ", age = " + age
				+ ", info = " + info + ", jno = " + jno + ", height = " + height
				+ ", ID = " + id + "]";
	} // toString
	
} // class
