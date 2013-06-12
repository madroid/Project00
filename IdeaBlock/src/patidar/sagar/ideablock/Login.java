package patidar.sagar.ideablock;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private EditText inputUsername;
	private EditText inputPassword;
	
	private Button loginButton; 
	
	private static ProgressDialog pDialog;
	private static HttpRequest httpRequest;
	private static HttpClient httpClient;
	private static HttpPost httppost;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		inputUsername = (EditText) findViewById(R.id.loginUsername);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		
		loginButton = (Button) findViewById(R.id.loginbutton);
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new signIn().execute();
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
    		///pDialog.setProgress(pDialog.getProgress());
    		pDialog.show();
    		
    	}
    		
		@Override
		protected String doInBackground(String... params) {
			String username = inputUsername.getText().toString();
			String passwd = inputPassword.getText().toString();
			
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair(Constants.TABLE_USER_DETAILS_EMAIL, username));
			param.add(new BasicNameValuePair(Constants.TABLE_USER_DETAILS_PASSWORD, passwd));
			
			
			try{
				httpClient = new DefaultHttpClient();
				httppost = new HttpPost(Constants.URL_SERVER_PROJECT+"login.php");
				httppost.setEntity(new UrlEncodedFormEntity(param));
				//httpResponse = httpClient.execute(httppost);
				
				
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				final String httpResponse = httpClient.execute(httppost, responseHandler);
				Log.d("String Result",httpResponse);
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(Login.this, httpResponse, 0);

					}
					});

			}
			catch(Exception e){
				System.out.println("error occured");
			}
			
			
			return null;
		}
		
	   /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
			startActivity(new Intent(Login.this,Home.class));
        }
    	

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		
		return true;
	}

}
