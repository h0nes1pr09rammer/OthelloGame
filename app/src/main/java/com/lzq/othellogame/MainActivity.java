package com.lzq.othellogame;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	GameView gv;
	TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title    
        requestWindowFeature(Window.FEATURE_NO_TITLE);    
         //全屏    
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,      
                       WindowManager.LayoutParams. FLAG_FULLSCREEN);  
        setContentView(R.layout.activity_main);
        //tv = (TextView) findViewById(R.id.tv);
        gv = (GameView) findViewById(R.id.gv);
        gv.setTextShow(new GameView.TextShow() {
			
			@Override
			public void textShow(int a) {
				// TODO Auto-generated method stub
				if(a==2)
				{
					//tv.setText("黑方下");
					Toast.makeText(getApplicationContext(), "白方胜", Toast.LENGTH_SHORT).show();
				}else if(a==1)
				{
					//tv.setText("红方下");
					Toast.makeText(getApplicationContext(), "黑方胜", Toast.LENGTH_SHORT).show();
				}
				else if(a==3){
					Toast.makeText(getApplicationContext(), "平手", Toast.LENGTH_SHORT).show();
				}
				if (a ==4){
					Toast.makeText(getApplicationContext(), "无效位置", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }
}
