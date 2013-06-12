package patidar.sagar.ideablock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;


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


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

}
