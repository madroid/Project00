package patidar.sagar.ideablock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RequestMoney extends Activity {

	private EditText edit_receiverID ;
	private EditText edit_amount ;
	private EditText edit_pin;
	private EditText edit_comment;
	private ImageView image_request ; 
	
	//For bitmap backgrounds
	private LinearLayout layout_title;
	private LinearLayout layout_body;
	private LinearLayout layout_header;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_money);

		this.edit_receiverID = (EditText) findViewById(R.id.request_receiverID);
		this.edit_amount = (EditText) findViewById(R.id.request_amount);
		this.edit_pin = (EditText) findViewById(R.id.request_pin);
		this.edit_comment = (EditText) findViewById(R.id.request_comment);
		this.image_request = (ImageView) findViewById(R.id.request_button);
		
		Constants.setEditTextFontStyle(getAssets(), this.edit_amount,this.edit_comment,this.edit_pin,this.edit_receiverID);

		//various layouts
		this.layout_body = (LinearLayout) findViewById(R.id.request_layout_body);
		this.layout_title = (LinearLayout) findViewById(R.id.request_layout_title);
		this.layout_header =  (LinearLayout) findViewById(R.id.request_layout_header);
		
		// Whether to focus first textbox or second
		Intent intent = getIntent();
		boolean check  = intent.getBooleanExtra("check", false);
		if(check){
			edit_receiverID.setText(intent.getStringExtra("id"));
			edit_amount.requestFocus();
		}
		
		this.image_request.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(RequestMoney.this, "Request concept is not implemented yet! Inconvenience caused is deeply regretted.", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	 public void onWindowFocusChanged(boolean hasFocus){
		 super.onWindowFocusChanged(hasFocus);
		 //Setting Background for various layouts
		 Constants.setVariousBackground(this, layout_title, layout_header, layout_body);
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_request_money, menu);
		return true;
	}

}
