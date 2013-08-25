package com.HackU.offlinetalk;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import android.os.Message;
import android.util.Log;

public class SendMessage implements Runnable {

	private String message;
	private Socket clientSocket;
	private String IP;

	public SendMessage(String message, String IP) {
		this.message = message;
		//this.IP = message;
		this.IP = IP;
	}

	@Override
	public void run() {
		try {
			Log.v("IP","here");
			clientSocket = new Socket(IP, 2001);
			ObjectOutputStream oos = new ObjectOutputStream(
					clientSocket.getOutputStream());
			oos.writeObject(message);
			Message serverMessage2 = Message.obtain();
			ObjectInputStream ois = new ObjectInputStream(
					clientSocket.getInputStream());
			String strMessage = (String) ois.readObject();
			oos.close();
			ois.close();
		} catch (Exception e) {
			
		}
	}
}
