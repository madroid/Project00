package patidar.sagar.ideablock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class SendMoney extends Activity {

	private TextView text_title ;
	private EditText edit_receiver ;
	private EditText edit_amount ;
	private EditText edit_pin ;
	private EditText edit_comment ;
	private Button button_send;
	private ImageView image_header ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_money);
		
		//this.text_title = (TextView) findViewById(R.id.send_text_title);
		this.edit_amount = (EditText) findViewById(R.id.send_amount);
		this.edit_comment = (EditText) findViewById(R.id.send_comment);
		this.edit_pin = (EditText) findViewById(R.id.send_pin);
		this.edit_receiver = (EditText) findViewById(R.id.send_receiverID);
		this.button_send = (Button) findViewById(R.id.send_button);
		//this.image_header = (ImageView) findViewById(R.id.image_sendMoney_header);
		
		Constants.setButtonFontStyle(getAssets(), this.button_send);
		Constants.setEditTextFontStyle(getAssets(), this.edit_amount,this.edit_comment,this.edit_pin,this.edit_receiver);
		//Constants.setTextViewFontStyle(getAssets(), this.text_title);
		
//		this.image_header.setY();
		
		Intent intent = getIntent();
		boolean check  = intent.getBooleanExtra("check", false);
		if(check){
			edit_receiver.setText(intent.getStringExtra("id"));
			edit_amount.requestFocus();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_send_money, menu);
		return true;
	}

}
