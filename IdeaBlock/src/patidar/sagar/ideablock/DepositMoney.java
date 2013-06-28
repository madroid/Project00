package patidar.sagar.ideablock;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DepositMoney extends Activity {

	private EditText edit_amount;
	private EditText edit_account ;
	private EditText edit_password;
	private EditText edit_pin;
	private EditText edit_id ;
	private ImageView image_deposite ;
	private Spinner spin ;
	
	//For bitmap backgrounds
	private LinearLayout layout_title;
	private LinearLayout layout_header;
	private LinearLayout layout_body;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposite_money);
		
		//this.text_title = (TextView) findViewById(R.id.deposite_text_title);
		this.edit_account = (EditText) findViewById(R.id.deposite_accountNo);
		this.edit_amount = (EditText) findViewById(R.id.deposite_amount);
		this.edit_password = (EditText) findViewById(R.id.deposite_password);
		this.edit_id = (EditText) findViewById(R.id.deposite_id);
		this.edit_pin = (EditText) findViewById(R.id.deposite_pin);
		this.image_deposite = (ImageView) findViewById(R.id.deposite_button);
		this.spin = (Spinner) findViewById(R.id.deposite_spinner_bank_name);
		
		//various layouts
		this.layout_body = (LinearLayout) findViewById(R.id.deposit_layout_body);
		this.layout_title = (LinearLayout) findViewById(R.id.deposit_layout_title);
		this.layout_header =  (LinearLayout) findViewById(R.id.deposit_layout_header);
		
		//Setting Custom Fonts
		Constants.setEditTextFontStyle(getAssets(), this.edit_account,this.edit_amount,this.edit_id,this.edit_password,this.edit_pin);
		
		this.image_deposite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(DepositMoney.this, "Deposite concept is not implemented yet! Inconvenience caused is deeply regretted.", Toast.LENGTH_LONG).show();
			}
		});
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	 public void onWindowFocusChanged(boolean hasFocus){
		 super.onWindowFocusChanged(hasFocus);
		 //Setting Background for various layouts
		 Constants.setVariousBackground(this, this.layout_title, this.layout_header, this.layout_body);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_deposite_money, menu);
		return true;
	}

}
