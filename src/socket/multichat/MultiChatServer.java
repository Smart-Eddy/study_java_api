package socket.multichat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class MultiChatServer {
	
	// Client의 정보를 저장할 Map
	HashMap clients;
	
	public MultiChatServer() {
		clients = new HashMap<String, DataOutputStream>();
		// 여러명의 Client가 동시에 HashMap에 접근할 경우 문제가 발생할 수 있음으로 동기화처리한다.
		Collections.synchronizedMap(clients); 
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			// ServerSocket은 9999 Port를 열고 Client의 요청을 대기한다.
			serverSocket = new ServerSocket(9999);
			System.out.println("Start Server....");
			
			while(true) {
				// socket에는 연결된 Client의 정보가 반환된다.
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + ":" + socket.getPort() + " Connect!!");
				
				// 스레드 생성
				ServerReceiver thread = new ServerReceiver(socket);
				thread.run();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 브로드캐스팅 기능을 담당할 메서드
	public void sendToAll(String message) {
		Iterator iterator = clients.keySet().iterator();
		while(iterator.hasNext()) {
			try {
				// 모든 Client에게 message를 송신한다.
				DataOutputStream out = (DataOutputStream)clients.get(iterator.next());
				out.writeUTF(message);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	// Inner Class로 Thread 구현
	class ServerReceiver extends Thread{
		// 멤버변수
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		// 생성자
		ServerReceiver(Socket socket){
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		// Thread run() 구현
		public void run() {
			String name = "";
			try {
				// 입력받은 사용자의 이름을 읽어온다.
				name = in.readUTF();
				// 동일한 사용자 이름이 존재한다면 접속종료
				if(clients.get(name) != null) {
					out.writeUTF("#Aleady exist name :" + name);
					out.writeUTF("#Please reconnect by other name!");
					System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "disconnect!");
					in.close();
					out.close();
					socket.close();
					socket = null;
				// 같은 이름이 존재하지 않는 경우(정상 연결)
				} else {
					// 브로드캐스팅으로 모든 사용자에게 접속한 name의 입장을 알린다.
					sendToAll("#" + name + " Join!");
					clients.put(name, out);
					// Client가 채팅을 입력 시 브로드캐스팅한다.
					while(in != null) {
						sendToAll(in.readUTF());
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				// 사용자가 Exit을 입력해서 접속을 종료했을 시
				if(socket != null) {
					sendToAll("#" + name + " Exit!");
					clients.remove(name);
					System.out.println(socket.getInetAddress() + ":" + socket.getPort() + "disconnect!");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// Server 실행
		MultiChatServer server = new MultiChatServer();
		server.start();

	}

}
