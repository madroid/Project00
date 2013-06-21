package patidar.sagar.ideablock;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DepositMoney extends Activity {

	private TextView text_title;
	private EditText edit_amount;
	private EditText edit_account ;
	private EditText edit_password;
	private EditText edit_pin;
	private EditText edit_id ;
	private Button button_deposite ;
	private Spinner spin ;
	
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
		this.button_deposite = (Button) findViewById(R.id.deposite_button);
		this.spin = (Spinner) findViewById(R.id.deposite_spinner_bank_name);
		
		Constants.setButtonFontStyle(getAssets(), this.button_deposite);
		Constants.setEditTextFontStyle(getAssets(), this.edit_account,this.edit_amount,this.edit_id,this.edit_password,this.edit_pin);
		//Constants.setTextViewFontStyle(getAssets(), this.text_title);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_deposite_money, menu);
		return true;
	}

}
