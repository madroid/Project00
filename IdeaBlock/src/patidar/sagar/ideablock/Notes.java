package patidar.sagar.ideablock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Notes extends Activity {

	private TextView text_title;
	private ImageButton button_view ;
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
		
//		ListAdapter adapter = new SimpleAdapter(Notes.this, Login.arrlistNotes,
//				R.layout.row_notes,
//				new String[] { NOTE_SUBJECT, NOTE_MESSAGE}, new int[] {
//				R.id.notes_subject, R.id.notes_message});
		
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
