package patidar.sagar.ideablock;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends Activity {

	private TextView text_date ;
	private TextView text_date_value ;
	private TextView text_time ;
	private TextView text_time_value;
	private TextView text_subject_name ;
	private TextView text_subject_value;
	private TextView text_content ;
	
	private ImageButton button_left ;
	private ImageButton button_right ;

	private static int position ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		text_date = (TextView) findViewById(R.id.details_text_date);
		text_date_value = (TextView) findViewById(R.id.details_text_date_value);
		text_time  = (TextView) findViewById(R.id.details_text_time);
		text_time_value  = (TextView) findViewById(R.id.details_text_time_value);
		text_subject_name  = (TextView) findViewById(R.id.details_subject_name);
		text_subject_value  = (TextView) findViewById(R.id.details_subject_value);
		text_content  = (TextView) findViewById(R.id.details_content);
		
		button_left = (ImageButton) findViewById(R.id.details_button_left);
		button_right = (ImageButton) findViewById(R.id.details_button_right);
		
		Constants.setTextViewFontStyle(getAssets(), this.text_date,this.text_content,this.text_date_value,
				this.text_subject_name,this.text_subject_value,this.text_time,this.text_time_value);
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position", -1);
		HashMap<String, String> item = Login.arrlistNotes.get(position);
		text_date_value.setText(item.get("date"));
		text_time_value.setText(item.get("time"));
		text_subject_value.setText(item.get("subject"));
		text_content.setText(item.get("message"));
//		text_subject_value.seto
		button_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int prev = position -1 ;
				if(prev>-1){
					position-- ;
					HashMap<String, String> prevItem = Login.arrlistNotes.get(prev);
					text_date_value.setText(prevItem.get("date"));
					text_time_value.setText(prevItem.get("time"));
					text_subject_value.setText(prevItem.get("subject"));
					text_content.setText(prevItem.get("message"));
				}
				else{
					Toast.makeText(Details.this, "No more note on the left side", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		button_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int next = position +1 ;
				if(next<Login.arrlistNotes.size()){
					position++;
					HashMap<String, String> nextItem = Login.arrlistNotes.get(next);
					text_date_value.setText(nextItem.get("date"));
					text_time_value.setText(nextItem.get("time"));
					text_subject_value.setText(nextItem.get("subject"));
					text_content.setText(nextItem.get("message"));
				}
				else{
					Toast.makeText(Details.this, "No more note on the right side", Toast.LENGTH_SHORT).show();
				}
			}
		});		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

}
