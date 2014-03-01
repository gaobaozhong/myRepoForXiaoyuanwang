package cn.edu.sdnu.i.page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.sdnu.i.LeftFragment;
import cn.edu.sdnu.i.R;
import cn.edu.sdnu.i.util.menu.BaseSlidingFragmentActivity;
import cn.edu.sdnu.i.util.menu.SlidingMenu;


public class LeftPage4 extends Fragment implements OnClickListener {

	protected static SlidingMenu mSlidingMenu;
	private TextView mTitleName;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView");
		View view = inflater.inflate(R.layout.left_page4, container, false);
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

}
