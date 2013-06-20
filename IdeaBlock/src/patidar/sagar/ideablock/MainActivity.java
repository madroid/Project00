package patidar.sagar.ideablock;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {

	EditText inputName;
	EditText inputMobileNumber;
	EditText inputEmailID;
	EditText inputPassword;
	Button buttonSignUp ;
	Button buttonSingIn ;

	HttpRequest httpRequest;
	HttpResponse httpResponse;
	HttpClient httpClient ;
	HttpPost httppost ;

	JSONObject jObj;

	private static int success;
	private static String message ;

	ProgressDialog pDialog ;
	//JSONObject jsonParser = new JSONParser();

	private ConnectionDetector checkInterentConnection ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Text box inputs
		inputName = (EditText) findViewById(R.id.inputName);
		inputMobileNumber = (EditText) findViewById(R.id.inputMobileNumber);
		inputEmailID = (EditText) findViewById(R.id.inputEmailID);
		inputPassword = (EditText) findViewById(R.id.inputPassword);

		//Button
		buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
		buttonSingIn = (Button) findViewById(R.id.buttonSingIn);

		TextView title = (TextView) findViewById(R.id.main_text_title);

		// Add custom Font Style
		Constants.setButtonFontStyle(getAssets(), this.buttonSignUp,this.buttonSingIn);
		Constants.setEditTextFontStyle(getAssets(), this.inputEmailID,this.inputMobileNumber,this.inputName,this.inputPassword);
		Constants.setTextViewFontStyle(getAssets(), title);

		checkInterentConnection = new ConnectionDetector(MainActivity.this);
		buttonSingIn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(MainActivity.this,Login.class));
			}
		});

		buttonSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkInterentConnection.isConnectingToInternet()){
					new registerUser().execute();
				}
				else{
					AlertDialogManager.showAlertDialog(MainActivity.this, "Network Error!", 
							"Please connect to a working internet.", true);
				}
			}
		});

	}

	class registerUser extends AsyncTask<String, String, String>{

		String name , email, passwd, mobile ;

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			name = inputName.getText().toString();
			email = inputEmailID.getText().toString();
			passwd = inputPassword.getText().toString();
			mobile = inputMobileNumber.getText().toString();

			if(((name==null) || (name.trim().equals(""))) &&  ((email==null) || (email.trim().equals(""))) &&  
					((mobile==null) || (mobile.trim().equals(""))) &&  ((passwd==null) || (passwd.trim().equals(""))) ){
				success = 4;
			}
			else{
				List<NameValuePair> param = new ArrayList<NameValuePair>();

				param.add(new BasicNameValuePair(Constants.TABLE_USER_DETAILS_NAME, name));
				param.add(new BasicNameValuePair(Constants.TABLE_USER_DETAILS_MOBILE, mobile));
				param.add(new BasicNameValuePair(Constants.TABLE_USER_DETAILS_EMAIL, email));
				param.add(new BasicNameValuePair(Constants.TABLE_USER_DETAILS_PASSWORD, passwd));


				try{
					httpClient = new DefaultHttpClient();
					httppost = new HttpPost(Constants.URL_SERVER_PROJECT+"register.php");
					httppost.setEntity(new UrlEncodedFormEntity(param));

					ResponseHandler<String> responseHandler = new BasicResponseHandler();
					final String httpresponse = httpClient.execute(httppost, responseHandler);

					Log.d("String Result",httpresponse);
					jObj = new JSONObject(httpresponse).getJSONObject("result");
					success = jObj.getInt("success");
					message = jObj.getString("message");

				}
				catch(Exception e){
					e.printStackTrace();
					System.out.println("error occured");
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
			inputEmailID.setText(null);
			inputMobileNumber.setText(null);
			inputName.setText(null);
			inputPassword.setText(null);
			if(success==1){
				Intent intent = new Intent(MainActivity.this,Home.class);
				intent.putExtra("user_name", name);
				intent.putExtra("user_id", mobile);
				intent.putExtra("user_balance", "0");
				startActivity(intent);
			}
			else if(success==2){
				Toast.makeText(MainActivity.this, "Oops! Some error occurred. Please Try Again.", Toast.LENGTH_LONG).show();
			}
			else if(success == 3){
				AlertDialogManager.showAlertDialog(MainActivity.this, "User Already Exists!", "Please Sign-in", true);
			}
			else if(success == 4){
				Toast.makeText(MainActivity.this, "Please fill the required fields.", Toast.LENGTH_LONG).show();
			}
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}



}