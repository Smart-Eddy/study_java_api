package excel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import excel.vo.ExcelVO;


public class NaverSearchAPI {

	public static void main(String[] args) {
		
		// Properties 파일에 정의된 API Key를 가져오기 위한 객체 생성
		Properties prop = new Properties();
		FileInputStream fis = null;
		
		// 콘솔로 부터 입력받기 위한 BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			// Properties 파일에서 API Key를 가져온다
			fis = new FileInputStream("src/navermapapi/config.properties");
			prop.load(fis);
			String clientId = prop.getProperty("search_clientId");
			String clientSecret = prop.getProperty("search_clientSecret");
			// 검색할 책정보를 입력받는다.
			System.out.print("책 제목 : ");
			String title = br.readLine();
			System.out.print("책 저자 : ");
			String author = br.readLine();
			System.out.print("출판사 : ");
			String company = br.readLine();
			
			ExcelVO excelVO = new ExcelVO(title, author, company);
			getIsbnImage(excelVO , clientId, clientSecret);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	// @SupperessWarnings 어노테이션은 컴파일러에게 경고 메세지를 억제하도록 지시하는 어노테이션이다.
	// 매개변수의 문자열에 따라 카테고리를 지시할 수 있고 deperaction은 비추천(또는 폐기예정)된 API를 계속 사용하도록 지시하는 것이다.
	@SuppressWarnings("deprecation")
	public static void getIsbnImage(ExcelVO vo, String clientId, String clientSecret) throws Exception {
		
		String url = "https://openapi.naver.com/v1/search/book_adv.xml?d_titl="
					+ URLEncoder.encode(vo.getTitle(), "UTF-8")
					+ "&d_auth=" + URLEncoder.encode(vo.getAuthor(), "UTF-8")
					+ "&d_publ=" + URLEncoder.encode(vo.getCompany(), "UTF-8");
		
		URL reqUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) reqUrl.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("X-Naver-Client-Id", clientId);
		conn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		// HTTP 응답코드를 받는다
		int respCode = conn.getResponseCode();
		
		BufferedReader br;
		// 정상적으로 응답이 왔다면
		if(respCode == 200) {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		// 응답실패 시
		}else {
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		// 응답받은 XML 데이터를 읽기위한 객체 생성
		String inputLine;
		StringBuffer respData = new StringBuffer();
		while((inputLine = br.readLine()) != null) {
			respData.append(inputLine); // StringBuffer에 응답받은 XML 데이터를 append
		}
		br.close();
		//System.out.println(respData.toString());
		// 필요한 데이터(isbn, image)만 가져오기위해 Jsoup API를 활용
		Document document = Jsoup.parse(respData.toString());
		// System.out.println(document.toString());
		// total이 0이면 데이터가 없다는 뜻임
		Element totEl = document.select("total").first();
		// 검색된 데이터가 있다면
		if(!totEl.text().equals("0")) {
			// isbn
			Element isbnEl = document.select("isbn").first();
			String isbn = isbnEl.text();
			vo.setIsbn(isbn);
			// img
			// Element imgEl = document.select("img").first(); img태그는 닫는 태그가 없기 때문에 다른 방식으로 가져온다
			String imgDoc = document.toString();
			String imgData = imgDoc.substring(imgDoc.indexOf("<img>") + 5); // img태그를 substring으로 찾고 5번째 index부터(태그를 제외한 데이터)
			// System.out.println(imgData); // <img>태그 다음부터 모든 document의 Data가 출력된다
			String imgUrl = imgData.substring(0, imgData.indexOf("<author>") - 1); // img 다음 태그인 <author>태그를 찾아고 해당 태그의 인덱스 -1하여 데이터를 담는다.
			// System.out.println(imgUrl);
			String imgFileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1); // imgUrl의 마지막 인덱스 부터 "/" 를 찾고 / 부터 마지막 문자열까지 찾는다.
			vo.setImgUrl(imgFileName);
			
		}
	}

}
