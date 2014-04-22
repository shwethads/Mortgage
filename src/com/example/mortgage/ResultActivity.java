package com.example.mortgage;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class ResultActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			String monthly = extras.getString("monthly");
			String total = extras.getString("total");
			String date = extras.getString("date");
			
			TextView tv1 = (TextView) findViewById(R.id.textView4);
			System.out.println(monthly);
			tv1.setText(monthly);
			
			TextView tv2 = (TextView) findViewById(R.id.textView5);
			System.out.println(total);
			tv2.setText(total);
			
			TextView tv3 = (TextView) findViewById(R.id.textView6);
			System.out.println(date);
			tv3.setText(date);
			
			// Back to home screen
			Button b = (Button) findViewById(R.id.button1);
			b.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ResultActivity.this, MainActivity.class);
					ResultActivity.this.startActivity(intent);
				}
			});
		}
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_result,
					container, false);
			return rootView;
		}
	}

}
