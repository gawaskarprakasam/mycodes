package com.example.accelerometerdemo;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManagement {
	
	SharedPreferences pref;
    
    // Editor for Shared preferences
    Editor editor;
     
    // Context
    Context _context;
     
    // Shared pref mode
    int PRIVATE_MODE = 0;
     
   
     
    // User name (make variable public to access from outside)
    public static final String userName = "name";
     
    
    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(userName, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void createLoginSession(String name ){
        // Storing login value as TRUE
     
         
        // Storing name in pref
        editor.putString(userName, name);
         
   
         
        // commit changes
        editor.commit();
    }   
    
    

	/**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(userName, pref.getString(userName, null));
         
             
        // return user
        return user;
    }
    

}
