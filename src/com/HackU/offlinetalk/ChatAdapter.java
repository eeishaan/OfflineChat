
package com.HackU.offlinetalk;


import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class ChatAdapter extends BaseAdapter{

	
	private List<ChatList> mChatList = null;
	private Activity mActivity = null;
	private LayoutInflater layoutInflator = null;
	
	public ChatAdapter(Activity activity, ArrayList<ChatList> chatList){
		mActivity = activity;
		mChatList = chatList;
		layoutInflator = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount() {
		return mChatList.size();
	}


	@Override
	public Object getItem(int arg0) {

		return mChatList.get(arg0);
	}


	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ChatList ua = mChatList.get(position);

		View v = convertView;
		if(convertView == null) {
			v = layoutInflator.inflate(R.layout.list_element, null);
		}

		TextView tvChat = (TextView) v.findViewById(R.id.tv_chat);
		tvChat.setText(ua.User+ua.Chat);
		
		return v;
	}
}

