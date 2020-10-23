package com.example.accelerometerdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.inputmethodservice.Keyboard.Row;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import  org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@SuppressLint("NewApi")
public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	TextView title,tv,tv1,tv2;
	RelativeLayout layout;
	String str1,str2,str3;
	Button b1,b2,b3;
	ArrayList al=new ArrayList();
	static HSSFWorkbook hwb;
	static HSSFSheet sheet;
	static HSSFRow rowhead;
	static Cell cell;
	static FileOutputStream fileOut;
	SessionManagement session;
	
	String name="";
	//static String filename="MyExcel.xls" ;
	static int count=0;
	static boolean isAvailable=false;
	TextView passshow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		session = new SessionManagement(getApplicationContext());
		mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		layout=(RelativeLayout)findViewById(R.id.relative);
		//title=(TextView)findViewById(R.id.name);
		passshow=(TextView)findViewById(R.id.textView1);
		tv=(TextView)findViewById(R.id.xval);
		
		tv1=(TextView)findViewById(R.id.yval);
		tv2=(TextView)findViewById(R.id.zval);
		
		  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder() 
			
			.permitAll().build();
			
			StrictMode.setThreadPolicy(policy);
		//b1=(Button)findViewById(R.id.btn);
		
		b2=(Button)findViewById(R.id.button1);
		
		b3=(Button)findViewById(R.id.button2);
		
		b2.setOnClickListener( new OnClickListener() {
				
		/*public void onClick(View view)
		{
			ArrayList al=new ArrayList();
			
			str1=tv.getText().toString();
			str2=tv1.getText().toString();
			str3=tv2.getText().toString();
			
			al.add(str1);
			al.add(str2);
			al.add(str3);
			
			Toast.makeText(MainActivity.this,str1+"\n"+str2+"\n"+str3,Toast.LENGTH_LONG).show();
		
		
		
			 
		     }  
			  
		
			
				
		});	
		*/
		 
				
				public void onClick(View view)
				{
				
					onResume();
					
				     }  
					  
					
						
				});	
         
		
		 b3.setOnClickListener( new OnClickListener() {
			
			public void onClick(View view)
			{
			
				onPause();
				try
				{
					Thread.sleep(1000);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				  
				try {
					readExcel();
				} catch (IOException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				  
			}	
					
			});	
	
	}
	
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	
	public void onStopClick(View view) {
	    mSensorManager.unregisterListener(this);
	   

	}
	
	public void saveExcelFile(ArrayList al)
	{
		
		HashMap<String, String> user = session.getUserDetails();
        // name
        name = user.get(SessionManagement.userName);
       System.out.println("user=============="+name); 
		
		int count=0;
		 File file = new File(Environment.getExternalStorageDirectory()
                 + "/Android/data/"
                 +name+".xls");

			try
			{
			//	String filename="MyExcel.xls" ;
				
				
				String list[]={"x-axis","y-axis","z-axis"};
				

				hwb=new HSSFWorkbook();
				sheet =  hwb.createSheet("newsheet");
				rowhead=   sheet.createRow(count);
					
				HSSFFont font = hwb.createFont();
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				HSSFCellStyle style = hwb.createCellStyle();
				style.setFont(font);
				style.setAlignment(style.ALIGN_CENTER);

				for(int i=0;i<list.length;i++)
				{
					cell = rowhead.createCell(i);
					cell.setCellValue(list[i].toString());
					cell.setCellStyle(style);
					sheet.setColumnWidth(i, 5000); 
//					rowhead.createCell(i).setCellValue(list[i].toString());
				}
			
				if(!isAvailable)
				{
					fileOut =  new FileOutputStream(file);

					hwb.write(fileOut);
					fileOut.close();
				}
				else{}
		//		System.out.println("Your excel file has been generated!");

			} 
			catch ( Exception ex ) 
			{
				System.out.println(ex);
			}
			
			//create rows
			
		/*	int cou=al.size()/3;
			for(int i=1;cou<=i;i++)
			{
				rowhead  =  sheet.createRow(cou);
			}
			
			System.out.println("Row Count=="+cou);
			*/
			
			//
			
			int cou=1;
			int temp=0;
			try {
				/*String x=al.get(0).toString();
				String y=al.get(1).toString();
				String z=al.get(2).toString();
				*/
				System.out.println("ArrayList==========="+al.toString());
				System.out.println("Array List Size======="+al.size());
				rowhead  =  sheet.createRow(1);
				int s=al.size()/3;
				System.out.println("asixeee::::::::::"+s);
				
				for(int i=0;i<s;)
				{
					System.out.println("Tempppppp::"+temp);
					temp++;
					if(temp==4)
					{	cou++;
						rowhead  =  sheet.createRow(cou);
						int rowNo=rowhead.getRowNum();
						System.out.println("Row No :::::::::::"+rowNo);
						temp=0;
					}
					cell = rowhead.createCell(0);
					cell.setCellValue(al.get(i).toString());
					i++;
					cell = rowhead.createCell(1);
					cell.setCellValue(al.get(i).toString());
					i++;
					cell = rowhead.createCell(2);
					cell.setCellValue(al.get(i).toString());
					
					
					
					/*System.out.println("For Loop==========="+al.get(i));
					HSSFRow header = sheet.getRow(0);
					System.out.println("Header Cell=="+header.getCell(0));
					System.out.println("Header Cell Value=="+header.getCell(0).getStringCellValue());
				
					
					if((header.getCell(0).getStringCellValue().trim()).equals("x-axis"))
					{
						cell = rowhead.createCell(i);
						cell.setCellValue(al.get(i).toString());
						System.out.println("Cell X"+al.get(i).toString());
					}
					else if((header.getCell(1).getStringCellValue().trim()).equals("y-axis"))
					{
						cell = rowhead.createCell(i);
						cell.setCellValue(al.get(i).toString());
						System.out.println("Cell Y"+al.get(i).toString());
					}
					else if((header.getCell(2).getStringCellValue().trim()).equals("z-axis"))
					{
						cell = rowhead.createCell(i);
						cell.setCellValue(al.get(i).toString());
						System.out.println("Cell Z"+al.get(i).toString());
					}*/
				}
				try
				{
					fileOut =  new FileOutputStream(file);
					hwb.write(fileOut);
					fileOut.close();
				}
				catch (IOException e)
				{
					System.out.println(e);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	  

	

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		//Vector al=new Vector();
		float x=event.values[0];
		float y=event.values[1];
		float z=event.values[2];
		//title.setText(R.string.app_name);
		
		tv.setText("X-Axis="+"\t\t"+x);
		tv1.setText("Y-Axis="+"\t\t"+y);
		tv2.setText("Z-Axis="+"\t\t"+z);
		
		al.add(x);
		al.add(y);
		al.add(z);
		
		saveExcelFile(al);
		
	}
	@Override
	protected void onResume()
	{super.onResume();
	mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}@
	Override
	
	protected void onPause()
	{super.onPause();
	mSensorManager.unregisterListener(this);
	

	}
	 
	private class Connection extends AsyncTask {
		
		 
		
       @Override

       protected Object doInBackground(Object... arg0) {

    	 
           return null;

       }
	}
	public void readExcel() throws IOException
	{
		System.out.println("**********************name******************"+name);
	File file = new File(Environment.getExternalStorageDirectory()
               + "/Android/data/"
                +name+".xls");
		//String dyPassword="0.09.74987.90498";
		
		System.out.println("*****************read excel value********************");
		
		
		if(file.exists())
		{
			FileInputStream input = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem( input );  
			HSSFWorkbook wb = new HSSFWorkbook(fs);  
			HSSFSheet sheet = wb.getSheetAt(0);  
			HSSFRow row;  
		System.out.println("*****************Enter the if condition**********************");	
			JSONArray arr=new JSONArray();
			
			int rowCount=sheet.getLastRowNum();
			
			int randomCount=randomPassword(rowCount);
			
			row=sheet.getRow(randomCount);
			
			String xaxis = ( row.getCell(0).getStringCellValue());
			String yaxis = ( row.getCell(1).getStringCellValue());
			String zaxis = ( row.getCell(2).getStringCellValue());
			
			String dyPassword=xaxis+"-"+yaxis+"-"+zaxis;
			
			System.out.println(xaxis+" "+yaxis+" "+zaxis+"\n"+dyPassword);
			
		/*	for(int i=1; i<=sheet.getLastRowNum(); i++)
			{  
				row = sheet.getRow(i);    
				String xaxis = ( row.getCell(0).getStringCellValue());
				String yaxis = ( row.getCell(1).getStringCellValue());
				String zaxis = ( row.getCell(2).getStringCellValue());

				System.out.println(xaxis+" "+yaxis+" "+zaxis);
				
				arr.add(xaxis);
				arr.add(yaxis);
				arr.add(zaxis);
						
			//	st.executeUpdate("INSERT INTO "+uname+" VALUES('"+id+"','"+xaxis+"','"+yaxis+"','"+zaxis+"')");
				
			}		*/
			
			JSONObject nObj=new JSONObject();
			JSONObject aObj=new JSONObject();
			JSONArray allArr=new JSONArray();
			JSONObject allObj=new JSONObject();
			
			nObj.put("name",name);
			aObj.put("pass",dyPassword);
			
			allArr.add(nObj);
			allArr.add(aObj);
			
			allObj.put("Datas",allArr);
			passshow.setText(dyPassword);
			String URL = "http://104.236.112.129:8080/Dynamic/ExcelValues?name="+name+"&dpass="+dyPassword;
	        System.out.println("***********************success****************************");
		 
			 HttpClient Client = new DefaultHttpClient();
			 
			 HttpGet httppost = new HttpGet(URL);
			   		 
	     
	       Log.i("httpget", URL);
	     
	      	 
	       try
           {
          	 
          	 java.net.URL url= new java.net.URL(URL);
          	 
          	 java.net.URLConnection con = url.openConnection();
  
             HttpResponse rp = Client.execute( httppost );  
             
             StatusLine staus = rp.getStatusLine();
             
             System.out.println("------------------------------"+staus.toString());
             
             Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                      
           } catch (ClientProtocolException e) {
          	 
       	
          	 e.printStackTrace();
           } catch (IOException e) {
       		
       		e.printStackTrace();
       	}
	          
	          
		}
	else
	{
		 Toast.makeText(getApplicationContext(), "Illegal Password Generation", Toast.LENGTH_LONG).show();
	}
	}
	
	public static int randomPassword(int count)
	{
		Random random=new Random();
		int rowNum=random.nextInt(count);

		return rowNum;
	}
	
	}


