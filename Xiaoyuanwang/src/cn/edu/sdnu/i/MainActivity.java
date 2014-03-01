package cn.edu.sdnu.i;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.sdnu.i.util.menu.BaseSlidingFragmentActivity;
import cn.edu.sdnu.i.util.menu.SlidingMenu;

public class MainActivity extends BaseSlidingFragmentActivity{

	protected SlidingMenu mSlidingMenu;

	Button button_test;
	TextView textView_test;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_center_layout);

		super.initSlidingMenu();
		super.initView(savedInstanceState);

	
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)

			.setTitle("消息")

			.setMessage("确定要退出么?")

			.setNegativeButton("No", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			})

			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton) {

					finish();
				    System.exit(0);

				}

			}).show();

			return true;

		} else {

			return super.onKeyDown(keyCode, event);

		}

	}



}
