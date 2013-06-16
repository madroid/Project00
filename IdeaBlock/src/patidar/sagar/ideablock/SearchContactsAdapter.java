package patidar.sagar.ideablock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchContactsAdapter extends ArrayAdapter<HashMap<String, String>> {

	private LayoutInflater li ;
	private int mResource ;
	private int mTextViewID ;
	private List<HashMap<String, String>> data_list ;
	private ArrayList<HashMap<String, String>> original_data_list;
	private static Activity contxt ;
	
	public SearchContactsAdapter(Activity context,int resource, int textViewResourceId,
			ArrayList<HashMap<String, String>> objects) {
		super(context, resource,textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.contxt = (Activity) context ;
		this.data_list = objects;
		this.mTextViewID = textViewResourceId ;
		this.mResource = resource;
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.original_data_list = new ArrayList<HashMap<String,String>>();
		this.original_data_list.addAll(objects);
		
	}
	
	public static class ViewHolder {
		private TextView contacts_name ;
		private TextView contacts_id ;
		private ImageView contacts_default_pic2 ;
		private ImageView contacts_button_send ;
		private ImageView contacts_button_request ;
		
			public ViewHolder(View v){
				this.contacts_id = (TextView) v.findViewById(R.id.row_contacts_id);
				this.contacts_name = (TextView) v.findViewById(R.id.row_contacts_name);
				this.contacts_button_request = (ImageView) v.findViewById(R.id.contacts_button_request);
				this.contacts_button_send = (ImageView) v.findViewById(R.id.contacts_button_send);
				this.contacts_default_pic2 = (ImageView) v.findViewById(R.id.contacts_default_pic2);
				Constants.setTextViewFontStyle(contxt.getAssets(), this.contacts_id,this.contacts_name);
			}
	}
	
	public View getView(int position, View convertView, ViewGroup Parent){
		ViewHolder holder ;
		if(convertView==null){
			convertView = li.inflate(this.mResource,null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, String> item = getItem(position);
		holder.contacts_id.setText(item.get("id"));
		holder.contacts_name.setText(item.get("name"));
		return convertView;
	}
	
	
    public void filter(String charText) {
        charText = charText.toLowerCase();
        data_list.clear();
        if (charText.length() == 0) {
            data_list.addAll(original_data_list);
        } else {
            for (HashMap hmap : original_data_list) {
                if (((String) hmap.get("name")).toLowerCase().contains(charText)) {
                    data_list.add(hmap);
                }
            }
        }	 
        notifyDataSetChanged();
    }
	
}
