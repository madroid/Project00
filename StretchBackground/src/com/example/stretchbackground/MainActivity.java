package com.example.stretchbackground;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private RelativeLayout layout ;
	private TextView text2 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		layout = (RelativeLayout) findViewById(R.id.layout);
		text2 = (TextView) findViewById(R.id.text);
		BitmapDrawable bitmap_title = (BitmapDrawable) getResources().getDrawable(R.drawable.footer_panel);
		bitmap_title.setTileModeX(TileMode.REPEAT);
		
		
//		layout.setBackgroundDrawable(bitmap_title);
		
		
	}
	
	 public void onWindowFocusChanged(boolean hasFocus) {
	        // TODO Auto-generated method stub
	        super.onWindowFocusChanged(hasFocus);
	        Drawable d = getRepeatingBG(this, R.drawable.header_panel);
	        text2.setBackgroundDrawable(d);

	    }

	private Drawable getRepeatingBG(Activity activity, int center)
	    {   

	        DisplayMetrics dm = new DisplayMetrics();
	        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inScaled=true;

	        Bitmap center_bmp = BitmapFactory.decodeResource(activity.getResources(), center, options);
	        center_bmp.setDensity(Bitmap.DENSITY_NONE);
	        center_bmp=Bitmap.createScaledBitmap(center_bmp,  center_bmp.getWidth(), dm.heightPixels ,true);

	        BitmapDrawable center_drawable = new BitmapDrawable(activity.getResources(),center_bmp);
	//change here setTileModeY to setTileModeX if you want to repear in X
	        center_drawable.setTileModeX(Shader.TileMode.REPEAT);

	        return center_drawable;
	    }


}
