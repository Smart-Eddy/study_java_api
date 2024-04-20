package crawling;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

// Runnable 인터페이스를 구현하는 방법으로 쓰레드를 생성한다.
// 클래스가 이미 다른 클래스를 상속 중일 때 Runnable을 구현하여 쓰레드 작업만 분리 시킬 수 있다.
// 또한, Runnable은 함수형 인터페이스로 람다식이나 익명 클래스로도 구현이 가능하다.
// 반면 Thread 클래스를 직접 상속하는 방법은 이미 다른 클래스를 상속하고 있는 경우의 클래스에 쓰레드 기능을 추가하는 것이기 때문에 유연성이 떨어질 수 있다.
public class DownloadBroker implements Runnable {
	
	private String url;
	private String fileName;

	
	public DownloadBroker(String url, String fileName) {
		super();
		this.url = url;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		try {
			String prjPath = System.getProperty("user.dir");
			prjPath += "/src/crawling/resoruces/";
			FileOutputStream fos = new FileOutputStream(prjPath + fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos); 
			
			URL reqUrl = new URL(this.url);
			InputStream is = reqUrl.openStream();
			BufferedInputStream input = new BufferedInputStream(is);
			
			int data;
			while((data = input.read()) != -1) {
				bos.write(data);
			}
			bos.close();
			input.close();
			System.out.println("download complate...");
			System.out.println(this.fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
