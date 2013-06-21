package patidar.sagar.ideablock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Notes extends Activity {

	private TextView text_title;
	private Button button_add;
	//private TextView text_create ;
//	private EditText edit_create ;
	
	private ListView listView ;
	public static final String NOTE_SUBJECT = "subject";
	public static final String NOTE_MESSAGE = "message";
	public static final String NOTE_DATE = "date";
	public static final String NOTE_TIME = "time";
	
	private NotesAdapter adapter ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		
		listView = (ListView) findViewById(R.id.notes_list);
		text_title = (TextView) findViewById(R.id.note_title);
		button_add = (Button) findViewById(R.id.notes_button_add);
//		text_create = (TextView) findViewById(R.id.note_create);
//		edit_create = (EditText) findViewById(R.id.notes_edit_new);

		Constants.setTextViewFontStyle(getAssets(),this.text_title);
		Constants.setButtonFontStyle(getAssets(), button_add);
//		Constants.setEditTextFontStyle(getAssets(), this.edit_create);
		
		text_title.requestFocus();
		adapter = new NotesAdapter(this, R.layout.row_notes, R.id.notes_subject, Login.arrlistNotes);
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

}
