package cn.edu.sdnu.i;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyTitleAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private List<Picture> pictures;
	private int selectedIndex;
	private int[] imgsOn;


	public MyTitleAdapter(String[] titles, int[] nums,Context context,int[] imgs,int[] imgsOn) {
		super();
		Log.i("AS","Adapter构造方法");
		pictures = new ArrayList<Picture>();
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.imgsOn = imgsOn;
		for (int i = 0; i < titles.length; i++) {
			if(null != nums){
				Picture picture = new Picture(titles[i], imgs[i], nums[i]);
				pictures.add(picture);
			}else {
				//传个默认参数-1
				Picture picture = new Picture(titles[i], imgs[i], -1);
				pictures.add(picture);
			}
		}
	}

	@Override
	public int getCount() {
		return null != pictures?pictures.size():0;
	}

	@Override
	public Object getItem(int position) {
		return pictures.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.my_title_item, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.my_title_tv);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.my_title_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title.setText(pictures.get(position).getTitle());
		viewHolder.image.setImageResource(pictures.get(position).getImageId());

		viewHolder.title.setBackgroundResource(R.drawable.menu_on);
		if (position == selectedIndex || -1 == selectedIndex) {
			if (0 == selectedIndex) {
				viewHolder.image.setImageResource(imgsOn[0]);
			} else if (1 == selectedIndex) {
				viewHolder.image.setImageResource(imgsOn[1]);
			} else if (2 == selectedIndex) {
				viewHolder.image.setImageResource(imgsOn[2]);
			} else if (3 == selectedIndex) {
				viewHolder.image.setImageResource(imgsOn[3]);
			} else if (4 == selectedIndex) {
				viewHolder.image.setImageResource(imgsOn[4]);
			}
			// 默认选中颜色
		} else {
			viewHolder.title.setBackgroundColor(mContext.getResources()
					.getColor(R.color.left_bg));
		}
		return convertView;
	}
	/**设置选中项
	 * @param selectedIndex
	 */
	public void setSelectedIndex(int selectedIndex){
		this.selectedIndex = selectedIndex;
		
	}

	public void setNums(int[] nums) {
		pictures.get(0).setNum(nums[0]);
		pictures.get(1).setNum(nums[1]);
		pictures.get(2).setNum(nums[2]);
		pictures.get(3).setNum(nums[3]);
		pictures.get(4).setNum(nums[4]);
	}

}

class ViewHolder {
	public TextView title;
	public ImageView image;
	public TextView num;
}

class Picture {
	private String title;
	private int imageId;
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Picture() {
		super();
	}

	public Picture(String title, int imageId, int num) {
		super();
		this.title = title;
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

}

