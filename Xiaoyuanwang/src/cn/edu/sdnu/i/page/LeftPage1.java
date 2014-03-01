package cn.edu.sdnu.i.page;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.sdnu.i.LeftFragment;
import cn.edu.sdnu.i.LoginActivity;
import cn.edu.sdnu.i.R;
import cn.edu.sdnu.i.util.menu.BaseSlidingFragmentActivity;
import cn.edu.sdnu.i.util.menu.SlidingMenu;
import cn.edu.sdnu.i.util.xauth.Xauth;

public class LeftPage1 extends Fragment implements OnClickListener {

	protected static SlidingMenu mSlidingMenu;
	private TextView mTitleName;
	String result ;

		
	//修改一下这个类文件  extends Fragment .
	//代码可以参考  YiKaTong.java
	TextView studentNumber;
	TextView studentName;
	TextView cardType;
	TextView cardBalance;
	TextView cardLoss;
	TextView cardFrozen;

	

	
	private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {  
            case 1:
            	result = (String)msg.obj;
            	decodeCard(result);
                break;
            default:  
                break;  
            }  
		}
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		
		View view = inflater.inflate(R.layout.left_page1, container,
				false);
		
		//实现异步通信.
		LoginActivity.xauth.setHandlerUrl(handler, Xauth.BASE_URL+Xauth.REST_URL+"?method="+"card.get");
		new Thread(LoginActivity.xauth).start(); 
		//显示部分的内容
		initView(view);
		return view;
	}

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
		
		studentNumber = (TextView) view.findViewById(R.id.student_number);
		studentName = (TextView) view.findViewById(R.id.student_name);
		cardType = (TextView) view.findViewById(R.id.card_type);
		cardBalance = (TextView) view.findViewById(R.id.card_balance2);
		cardLoss =  (TextView) view.findViewById(R.id.card_loss2);
		cardFrozen =  (TextView) view.findViewById(R.id.card_frozen2);
	

	}


	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		BaseSlidingFragmentActivity baseFragment = (BaseSlidingFragmentActivity) getActivity();
		mSlidingMenu = baseFragment.getSlidingMenu();
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

	


		//这个方法该在何处调用.
		//String result =Xauth.doMethod(Xauth.BASE_URL+Xauth.REST_URL+"?method="+"card.get").toString();
	    //decodeCard(result);

		
		private void decodeCard(String result){
			try {
				//JSONObject decode = new JSONObject(result); 
				JSONArray jsonArray = new JSONArray(result);
				String identityNumber =null;
				String name =null;
				String cardTypeName =null;
			    double balance =(double) 0.0;
				boolean lossState = false;
				boolean freezeState = false;
				int iSize = jsonArray.length();
				for (int i = 0; i < iSize; i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
				    identityNumber = (String) jsonObj.get("identityNumber");
					 name = (String)jsonObj.get("name");
					 cardTypeName = (String)jsonObj.get("cardTypeName");
					 balance =  jsonObj.getDouble("balance");
					 lossState = (Boolean) jsonObj.get("lossState");
					 freezeState=(Boolean) jsonObj.get("freezeState");
				}
					
				Log.d("XauthDemo","学号:"+identityNumber);
				Log.d("XauthDemo","姓名:"+name);
				Log.d("XauthDemo","卡片类型:"+cardTypeName);
				Log.d("XauthDemo","余额:"+balance);
				Log.d("XauthDemo","挂失:"+lossState);
				Log.d("XauthDemo","冻结:"+freezeState);
				studentNumber.setText(identityNumber);
				studentName.setText(name);
				cardType.setText(cardTypeName);
				cardBalance.setText(balance+"元");
				cardLoss.setText( lossState ? "是":"否");
				cardFrozen.setText(freezeState ? "是":"否");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	
	
}
