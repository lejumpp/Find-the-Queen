/*Nicholas Jumpp*/
package client;



import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
	public static void main(String[] args) throws IOException
	{
		try
		{
			Scanner scanner = new Scanner(System.in);
			
			InetAddress ip = InetAddress.getByName("localhost");
	
			// establish the connection with server port 7621
			Socket s = new Socket(ip, 7621);
	
			// obtaining input and out streams
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());

			//username
			System.out.println(dis.readUTF());
			dos.writeUTF(scanner.nextLine());


			//password
			System.out.println(dis.readUTF());
			dos.writeUTF(scanner.nextLine());

			//check if connection is established
			if (dis.readUTF().equals("Established"))
			{
				System.out.println(dis.readUTF());
				/*the following loop performs the exchange of
				information between client and client handler*/
				for (int i = 0; i < 5; i++) 
				{
					System.out.println("");
					System.out.println(dis.readUTF());
	
					System.out.println(dis.readUTF());
	
					dos.writeUTF(scanner.nextLine());
				}
			
			//win or loss message
			System.out.println(dis.readUTF());
			//thank you closing message
			System.out.println(dis.readUTF());

			
			scanner.close();
			dis.close();
			dos.close();
			}

		else{
			scanner.close();
			dis.close();
			dos.close();
		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
