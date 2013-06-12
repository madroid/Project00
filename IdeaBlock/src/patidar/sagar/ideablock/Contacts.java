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
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Contacts extends Activity {

	private ProgressDialog pDialog;
	private ListView listView ;
	
	private EditText text_search ;
	private ImageButton button_add_contact;
	
	private String user_id_self ;
	
	private JSONObject jObj ;
	private List<NameValuePair> parameter ;
	private String jsonOutput ;
	private ArrayList<HashMap<String, String>> contacts_list = new ArrayList<HashMap<String,String>>();
	
	private static final String CONTACT_NAME = "name";
	private static final String CONTACT_ID = "id";
	private ListAdapter adapter ;
	private ArrayAdapter<ArrayList<HashMap<String, String>>> adapter2 ;
	
	private SearchContactsAdapter adapter3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		user_id_self = "9899637620";
		
		text_search = (EditText) findViewById(R.id.contacts_edit_search);
		button_add_contact = (ImageButton) findViewById(R.id.contacts_button_add);
		listView = (ListView) findViewById(R.id.contacts_list);
		
		
		parameter = new ArrayList<NameValuePair>();
		parameter.add(new BasicNameValuePair("user_id_self", user_id_self));
		
		new AsynchronousTask().execute();
		
		text_search.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String text = text_search.getText().toString();
				adapter3.filter(text);
			}
		});
		
	}
	
	class AsynchronousTask extends AsyncTask<String, String, String>{

		HttpClient httpClient ;
		HttpPost httppost;

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Contacts.this);
			pDialog.setMessage("processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			try{
				httpClient = new DefaultHttpClient();
				httppost = new HttpPost(Constants.URL_SERVER_PROJECT+"contacts_get.php");
				httppost.setEntity(new UrlEncodedFormEntity(parameter));

				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				jsonOutput= httpClient.execute(httppost, responseHandler);


			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("error occured in Asynchronous.java");
			}


			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
			  runOnUiThread(new Runnable() {				  
				  public void run() {
					  try{
						  jObj = (JSONObject) new JSONTokener(jsonOutput).nextValue();
						  int success = jObj.getInt("success");
						  String message = jObj.getString("message");
						  JSONArray arr = jObj.getJSONArray("contacts");
						  int length = arr.length();
						  for(int i=0;i<length;i++){
							  HashMap<String, String> hMap = new HashMap<String, String>();
							  JSONObject row = arr.getJSONObject(i);
							  hMap.put(CONTACT_NAME, row.getString("name"));
							  hMap.put(CONTACT_ID, row.getString("id"));
							  contacts_list.add(hMap);
						  }

						  adapter3 = new SearchContactsAdapter(Contacts.this,R.layout.row_contacts,R.id.contacts_name,contacts_list);
						  
						  listView.setAdapter(adapter3);
					  }
					  catch(Exception e){
						  e.printStackTrace();
					  }

                }});
		}



	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

}
