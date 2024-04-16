package navermapapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class GeoCoding {
	
	private String clientId;
	private String clientSecret;
	
	public GeoCoding(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	public Map<String, String> getGeoCode(String address, String reqUrl) throws IOException{
		
		// HTTP 요청 메서드 및 헤더 설정하기
		URL url = new URL(reqUrl);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", this.clientId);
		urlConn.setRequestProperty("X-NCP-APIGW-API-KEY", this.clientSecret);
		
		// 응답받은 데이터 읽기
		BufferedReader addrReader = null;
		int responseCode = urlConn.getResponseCode();
		if(responseCode == 200) { // 응답성공 시 HttpStatus 200 return
			System.out.println("Naver geocode API 호출 성공...");
			addrReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));
		}else {
			System.out.println("API 호출 실패...");
			addrReader = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
		}
		
		String line;
		StringBuffer response = new StringBuffer(); // JSON
		while((line = addrReader.readLine()) != null) { // 데이터를 모두 읽게 되면 null을 return
			response.append(line);
		}
		addrReader.close();
		
		// JSON 파싱
		// JSONTokener는 JSON형식의 문자열을 읽고 객체로 파싱해주는 역활을 수행한다.
		// 응답받은 JSON(문자열) -> JSONTokener -> JSONObject
		JSONTokener tokener = new JSONTokener(response.toString());
		JSONObject object = new JSONObject(tokener);
		System.out.println(object.toString(2)); // JSON 데이터 확인용
		JSONArray arr = object.getJSONArray("addresses");
		Map<String, String> addrData = new HashMap<String, String>();
		for(int i = 0; i < arr.length(); i++) {
			JSONObject temp = (JSONObject) arr.get(i);
			System.out.println("도로명주소 : " + temp.get("roadAddress"));
			addrData.put("roadAddress", (String) temp.get("roadAddress"));
			System.out.println("지번주소 : " + temp.get("jibunAddress"));
			System.out.println("경도 : " + temp.get("x"));
			addrData.put("x", (String) temp.get("x"));
			System.out.println("위도 : " + temp.get("y"));
			addrData.put("y", (String) temp.get("y"));
		}
		
		
		return addrData;
	}


}
