package navermapapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class StaticMap {
	private String clientId;
	private String clientSecret;
	
	public StaticMap(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}
	
	public void getStaticMap(String point_x, String point_y, String roadAddress, String reqUrl) throws IOException{
		
		// HTTP 요청 메서드 및 헤더 설정하기
		URL url = new URL(reqUrl);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", this.clientId);
		urlConn.setRequestProperty("X-NCP-APIGW-API-KEY", this.clientSecret);
		int responseCode = urlConn.getResponseCode();
		
		// 응답받은 데이터 읽기
		BufferedReader mapReader = null;
		if(responseCode == 200) { // 응답성공 시 HttpStatus 200 return
			InputStream inputStream = urlConn.getInputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			
			String prjPath = System.getProperty("user.dir"); // 현재 PROJECT의 root 디렉토리 이름을 리턴한다.
			String dirPath = prjPath + "/src/navermapapi/map_images/";
			String fileName = dirPath + Long.valueOf(new Date().getTime()).toString(); // 파일 이름은 랜덤생성(중복방지, 파일이름이 동일할 때 덮어씌기 될 수 있음)
			File mapImageFile = new File(fileName + ".jpg"); // 생성된 jpg.파일의 이름을 지정 
			mapImageFile.getParentFile().mkdirs(); // 디렉토리 생성
			mapImageFile.createNewFile(); // 실제 시스템에 파일을 생성
			OutputStream outputStream = new FileOutputStream(mapImageFile); // 해당 파일에 출력스트림 생성
			
			// StaticMap API로 부터 response받은 데이터를 읽고 읽은 데이터를 생성한 파일에 출력한다.
			while((read = inputStream.read(bytes)) != -1) { // End of File == -1
				outputStream.write(bytes, 0, read);
			}
			System.out.println("입력하신 주소의 이미지가 생성되었습니다!");
			inputStream.close();
		}else {
			mapReader = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = mapReader.readLine()) != null) {
				response.append(inputLine);
			}
			mapReader.close();
		}
	}
	


}
