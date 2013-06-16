package patidar.sagar.ideablock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.android.gms.internal.m;

import patidar.sagar.ideablock.SearchContactsAdapter.ViewHolder;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionAdapter extends ArrayAdapter<HashMap<String, String>> {

	private LayoutInflater li ;
	private int mResource ;
	private List<HashMap<String, String>> data_list ;
	private ArrayList<HashMap<String, String>> original_data_list;
	private static Activity contxt ;
	
	public TransactionAdapter(Activity context,int resource,
			ArrayList<HashMap<String, String>> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.contxt = (Activity) context ;
		this.data_list = objects;
		this.mResource = resource;
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.original_data_list = new ArrayList<HashMap<String,String>>();
		this.original_data_list.addAll(objects);
		
	}
	
	public static class ViewHolder {
		private TextView transID ;
		private TextView transID2 ;
		private TextView transAmount ;
		private TextView transAmount2 ;
		private TextView transDate ;
		private TextView transDate2 ;
		private ImageView transDetails ;
		
			public ViewHolder(View v){
				transID = (TextView) v.findViewById(R.id.trans_ID);
				transID2 = (TextView) v.findViewById(R.id.trans_ID2);
				transAmount = (TextView) v.findViewById(R.id.trans_amount);
				transAmount2 = (TextView) v.findViewById(R.id.trans_amount2);
				transDate = (TextView) v.findViewById(R.id.trans_date);
				transDate2 = (TextView) v.findViewById(R.id.trans_date2);
				transDetails = (ImageView) v.findViewById(R.id.trans_image_row_details);
				Constants.setTextViewFontStyle(contxt.getAssets(), this.transAmount,this.transAmount2,this.transDate,this.transDate2,
						this.transID,this.transID2);
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
		holder.transAmount2.setText(item.get("trans_amount2"));
		holder.transID2.setText(item.get("trans_ID2"));
		holder.transDate2.setText(item.get("trans_date2"));
		return convertView;
	}
	
	
    public void filter(String month,String year) {
        data_list.clear();
        if(month.equals("Month") && year.equals("Year")){
        	data_list.addAll(original_data_list);
        }
        else if(!month.equals("Month") && year.equals("Year")){
            for (HashMap hmap : original_data_list) {
                if (hmap.get("month").equals(month)) {
                    data_list.add(hmap);
                }
            }
        }
        else if(month.equals("Month") && !year.equals("Year")){
            for (HashMap hmap : original_data_list) {
                if (hmap.get("year").equals(year)) {
                    data_list.add(hmap);
                }
            }
        }
        else{
            for (HashMap hmap : original_data_list) {
                if (hmap.get("month").equals(month) && hmap.get("year").equals(year)) {
                    data_list.add(hmap);
                }
            }
        }    
            
        notifyDataSetChanged();
    }
    
	
}
