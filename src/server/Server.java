/*Nicholas Jumpp*/
package server;


import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
import java.util.Random;

import clientHandler.ClientHandler;

// Server class
public class Server
{
	public static void main(String[] args) throws IOException
	{
		// flags to show if users are connected or not
		boolean connected_user1 = false;
		boolean connected_user2 = false;

		// Initialize objects of class ClientHandler		
		ClientHandler user1 = new ClientHandler();
		ClientHandler user2 = new ClientHandler();

		// this is used to randomly assign positions to users at the beginning of the round
		String[] positions = new String[]{ "dealer", "spotter"};
		Random rand=new Random(); 
		int randomNumber = rand.nextInt(positions.length);
		String user1_position = positions[randomNumber];
		String user2_position = "dealer";
		if(user1_position.equals(user2_position))
		{
			user2_position = "spotter";
		}

		// initialize users score with zero
		int user1_score = 0;
		int user2_score = 0;

		// initialize variable for storing users choice
		String user1_choice = "1";
		String user2_choice = "2";
		// server is listening on port 7621
		ServerSocket ss = new ServerSocket(7621);
		


		/*running infinite loop for getting
		client request until both users are connected*/
		while (!connected_user1 || !connected_user2)
		{
			Socket s = null;
			//declare variable username and password
			String username;
			String password;
			try
			{
				// socket object to receive incoming client requests
				s = ss.accept();
				
				System.out.println("Request recieved for connection from : " + s);
				
				// obtaining input and out streams
				DataInputStream dis = new DataInputStream(s.getInputStream());
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());

				//ask client to write username and receive it
				dos.writeUTF("UserName");
				username = dis.readUTF();

				//ask client to write password and receive it
				dos.writeUTF("Password ");
				password = dis.readUTF();

				// check if credentials are valid
				if(username.equals("dannyboi") && password.equals("dre@margh_shelled"))
				{
					//check if user with these credentials is not already connected
					if(!connected_user1)
					{
						System.out.println("User1 connected");
						
						user1.initializeObject(s,dis,dos);
						// send message to client that connection is established
						dos.writeUTF("Established");
						// set as true to indicate that connection is established with these creds
						connected_user1 = true;
					}

					else
					{
						dos.writeUTF("This user is already connected");
					}

				}
				else if(username.equals("matty7") && password.equals("win&win99"))
				{
					if(!connected_user2)
					{
						System.out.println("User2 connected");
						user2.initializeObject(s,dis,dos);
						dos.writeUTF("Established");
						connected_user2 = true;
					}

					else
					{
						dos.writeUTF("This user is already connected");
					}
				}
				// if incorrect credentials then write a message and close the connection 
				else
				{
					dos.writeUTF("Wrong credentials");
					s.close();
				}
				
			}
			catch (Exception e){
				s.close();
				e.printStackTrace();
			}
		}

		user1.writeMessage("Find the Queen Has Started.");
		user2.writeMessage("Find the Queen Has Started.");

		// check if both the users are connected to server
		if(connected_user1 && connected_user2)
		{
			// loop for the 5 rounds
			for (int i = 0; i < 5; i++) 
			{
 				user1.writePosition(user1_position);
				user2.writePosition(user2_position);

				//if statement to allow the user that is the dealer to choose position first
				if(user1_position.equals("dealer"))
				{
				user1.writeMessage("Enter Number between 0 and 4");
				user1_choice = user1.readChoice();
				user2.writeMessage("Enter Number between 0 and 4");
				user2_choice = user2.readChoice();
				}
				
				else
				{
				user2.writeMessage("Enter Number between 0 and 4");
				user2_choice = user2.readChoice();
				user1.writeMessage("Enter Number between 0 and 4");
				user1_choice = user1.readChoice();
				}

				

				if(user1_choice.equals(user2_choice))
				{
					if(user1_position.equals("spotter"))
					{
						user1_score = user1_score+1;
					}

					else
					{
						user2_score = user2_score+1;
					}
				}

				else{
					if(user1_position.equals("dealer"))
					{
						user1_score = user1_score+1;
					}

					else
					{
						user2_score = user2_score+1;
					}
				}

				//swap roles
				String temp_position = user1_position;
				user1_position = user2_position;
				user2_position = temp_position;

			}

			if(user1_score>user2_score)
			{
				user1.writeMessage("You Won!");
				user2.writeMessage("Better Luck Next Time, you lost.");
			}
			else
			{
				user2.writeMessage("You Won!!!");
				user1.writeMessage("Better Luck Next Time, you lost.");
			}

			user1.writeMessage("Thanks for playing, find the queen will be closing now.");
			user2.writeMessage("Thanks for playing, find the queen will be closing now.");

		}

	}
}
