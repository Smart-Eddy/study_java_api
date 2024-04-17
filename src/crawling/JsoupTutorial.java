package crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @ Jsoup Java HTML Parser
 * 1. 크롤링(웹 스크래핑) 작업을 할 때 HTML파싱 및 조작에 필요한 다양한 기능을 제공하는 오픈소스 API
 * 2. Jsoup은 CSS Selector로 DOM을 조작하는 흡사 jQuery와 유사한 방식으로 조작할 수 있어 편리하다.
 * ※ 자세한 설명은 https://jsoup.org 를 참조하자...
 */
public class JsoupTutorial {

	public static void main(String[] args) {
		// 크롤링할 URL
		String url = "https://sports.news.naver.com/wfootball/index.nhn";
		Document document = null;
		try {
			// 해당 URL로 HTTP(GET)으로 Request하고 해당 URL의 HTML을 리턴받는다.
			document = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Elements element = document.select("div.home_news"); // <div class="home_news">
		String title = element.select("h2").text(); // <h2>추천뉴스</h2>
		System.out.println("====================================================================================================");
		System.out.println(title);
		System.out.println("====================================================================================================");
		for(Element el : element.select("li")) { 
			// li 태그의 기사 내용들을 콘솔에 출력
			System.out.println(el.text());
		}
		System.out.println("====================================================================================================");

	}

}
