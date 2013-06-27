package patidar.sagar.ideablock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SendMoney extends Activity {

	private EditText edit_receiver ;
	private EditText edit_amount ;
	private EditText edit_pin ;
	private EditText edit_comment ;
	private ImageView image_send;
	private ImageView image_settings ;
	private ConnectionDetector cd ;
	private Toast toast ;
	private ProgressDialog pDialog;
	private JSONObject jObj;
	private int success ;

	private String SEND_AMOUNT = "amount";
	private String SEND_TO_ID = "to_id";
	private String SEND_PIN = "pin";
	private String SEND_COMMENT = "comment";
	private String SEND_FROM_ID = "from_id";
	
	private int amount = 0;
	
	//For bitmap backgrounds
	private LinearLayout layout_title;
	private LinearLayout layout_body;
	private LinearLayout layout_header;
	private RelativeLayout layout_footer;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_money);
		toast =  new Toast(SendMoney.this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(toast.LENGTH_LONG);
		cd = new ConnectionDetector(SendMoney.this);
		//this.text_title = (TextView) findViewById(R.id.send_text_title);
		this.edit_amount = (EditText) findViewById(R.id.send_amount);
		this.edit_comment = (EditText) findViewById(R.id.send_comment);
		this.edit_pin = (EditText) findViewById(R.id.send_pin);
		this.edit_receiver = (EditText) findViewById(R.id.send_receiverID);
		this.image_send = (ImageView) findViewById(R.id.send_button);
		this.image_settings = (ImageView) findViewById(R.id.send_settings);
		//this.image_header = (ImageView) findViewById(R.id.image_sendMoney_header);
		
		Constants.setEditTextFontStyle(getAssets(), this.edit_amount,this.edit_comment,this.edit_pin,this.edit_receiver);

		//various layouts
		this.layout_body = (LinearLayout) findViewById(R.id.send_layout_body);
		this.layout_title = (LinearLayout) findViewById(R.id.send_layout_title);
		this.layout_header =  (LinearLayout) findViewById(R.id.send_layout_header);
		this.layout_footer = (RelativeLayout) findViewById(R.id.send_layout_footer);

		
		Intent intent = getIntent();
		boolean check  = intent.getBooleanExtra("check", false);
		if(check){
			edit_receiver.setText(intent.getStringExtra("id"));
			edit_amount.requestFocus();
		}
		
		image_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edit_amount.getText().toString().trim().equals("") || edit_pin.getText().toString().trim().equals("") || 
						edit_receiver.getText().toString().trim().equals("")){
//					toast.setText("Please fill up the required fields");
//					toast.show();
					Toast.makeText(getApplicationContext(), "Please fill up the required fields", Toast.LENGTH_LONG).show();
				}
				else if(!cd.isConnectingToInternet()){
//					toast.setText("Please connect to a working internet connection.");
//					toast.show();
					Toast.makeText(getApplicationContext(), "Please connect to a working internet connection.", Toast.LENGTH_LONG).show();
				}
				else{
					amount = Integer.parseInt(edit_amount.getText().toString());
					if(amount<=Home.getBalance()){
						new proceedTransaction().execute();
					}
					else{
						AlertDialogManager.showAlertDialog(SendMoney.this, "No sufficient balance", "You don't have sufficient balance. Please recharge you account first.", true);
					}
				}
			}
		});
		
		this.image_settings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(SendMoney.this, "Settings for Send Money is not implemented yet! Inconvenience caused is deeply regretted.", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	 public void onWindowFocusChanged(boolean hasFocus){
		 super.onWindowFocusChanged(hasFocus);
		 Log.d("DEPOSIT DEBUG", "height: "+layout_title.getHeight());
		 //Setting Background for various layouts
		 this.layout_body.setBackgroundDrawable(Constants.getRepeatingBackgroundX(this, R.drawable.body_panel, layout_body.getHeight()));
		 this.layout_title.setBackgroundDrawable(Constants.getRepeatingBackgroundX(this, R.drawable.title_panel, layout_title.getHeight()));
		 this.layout_footer.setBackgroundDrawable(Constants.getRepeatingBackgroundX(this, R.drawable.footer_panel, layout_footer.getHeight()));
   		 this.layout_header.setBackgroundDrawable(Constants.getRepeatingBackgroundX(this, R.drawable.header_panel, layout_header.getHeight()));
	 }


	
	class proceedTransaction extends AsyncTask<String, String, String>{
		
		HttpPost httppost ;
		HttpClient httpClient ;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(SendMoney.this);
			pDialog.setMessage("Please wait...We are processing your request.");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			

		}

		@Override
		protected String doInBackground(String... params) {

				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair(SEND_AMOUNT, ""+amount));
				param.add(new BasicNameValuePair(SEND_COMMENT, edit_comment.getText().toString()));
				param.add(new BasicNameValuePair(SEND_FROM_ID, Home.USER_ID));
				param.add(new BasicNameValuePair(SEND_PIN, edit_pin.getText().toString()));
				param.add(new BasicNameValuePair(SEND_TO_ID, edit_receiver.getText().toString()));
					
				try{
					httpClient = new DefaultHttpClient();
					httppost = new HttpPost(Constants.URL_SERVER_PROJECT+"send_money.php");
					httppost.setEntity(new UrlEncodedFormEntity(param));

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					final String httpresponse = httpClient.execute(httppost, responseHandler);

					Log.d("String Result",httpresponse);
					jObj = new JSONObject(httpresponse);
					success = jObj.getInt("success");
					Log.d("SEND MONEY MESSAGE", jObj.getString("message"));
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(), "Oops! Some error occurred. Please make sure that you are connected to a working internet connection.", Toast.LENGTH_LONG).show();
					e.printStackTrace();
					
//					toast.setText("Oops! Some error occurred. Transaction failed");
//					toast.show();

				}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
			if(success==1){
				SendMoney.this.edit_receiver.setHint("Receiver's ID/E-mail/Ph. no.");
				SendMoney.this.edit_comment.setHint("Comments!");
				SendMoney.this.edit_pin.setHint("PIN");
				SendMoney.this.edit_amount.setHint("amount");
				
				HashMap<String, String> hmap = new HashMap<String, String>();
				try {
					 JSONObject jDetails = jObj.getJSONObject("details_transaction");
					hmap.put(Transactions.PAYMENT_NAME, jDetails.getString("name"));
					hmap.put(Transactions.PAYMENT_AMOUNT, jDetails.getString("amount"));
					hmap.put(Transactions.PAYMENT_DATE, jDetails.getString("date"));
					hmap.put(Transactions.PAYMENT_TIME, jDetails.getString("time"));
					hmap.put(Transactions.PAYMENT_MONTH, jDetails.getString("month"));
					hmap.put(Transactions.PAYMENT_YEAR, jDetails.getString("year"));
					hmap.put(Transactions.PAYMENT_SENDER, jDetails.getString("sender"));
					String id1 = jDetails.getString("from_id");
					String id2 = jDetails.getString("to_id");
					if(id1.equals(Home.USER_ID)){
						hmap.put(Transactions.PAYMENT_ID, id2);
					}
					else{
						hmap.put(Transactions.PAYMENT_ID, id1);
					}
					Login.arrlistTransactions.add(0, hmap);
					Home.setBalance(Home.getBalance()-amount);
				}
				catch (JSONException e) {
					e.printStackTrace();
					Log.v("ERROR SEND MONEY", "Some error occurred while parsing transaction details");
				}
				Intent intent = new Intent(SendMoney.this, Invoice.class);
				startActivity(intent);
			}
			else if(success==2){
			}
			else if(success == 3){
			}
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_send_money, menu);
		return true;
	}

}
