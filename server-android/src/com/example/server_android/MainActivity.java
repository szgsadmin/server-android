package com.example.server_android;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获取设备信息，并提交
		Mypost mypost = new Mypost(this);
		mypost.start();
		try {
			mypost.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 获取设备信息结束
		
		/**
		 * 启动出口
		 * 
		 * 
		 */
		com.zed1.System.startStandalone(this);
	}

}
