package json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import json.dto.BookDTO;

/**
 * @ Gson
 * ※ google에서 제공하는 JSON 포맷의 데이터를 쉽게 핸들링할 수 있는 API
 * 1. lib를 추가 후 Gson 객체를 생성한다.
 * 2. Gson 객체의 method를 호출하여 손쉽게 JSON <-> Object 핸들링이 가능하다.
 * 3. toJson : Object를 JSON 포맷으로 파싱해주는 메서드
 * 4. formJson : JSON 포맷의 문자열을 Java의 Object로 파싱해주는 메서드
 *    -> 단순 JSON(String) {key:value, key:value ....} 형태라면 해당클래스.class 로 Type을 명시하면 된다.
 *    -> 하지만 JSON(String) Array일 경우 [{},{},{}....] TypeToken클래스의 getType()메서드로 반환하려는 객체의 타입정보를 명시해주어야한다.
 *
 */
public class GsonDataTutorial {

	public static void main(String[] args) {
		// 1. Object(BookDTO) --> JSON(String)
		BookDTO bookDto1 = new BookDTO("자바", 21000, "인프런", 670);
		// Gson -> https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5
		Gson gson = new Gson();
		String json = gson.toJson(bookDto1); // Object를 Json으로 변경해주는 메서드
		System.out.println(json); // {"title":"자바","price":21000,"company":"인프런","page":670}
		
		// 2. JSON(String) --> Object(BookDTO)
		BookDTO bookDto2 = gson.fromJson(json, BookDTO.class);
		System.out.println(bookDto2); // BookDTO [title=자바, price=21000, company=인프런, page=670]
		
		// 3. Object(List<BookDTO>) --> JSON(String) Array : [{  },{  },{  }.....]
		List<BookDTO> bookDtoList1 = new ArrayList<BookDTO>();
		bookDtoList1.add(new BookDTO("자바", 21000, "인프런", 670));
		bookDtoList1.add(new BookDTO("파이썬", 19000, "인프런", 400));
		bookDtoList1.add(new BookDTO("C++", 31000, "인프런", 990));
		
		String jsonList = gson.toJson(bookDtoList1);
		System.out.println(jsonList);
		//  [{"title":"자바","price":21000,"company":"인프런","page":670}
		//  ,{"title":"파이썬","price":19000,"company":"인프런","page":400}
		//  ,{"title":"C++","price":31000,"company":"인프런","page":990}]
		
		// 4. JSON(String) Array : [{  },{  },{  }.....] --> Object(List<BookDTO>)
		// JSON(String) Array일 경우 fromJson() 메서드에서 TypeToken 클래스를 통해 BookDTO의 Type을 알 수 있도록 해주어야 한다.
		List<BookDTO> bookDtoList2 = gson.fromJson(jsonList, new TypeToken<List<BookDTO>>() {}.getType());
		for(BookDTO dto : bookDtoList2) {
			System.out.println(dto);
			// BookDTO [title=자바, price=21000, company=인프런, page=670]
			// BookDTO [title=파이썬, price=19000, company=인프런, page=400]
			// BookDTO [title=C++, price=31000, company=인프런, page=990]
		}
		
	}
	
}
