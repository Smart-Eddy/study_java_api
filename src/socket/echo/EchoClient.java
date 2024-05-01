package socket.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	public static void main(String[] args) {
		// Server에 메세지를 전송하기 위한 Client
		try {
			// Socket 생성
			Socket socket = new Socket("127.0.0.1", 9999); // 요청을 보낼 Server의 정보
			System.out.println("Connection Success!!");
			// 메세지를 입력 받기 위한 스캐너 생성
			Scanner scanner = new Scanner(System.in);
			String message = scanner.nextLine();
			
			// 서버에 메세지를 전송하기 위한 OutputStream 생성
			OutputStream out = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(out);
			dos.writeUTF(message);
			
			// 서버에서 메세지를 전송받기 위한 InputStream 생성
			InputStream in = socket.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			System.out.println("[Receive] " + dis.readUTF());
			
			// 자원회수
			dis.close();
			dos.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
