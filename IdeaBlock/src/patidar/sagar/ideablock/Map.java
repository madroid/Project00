package patidar.sagar.ideablock;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Activity{

	private TextView text_title;
	private EditText edit_search;
	private Button   button_search ;
	
	private String searchType = "food";
	private int searchRadius  = 1000;
	private static final String API_KEY = Constants.MAP_API_KEY;
	
	private Marker selfMarker;
	private Marker[] placeMarker;
	private MarkerOptions[] places ;
	private static final int MAX_PLACES = 20 ;
	private GPSTracker gps;
	private Context _context ;
	
	private GoogleMap mMap ;
	private UiSettings uiSettings ;
	
	private static int minLat = Integer.MAX_VALUE;
	private static int minLong = Integer.MAX_VALUE;
	private static int maxLat = Integer.MIN_VALUE;
	private static int maxLong = Integer.MIN_VALUE;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		_context = this ;
		this.text_title = (TextView) findViewById(R.id.map_title);
		this.edit_search = (EditText) findViewById(R.id.map_edit_search);
		this.button_search = (Button) findViewById(R.id.map_button_go);
		
		Constants.setButtonFontStyle(getAssets(), this.button_search);
		Constants.setEditTextFontStyle(getAssets(), this.edit_search);
		Constants.setTextViewFontStyle(getAssets(), this.text_title);
		
		button_search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String type = edit_search.getText().toString();
				edit_search.setText(null);
				updateSearchType(type);
			}
		});
		
		if(mMap==null){	
			mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			if(mMap!=null){ // to check in case Google Play Services is Not Avaiable
				placeMarker = new
						Marker[MAX_PLACES];
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				updatePlaces();
			}
			else{
				Log.d("MAIN_MAP_ACTIVITY.JAVA","Google play service is not available");
			}
			
		}	
		
		uiSettings = mMap.getUiSettings();
		uiSettings.setMyLocationButtonEnabled(true);
		uiSettings.setRotateGesturesEnabled(false);
		uiSettings.setTiltGesturesEnabled(false);
		
	}
	
	private void updatePlaces(){
		gps = new GPSTracker(_context);
		LatLng lastLatLng = new LatLng(gps.getLatitude(), gps.getLongitude());
		
		if(selfMarker!=null){
			selfMarker.remove();
		}
		selfMarker = mMap.addMarker(new MarkerOptions()
					.position(lastLatLng)
					.title("You are here!")
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red))
					.snippet("Your last known location"));
		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 14), 1000, null);
		
		Log.d("LOCATION", gps.getLatitude()+","+gps.getLongitude());
		
		String searchURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"+
							"location="+gps.getLatitude()+","+gps.getLongitude()+
							"&radius="+searchRadius+
							"&sensor=true"+
							"&types="+searchType+
							"&key="+API_KEY;
		
		new GetPlaces().execute(searchURL);
	}
	
	public void updateSearchType(String type){
		this.searchType = type;
		updatePlaces();
	}
	
	private class GetPlaces extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			StringBuilder placeBuilder = new StringBuilder();
			for(String placeSearchURL : params){
				HttpClient httpClient = new DefaultHttpClient();
				try{
					HttpGet httpGET = new HttpGet(placeSearchURL);
					HttpResponse httpResponse = httpClient.execute(httpGET);
					StatusLine reponseCode = httpResponse.getStatusLine();
					if(reponseCode.getStatusCode() == 200){ // 200 is code for OK
						HttpEntity httpEntity = httpResponse.getEntity();
						InputStream placedContent = httpEntity.getContent();
						InputStreamReader placesInput = new InputStreamReader(placedContent);
						BufferedReader bReader = new BufferedReader(placesInput);
						String inLine ;
						while((inLine = bReader.readLine())!=null){
							placeBuilder.append(inLine);
						}
					}
				}
				catch(Exception e){
					e.printStackTrace();
					Log.d("ERROR HTTP_RESPONSE", "ERROR HTTP_RESPONSE");
				}
			}
			return placeBuilder.toString();
		}
		
		protected void onPostExecute(String result) {
			
			if(placeMarker!=null){
				for(int i=0;i<placeMarker.length;i++){
					if(placeMarker[i]!=null){
						placeMarker[i].remove();
					}
				}
			}
			
			try{
				JSONObject jObj = new JSONObject(result);
				JSONArray jArray = jObj.getJSONArray("results");
				places = new MarkerOptions[jArray.length()];
				for(int j=0;j<places.length;j++){
					boolean missingValue = false ;
					LatLng pLatLng = null;
					String pName = "";
					String pVicinity = "";
					try{
						missingValue = false ;
						JSONObject placeObject = jArray.getJSONObject(j);
						JSONObject placeLoc = placeObject.getJSONObject("geometry").getJSONObject("location");

						minLat  = (int) Math.min( placeLoc.getDouble("lat")*1E6, minLat );
					    minLong = (int) Math.min( placeLoc.getDouble("lng")*1E6, minLong);
					    maxLat  = (int) Math.max( placeLoc.getDouble("lat")*1E6, maxLat );
					    maxLong = (int) Math.max( placeLoc.getDouble("lng")*1E6, maxLong );						
						pLatLng = new LatLng(Double.valueOf(placeLoc.getString("lat")), 
								Double.valueOf(placeLoc.getString("lng")));
						pName = placeObject.getString("name");
						pVicinity = placeObject.getString("vicinity");
					}
					catch(JSONException e){
						Log.d("MISSING VALUES", "Values are missing");
						missingValue = true;
						Log.d("ERROR IN MAIN MAP CLASS", "Error occurred in parsing JSON + 1");
						e.printStackTrace();
					}
					if(missingValue){
						places[j] = null; // if value is missing then no details will be displayed
					}
					else{
						places[j] = new MarkerOptions()
									.position(pLatLng)
									.title(pName)
									.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_blue))
									.snippet(pVicinity);
					}
				}
				
			}
			catch(Exception e){
				Log.d("ERROR IN MAIN MAP CLASS", "Error occurred in parsing JSON + 2");
				e.printStackTrace();
			}
			if(places!=null && placeMarker !=null){
				for(int k=0;k<places.length && k<placeMarker.length;k++){
					if(places[k]!=null){
						placeMarker[k] = mMap.addMarker(places[k]);
					}
				}
			}
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(mMap!=null){
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(mMap!=null){
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	

}
