package patidar.sagar.ideablock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class Home extends Activity {

	ImageView sendMoney ;
	ImageView depositeMoney ;
	ImageView requestMoney;
	ImageView instantHelp ;
	ImageView transactions ;
	ImageView contacts ;
	ImageView merchants ;
	ImageView myNotes ;
	ImageView aroundMe ;
	
	private TextView text_name;
	private TextView text_id;
	private TextView text_money;
	
	private TextView text_send ;
	private TextView text_deposite;
	private TextView text_request;
	private TextView text_instant;
	private TextView text_transaction;
	private TextView text_contacts;
	private TextView text_merchants;
	private TextView text_notes;
	private TextView text_around;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		sendMoney = (ImageView) findViewById(R.id.image_sendMoney);
		depositeMoney = (ImageView) findViewById(R.id.image_depositeMoney);
		requestMoney = (ImageView) findViewById(R.id.image_requestMoney);
		instantHelp = (ImageView) findViewById(R.id.image_instantHelp);
		transactions = (ImageView) findViewById(R.id.image_transactionHistory);
		contacts = (ImageView) findViewById(R.id.image_contacts);
		merchants = (ImageView) findViewById(R.id.image_merchants);
		myNotes = (ImageView) findViewById(R.id.image_myNotes);
		aroundMe = (ImageView) findViewById(R.id.image_aroundMe);

		this.text_around = (TextView) findViewById(R.id.home_around_me);
		this.text_contacts = (TextView) findViewById(R.id.home_contacts);
		this.text_deposite = (TextView) findViewById(R.id.home_deposite_money);
		this.text_instant = (TextView) findViewById(R.id.home_instant_help);
		this.text_merchants = (TextView) findViewById(R.id.home_merchants);
		this.text_notes = (TextView) findViewById(R.id.home_my_notes);
		this.text_request = (TextView) findViewById(R.id.home_request_money);
		this.text_send = (TextView) findViewById(R.id.home_send_money);
		this.text_transaction = (TextView) findViewById(R.id.home_transaction);
		this.text_id = (TextView) findViewById(R.id.userID);
		this.text_name = (TextView) findViewById(R.id.username);
		this.text_money = (TextView) findViewById(R.id.balanceAmount);
		
		Constants.setTextViewFontStyle(getAssets(), this.text_around,this.text_contacts,this.text_deposite,this.text_instant,
				this.text_merchants,this.text_notes,this.text_request,this.text_send,this.text_transaction,this.text_id,this.text_money,
				this.text_name);
		
		Intent i = getIntent();
		text_name.setText(i.getStringExtra("user_name"));
		text_id.setText(i.getStringExtra("user_id"));
		text_money.setText("Rs. "+i.getStringExtra("user_balance"));
		
		sendMoney.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Home.this, SendMoney.class));
			}
		});

		depositeMoney.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Home.this,DepositMoney.class));
			}
		});

		requestMoney.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Home.this, RequestMoney.class));
			}
		});
		
		transactions.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this,Transactions.class);
				startActivity(intent);
				
			}
		});
		
		contacts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this,Contacts.class);
				startActivity(intent);
			}
		});
		
		myNotes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Home.this,Notes.class);
				startActivity(intent);
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

}
