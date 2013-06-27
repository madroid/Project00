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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	private EditText inputUsername;
	private EditText inputPassword;
	private Button loginButton;
	private TextView forgotID ;
	private TextView forgotPass ;
	private Toast toast ;

	private static ProgressDialog pDialog;
	private static HttpClient httpClient;
	private static HttpPost httppost;
	private Intent intent ;
	public static String res ;
	private static int success;

	private JSONObject jObj ;
	public static ArrayList<HashMap<String, String>> arrlistContacts = new ArrayList<HashMap<String,String>>();
	public static ArrayList<HashMap<String, String>> arrlistTransactions = new ArrayList<HashMap<String,String>>();
	public static ArrayList<HashMap<String, String>> arrlistNotes = new ArrayList<HashMap<String,String>>();

	private ConnectionDetector checkInterentConnection ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		toast = new Toast(this);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(toast.LENGTH_LONG);
		
		inputUsername = (EditText) findViewById(R.id.loginUsername);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		loginButton = (Button) findViewById(R.id.loginbutton);
		forgotID = (TextView) findViewById(R.id.loginForgotEmail);
		forgotPass = (TextView) findViewById(R.id.loginForgotPassword);

		Constants.setTextViewFontStyle(getAssets(), this.forgotID,this.forgotPass);
		Constants.setButtonFontStyle(getAssets(), this.loginButton);
		Constants.setEditTextFontStyle(getAssets(), inputPassword,inputUsername);

		checkInterentConnection = new ConnectionDetector(this);
		
		loginButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(checkInterentConnection.isConnectingToInternet()){
					new signIn().execute();
				}
				else{
					AlertDialogManager.showAlertDialog(Login.this, "Network Error!", 
							"Please connect to a working internet.", true);
//					Toast.makeText(Login.this, "Please connect to a working Internet Connection.", Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	class signIn extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Verifying Details...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String username = "9899637620"; //inputUsername.getText().toString();
			String passwd = "1234" ;//inputPassword.getText().toString();
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("loginUsername", username));
			param.add(new BasicNameValuePair("passwd", passwd));

			if(((username == null) || (username.trim().equals(""))) && ((passwd== null) || (passwd.trim().equals("")))){
				success = 5 ;
			}
			else{
				try{
					httpClient = new DefaultHttpClient();
					httppost = new HttpPost(Constants.URL_SERVER_PROJECT+"login_get_all.php");
					httppost.setEntity(new UrlEncodedFormEntity(param));

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					final String httpResponse = httpClient.execute(httppost, responseHandler); //Constants.data ;//
					res = httpResponse;
					jObj = new JSONObject(httpResponse);
					success = jObj.getInt("success");
					String message = jObj.getString("message");
					Log.d("LOGIN RESULT", message);

					if(success==1){
						final JSONObject contacts = jObj.getJSONObject("contacts_detail");
						final JSONObject user_detail = jObj.getJSONObject("user_detail");
						final JSONObject notes_detail = jObj.getJSONObject("notes_detail");
						final JSONObject trans_detail = jObj.getJSONObject("trans_detail");

						intent = new Intent(Login.this,Home.class);
						intent.putExtra("user_name", user_detail.getString("user_name"));
						intent.putExtra("user_id", user_detail.getString("user_id"));
						intent.putExtra("user_balance", user_detail.getString("user_balance"));

						
						
						runOnUiThread(new Runnable() {
							public void run() {
								try {
									Login.arrlistContacts.clear();
									JSONArray arr = contacts.getJSONArray("contacts");
									int length = arr.length();
									if(length>0){
										for(int i=0;i<length;i++){
											HashMap<String, String> hMap = new HashMap<String, String>();
											JSONObject row = arr.getJSONObject(i);
											hMap.put(Contacts.CONTACT_NAME, row.getString("name"));
											hMap.put(Contacts.CONTACT_ID, row.getString("id"));
											arrlistContacts.add(hMap);
										}
									}

								} catch (JSONException e) {
									e.printStackTrace();
									Log.d("ERROR Login.php", "IN CONTACTS THREAD");
								}
							}
						});

						runOnUiThread(new Runnable() {
							public void run() {
								try {
									Login.arrlistTransactions.clear();
									JSONArray transactions = trans_detail.getJSONArray("trans");
									int length = transactions.length();
									if(length>0){
										for(int i=0;i<length;i++){
											JSONObject row  = transactions.getJSONObject(i);
											HashMap<String, String> hMap = new HashMap<String, String>();
//											Log.d("Date", dt+" "+row.getString("month")+", "+row.getString("year"));
											hMap.put(Transactions.PAYMENT_AMOUNT, row.getString("amount"));
											hMap.put(Transactions.PAYMENT_DATE, row.getString("date"));
											hMap.put(Transactions.PAYMENT_TIME, row.getString("time"));
											hMap.put(Transactions.PAYMENT_MONTH, row.getString("month"));
											hMap.put(Transactions.PAYMENT_YEAR, row.getString("year"));
											hMap.put(Transactions.PAYMENT_SENDER, row.getString("sender"));
											hMap.put(Transactions.PAYMENT_NAME, row.getString("name"));
											hMap.put(Transactions.PAYMENT_ID, row.getString("id"));
											
//											String id1 = row.getString("from_id");
//											String id2 = row.getString("to_id");
//											if(id1.equals(user_detail.getString("user_id"))){
//												hMap.put(Transactions.PAYMENT_ID, id2);
//											}
//											else{
//												hMap.put(Transactions.PAYMENT_ID, id1);
//											}
											arrlistTransactions.add(hMap);
										}
									}

								} catch (JSONException e) {
									
									e.printStackTrace();
									Log.d("ERROR Login.php", "IN TRANSACTION THREAD");
									
								}
							}
						});

						runOnUiThread(new Runnable() {
							public void run() {
								try {
									Login.arrlistNotes.clear();
									JSONArray notes = notes_detail.getJSONArray("notes");
									int length = notes.length();
									if(length>0){
										for(int i=0;i<length;i++){
											JSONObject row  = notes.getJSONObject(i);
											HashMap<String, String> hMap = new HashMap<String, String>();
											hMap.put(Notes.NOTE_SUBJECT, row.getString("subject"));
											hMap.put(Notes.NOTE_MESSAGE, row.getString("note"));
											hMap.put(Notes.NOTE_TIME, row.getString("time"));
											hMap.put(Notes.NOTE_DATE, row.getString("date"));
											arrlistNotes.add(hMap);
										}
									}

								} catch (JSONException e) {
									e.printStackTrace();
									Log.d("ERROR Login.php", "IN NOTES THREAD");
								}
							}
						});				

					}


				}
				catch(Exception e){
					success = 6;
					e.printStackTrace();
					Log.d("LOGIN.PHP ERROR", "SOME ERROR OCCURRED");
					
				}
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
				startActivity(intent);
			}
			else if(success==2 || success==3){
				Log.d("LOGIN UNSUCCESSFUL", "FORGOT ID ? | FORGOT PASSWORD");
				AlertDialogManager.showAlertDialog(Login.this, "Unsuccessful Login", "Forgot ID? | Forgot Password",
						true);
//				toast.setText("Unsuccessful Login! Forgot ID or Password ?");
//				toast.show();
			}
			else if(success == 4){
				AlertDialogManager.showAlertDialog(Login.this, "New User?", "Please Sign Up!", true);
//				toast.setText("New user? Please Sign up!");
//				toast.show();
			}
			else if(success == 5){
				Toast.makeText(Login.this, "Please fill up the fields!", Toast.LENGTH_LONG).show();
//				toast.setText("Please fill up the fields!");
//				toast.show();
			}
			else if(success == 6){
				Toast.makeText(Login.this, "Oops! Some error occurred in server connection. Make sure that you can access internet!", Toast.LENGTH_LONG).show();
//				toast.setText("Oops! Some error occurred in server connection. Make sure that you can access internet!");
//				toast.show();
			}
		}
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);

		return true;
	}

}
