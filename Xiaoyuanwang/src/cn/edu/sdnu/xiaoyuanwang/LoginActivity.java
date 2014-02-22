package cn.edu.sdnu.xiaoyuanwang;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.sdnu.xiaoyuanwang.util.http.xauth.Xauth;

import com.example.fragmenttest.R;

public class LoginActivity extends Activity {

	private static final String PREF = "XIAOYUANWANG_PREF";
	private static final String PREF_YONGHU = "XIAOYUANWANG_YONGHU";
	Button button_Denglu;
	EditText editText_Yonghu;
	EditText editText_Mima;

	String yonghu;
	String mima;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_login);
		denglu();
	}

	private void denglu() {

		// 得到 登录按钮
		findViews();
		// 恢复上一次用户登录时输入的数据
		SharedPreferences settings = this.getSharedPreferences(PREF, 0);
		String pref_yonghu = settings.getString(PREF_YONGHU, "");
		if (!"".equals(pref_yonghu)) {
			editText_Yonghu.setText(pref_yonghu);
			editText_Mima.requestFocus();
		}

		// 添加处理事件
		setListeners();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// 保存用户名
		SharedPreferences settings = this.getSharedPreferences(PREF, 0);
		settings.edit()
				.putString(PREF_YONGHU, editText_Yonghu.getText().toString())
				.commit();
	}

	private void setListeners() {

		button_Denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 验证用户和密码
				yonghu = editText_Yonghu.getText().toString().trim();
				mima = editText_Mima.getText().toString().trim();				

				// 执行输入校验
				if (validate()) {
					// 如果登陆成功
					if (loginPro()) {

						// 如果成功
						// 进入主页
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						LoginActivity.this.startActivity(intent);
					} else// 否则
					{
						Toast.makeText(LoginActivity.this,
								"出错了,请重新输入." + yonghu + ":" + mima,
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
	}

	// 执行登陆校验
	protected boolean loginPro() {
		JSONObject jsonObj;
		try {
			jsonObj = query(yonghu, mima);
			// 如果userId大于0
			if (jsonObj.getInt("userId") > 0) {
				return true;
			}
		} catch (Exception e) {
			Toast.makeText(LoginActivity.this, "服务器响应异常，请稍后再试！",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return true;
	}

	private JSONObject query(String yonghu, String mima) throws JSONException,
			Exception {

		// 生成Xauth认证对象。调用构造器传递用户名和密码。
		// 并调用doAccessToken生成TokenKey和TokenSecret。
		Xauth xauth = new Xauth(yonghu, mima);

		// 根据获得的tokenkey和tokenSecret再来获得相应的用户id

		// 临时生成返回变量resultm，包含用户id和用户密码。
		Map<String, String> resultm = new HashMap<String, String>();

		// resultm = xauth.doMethod();

		return new JSONObject(resultm);
	}

	// 执行输入校验
	protected boolean validate() {
		if (yonghu.equals("")) {
			Toast.makeText(LoginActivity.this, "用户名必须填！", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (mima.equals("")) {
			Toast.makeText(LoginActivity.this, "密码必须填！", Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		return true;
	}

	private void findViews() {
		button_Denglu = (Button) this.findViewById(R.id.button_Denglu);
		editText_Yonghu = (EditText) this.findViewById(R.id.editText_Yonghu);
		editText_Mima = (EditText) this.findViewById(R.id.editText_Mima);
	}
	
	public void startIntentServices(View Source)
	{
		
	}

}
