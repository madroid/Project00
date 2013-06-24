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
import android.widget.TextView;
import android.widget.Toast;


public class SendMoney extends Activity {

	private TextView text_title ;
	private EditText edit_receiver ;
	private EditText edit_amount ;
	private EditText edit_pin ;
	private EditText edit_comment ;
	private ImageView button_send;
	private ImageView image_header ;
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
		this.button_send = (ImageView) findViewById(R.id.send_button);
		//this.image_header = (ImageView) findViewById(R.id.image_sendMoney_header);
		
		Constants.setEditTextFontStyle(getAssets(), this.edit_amount,this.edit_comment,this.edit_pin,this.edit_receiver);
		//Constants.setTextViewFontStyle(getAssets(), this.text_title);
		
		
		Intent intent = getIntent();
		boolean check  = intent.getBooleanExtra("check", false);
		if(check){
			edit_receiver.setText(intent.getStringExtra("id"));
			edit_amount.requestFocus();
		}
		
		button_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edit_amount.getText().toString().trim().equals("") || edit_pin.getText().toString().trim().equals("") || 
						edit_receiver.getText().toString().trim().equals("")){
					toast.setText("Please fill up the required fields");
					toast.show();
				}
				else if(!cd.isConnectingToInternet()){
					toast.setText("Please connect to a working internet connection.");
					toast.show();
				}
				else{
					new proceedTransaction().execute();
				}
			}
		});
		
		
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
				param.add(new BasicNameValuePair(SEND_AMOUNT, edit_amount.getText().toString()));
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
					e.printStackTrace();
					toast.setText("Oops! Some error occurred. Transaction failed");
					toast.show();
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
