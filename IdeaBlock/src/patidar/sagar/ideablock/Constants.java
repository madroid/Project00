package patidar.sagar.ideablock;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Constants {

	public static final String TABLE_USER_DETAILS_NAME = "fullName";
	public static final String TABLE_USER_DETAILS_MOBILE = "mobile";
	public static final String TABLE_USER_DETAILS_EMAIL = "email";
	public static final String TABLE_USER_DETAILS_PASSWORD = "passwd";
	public static final String TABLE_USER_DETAILS_USERNAME = "loginUsername";
	
	public static final String MAP_API_KEY = "AIzaSyBJk0SiNINYMomeS5t2g33yCT6POne2j78";
	
	public static final String URL_SERVER_PROJECT = "http://10.0.2.2/iServer/idea/Practice/";

	public static final String CUSTOM_FONT = "fonts/Futura Medium.ttf";
	
	
	public static void setTextViewFontStyle(AssetManager asset,TextView...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(TextView v: params){
			v.setTypeface(tf);
		}
	}
	
	public static void setEditTextFontStyle(AssetManager asset,EditText...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(EditText v: params){
			v.setTypeface(tf);
		}
	}
	
	public static void setButtonFontStyle(AssetManager asset,Button...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(Button v: params){
			v.setTypeface(tf);
		}
	}
	
	public static void setSpinnerFontStyle(AssetManager asset,Spinner...params ){
		Typeface tf = Typeface.createFromAsset(asset, CUSTOM_FONT);
		for(Spinner v: params){
			
		}
	}
	

	
}
