/**
 * 
 */
package patidar.sagar.ideablock;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import com.google.android.gcm.GCMRegistrar;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author Sagar Patidar
 *
 */
public class ServerUtilities {

	private ConnectionDetector cd ;

	public void register(Context context, String user_id, String reg_id){

		List<NameValuePair> parameter = new ArrayList<NameValuePair>();
		parameter.add(new BasicNameValuePair("user_id", user_id));
		parameter.add(new BasicNameValuePair("reg_id", reg_id));
		post(context, parameter, "GCM_register.php");

	}
	
	public void unregister(Context context, String user_id, String reg_id){
		
		List<NameValuePair> parameter = new ArrayList<NameValuePair>();
		parameter.add(new BasicNameValuePair("user_id", user_id));
		parameter.add(new BasicNameValuePair("reg_id", reg_id));
		post(context, parameter, "GCM_unregister.php");

	}	
	
	
	public void post(final Context context, final List<NameValuePair> paramList, final String fileName){
		cd = new ConnectionDetector(context);
		
		if(cd.isConnectingToInternet()){
			AsyncTask<Void, Void, Void> mPost = new AsyncTask<Void, Void, Void>(){
				HttpClient httpClient;
				HttpPost httppost ;
				@Override
				protected Void doInBackground(Void... params) {
					// TODO Auto-generated method stub
					try{
						httpClient = new DefaultHttpClient();
						httppost = new HttpPost(Constants.URL_SERVER_PROJECT+fileName);
						httppost.setEntity(new UrlEncodedFormEntity(paramList));
						
						ResponseHandler<String> responseHandler = new BasicResponseHandler();
						String jsonOutput = httpClient.execute(httppost, responseHandler);
						JSONObject jObj = new JSONObject(jsonOutput);
						if(fileName.equals("GCM_register.php")){
							if(jObj.getInt("success")==1){
								GCMRegistrar.setRegisteredOnServer(context, true);
								Log.d("GCM_REGISTER.PHP", jsonOutput);
							}
						}
						else{
							if(jObj.getInt("success")==1){
								GCMRegistrar.setRegisteredOnServer(context, false);
								Log.d("GCM_UNREGISTER.PHP", jsonOutput);
							}
						}
						Log.d(fileName+" POST MESSAGE", jObj.getString("message"));
					}
					catch(Exception e){
						e.printStackTrace();
					}
	
					return null;
				}
				
			};
			mPost.execute();
		}
		else{
			Toast toast = new Toast(context);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(toast.LENGTH_LONG);
			toast.setText("Please connect to a working internet connection");
			toast.show();
		}
	}
	
}
