package patidar.sagar.ideablock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RequestMoney extends Activity {

	private TextView text_title ;
	private EditText edit_receiverID ;
	private EditText edit_amount ;
	private EditText edit_pin;
	private EditText edit_comment;
	private Button button_request ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_money);
		this.text_title = (TextView) findViewById(R.id.request_text_title);
		this.edit_receiverID = (EditText) findViewById(R.id.request_receiverID);
		this.edit_amount = (EditText) findViewById(R.id.request_amount);
		this.edit_pin = (EditText) findViewById(R.id.request_pin);
		this.edit_comment = (EditText) findViewById(R.id.request_comment);
		this.button_request = (Button) findViewById(R.id.request_button);
		
		Constants.setButtonFontStyle(getAssets(), this.button_request);
		Constants.setEditTextFontStyle(getAssets(), this.edit_amount,this.edit_comment,this.edit_pin,this.edit_receiverID);
		Constants.setTextViewFontStyle(getAssets(), this.text_title);
		
		Intent intent = getIntent();
		boolean check  = intent.getBooleanExtra("check", false);
		if(check){
			edit_receiverID.setText(intent.getStringExtra("id"));
			edit_amount.requestFocus();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_request_money, menu);
		return true;
	}

}
