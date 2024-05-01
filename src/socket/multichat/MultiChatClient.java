package socket.multichat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MultiChatClient {
	
	public static void main(String[] args) {
		try {
			// Socket 생성
			Socket socket = new Socket("127.0.0.1", 9999);
			// 사용자의 채팅을 입력받기 위한 Scanner
			Scanner scanner = new Scanner(System.in);
			System.out.println("name : ");
			String name = scanner.nextLine();
			
			// 요청 스레드
			Thread sender = new Thread(new ClientSender(socket, name));
			// 응답 스레드
			Thread receiver = new Thread(new ClientReceiver(socket));
			sender.start();
			receiver.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // main end
	
	// InnerClass
	static class ClientSender extends Thread{
		// 멤버변수
		Socket socket;
		DataOutputStream out;
		String name;
		
		// 생성자
		public ClientSender(Socket socket, String name) {
			this.socket = socket;
			this.name = name;
			try {
				out = new DataOutputStream(socket.getOutputStream());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			Scanner scanner = new Scanner(System.in);
			try {
				if(out != null) {
					out.writeUTF(name);
				}
				while(out != null) {
					String message = scanner.nextLine();
					if(message.equals("quit"))
						break;
					out.writeUTF("[" + name + "]" + message);
				}
				out.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}// InnerClass end
	
	// InnerClass
	static class ClientReceiver extends Thread{
		// 멤버변수
		Socket socket;
		DataInputStream in;
		
		// 생성자
		public ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				
				in = new DataInputStream(socket.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			while(in != null) {
				try {
					System.out.println(in.readUTF());
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			try {
				in.close();
				socket.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
