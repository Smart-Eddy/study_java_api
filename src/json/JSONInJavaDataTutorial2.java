package json;

import java.io.InputStream;

import org.json.*;

/**
 * @ JSON In Java(org.json)
 * ※ file형식의 JSON 데이터 읽기
 */
public class JSONInJavaDataTutorial2 {

	public static void main(String[] args) {
		
		String src = "info.json";
		// 해당 클래스가 만들어진 경로에서 resource를 찾아서 Stream 연결 (클래스와 같은 경로에 resource가 있을 시)
		InputStream inputStream = JSONInJavaDataTutorial2.class.getResourceAsStream(src);
		if(inputStream == null) {
			throw new NullPointerException("Cannot find resource file");
		}
		
		// JSONTokener 객체는 JSON 문자열을 읽고 파싱하는데 사용되는 객체이다.
		// 주어진 JSON 문자열에서 Token(키워드, 문자열, 숫자 등)을 추출하고 이를 JSON 객체로 파싱한다.
		// JSON(String) --> JSON (Object, Array ...)
		JSONTokener tokener = new JSONTokener(inputStream);
		JSONObject object = new JSONObject(tokener);
		JSONArray list = object.getJSONArray("students");
		
		for(int i = 0; i < list.length(); i++) {
			JSONObject student = (JSONObject)list.get(i);
			System.out.print(student.get("name") + "\t");
			System.out.print(student.get("address") + "\t");
			System.out.println(student.get("phone"));
		}
		
		
	}

}
