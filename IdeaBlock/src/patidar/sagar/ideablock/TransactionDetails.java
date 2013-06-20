package patidar.sagar.ideablock;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class TransactionDetails extends Activity {

	private TextView text_date ;
	private TextView text_date_value ;
	private TextView text_time ;
	private TextView text_time_value;
	private TextView text_name;
	private TextView text_id;
	private TextView text_invoice_title;
	private TextView text_net_amount;
	private TextView text_more_details;
	
	private ImageButton button_left;
	private ImageButton button_right ;
	
	private static int position ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_details);
		
		this.text_date = (TextView) findViewById(R.id.trans_details_text_date);
		this.text_date_value = (TextView) findViewById(R.id.trans_details_text_date_value);
		this.text_id = (TextView) findViewById(R.id.trans_details_text_id);
		this.text_invoice_title = (TextView) findViewById(R.id.trans_details_text_invoice);
		this.text_more_details = (TextView) findViewById(R.id.trans_details_text_more_details);
		this.text_name = (TextView) findViewById(R.id.trans_details_text_name);
		this.text_net_amount = (TextView) findViewById(R.id.trans_details_text_amount);
		this.text_time = (TextView) findViewById(R.id.trans_details_text_time);
		this.text_time_value = (TextView) findViewById(R.id.trans_details_text_time_value);
		
		this.button_left = (ImageButton) findViewById(R.id.trans_details_button_left);
		this.button_right = (ImageButton) findViewById(R.id.trans_details_button_right);
		
		Constants.setTextViewFontStyle(getAssets(), this.text_date,this.text_date_value,this.text_id,
				this.text_invoice_title,this.text_more_details,this.text_name,this.text_name,this.text_net_amount,
				this.text_time,this.text_time_value);
		
		Intent intent = getIntent();
		position = intent.getIntExtra("position", -1);
		
		HashMap<String, String> item = TransactionAdapter.data_list.get(position);
		
		this.text_date_value.setText(item.get(Transactions.PAYMENT_DATE));
		this.text_id.setText("ID : "+item.get(Transactions.PAYMENT_ID));
		this.text_time_value.setText(item.get(Transactions.PAYMENT_TIME));
		this.text_net_amount.setText("Net Amount : "+item.get(Transactions.PAYMENT_AMOUNT));
		this.text_name.setText("Name : (Sender's/Receiver's name)");
		
		button_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int prev = position -1 ;
				if(prev>-1){
					position-- ;
					HashMap<String, String> prevItem = TransactionAdapter.data_list.get(prev);
					text_date_value.setText(prevItem.get(Transactions.PAYMENT_DATE));
					text_id.setText("ID : "+prevItem.get(Transactions.PAYMENT_ID));
					text_time_value.setText(prevItem.get(Transactions.PAYMENT_TIME));
					text_net_amount.setText("Net Amount : "+prevItem.get(Transactions.PAYMENT_AMOUNT));
					text_name.setText("Name : (Sender's/Receiver's name)");
				}
				else{
					Toast.makeText(TransactionDetails.this, "No more note on the left side", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		button_right.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int next = position +1 ;
				if(next<TransactionAdapter.data_list.size()){
					position++;
					HashMap<String, String> nextItem = TransactionAdapter.data_list.get(next);
					text_date_value.setText(nextItem.get(Transactions.PAYMENT_DATE));
					text_id.setText("ID : "+nextItem.get(Transactions.PAYMENT_ID));
					text_time_value.setText(nextItem.get(Transactions.PAYMENT_TIME));
					text_net_amount.setText("Net Amount : "+nextItem.get(Transactions.PAYMENT_AMOUNT));
					text_name.setText("Name : (Sender's/Receiver's name)");				}
				else{
					Toast.makeText(TransactionDetails.this, "No more note on the right side", Toast.LENGTH_SHORT).show();
				}
			}
		});		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_details, menu);
		return true;
	}

}
