import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;

public class MailClient {

	public static void main(String[] args) throws Exception {

		// initialization
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String userid = args[2];

		// connect to server
		Socket s = new Socket(host,port);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
		oos.flush();
		ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

		// TO DO: login

		// these two lines are here just to make the supplied programs run without crashing.
		// You may want to change them, and certainly add things after them
		dos.writeUTF(userid);







		boolean answer = dis.readBoolean();





		// receive how many messages
		int numMsg = dis.readInt();
		System.out.println("You have " + numMsg + " incoming messages.");

		// TO DO: read messages


		
		
		
		
		
		
		// send messages
		System.out.println("Do you want to send a message [Y/N]?");
		String wantToSend = br.readLine();
		if (!wantToSend.equals("Y")) {
			dos.writeBoolean(false);
			return;
		}
		dos.writeBoolean(true);

		System.out.println("Enter userid of recipient:");
		String recipient = br.readLine();
		System.out.println("Type your message:");
		String message = br.readLine();

		// TO DO: send mail
		Mail m = new Mail(userid, recipient, message);







		oos.writeObject(m);

	}

}
