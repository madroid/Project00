package patidar.sagar.ideablock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Transactions extends Activity {

	private Spinner month ;
	private Spinner year ;
	private TextView text_title;
	private TextView text_year;
	private TextView text_month ;

	private ListView listView ;

	public static final String PAYMENT_ID = "trans_ID2";
	public static final String PAYMENT_AMOUNT = "trans_amount2";
	public static final String PAYMENT_DATE = "trans_date2";
	public static final String PAYMENT_TIME = "trans_time";
	public static final String PAYMENT_YEAR = "year";
	public static final String PAYMENT_MONTH = "month";
	
	private static TransactionAdapter  adapter ;
	
	private static boolean yearBool = false, monthBool = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactions);

		month = (Spinner) findViewById(R.id.trans_spinner_month);
		year = (Spinner) findViewById(R.id.trans_spinner_year);
		listView = (ListView) findViewById(R.id.trans_list);
		text_title = (TextView) findViewById(R.id.trans_title);
		text_month = (TextView) findViewById(R.id.trans_text_month);
		text_year = (TextView) findViewById(R.id.trans_text_year);
		
		Constants.setTextViewFontStyle(getAssets(), this.text_title,this.text_month,this.text_year);
	
		adapter = new TransactionAdapter(Transactions.this, R.layout.row_transaction, Login.arrlistTransactions);
		listView.setAdapter(adapter);
		
		year.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
//				Toast.makeText(Transactions.this, (String)year.getSelectedItem(), Toast.LENGTH_LONG).show();
				if(yearBool){
					adapter.filter((String)month.getSelectedItem(), (String)year.getSelectedItem());
					
				}
				text_year.setText((String)year.getSelectedItem());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		month.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(monthBool){
					adapter.filter((String)month.getSelectedItem(), (String)year.getSelectedItem());
				}
				text_month.setText((String)month.getSelectedItem());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
			
		});

		monthBool = true;
		yearBool = true ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transactions, menu);
		return true;
	}

}
