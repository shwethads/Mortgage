package com.example.mortgage;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	
	String purchase_price, down_payment, mortgage_term, interest_rate,  
			first_payment_date, payoffDate;
	EditText et;
	String monthly, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        Button b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				//Get all values
				et = (EditText) findViewById(R.id.editText1);
				purchase_price = et.getText().toString();
		        
		        et = (EditText) findViewById(R.id.editText2);
		        down_payment = et.getText().toString();
				
		        et = (EditText) findViewById(R.id.editText3);
		        mortgage_term = et.getText().toString();
		        
				et = (EditText) findViewById(R.id.editText4);
		        interest_rate = et.getText().toString();
		        
		        et = (EditText) findViewById(R.id.editText5);
		        first_payment_date = et.getText().toString();
		        
		        int[] date = getDate(first_payment_date);
		        
		        //If date is in the required format
		        if(checkDate(date)) {
		        	//Calculate the required values
		        	monthly = monthlyPayment();
		        	total = totalPayment(Float.parseFloat(monthly));
		        	String payoffDate = calcPayoffDate(date);
		        	
		        	//Launch new Activity
			        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
			        intent.putExtra("monthly", monthly);
			        intent.putExtra("total", total);
			        intent.putExtra("date", payoffDate);
			        MainActivity.this.startActivity(intent);
		        }
			}
		});        
    }
    
   /**
    * calculate payoff date
    * @param date
    * @return
    */
   public String calcPayoffDate(int[] date) {
	   int[] finalDate = date;
	   finalDate[2] += Integer.parseInt(mortgage_term);
	   
	   String str = new String(finalDate[0]+"/"+finalDate[1]+"/"+finalDate[2]);
	   System.out.println("Payoff"+str);
	   return str;
   }
    
   /**
    * Calculate monthly payment
    * @return
    */
    public String monthlyPayment() {
    	float interest = Float.parseFloat(interest_rate);
    	System.out.println(interest);
    	double monthlyRate = interest/12.0;
    	System.out.println(monthlyRate);
    	float termInMonths = Float.parseFloat(mortgage_term)*12;
    	System.out.println(termInMonths);
    	float price = Float.parseFloat(purchase_price);
    	System.out.println(price);
    	float downPaymentPercentage = Float.parseFloat(down_payment);
    	System.out.println(downPaymentPercentage);
    	
    	float downPayment = (downPaymentPercentage/100)*(price);
    	System.out.println("DP="+downPayment);
    	float loanAmount = price-downPayment;
    	System.out.println("LA = "+loanAmount);
    	
    	DecimalFormat df = new DecimalFormat("#.##");
    	double monthlyPayment = (loanAmount*monthlyRate)
    			/(1-Math.pow(1+monthlyRate, -termInMonths));
    	String monthly = df.format(monthlyPayment);
    	System.out.println("....."+monthlyPayment);
    	return monthly;
    }
    
    
    /**
     * Calculate total payment
     * @param monthlyPayment
     * @return
     */
    public String totalPayment(double monthlyPayment) {
    	DecimalFormat df = new DecimalFormat("#.##");
    	double totalPayment = monthlyPayment*12*Integer.parseInt(mortgage_term);
    	return df.format(totalPayment);
    }
    
    /**
     * Check if date value id correct
     * @param date
     * @return
     */
    
    public boolean checkDate(int[] date) {
    	boolean result = true;
    
    	if(date[1]<1 || date[1]>12) {
    		result = false;
    	}	
    	else {
    		if(date[1]%2 == 0) {
    			if((date[1]==4 || date[1]==6 || date[1]==9 || date[1]==11) && date[0]>28)
    				result = false;
    			else if(date[1]>30)
    			result = false;
    		}
    	}
    	
    	if(date[0]<1 || date[0]>31)
    		result = false;
    	if(date[2] > 2013)
    		result = false;
    	
    	return result;
    }
    
    
    /**
     * Parse date string and separate tokens
     * @param date
     * @return
     */
    public int[] getDate(String date) {
    	int[] dateArr = new int[3];
    	StringTokenizer st = new StringTokenizer(date, "/");
    	for(int i=0; i<3; i++)
    		dateArr[i] = Integer.parseInt(st.nextToken());
    	return dateArr;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
