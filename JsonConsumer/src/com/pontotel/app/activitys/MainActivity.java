package com.pontotel.app.activitys;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pontotel.app.R;
import com.pontotel.app.bean.DataBean;
/**
 * 
 * @author Bruno
 *
 */
public class MainActivity extends Activity {

	private DataBean dataBean = new DataBean();

	public static String AssetJSONFile(String fileName, Context context) throws IOException {
		AssetManager manager = context.getAssets();
		InputStream file = manager.open(fileName);
		byte[] formArray = new byte[file.available()];
		file.read(formArray);
		file.close();

		return new String(formArray);
	}

	/**
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView view = (ListView) findViewById(R.id.listFormulas);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00BFFF")));

		ArrayList<String> formList = new ArrayList<String>();

		Context context = getApplicationContext();

		try {
			ArrayList<String> m_li;

			String jsonLocation = AssetJSONFile("data.json", context);

			JSONObject object = new JSONObject(jsonLocation);
			JSONArray formArray = object.getJSONArray("data");

			for (int i = 0; formArray.length() > i; i++) {
				JSONObject obj = formArray.getJSONObject(i);
				dataBean.setId(obj.getString("id"));
				dataBean.setName(obj.getString("name"));
				dataBean.setPwd(obj.getString("pwd"));

				m_li = new ArrayList<String>();
				m_li.add("\nID: " + dataBean.getId() + "\nName: " + dataBean.getName() + "\nPwd: " + dataBean.getPwd()+"\n");

				formList.addAll(m_li);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		ArrayAdapter<String> dataBeanListView = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				formList) {

			/**
			 * 
			 */
			public View getView(int position, View convertView, ViewGroup parent) {

				View view = super.getView(position, convertView, parent);
				((TextView) view).setTextColor(R.color.CINZA);
				((TextView) view).setTypeface(null, Typeface.BOLD);

				return view;
			}

		};
		view.setAdapter(dataBeanListView);
		view.setVerticalScrollBarEnabled(false);
		view.setHorizontalScrollBarEnabled(false);
	}
}
