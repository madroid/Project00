package patidar.sagar.ideablock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Constants {

	public static final String TABLE_USER_DETAILS_NAME = "fullName";
	public static final String TABLE_USER_DETAILS_MOBILE = "mobile";
	public static final String TABLE_USER_DETAILS_EMAIL = "email";
	public static final String TABLE_USER_DETAILS_PASSWORD = "passwd";
	public static final String TABLE_USER_DETAILS_USERNAME = "loginUsername";
	
	public static final String MAP_API_KEY = "AIzaSyBJk0SiNINYMomeS5t2g33yCT6POne2j78";
	
	public static final String URL_SERVER_PROJECT = "http://yosagar.5gbfree.com/iServer/idea/Practice/";

	public static final String CUSTOM_FONT = "fonts/Futura Medium.ttf";
	public static final int CUSTOM_FONT_SIZE = 15 ;
	
	public static final String data = "{\"user_detail\":{\"user_name\":\"Sagar Patidar\",\"user_id\":\"9899637620\",\"user_balance\":\"1090000\"},\"contacts_detail\":{\"success\":1,\"message\":\"Succesfully got Contacts details\",\"contacts\":[{\"id\":\"9873011322\",\"name\":\"Abdul Khalid\"},{\"id\":\"7838373895\",\"name\":\"Misbah Ashraf\"},{\"id\":\"8462959756\",\"name\":\"Mummy\"},{\"id\":\"9414194038\",\"name\":\"Nana\"},{\"id\":\"9926408112\",\"name\":\"Papa\"},{\"id\":\"9873803932\",\"name\":\"Rhythm Gupta\"},{\"id\":\"9555157134\",\"name\":\"Rishav Sinha\"},{\"id\":\"9871457685\",\"name\":\"Sachin Goel\"},{\"id\":\"9926092501\",\"name\":\"Uncle\"},{\"id\":\"7838822816\",\"name\":\"Utkarsh Ankit Prataap Singh\"}]},\"notes_detail\":{\"success\":1,\"message\":\"Succesfully got Notes details\",\"notes\":[{\"serial\":\"5\",\"user_id\":\"9899637620\",\"subject\":\"Important\",\"note\":\"Dekhta kya h be...kuch padne lyk thode hi likha h isme \",\"date\":\"20th June,2013\",\"time\":\"12:46 AM\"},{\"serial\":\"4\",\"user_id\":\"9899637620\",\"subject\":\"Zorroh\",\"note\":\"Wanna be my chammak chalo o.o.o :P\",\"date\":\"20th June,2013\",\"time\":\"12:46 AM\"},{\"serial\":\"3\",\"user_id\":\"9899637620\",\"subject\":\"Wait 5 min\",\"note\":\"Hey I am coming...just wait 5 mins :P\",\"date\":\"20th June,2013\",\"time\":\"12:45 AM\"},{\"serial\":\"2\",\"user_id\":\"9899637620\",\"subject\":\"Blast a Bomb\",\"note\":\"blast a grenade in Satpura Hostel\",\"date\":\"18th June,2013\",\"time\":\"04:03 PM\"},{\"serial\":\"1\",\"user_id\":\"9899637620\",\"subject\":\"Kill that bastard!\",\"note\":\"His name is sachin goel (2011cs10249)...He is bloody fool but damn he is my rommie\",\"date\":\"18th June,2013\",\"time\":\"04:01 PM\"}]},\"trans_detail\":{\"success\":1,\"message\":\"Succesfully got transaction details\",\"trans\":[{\"serial\":\"1\",\"from_id\":\"9899637620\",\"to_id\":\"9926408112\",\"amount\":\"100\",\"timestamp\":\"1371552095\",\"month\":\"03\",\"year\":\"2013\",\"date\":\"18th March,2013\",\"time\":\"04:11 PM\"},{\"serial\":\"2\",\"from_id\":\"9926408112\",\"to_id\":\"9899637620\",\"amount\":\"1000\",\"timestamp\":\"1371552134\",\"month\":\"03\",\"year\":\"2013\",\"date\":\"18th March,2013\",\"time\":\"04:12 PM\"},{\"serial\":\"3\",\"from_id\":\"9899637620\",\"to_id\":\"9926408112\",\"amount\":\"10000000\",\"timestamp\":\"1371688205\",\"month\":\"06\",\"year\":\"2013\",\"date\":\"20th June,2013\",\"time\":\"06:00 AM\"},{\"serial\":\"4\",\"from_id\":\"9899637620\",\"to_id\":\"9926408112\",\"amount\":\"5000\",\"timestamp\":\"1371688216\",\"month\":\"06\",\"year\":\"2013\",\"date\":\"21st June,2013\",\"time\":\"06:00 AM\"},{\"serial\":\"5\",\"from_id\":\"9926408112\",\"to_id\":\"9899637620\",\"amount\":\"10000\",\"timestamp\":\"1371688229\",\"month\":\"06\",\"year\":\"2013\",\"date\":\"25th June,2013\",\"time\":\"06:00 AM\"},{\"serial\":\"6\",\"from_id\":\"9926408112\",\"to_id\":\"9899637620\",\"amount\":\"50\",\"timestamp\":\"1371688276\",\"month\":\"07\",\"year\":\"2014\",\"date\":\"21st August,2014\",\"time\":\"06:01 AM\"},{\"serial\":\"7\",\"from_id\":\"9899637620\",\"to_id\":\"9926408112\",\"amount\":\"1500\",\"timestamp\":\"1371806691\",\"month\":\"12\",\"year\":\"2014\",\"date\":\"16th December,2014\",\"time\":\"02:54 PM\"}]},\"success\":1,\"message\":\"Login Successfull\"}";
	
	public static void setTextViewFontStyle(AssetManager asset,TextView...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(TextView v: params){
			v.setTypeface(tf);
			v.setTextSize(CUSTOM_FONT_SIZE);
		}
	}
	
	public static void setHomeTextViewFontStyle(AssetManager asset,TextView...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(TextView v: params){
			v.setTypeface(tf);
			//v.setTextSize(CUSTOM_FONT_SIZE);
		}
	}
	
	public static void setEditTextFontStyle(AssetManager asset,EditText...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(EditText v: params){
			v.setTypeface(tf);
			v.setTextSize(CUSTOM_FONT_SIZE);
		}
	}
	
	public static void setButtonFontStyle(AssetManager asset,Button...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(Button v: params){
			v.setTypeface(tf);
			v.setTextSize(CUSTOM_FONT_SIZE);
		}
	}
	
	/**
	 * Common Utilities for Push Notifications
	 */
	
	public static String SENDER_ID = "358194037121";

	public static final String DISPLAY_MESSAGE_ACTION =
			"patidar.sagar.ideablock.DISPLAY_MESSAGE";

	public static final String EXTRA_MESSAGE = "message";

	 static void displayMessage(Context context, String message) {
	        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
	        intent.putExtra(EXTRA_MESSAGE, message);
	        context.sendBroadcast(intent);
	    }

	
	 
	 /**
	  * Repeating background image in x direction and stretching in y-direction
	  */

	public static Drawable getRepeatingBackgroundX(Activity activity, int drawableImage, int height){

	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inScaled=true;

	        Bitmap center_bmp = BitmapFactory.decodeResource(activity.getResources(), drawableImage, options);
	        center_bmp.setDensity(Bitmap.DENSITY_NONE);
	        center_bmp=Bitmap.createScaledBitmap(center_bmp,  center_bmp.getWidth(), height ,true);

	        BitmapDrawable center_drawable = new BitmapDrawable(activity.getResources(),center_bmp);
	        center_drawable.setTileModeX(Shader.TileMode.REPEAT);

	        return center_drawable;
    }
	
	public static void setVariousBackground(Activity context, View titleLayout, View headerLayout, View bodyLayout){
		 titleLayout.setBackgroundDrawable(Constants.getRepeatingBackgroundX(context, R.drawable.title_panel, titleLayout.getHeight()));
		 headerLayout.setBackgroundDrawable(Constants.getRepeatingBackgroundX(context, R.drawable.header_panel, headerLayout.getHeight()));
		 bodyLayout.setBackgroundDrawable(Constants.getRepeatingBackgroundX(context, R.drawable.body_panel, bodyLayout.getHeight()));
	}

	
}
