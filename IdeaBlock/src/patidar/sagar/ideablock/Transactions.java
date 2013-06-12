package patidar.sagar.ideablock;

import java.sql.Date;
import java.sql.Timestamp;
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
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class Transactions extends Activity {

	private Spinner month ;
	private Spinner year ;
	private Button sort ;

	private List<NameValuePair> list ;
	private JSONObject jObj ;
	ProgressDialog pDialog ;

	private String user_id;
	private String jsonOutput ;

	private ListView listView ;

	private static final String PAYMENT_ID = "trans_ID2";
	private static final String PAYMENT_AMOUNT = "trans_amount2";
	private static final String PAYMENT_DATE = "trans_date2";
	private static final String PAYMENT_TIME = "trans_time";

	private ArrayList<HashMap<String, String>> listAttributes = new ArrayList<HashMap<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactions);

		user_id = "9899637620" ;
		month = (Spinner) findViewById(R.id.tras_spinner_month);
		year = (Spinner) findViewById(R.id.trans_spinner_year);
		sort = (Button) findViewById(R.id.trans_button_sort);
		listView = (ListView) findViewById(R.id.trans_list);

		list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("user", user_id));

		new AsynchronousTask().execute();

	}

	class AsynchronousTask extends AsyncTask<String, String, String>{

		HttpClient httpClient ;
		HttpPost httppost;

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Transactions.this);
			pDialog.setMessage("processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			try{
				httpClient = new DefaultHttpClient();
				httppost = new HttpPost(Constants.URL_SERVER_PROJECT+"transaction_get.php");
				httppost.setEntity(new UrlEncodedFormEntity(list));

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
						int code = jObj.getInt("code");
						int success = jObj.getInt("success");
						JSONArray transactions = jObj.getJSONArray("trans");
						int length = transactions.length();
						for(int i=0;i<length;i++){
							JSONObject row  = transactions.getJSONObject(i);
							HashMap<String, String> hMap = new HashMap<String, String>();
							Timestamp time = new Timestamp(row.getInt("timestamp")); 
							Date date = new Date(time.getTime());
							int dt = date.getDate();
							Log.d("Date", dt+" "+row.getString("month")+", "+row.getString("year"));
							hMap.put(PAYMENT_AMOUNT, row.getString("amount"));
							hMap.put(PAYMENT_DATE, (dt+" "+row.getString("month")+", "+row.getString("year")));
							hMap.put(PAYMENT_TIME, "5:45PM");
							String id1 = row.getString("from_id");
							String id2 = row.getString("to_id");
							if(id1.equals(user_id)){
								hMap.put(PAYMENT_ID, id2);
							}
							else{
								hMap.put(PAYMENT_ID, id1);
							}
							listAttributes.add(hMap);
						}
						ListAdapter adapter = new SimpleAdapter(Transactions.this, listAttributes,
								R.layout.list_row,
								new String[] { PAYMENT_ID, PAYMENT_AMOUNT,PAYMENT_DATE,PAYMENT_TIME}, new int[] {
								R.id.trans_ID2, R.id.trans_amount2,R.id.trans_date2,R.id.trans_time});
						listView.setAdapter(adapter);
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
		getMenuInflater().inflate(R.menu.transactions, menu);
		return true;
	}

}
