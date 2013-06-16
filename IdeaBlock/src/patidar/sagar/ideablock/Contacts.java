package patidar.sagar.ideablock;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Contacts extends Activity {

	private ListView listView ;

	private TextView text_title;
	private TextView text_row_name ;
	private TextView text_row_id;
	
	private EditText text_search ;
	private ImageButton button_add_contact;
	
	public static final String CONTACT_NAME = "name";
	public static final String CONTACT_ID = "id";
	
	private SearchContactsAdapter adapter3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		this.text_row_id = (TextView) findViewById(R.id.row_contacts_id);
		this.text_row_name = (TextView) findViewById(R.id.row_contacts_name);
		this.text_title = (TextView) findViewById(R.id.contacts_text_title);
		text_search = (EditText) findViewById(R.id.contacts_edit_search);
		
		button_add_contact = (ImageButton) findViewById(R.id.contacts_button_add);
		listView = (ListView) findViewById(R.id.contacts_list);
		
		Constants.setEditTextFontStyle(getAssets(), this.text_search);
		Constants.setTextViewFontStyle(getAssets(), this.text_title);
		adapter3 = new SearchContactsAdapter(Contacts.this,R.layout.row_contacts,R.id.row_contacts_name,Login.arrlistContacts);
	  
		listView.setAdapter(adapter3);
		
		text_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String text = text_search.getText().toString();
				adapter3.filter(text);
			}
		});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

}
