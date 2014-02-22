package cn.edu.sdnu.xiaoyuanwang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.sdnu.xiaoyuanwang.util.http.xauth.Xauth;
import cn.edu.sdnu.xiaoyuanwang.util.menu.BaseSlidingFragmentActivity;
import cn.edu.sdnu.xiaoyuanwang.util.menu.SlidingMenu;

import com.example.fragmenttest.R;

public class MainActivity extends BaseSlidingFragmentActivity implements
		OnClickListener {

	protected SlidingMenu mSlidingMenu;

	private LeftFragment mLeftFragment;

	private RightFragment mRightFragment;

	private TextView mTitleName;

	Button button_test;
	TextView textView_test;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu();
		setContentView(R.layout.main_center_layout);
		initView(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	private void initView(Bundle savedInstanceState) {

		mSlidingMenu.setSecondaryMenu(R.layout.main_right_layout);
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment mFrag = new RightFragment();
		mFragementTransaction.replace(R.id.main_right_fragment, mFrag);
		mFragementTransaction.commit();
		
		//test
		button_test = (Button)findViewById(R.id.button_test);
		textView_test = (TextView)findViewById(R.id.textView_test);
		
		
		((ImageButton) findViewById(R.id.ivTitleBtnLeft))
				.setOnClickListener(this);
		((ImageButton) findViewById(R.id.ivTitleBtnRigh))
				.setOnClickListener(this);
		this.findViewById(R.id.button_test).setOnClickListener(this);
	}

	private void initSlidingMenu() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int mScreenWidth = dm.widthPixels;// ��ȡ��Ļ�ֱ��ʿ��
		// TODO Auto-generated method stub
		setBehindContentView(R.layout.main_left_layout);// ������˵�
		FragmentTransaction mFragementTransaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment mFrag = new LeftFragment();
		mFragementTransaction.replace(R.id.main_left_fragment, mFrag);
		mFragementTransaction.commit();
		// customize the SlidingMenu
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);// �������󻬻����һ����������Ҷ����Ի������������Ҷ����Ի�
		mSlidingMenu.setShadowWidth(mScreenWidth / 40);// ������Ӱ���
		mSlidingMenu.setBehindOffset(mScreenWidth / 8);// ���ò˵����
		mSlidingMenu.setFadeDegree(0.35f);// ���õ��뵭���ı���
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mSlidingMenu.setShadowDrawable(R.drawable.slidingmenu_shadow);// ������˵���ӰͼƬ
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.right_shadow);// �����Ҳ˵���ӰͼƬ
		mSlidingMenu.setFadeEnabled(true);// ���û���ʱ�˵����Ƿ��뵭��
		mSlidingMenu.setBehindScrollScale(0.333f);// ���û���ʱ��קЧ��
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivTitleBtnLeft:
			mSlidingMenu.showMenu(true);
			break;
		case R.id.ivTitleBtnRigh:
			mSlidingMenu.showSecondaryMenu(true);
			break;
		case R.id.button_test:
			textView_test.setText(Xauth.doMethod(Xauth.BASE_URL+Xauth.REST_URL+"?method="+"people.get").toString());
		default:
			break;
		}
	}

}
