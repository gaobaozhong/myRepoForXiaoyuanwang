package cn.edu.sdnu.i.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.sdnu.i.LeftFragment;
import cn.edu.sdnu.i.LoginActivity;
import cn.edu.sdnu.i.R;
import cn.edu.sdnu.i.util.menu.BaseSlidingFragmentActivity;
import cn.edu.sdnu.i.util.menu.SlidingMenu;
import cn.edu.sdnu.i.util.xauth.Xauth;

public class LeftPage2 extends Fragment implements OnClickListener {

	protected static SlidingMenu mSlidingMenu;
	private TextView mTitleName;
	private ListView listView;
	private List<String> data;
	private ArrayAdapter<String> adapter;
	String result ;
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.left_page2, container, false);
		initView(view);
		listView = (ListView) view.findViewById(R.id.lv_borrowed);
	
		
		//实现异步通信.
				LoginActivity.xauth.setHandlerUrl(handler,Xauth.BASE_URL+Xauth.REST_URL+"?method="+"library.getborrowlist");
				new Thread(LoginActivity.xauth).start();
		return view;
	}

	private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {  
            case 1:
            	result = (String)msg.obj;
            	decodeBook(result);
                break;  
            default:  
                break;  
            }  
		}
		
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private void initView(View view) {
		((ImageButton) view.findViewById(R.id.ivTitleBtnLeft))
				.setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.ivTitleBtnRigh))
				.setOnClickListener(this);

		mTitleName = (TextView) view.findViewById(R.id.ivTitleName);
		mTitleName.setText(LeftFragment.mTitleName);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		BaseSlidingFragmentActivity baseFragment = (BaseSlidingFragmentActivity) getActivity();
		mSlidingMenu = baseFragment.getSlidingMenu();
		Intent intent = new Intent();
		// 用bundle 在activity 间传递数据.
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.ivTitleBtnLeft:
			mSlidingMenu.showMenu(true);
			break;
		case R.id.ivTitleBtnRigh:
			mSlidingMenu.showSecondaryMenu(true);
			break;

		default:
			break;
		}
	}



	private void decodeBook(String result) {

		int count = 0;
		String[] identityNumber = null;
		String[] bookName = null;
		String[] borrowDate = null;
		String[] mustReturnDate = null;
		String[] isRenew = null;

		try {
			JSONObject decode = new JSONObject(result);
			// 显示借书总数.
			count = decode.getInt("recordCount");
			String records = decode.getString("records");

			JSONArray jsonArray = new JSONArray(records);
			identityNumber = new String[count];
			bookName = new String[count];
			borrowDate = new String[count];
			mustReturnDate = new String[count];
			isRenew = new String[count];
			int iSize = jsonArray.length();
			for (int i = 0; i < iSize; i++) {
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				identityNumber[i] = (String) jsonObj.get("identityNumber");
				bookName[i] = "书籍名称:" + (String) jsonObj.get("bookName")
						+ "\r\n";
				borrowDate[i] = "借阅日期:" + (String) jsonObj.get("borrowDate")
						+ "\r\n";
				mustReturnDate[i] = "归还日期:"
						+ (String) jsonObj.get("mustReturnDate") + "\r\n";
				isRenew[i] = "是否续借:"
						+ ((Boolean) jsonObj.get("isRenew") ? "是" : "否");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data = new ArrayList<String>();
		// 把数据显示到listView 中
		for (int i = 0; i < count; i++) {
			String temp = bookName[i] + borrowDate[i] + mustReturnDate[i]
					+ isRenew[i];
			data.add(temp);
		}
		adapter = new ArrayAdapter<String>(getActivity(), R.layout.itembook,
				R.id.book_item, data);
		listView.setAdapter(adapter);

	}
}
