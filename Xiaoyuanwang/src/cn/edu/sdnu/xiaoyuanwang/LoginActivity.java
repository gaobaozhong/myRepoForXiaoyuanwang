package cn.edu.sdnu.xiaoyuanwang;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.sdnu.xiaoyuanwang.util.http.HttpUtil;

import com.example.fragmenttest.R;

public class LoginActivity extends Activity {

	Button button_Denglu;
	EditText editText_Yonghu;
	EditText editText_Mima;

	String yonghu;
	String mima;
	
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

	}

	private void setListeners() {

		button_Denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 验证用户和密码
				yonghu = editText_Yonghu.getText().toString().trim();
				mima = editText_Mima.getText().toString().trim();

				//执行输入校验
				if (validate()) {
					//如果登陆成功
					if (loginPro()) {
						
						// 如果成功进入主页
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						LoginActivity.this.startActivity(intent);
					} else// 否则
					{
						Toast.makeText(LoginActivity.this, "出错了,请重新输入",
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
	}
	//执行登陆校验
	protected boolean loginPro() {
		JSONObject jsonObj;
		try
		{
			jsonObj = query(yonghu,mima);
			//如果userId大于0
			if(jsonObj.getInt("userId")>0)
			{
				return true;
			}
		}
		catch(Exception e)
		{
			Toast.makeText(LoginActivity.this, "服务器响应异常，请稍后再试！",Toast.LENGTH_SHORT).show();		
			e.printStackTrace();
		}
		return true;
	}
	
	private JSONObject query(String yonghu2, String mima2) throws JSONException, Exception {
		// TODO Auto-generated method stub
		//使用Map封装请求参数
		Map<String,String> map = new HashMap<String,String>();
		map.put("yonghu", yonghu);
		map.put("mima", mima);
		//定义发送请求的URL
		String url = HttpUtil.BASE_URL + "AccessToken.ashx";
		//发送请求
		
		return new JSONObject(HttpUtil.postRequest(url,map));
	}

	//执行输入校验
	protected boolean validate() {
		// TODO Auto-generated method stub
		if(yonghu.equals(""))
		{
			Toast.makeText(LoginActivity.this, "用户名必须填！",Toast.LENGTH_SHORT).show();		
			return false;
		}
		if(mima.equals(""))
		{
			Toast.makeText(LoginActivity.this, "密码必须填！",Toast.LENGTH_SHORT).show();			
			return false;
		}
		return true;
	}

	private void findViews() {
		button_Denglu = (Button) this.findViewById(R.id.button_Denglu);
		editText_Yonghu = (EditText) this.findViewById(R.id.editText_Yonghu);
		editText_Mima = (EditText) this.findViewById(R.id.editText_Mima);
	}



}
