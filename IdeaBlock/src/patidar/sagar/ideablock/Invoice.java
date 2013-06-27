package patidar.sagar.ideablock;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Invoice extends Activity {

	private TextView text_date ;
	private TextView text_date_value ;
	private TextView text_time ;
	private TextView text_time_value;
	private TextView text_name;
	private TextView text_id;
	private TextView text_invoice_title;
	private TextView text_net_amount;
	private TextView text_more_details;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice);
		
		this.text_date = (TextView) findViewById(R.id.invoice_text_date);
		this.text_date_value = (TextView) findViewById(R.id.invoice_text_date_value);
		this.text_id = (TextView) findViewById(R.id.invoice_text_id);
		this.text_invoice_title = (TextView) findViewById(R.id.invoice_text_invoice);
		this.text_more_details = (TextView) findViewById(R.id.invoice_text_more_details);
		this.text_name = (TextView) findViewById(R.id.invoice_text_name);
		this.text_net_amount = (TextView) findViewById(R.id.invoice_text_amount);
		this.text_time = (TextView) findViewById(R.id.invoice_text_time);
		this.text_time_value = (TextView) findViewById(R.id.invoice_text_time_value);
		
		Constants.setTextViewFontStyle(getAssets(), this.text_date,this.text_date_value,this.text_id,
				this.text_invoice_title,this.text_more_details,this.text_name,this.text_name,this.text_net_amount,
				this.text_time,this.text_time_value);

		
		HashMap<String, String> item = Login.arrlistTransactions.get(0);
		
		this.text_date_value.setText(item.get(Transactions.PAYMENT_DATE));
		this.text_id.setText("ID : "+item.get(Transactions.PAYMENT_ID));
		this.text_time_value.setText(item.get(Transactions.PAYMENT_TIME));
		this.text_net_amount.setText("Net Amount : "+item.get(Transactions.PAYMENT_AMOUNT));
		
		if(Integer.parseInt(item.get(Transactions.PAYMENT_SENDER)) == 0){
			this.text_name.setText("Sender's name : "+item.get(Transactions.PAYMENT_NAME));
		}
		else{
			this.text_name.setText("Receiver's name : "+item.get(Transactions.PAYMENT_NAME));
		}


	}
	
	@Override
	public void onBackPressed() {
	    Intent intent = new Intent(Invoice.this,Home.class);
	    if(Transactions.adapter!=null){
			Transactions.adapter.resetFilter();
		}
	    startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.invoice, menu);
		return true;
	}

}
