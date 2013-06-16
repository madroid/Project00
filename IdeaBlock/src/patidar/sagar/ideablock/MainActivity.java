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
	
	ProgressDialog pDialog ;
	//JSONObject jsonParser = new JSONParser();

	
	
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
				new registerUser().execute();
			}
		});
        
    }
    
    class registerUser extends AsyncTask<String, String, String>{
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
			String name = inputName.getText().toString();
			String email = inputEmailID.getText().toString();
			String passwd = inputPassword.getText().toString();
			String mobile = inputMobileNumber.getText().toString();
			
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
				jObj = new JSONObject(httpresponse);
				Log.d("CHECK OUTPUT", jObj.getJSONObject("result").getString("success"));
				//if(Integer.parseInt(jObj.getJSONObject("success").toString())==0){
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(MainActivity.this, httpresponse, 0);
							startActivity(new Intent(MainActivity.this,Home.class));
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
        }
    	
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
}