import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;

public class MailServer {

	public static void main(String[] args) throws Exception {

		int port = Integer.parseInt(args[0]);
		ServerSocket ss = new ServerSocket(port);

		HashMap<String, ArrayList<Mail>> allMailBoxes = new HashMap<String, ArrayList<Mail>>();

		while(true) {

			Socket s = ss.accept();
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.flush(); // resolves hanging due to OIS constructor issue
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			// check login
			String clientId = dis.readUTF();
			if (!verifyLogin(dis, clientId)) { // failed to login
				System.out.println("Client " + clientId + " failed to log in.");
				dos.writeBoolean(false);
				continue;
			}

			System.out.println("Client " + clientId + " logged in.");
			dos.writeBoolean(true);

			// find mailbox for client
			if (!allMailBoxes.containsKey(clientId)) dos.writeInt(0);
			else {
				ArrayList<Mail> mailbox = (ArrayList<Mail>)allMailBoxes.get(clientId);
				dos.writeInt(mailbox.size());

				// send stored messages
				while(mailbox.size() > 0) {

					// message removed once sent
					oos.writeObject(mailbox.get(0));
					mailbox.remove(0);
				}
			}

			// receive message if this client wants to send
			if (dis.readBoolean()) {

				Mail m = (Mail)ois.readObject();

				// saves it
				ArrayList<Mail> mailbox;
				if (allMailBoxes.containsKey(m.recipient))
					mailbox = (ArrayList<Mail>)allMailBoxes.get(m.recipient);
				else
					mailbox = new ArrayList<Mail>();

				mailbox.add(m);
				allMailBoxes.put(m.recipient, mailbox);
				// replaces old entry in hashmap, if any

			}

			s.close();

		}  // end while

	}  // end main

	public static boolean verifyLogin(DataInputStream dis, String userid) {

		// TO DO
		
		
		
		
		



		return true; // stub
	}

}  // end class
