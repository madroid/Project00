package patidar.sagar.ideablock;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
	
	private ImageView button_left;
	private ImageView button_right ;
	
	private static int position ;
	private boolean isLeftTriggered  = false;
	private boolean isRightTriggered  = false;

	
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
		
		this.button_left = (ImageView) findViewById(R.id.trans_details_button_left);
		this.button_right = (ImageView) findViewById(R.id.trans_details_button_right);
		
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
		
		if(Integer.parseInt(item.get(Transactions.PAYMENT_SENDER)) == 1){
			this.text_name.setText("Sender's name : "+item.get(Transactions.PAYMENT_NAME));
		}
		else{
			this.text_name.setText("Receiver's name : "+item.get(Transactions.PAYMENT_NAME));
		}
		
		button_left.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int prev = position -1 ;
				if(prev>-1){
					position-- ;
					Intent intent = new Intent(TransactionDetails.this,TransactionDetails.class);
					intent.putExtra("position", position);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
					
				}
				else if(prev<0 && !isLeftTriggered){
					Toast.makeText(TransactionDetails.this, "No more note on the left side", Toast.LENGTH_SHORT).show();
					isLeftTriggered = true ;
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
					Intent intent = new Intent(TransactionDetails.this,TransactionDetails.class);
					intent.putExtra("position", position);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				}
				else if(next>=TransactionAdapter.data_list.size() && !isRightTriggered){
					Toast.makeText(TransactionDetails.this, "No more note on the right side", Toast.LENGTH_SHORT).show();
					isRightTriggered = true ;
				}
			}
		});		

	}
	
	@Override
	public void onBackPressed() {
	    Intent intent = new Intent(TransactionDetails.this,Transactions.class);
	    if(Transactions.adapter!=null){
			Transactions.adapter.resetFilter();
		}
	    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction_details, menu);
		return true;
	}

}
