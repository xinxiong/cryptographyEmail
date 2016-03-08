import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.security.*;

public class MailClient {

	public static void main(String[] args) throws Exception {

		// initialization
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String userid = args[2];
		
		while(true) {
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
		
		
		
		//from sigClient.java

		String userPrivateKeyFileName = userid + ".prv";
		// Get the key to create the signature
        ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(userPrivateKeyFileName));
        PrivateKey privateKey = (PrivateKey)keyIn.readObject();
        keyIn.close();
        
        // create timestamp and random number
        long t1 = (new Date()).getTime();
        double q1 = Math.random();
        // ByteBuffer to convert to bytes later
        ByteBuffer bb = ByteBuffer.allocate(16);
        bb.putLong(t1);
        bb.putDouble(q1);

        // create signature, using timestamp and random number as data
        Signature sig = Signature.getInstance("SHA-1");
        sig.initSign(privateKey);
        sig.update(bb.array());
        byte[] signature = sig.sign();

        // send data and signature
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        out.writeUTF(userid);
        out.writeLong(t1);
        out.writeDouble(q1);
        out.writeInt(signature.length);
        out.write(signature);
        out.flush();

        //end from sigClient.java




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

}
