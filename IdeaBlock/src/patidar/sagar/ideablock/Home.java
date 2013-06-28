package patidar.sagar.ideablock;

import static patidar.sagar.ideablock.Constants.EXTRA_MESSAGE;
import static patidar.sagar.ideablock.Constants.DISPLAY_MESSAGE_ACTION;
import static patidar.sagar.ideablock.Constants.SENDER_ID;
import com.google.android.gcm.GCMRegistrar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


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
	ImageView profile ;
	
	//For bitmap backgrounds
	private LinearLayout layout_title;
	private LinearLayout layout_body;
	private LinearLayout layout_header;

	public static TextView text_name;
	private static TextView text_id;
	public static TextView text_money;
	
	public static String USER_ID  = "";
	private static int USER_BALANCE = 0;
	
	private AsyncTask<Void, Void, Void> serverTask ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GCMRegistrar.checkManifest(this);
		GCMRegistrar.checkDevice(this);

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
		profile = (ImageView) findViewById(R.id.image_profilePic);
		
		this.text_id = (TextView) findViewById(R.id.userID);
		this.text_name = (TextView) findViewById(R.id.username);
		this.text_money = (TextView) findViewById(R.id.balanceAmount);

		//various layouts
		this.layout_body = (LinearLayout) findViewById(R.id.home_layout_body);
		this.layout_title = (LinearLayout) findViewById(R.id.home_layout_title);
		this.layout_header =  (LinearLayout) findViewById(R.id.home_layout_header);

		Constants.setTextViewFontStyle(getAssets(), this.text_id,this.text_money,this.text_name);

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
			regId  = GCMRegistrar.getRegistrationId(this);
		}
		final String reg_id = regId ;
		Log.d("HOME.JAVA REG ID", reg_id);
			serverTask = new AsyncTask<Void, Void, Void>(){
				ServerUtilities server = null;
				@Override
				protected Void doInBackground(Void... params) {
					server = new ServerUtilities();
					server.register(Home.this, USER_ID, reg_id);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					serverTask = null;
					server = null ;
				}
			};
			serverTask.execute();

		Intent i = getIntent();
		text_name.setText(i.getStringExtra("user_name"));
		text_id.setText(i.getStringExtra("user_id"));
		
		USER_ID = i.getStringExtra("user_id");
		USER_BALANCE = Integer.parseInt(i.getStringExtra("user_balance"));
		text_money.setText("Rs. "+USER_BALANCE);
		
		profile.setX(sendMoney.getX());
		
		sendMoney.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Home.this,SendMoney.class));
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
				if(Transactions.adapter!=null){
					Transactions.adapter.resetFilter();
				}
				startActivity(intent);
				overridePendingTransition(R.anim.fadein, R.anim.fadeout);
			}
		});

		contacts.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this,Contacts.class);
				if(Contacts.adapter!=null){
					Contacts.adapter.resetFilter();
				}
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

		aroundMe.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Home.this,Map.class);
				startActivity(intent);
			}
		});
		
		instantHelp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Home.this, "Not implemented yet!", Toast.LENGTH_LONG).show();
			}
		});
		
		merchants.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(Home.this, "Not implemented yet!", Toast.LENGTH_LONG).show();
			}
		});
		
		
		

	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			Log.d("NEW MESSAGE", newMessage);
			Toast.makeText(Home.this, "Receiver", Toast.LENGTH_LONG).show();
		}
	};


	@Override
	protected void onDestroy() {
		if (serverTask != null) {
			serverTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	@Override
	 public void onWindowFocusChanged(boolean hasFocus){
		 super.onWindowFocusChanged(hasFocus);
		 //Setting Background for various layouts
		 Constants.setVariousBackground(this, this.layout_title, this.layout_header, this.layout_body);
	}

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	public static void setBalance(int bal){
		USER_BALANCE = bal ;
		text_money.setText("Rs. "+USER_BALANCE);
	}
	
	public static int getBalance(){
		return USER_BALANCE ;
	}

}
