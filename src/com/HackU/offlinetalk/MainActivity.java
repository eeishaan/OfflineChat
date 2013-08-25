package com.HackU.offlinetalk;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void otherActivity(View view)
	{
		EditText ed = (EditText)findViewById(R.id.editText1);
		if(ed.getText().toString().length()!=0)
		{
			Intent i = new Intent(getApplicationContext(), OtherActivity.class);
			i.putExtra("nick",ed.getText().toString());
			startActivity(i);
		}
		else
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Please select a Nick", 500);
			toast.show();
		}
	}

}
