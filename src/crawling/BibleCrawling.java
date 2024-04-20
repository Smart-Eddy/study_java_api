package crawling;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BibleCrawling {

	public static void main(String[] args) {
		
		// 크롤링할 URL
		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BodyMatter?qt_ty=QT1";
		
		// 콘솔로 날짜정보를 입력받는다.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print("[입력형식 -> 년(YYYY)-월(MM)-일(DD)] : ");
			String bible = br.readLine();
			
			// 입력받은 날짜를 요청할 URL에 쿼리스트링 형식으로 붙여준다.
			url = url + "&Base_de" + bible +  "&bibleType=1";
			System.out.println("=================================================");
			
			// 크롤링할 URL에 HTTP 요청 메서드가 POST이기 때문에 POST로 요청한다.
			// 리턴받은 Document 객체에는 HTML 정보가 담겨있다.
			// 해당 HTML페이지를 분석하여 CSS Selector를 이용해서 데이터를 핸들링할 수 있다.
			Document document =  Jsoup.connect(url).post();
			
			// 타이틀
			Element bible_text = document.select(".bible_text").first();
			System.out.println(bible_text.text());
			
			// 본문
			Element bibleinfo_box = document.select(".bibleinfo_box").first();
			System.out.println(bibleinfo_box.text());
			
			// 성경구절 리스트
			Elements liList = document.select(".body_list > li");
			for(Element li : liList ) {
				System.out.print(li.select(".num").first().text() + " : ");
				System.out.println(li.select(".info").first().text());
			}
			// 리소스 다운로드(mp3, image)
			
			// [MP3 File]
			Element mp3Tag = document.select("source").first();
			// 소스에 공백이 있을 시 제거한다.
			String mp3Path = mp3Tag.attr("src").trim();
			//System.out.println(dPath); mp3 파일경로
			// 가져온 mp3 파일경로에서 마지막 / 구분자를 찾고 해당 파일의 이름만 변수에 담는다.
			String mp3FileName = mp3Path.substring(mp3Path.lastIndexOf("/") + 1);
			
			// [Image File]
			Element iTag = document.select(".img > img").first();
			String imgPath = "https://sum.su.or.kr:8888" + iTag.attr("src").trim();
			//System.out.println(imgPath); // image 파일경로
			// 가져온 image 파일경로에서 마지막 / 구분자를 찾고 해당 파일의 이름만 변수에 담는다.
			String imgFileName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
			
			
			// file 다운로드를 위해 스레드를 생성하여 처리한다.
			// file 다운로드 처럼 오래걸리는 작업을 main 메서드에서 진행하게 되면 메인 메서드는 멈춘 채로 다운로드가 진행되기 때문에
			// 사용자경험이 매우 좋지 않기 때문에 다운로드용 스레드를 생성해서 처리해 주는 것이 좋다.
			Runnable mR = new DownloadBroker(mp3Path, mp3FileName); 
			Thread mp3Load = new Thread(mR);
			Runnable iR = new DownloadBroker(imgPath, imgFileName); 
			Thread imageLoad = new Thread(iR);
			mp3Load.start();
			imageLoad.start();
			System.out.println("=================================================");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
