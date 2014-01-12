package com.example.doubanget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Chance extends Activity {
	private Button btnYes;
	private Button btnNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chance);
		btnYes=(Button)this.findViewById(R.id.btnYes);
		btnNo=(Button)this.findViewById(R.id.btnNo);
		btnYes.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(Chance.this,Register.class);
				startActivity(intent);
			}
		});
		btnNo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent=new Intent(Chance.this,MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
}
