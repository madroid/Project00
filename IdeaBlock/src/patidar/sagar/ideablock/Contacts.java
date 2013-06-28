package patidar.sagar.ideablock;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Contacts extends Activity {

	private ListView listView ;
	private TextView text_title;
	private EditText text_search ;
	private Button button_add_contact;
	
	private LinearLayout layout_title;
	private LinearLayout layout_header;
	
	public static final String CONTACT_NAME = "name";
	public static final String CONTACT_ID = "id";
	
	public static SearchContactsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		this.text_title = (TextView) findViewById(R.id.contacts_text_title);
		text_search = (EditText) findViewById(R.id.contacts_edit_search);
		button_add_contact = (Button) findViewById(R.id.contacts_button_add);
		listView = (ListView) findViewById(R.id.contacts_list);
		
		this.layout_title = (LinearLayout) findViewById(R.id.contacts_layout_title);
		this.layout_header =  (LinearLayout) findViewById(R.id.contacts_layout_header);
		
		Constants.setEditTextFontStyle(getAssets(), this.text_search);
		Constants.setTextViewFontStyle(getAssets(), this.text_title);
		adapter = new SearchContactsAdapter(Contacts.this,R.layout.row_contacts,R.id.row_contacts_name,Login.arrlistContacts);
	  
		listView.setAdapter(adapter);
		
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
				adapter.filter(text);
				
			}
		});
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	 public void onWindowFocusChanged(boolean hasFocus){
		 super.onWindowFocusChanged(hasFocus);
		 //Setting Background for various layouts
		 Constants.setVariousBackground(this, this.layout_title, this.layout_header, this.listView);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contacts, menu);
		return true;
	}

}
