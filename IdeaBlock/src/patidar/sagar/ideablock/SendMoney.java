package patidar.sagar.ideablock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


public class SendMoney extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_money);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_send_money, menu);
		return true;
	}

}
