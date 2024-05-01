package socket.echo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static void main(String[] args) {
		
		// ServerSocket은 Client의 요청을 대기한다.
		ServerSocket serverSocket = null;
		
		try {
			// ServerSocket 객체생성
			serverSocket = new ServerSocket(9999);
			System.out.println("Server ready.....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			try {
				// ※ ServerSocket -> accept()
				// 1. accept() 메서드는 Client의 요청이 올 때 까지 대기한다(blocking)
				// 2. 요청이 오게되면 Client의 정보(IP/Port)를 식별한다
				// 3. Client의 요청을 수락한다.
				// 4. Server에서 Socket을 생성하고 Client의 정보를 바인딩한다
				// 5. 생성된 소켓정보를 전송한다.
				Socket socket = serverSocket.accept(); // Client의 정보를 바인딩한 Socket 생성
				System.out.println("Client connect success!!");
				
				// 클라이언트에서 전송한 데이터를 읽어오기 위한 InputStream 생성
				InputStream in = socket.getInputStream(); // InputStream은 byte단위로 데이터를 읽는다
				DataInputStream dis = new DataInputStream(in); // 문자열 단위로 데이터를 통신하기 위해 DataInputStream을 생성한다
				String message = dis.readUTF();
				
				// 클라이언트에게 데이터를 전송하기 위한 OutputStream 생성
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);
				dos.writeUTF("[ECHO] " + message + " from Server!");
				
				// 자원회수
				dos.close();
				dis.close();
				socket.close();
				System.out.println("client socket close....");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // while end

	}

}
