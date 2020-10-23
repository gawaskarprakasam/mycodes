package com.example.accelerometerdemo;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity 
{
	Button btnSignIn,btnSignUp,btnDialog;
	
	 public static  String userName = " ";
	 EditText editTextUserName;
	
	    SharedPreferences pref;
	     
	 
	    Editor editor;
	     
	   
	    Context _context;
	     
	    
	    int PRIVATE_MODE = 0;
	
	 
	   
	    public void createLoginSession(String name){
	     
	        editor.putString(userName, name);
	         
	      
	        editor.commit();
	    }   

	    SessionManagement session;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.login);
	 
	        session = new SessionManagement(getApplicationContext());   
	       editTextUserName=(EditText)findViewById(R.id.editText1);
	        
	     btnSignUp=(Button)findViewById(R.id.button1);
			
	    // Set OnClick Listener on SignUp button 
	    btnSignUp.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		    
		    System.out.println("*******usernameVASA*********"+editTextUserName);
		    userName=editTextUserName.getText().toString();
		    System.out.println("*******usernameVASA*********"+userName);
		    session.createLoginSession(userName);
			//user.setUsername(userName);
			Intent intent = new Intent( getApplicationContext(), MainActivity.class );
			startActivity(intent);
			
			}
		});
	}
	// Methos to handleClick Event of Sign In Button
	
			
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		
	}
	
	/**
	 * Persist account details to preference manager
	 * @param accountName
	 */
	
}
