package navermapapi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;


public class NaverMapAPI {

	public static void main(String[] args) {
		
		Properties prop = new Properties();
		FileInputStream input = null;
		BufferedReader addrInput = null;
		
		try {
			// properties 파일에 정의된 API Key를 꺼내온다.
			input = new FileInputStream("src/navermapapi/config.properties"); // ignore file
			prop.load(input);
			String clientId = prop.getProperty("clientId");
			String clientSecret = prop.getProperty("clientSecret");
			
			// NAVER MAP API : 주소 -> 위도, 경도 좌표 얻어오기
			String apiUrl= "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
			
			// 콘솔을 통해서 주소를 입력받는다.
			addrInput = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("주소를 입력하세요 : ");
			String address = addrInput.readLine();
			String addr = URLEncoder.encode(address, "UTF-8"); // 입력문자 encoding
			String reqUrl =  apiUrl + addr; // 최종적으로 Request할 URL
			
			// 입력한 주소의 위도와 경도 데이터를 Geocoding API에서 반환 
			GeoCoding geoCoding = new GeoCoding(clientId, clientSecret);
			Map<String, String> addrData = geoCoding.getGeoCode(address, reqUrl);
			String x = addrData.get("x");
			String y = addrData.get("y");
			String roadAddress = addrData.get("roadAddress");
			
			StaticMap staticMap = new StaticMap(clientId, clientSecret);
			apiUrl = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
			String pos = URLEncoder.encode(x + " " + y, "UTF-8");
			reqUrl = apiUrl;
			reqUrl += "center=" + x + "," + y;
			reqUrl += "&level=16&w=700&h=500";
			reqUrl += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(roadAddress, "UTF-8");
			staticMap.getStaticMap(x, y, roadAddress, reqUrl); // 최종 RequestUrl은 getStaticMap 메서드 내에서 만들어진다.(위도,경도 정보 필요)
			
	}catch (Exception e) {
		e.printStackTrace();
	} finally {
		// 자원회수
		if(input != null) {
			try {
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	}

}
