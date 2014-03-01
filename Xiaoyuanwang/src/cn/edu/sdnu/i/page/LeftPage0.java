package cn.edu.sdnu.i.page;

import android.os.Bundle;
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
import cn.edu.sdnu.i.R;
import cn.edu.sdnu.i.util.menu.BaseSlidingFragmentActivity;
import cn.edu.sdnu.i.util.menu.SlidingMenu;

public class LeftPage0 extends Fragment implements OnClickListener {

	protected static SlidingMenu mSlidingMenu;
	private TextView mTitleName;

	private TextView textview1;
	private TextView textview2;
	private TextView textview3;
	private TextView textview4;
	private TextView textview5;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.left_page0, container, false);
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

		Bundle bundle = this.getActivity().getIntent().getExtras();
		int identityNumber = bundle.getInt("identityNumber");
		String name = bundle.getString("name");
		String sex = bundle.getString("sex");
		String nation = bundle.getString("nation");
		String organizationName = bundle.getString("organizationName");
		Log.d("test2", "organizationName:" + organizationName);


		textview2 = (TextView) view.findViewById(R.id.textView2);

		textview5 = (TextView) view.findViewById(R.id.textView5);


		textview2.setText(name);

		textview5.setText(organizationName);

		((ImageButton) view.findViewById(R.id.ivTitleBtnLeft))
				.setOnClickListener(this);
		((ImageButton) view.findViewById(R.id.ivTitleBtnRigh))
				.setOnClickListener(this);
		((Button) view.findViewById(R.id.button_home))
		.setOnClickListener(this);
		mTitleName = (TextView) view.findViewById(R.id.ivTitleName);
		mTitleName.setText(LeftFragment.mTitleName);

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
		case R.id.button_home:
			mSlidingMenu.showMenu(true);
			break;
		default:
			break;
		}
	}

}

/*
 * package cn.edu.sdnu.xiaoyuanwang.page;
 * 
 * import org.json.JSONException; import org.json.JSONObject;
 * 
 * import android.os.Bundle; import android.support.v4.app.Fragment; import
 * android.util.Log; import android.view.LayoutInflater; import
 * android.view.View; import android.view.View.OnClickListener; import
 * android.view.ViewGroup; import android.widget.ImageButton; import
 * android.widget.TextView; import cn.edu.sdnu.xiaoyuanwang.LeftFragment; import
 * cn.edu.sdnu.xiaoyuanwang.util.menu.BaseSlidingFragmentActivity; import
 * cn.edu.sdnu.xiaoyuanwang.util.menu.SlidingMenu; import
 * cn.edu.sdnu.xiaoyuanwang.util.xauth.Xauth;
 * 
 * import com.example.fragmenttest.R;
 * 
 * public class LeftPage0 extends Fragment implements OnClickListener {
 * 
 * protected static SlidingMenu mSlidingMenu; private TextView mTitleName;
 * 
 * // test private TextView textview1; private TextView textview2; private
 * TextView textview3; private TextView textview4; private TextView textview5;
 * 
 * 
 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
 * container, Bundle savedInstanceState) {
 * 
 * View view = inflater.inflate(R.layout.left_page0, container, false);
 * initView(view); return view; }
 * 
 * @Override public void onCreate(Bundle savedInstanceState) { // TODO
 * Auto-generated method stub super.onCreate(savedInstanceState);
 * 
 * }
 * 
 * private void initView(View view) { try {String result; JSONObject jso; int
 * identityNumber; String name; String sex; String nation; String
 * organizationName;
 * 
 * result = Xauth.doMethod(Xauth.BASE_URL + Xauth.REST_URL + "?method=" +
 * "people.get");
 * 
 * jso = new JSONObject(result); identityNumber = jso.getInt("identityNumber");
 * Log.d("test2", "identityNumber:" + identityNumber); name =
 * jso.getString("name"); sex = jso.getString("sex"); nation =
 * jso.getString("nation"); organizationName =
 * jso.getString("organizationNumber");
 * 
 * ((ImageButton) view.findViewById(R.id.ivTitleBtnLeft))
 * .setOnClickListener(this); ((ImageButton)
 * view.findViewById(R.id.ivTitleBtnRigh)) .setOnClickListener(this);
 * 
 * textview1 = (TextView) view.findViewById(R.id.textView1); textview2 =
 * (TextView) view.findViewById(R.id.textView2); textview3 = (TextView)
 * view.findViewById(R.id.textView3); textview4 = (TextView)
 * view.findViewById(R.id.textView4); textview5 = (TextView)
 * view.findViewById(R.id.textView5);
 * 
 * textview1.setText(identityNumber); textview2.setText(name);
 * textview3.setText(sex); textview4.setText(nation);
 * textview5.setText(organizationName); mTitleName = (TextView)
 * view.findViewById(R.id.ivTitleName);
 * mTitleName.setText(LeftFragment.mTitleName);
 * 
 * // textview1.setText(Xauth.doMethod(Xauth.BASE_URL+Xauth.REST_URL+"?method="+
 * "people.get").toString()); } catch (JSONException e) { // TODO Auto-generated
 * catch block e.printStackTrace(); } }
 * 
 * @Override public void onClick(View v) { // TODO Auto-generated method stub
 * BaseSlidingFragmentActivity baseFragment = (BaseSlidingFragmentActivity)
 * getActivity(); mSlidingMenu = baseFragment.getSlidingMenu(); switch
 * (v.getId()) { case R.id.ivTitleBtnLeft: mSlidingMenu.showMenu(true); break;
 * case R.id.ivTitleBtnRigh: mSlidingMenu.showSecondaryMenu(true); break;
 * 
 * default: break; } }
 * 
 * }
 */