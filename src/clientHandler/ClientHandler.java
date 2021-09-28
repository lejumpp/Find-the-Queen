/*Nicholas Jumpp*/
package clientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler
{
	DataInputStream dis;
	DataOutputStream dos;
	Socket s;
	

	public void initializeObject(Socket s, DataInputStream dis, DataOutputStream dos){
		this.s = s;
		this.dis = dis;
		this.dos = dos;
	}

	public void writePosition(String position){
		try{
		dos.writeUTF("You are " + position);
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}

	public String readChoice(){
		String in = new String();
		try{
			in = dis.readUTF();	
		}
		catch (Exception e){
			e.printStackTrace();
		}

		return in;
	}

	public void writeMessage(String message){
		try{
			dos.writeUTF(message);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
