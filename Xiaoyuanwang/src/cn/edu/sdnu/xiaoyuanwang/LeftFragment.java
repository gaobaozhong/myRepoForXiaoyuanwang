package cn.edu.sdnu.xiaoyuanwang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmenttest.R;



public class LeftFragment extends Fragment {



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
		
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private void initView(View view) {


		// title
		view.findViewById(R.id.ivTitleBtnLeft).setVisibility(View.GONE);
		view.findViewById(R.id.ivTitleBtnRigh).setVisibility(View.GONE);
		TextView title = (TextView) view.findViewById(R.id.ivTitleName);
		title.setText("左边");
	}

}
