package cn.edu.sdnu.i;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.sdnu.i.util.xauth.Xauth;

public class LoginActivity extends Activity {

	private static final String PREF = "XIAOYUANWANG_PREF";
	private static final String PREF_YONGHU = "XIAOYUANWANG_YONGHU";
	private static final String PREF_MIMA = "XIAOYUANWANG_MIMA";
	Button button_Denglu;
	EditText editText_Yonghu;
	EditText editText_Mima;

	String yonghu;
	String mima;
	String people;
	int identityNumber;
	String name;
	String sex;
	String nation;
	String organizationName;

	// 定义成静态全局,别处可见.不过显然不是很好啊.
	public static Xauth xauth;

	private String result;

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
		String pref_mima = settings.getString(PREF_MIMA, "");
		if (!"".equals(pref_yonghu) && !"".equals(pref_mima)) {
			editText_Yonghu.setText(pref_yonghu);
			editText_Mima.setText(pref_mima);
			button_Denglu.requestFocus();
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
				.putString(PREF_MIMA, editText_Mima.getText().toString())
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
					// 生成Xauth认证对象。调用构造器传递用户名和密码。
					// 并调用doAccessToken生成TokenKey和TokenSecret。
					xauth = new Xauth(yonghu, mima);
					// 启动多线程.
					xauth.setHandlerUrl(handler, Xauth.BASE_URL
							+ Xauth.REST_URL + "?method=" + "people.get");
					new Thread(LoginActivity.xauth).start();
					/*
					 * // 如果登陆成功 if (loginPro()) {
					 * 
					 * // 进入主页 Intent intent = new Intent();
					 * intent.setClass(LoginActivity.this, MainActivity.class);
					 * 
					 * Bundle bundle = new Bundle();
					 * bundle.putInt("identityNumber", identityNumber);
					 * bundle.putString("name", name); bundle.putString("sex",
					 * sex); bundle.putString("nation", nation);
					 * bundle.putString("organizationName", organizationName);
					 * intent.putExtras(bundle);
					 * 
					 * LoginActivity.this.startActivity(intent);
					 * 
					 * // 需要修正返回键返回到 login界面. finish(); } else// 否则 {
					 * Toast.makeText(LoginActivity.this, "出错了,请重新输入.",
					 * Toast.LENGTH_SHORT).show(); }
					 */
				}

			}
		});
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				result = (String) msg.obj;
				loginPro(result);
				break;
			case 0:
				Toast.makeText(LoginActivity.this, "登陆失败！", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		}

	};

	// 执行登陆校验
	protected void loginPro(String result) {
		try {
			JSONObject jso = new JSONObject(result);
			identityNumber = jso.getInt("identityNumber");
			Log.d("test3", "identityNumber:" + identityNumber);
			name = jso.getString("name");
			Log.d("test3", "name:" + name);
			sex = jso.getString("sex");
			Log.d("test3", "sex:" + sex);
			nation = jso.getString("nation");
			Log.d("test3", "nation:" + nation);
			organizationName = jso.getString("organizationName");
			Log.d("test3", "organizationName:" + organizationName);
			// 如果userId大于0
			if (jso.getInt("identityNumber") > 0) {

				goMainActivity();
			}
		} catch (Exception e) {
			Toast.makeText(LoginActivity.this, "服务器响应异常，请稍后再试！",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

		/*
		 * try { JSONObject jso; jso = query(yonghu, mima);
		 * 
		 * identityNumber = jso.getInt("identityNumber"); Log.d("test3",
		 * "identityNumber:" + identityNumber); name = jso.getString("name");
		 * Log.d("test3", "name:" + name); sex = jso.getString("sex");
		 * Log.d("test3", "sex:" + sex); nation = jso.getString("nation");
		 * Log.d("test3", "nation:" + nation); organizationName =
		 * jso.getString("organizationName"); Log.d("test3", "organizationName:"
		 * + organizationName); // 如果userId大于0 if (jso.getInt("identityNumber")
		 * > 0) { return true; } } catch (Exception e) {
		 * Toast.makeText(LoginActivity.this, "服务器响应异常，请稍后再试！",
		 * Toast.LENGTH_SHORT).show(); e.printStackTrace(); } return false;
		 */
	}

	private void goMainActivity() {

		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt("identityNumber", identityNumber);
		bundle.putString("name", name);
		bundle.putString("sex", sex);
		bundle.putString("nation", nation);
		bundle.putString("organizationName", organizationName);
		intent.putExtras(bundle);

		LoginActivity.this.startActivity(intent);

		// 需要修正返回键返回到 login界面.
		finish();
	}

	private JSONObject query(String yonghu, String mima) throws JSONException,
			Exception {

		// 生成Xauth认证对象。调用构造器传递用户名和密码。
		// 并调用doAccessToken生成TokenKey和TokenSecret。
		Xauth xauth = new Xauth(yonghu, mima);

		// 根据获得的tokenkey和tokenSecret再来获得相应的用户id

		// 临时生成返回变量resultm，包含用户id和用户密码。

		people = Xauth.doMethod(Xauth.BASE_URL + Xauth.REST_URL + "?method="
				+ "people.get");

		return new JSONObject(people);

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

	public void startIntentServices(View Source) {

	}

}
