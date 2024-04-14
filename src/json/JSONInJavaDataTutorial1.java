package json;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @ JSON In Java(org.json)
 * ※ JSON 포맷의 데이터를 효과적으로 핸들링할 수 있는 서드파티 라이브러리이다.
 * 1. JSON-Java는 JSON 데이터의 파싱과 생성에 중점을 둔 라이브러리이다.
 * 2. Gson에 비해 더 많은 작업을 수동으로 처리해야하지만 좀 더 세밀한 제어가 가능하다고도 볼 수 있다.
 */
public class JSONInJavaDataTutorial1 {

	public static void main(String[] args) {
		
		JSONArray students = new JSONArray();
		JSONObject student = new JSONObject();
		
		student.put("name", "홍길동");
		student.put("phone", "010-1111-1111");
		student.put("address", "서울");
		System.out.println(student); // {"address":"서울","phone":"010-1111-1111","name":"홍길동"}
		students.put(student); // JSONObject를 JSONArray에 put()
		
		student = new JSONObject();
		student.put("name", "홍길동");
		student.put("phone", "010-2222-2222");
		student.put("address", "대전");
		System.out.println(student); // {"address":"대전","phone":"010-2222-2222","name":"홍길동"}
		students.put(student); // JSONObject를 JSONArray에 put()
		
		System.out.println(students); 
		// [{"address":"서울","phone":"010-1111-1111","name":"홍길동"},{"address":"대전","phone":"010-2222-2222","name":"홍길동"}]
		
		JSONObject obj = new JSONObject();
		
		obj.put("students", students); // 최종적으로 JSONObject 형태의 객체로 만들어주어야 한다.
		System.out.println(obj);
		/*
		 * {"students": 
		 * [ { "address": "서울", "phone": "010-1111-1111", "name": "홍길동" },
		 * { "address": "대전", "phone": "010-2222-2222", "name": "홍길동" } ]}
		 */
	}

}
