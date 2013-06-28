package patidar.sagar.ideablock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Transactions extends Activity {

	private Spinner month ;
	private Spinner year ;
	private TextView text_title;
	private TextView text_month_year ;
	private static Context context ;
	private static ListView listView ;
	
	private LinearLayout layout_title;
	private LinearLayout layout_header;

	public static final String PAYMENT_ID = "trans_ID2";
	public static final String PAYMENT_AMOUNT = "trans_amount2";
	public static final String PAYMENT_DATE = "trans_date2";
	public static final String PAYMENT_TIME = "trans_time";
	public static final String PAYMENT_YEAR = "year";
	public static final String PAYMENT_MONTH = "month";
	public static final String PAYMENT_SENDER = "sender";
	public static final String PAYMENT_NAME = "name";
	
	public static TransactionAdapter  adapter ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactions);

		month = (Spinner) findViewById(R.id.trans_spinner_month);
		year = (Spinner) findViewById(R.id.trans_spinner_year);
		listView = (ListView) findViewById(R.id.trans_list);
		text_title = (TextView) findViewById(R.id.trans_title);
		text_month_year = (TextView) findViewById(R.id.trans_text_month_year);
		this.layout_title = (LinearLayout) findViewById(R.id.trans_layout_title);
		this.layout_header =  (LinearLayout) findViewById(R.id.trans_layout_header);

		
		context = getApplicationContext();

		Constants.setTextViewFontStyle(getAssets(), this.text_title,this.text_month_year);

		adapter = new TransactionAdapter(Transactions.this, R.layout.row_transaction, Login.arrlistTransactions);
		listView.setAdapter(adapter);

		year.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				adapter.filter(month.getSelectedItemPosition()+1, (String)year.getSelectedItem());
				text_month_year.setText(month.getSelectedItem()+" "+year.getSelectedItem());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		month.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				adapter.filter(month.getSelectedItemPosition()+1, (String)year.getSelectedItem());
				text_month_year.setText(month.getSelectedItem()+" "+year.getSelectedItem());
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		
		adapter.resetFilter();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	 public void onWindowFocusChanged(boolean hasFocus){
		 super.onWindowFocusChanged(hasFocus);
		 //Setting Background for various layouts
		 Constants.setVariousBackground(this, this.layout_title, this.layout_header, this.listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.transactions, menu);
		return true;
	}
	
}
