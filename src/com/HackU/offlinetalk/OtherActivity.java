package com.HackU.offlinetalk;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends Activity implements DataDisplay {

	EditText ed;
	TextView tv;
	Thread m_objThreadClient;
	public String nick;
	ArrayList<String> IPs = new ArrayList<String>();
	ProgressDialog dialog;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			nick = extras.getString("nick");
		}
		
		ed = (EditText) findViewById(R.id.editText2);
		tv = (TextView) findViewById(R.id.textview1);
		
		MyServer server = new MyServer();
		server.setEventListener(this);
		server.startListening();

		dialog = ProgressDialog.show(this, "", "creating list", true);
		
		Thread thread = new Thread(new Runnable() {
			public void run()
			{
				WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
				WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
				int ip = wifiInfo.getIpAddress();
				String ipAddress = Formatter.formatIpAddress(ip);
				
				String shortIp = ipAddress.substring(0, ipAddress.lastIndexOf(".")+1);
				Log.v("short",shortIp);
				int own = Integer.parseInt(ipAddress.substring(ipAddress.lastIndexOf(".")+1, ipAddress.length()));
				Log.v("own",""+own);
				for(int i=1;i<=255;i++)
				{
					
					if(i!=own){
					try {
						InetAddress add = InetAddress.getByName(shortIp+i);
						if(add.isReachable(200)){
							IPs.add(shortIp+i);
							Log.v("ip",shortIp+i);
						}
							
					} catch (IOException e) {


					}}
				}
				
				Message clientmessage = Message.obtain();
				clientmessage.obj="closedialog";
			    mHandler.sendMessage(clientmessage);
			}
			});
		thread.start();
		
		
		
	}

	public void Display(String message) {
		tv.append("\n"+message);
	}

	public void Start(View view) {
		final String text = ed.getText().toString();
		if(text.length()!=0)
		{
			tv.append("\nme: " + text);
			ed.setText("");

			for (int i = 0; i < IPs.size(); i++) {
				Log.v("IP",IPs.get(i));
				SendMessage sm = new SendMessage(nick + ": " + text, IPs.get(i));
				m_objThreadClient = new Thread(sm);
				m_objThreadClient.start();
			}
		}
		else
		{
			Toast toast = Toast.makeText(getApplicationContext(), "fill up some words to send", 500);
			toast.show();
		}

	}
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message status) {
			dialog.dismiss();
		}
	};

}
