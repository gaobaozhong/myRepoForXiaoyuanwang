package cn.edu.sdnu.xiaoyuanwang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button button_Denglu;
	EditText editText_Yonghu;
	EditText editText_Mima;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_login);
		denglu();
	}

	private void denglu() {

		// 得到 登录按钮
		findViews();
		// 添加处理事件
		setListeners();
		button_Denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 验证用户和密码
				String yonghu = editText_Yonghu.getText().toString();
				String mima = editText_Mima.getText().toString();

				if (yonghu.equals("abc") && mima.equals("abc")) {
					// 如果成功进入主页
					Intent intent = new Intent();
					intent.setClass(MainActivity.this, HomeActivity.class);
					MainActivity.this.startActivity(intent);
				}else//否则
				{
					Toast.makeText(MainActivity.this, "出错了,请重新输入", Toast.LENGTH_SHORT).show();
				}
				
			}
		});

	}

	private void setListeners() {
		// TODO Auto-generated method stub

	}

	private void findViews() {
		button_Denglu = (Button) this.findViewById(R.id.button_Denglu);
		editText_Yonghu = (EditText) this.findViewById(R.id.editText_Yonghu);
		editText_Mima = (EditText) this.findViewById(R.id.editText_Mima);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
