package patidar.sagar.ideablock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Notes extends Activity {

	private TextView text_title;
	private TextView text_create ;
	private EditText edit_create ;
	
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
		edit_create = (EditText) findViewById(R.id.notes_edit_new);
		listView = (ListView) findViewById(R.id.notes_list);
		text_title = (TextView) findViewById(R.id.note_title);
		text_create = (TextView) findViewById(R.id.note_create);

		Constants.setTextViewFontStyle(getAssets(), this.text_create,this.text_title);
		Constants.setEditTextFontStyle(getAssets(), this.edit_create);
		
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
