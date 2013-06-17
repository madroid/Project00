package patidar.sagar.ideablock;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Map extends Activity {

	private TextView text_title ;
	private EditText edit_search;
	private Button button_search ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		text_title = (TextView) findViewById(R.id.map_title);
		edit_search = (EditText) findViewById(R.id.map_search);
		button_search = (Button) findViewById(R.id.map_button_search);
		
		Constants.setButtonFontStyle(getAssets(), button_search);
		Constants.setTextViewFontStyle(getAssets(), text_title);
		Constants.setEditTextFontStyle(getAssets(), edit_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}
