package patidar.sagar.ideablock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import patidar.sagar.ideablock.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


public class NotesAdapter extends ArrayAdapter<HashMap<String, String>>{

	private LayoutInflater li ;
	private int mResource ;
	private ArrayList<HashMap<String, String>> data_list;
	private static Activity cntxt ;

	public NotesAdapter(Activity context, int resource, int textViewResourceId,
			List<HashMap<String, String>> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data_list = (ArrayList<HashMap<String, String>>) objects;
		this.mResource = resource;
		this.cntxt = (Activity) context ;
	}

	public static class ViewHolder {
		private TextView notes_subject ;
		private TextView notes_message ;
		private ImageButton notes_arrow ;


		public ViewHolder(View v){
			notes_subject = (TextView) v.findViewById(R.id.notes_subject);
			notes_message = (TextView) v.findViewById(R.id.notes_message);
			notes_arrow = (ImageButton) v.findViewById(R.id.notes_button_view);
			Constants.setTextViewFontStyle(cntxt.getAssets(),this.notes_message,this.notes_subject);
		}
		
		
	}
	
	public View getView(int position, View convertView, ViewGroup Parent){
		ViewHolder holder ;
		if(convertView==null){
			convertView = li.inflate(this.mResource, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, String> item = getItem(position);
		holder.notes_subject.setText(item.get("subject"));
		holder.notes_message.setText(item.get("message"));
		final Intent intent = new Intent(cntxt,Details.class);
		intent.putExtra("position", position);
		
		holder.notes_arrow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cntxt.startActivity(intent);
//				Log.d("POSITION_CHECK", intent.getStringExtra("position"));
			}
		});
		
		
		
		return convertView;
	}



}
