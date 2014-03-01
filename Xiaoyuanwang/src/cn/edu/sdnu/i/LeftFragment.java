package cn.edu.sdnu.i;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.sdnu.i.page.LeftPage0;
import cn.edu.sdnu.i.page.LeftPage1;
import cn.edu.sdnu.i.page.LeftPage2;
import cn.edu.sdnu.i.page.LeftPage3;
import cn.edu.sdnu.i.page.LeftPage4;
import cn.edu.sdnu.i.page.LeftPage5;
import cn.edu.sdnu.i.util.menu.BaseSlidingFragmentActivity;
import cn.edu.sdnu.i.util.menu.SlidingMenu;

public class LeftFragment extends Fragment {

	
	private ListView lv_set;
	private SlidingMenu mSlidingMenu;
	private FragmentTransaction ft;
	private View view;
	private Activity activity;
	private MyTitleAdapter adapter;
	public static String title[] = { "首页", "一卡通", "图书馆", "失物招领","空闲教室","班车信息" };
	private Fragment mFrag[];
	public static String mTitleName = title[0];
	private int item[] = new int[title.length];
	private int clickFlag = 0;
	private int[] imgs = { R.drawable.left_list_cust,
			R.drawable.left_list_busi, R.drawable.left_list_cust,
			R.drawable.left_list_busi , R.drawable.left_list_cust,
			R.drawable.left_list_busi };
	private int[] imgsOn = { R.drawable.left_list_cust_on,
			R.drawable.left_list_busi_on, R.drawable.left_list_cust_on,
			R.drawable.left_list_busi_on , R.drawable.left_list_cust_on,
			R.drawable.left_list_busi_on };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_left_fragment, container,
				false);
		initView(view);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFrag = new Fragment[]{ new LeftPage0(), new LeftPage1(),
				new LeftPage2(), new LeftPage3(), new LeftPage4(), new LeftPage5() };
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initView(View view) {

		// title
		
		TextView title1 = (TextView) view.findViewById(R.id.ivTitleName);
		title1.setText("菜单");
		ImageButton left = (ImageButton)view.findViewById(R.id.ivTitleBtnLeft);
		ImageButton right = (ImageButton)view.findViewById(R.id.ivTitleBtnRigh);
		left.setVisibility(view.INVISIBLE);
		right.setVisibility(view.INVISIBLE);
		
		lv_set = (ListView) view.findViewById(R.id.lv_set);
		lv_set.setAdapter(new ArrayAdapter<String>(this.getActivity(),R.layout.item,R.id.tv_item,title));
		
		adapter = new MyTitleAdapter(title, null,
				LeftFragment.this.getActivity(), imgs, imgsOn);
		lv_set.setAdapter(adapter);
		lv_set.setOnItemClickListener(new mItemClickListener());

		activity = this.getActivity(); // 得到该Fragment所在的Activity
		// 在该activity下得到fragment控制器
		ft = ((FragmentActivity) activity).getSupportFragmentManager()
				.beginTransaction();
		mFrag[0] = new LeftPage0();
		ft.replace(R.id.main_center_fragment, mFrag[0]);
		// ft.show(mFrag[0]);
		ft.commit();

	}

	class mItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			adapter.setSelectedIndex(position);
			adapter.notifyDataSetChanged();
			ft.remove(mFrag[0]);
			ft = ((FragmentActivity) activity).getSupportFragmentManager()
					.beginTransaction();
			// 当某个菜单项被初次点击或者连续点两次时，有初始化or刷新的效果
			if (item[position] != position || clickFlag == position) {
				ft.remove(mFrag[position]);
				if (position == 0) {
					mFrag[position] = new LeftPage0();
				}
				if (position == 1) {
					mFrag[position] = new LeftPage1();
				}
				if (position == 2) {
					mFrag[position] = new LeftPage2();
				}
				if (position == 3) {
					mFrag[position] = new LeftPage3();
				}
				if (position == 4) {
					mFrag[position] = new LeftPage4();
				}
				if (position == 5) {
					mFrag[position] = new LeftPage5();
				}
				ft.add(R.id.main_center_fragment, mFrag[position]);
			}
			for (int i = 0; i < title.length; i++) {
				ft.hide(mFrag[i]);
			}
			ft.show(mFrag[position]);
			ft.commit();// 必须commit，转换器才会开始工作
			item[position] = position;
			clickFlag = position;

			BaseSlidingFragmentActivity baseFragment = (BaseSlidingFragmentActivity) getActivity();
			mSlidingMenu = baseFragment.getSlideMenu();
			mSlidingMenu.showContent();
			mTitleName = title[position];
		}
	}
}
