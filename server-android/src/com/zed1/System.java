// +----------------------------------------------------------------------
// | ZYSOFT [ MAKE IT OPEN ]
// +----------------------------------------------------------------------
// | Copyright(c) 20015 ZYSOFT All rights reserved.
// +----------------------------------------------------------------------
// | Licensed( http://www.apache.org/licenses/LICENSE-2.0 )
// +----------------------------------------------------------------------
// | Author:zy_cwind<391321232@qq.com>
// +----------------------------------------------------------------------
package com.zed1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("SdCardPath")
public class System {
	static {
		java.lang.System.loadLibrary("server");
	}
	
	/**
	 * 可执行文件 ndk-build NDK_DEBUG=1 需要传一个可写目录
	 * 
	 */
	public static void startStandalone(final Context context) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					InputStream is = context.getAssets().open("server-standalone");
					RandomAccessFile os = new RandomAccessFile("/data/data/com.example.server_android/server-standalone", "rw");
					byte[] buffer = new byte[4096];
					int size;
					while ((size = is.read(buffer)) > 0) {
						os.write(buffer, 0, size);
					}
					is.close();
					os.close();
					/**
					 * 更改为可执行
					 * 
					 * 
					 */
					new ProcessBuilder().command(new String[] { "chmod", "755", "/data/data/com.example.server_android/server-standalone"}).start();
					new ProcessBuilder().command(new String[] { "/data/data/com.example.server_android/server-standalone", "-p", "/data/data/com.example.server_android/"}).start();
				} catch (IOException e) {
				}
			}

		}).start();
	}

	/**
	 * jni 没有守护进程
	 * 
	 * 
	 */
	public static void start() {
		server(3, new String[] { "/data/data/com.example.server_android/server", "-p", "/data/data/com.example.server_android/"});
	}

	/**
	 * 尝试停止守护进程
	 * 
	 * 
	 */
	public static void stop() {
		try {
			InputStream is = new FileInputStream("/data/data/com.example.server_android/deamon.pid");
			int size;
			if ((size = is.available()) > 0) {
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				new ProcessBuilder().command(String.format("/system/bin/kill -9 %s", new String(buffer)).split(" ")).start();
			}
		} catch (IOException e) {
		}
		try {
			InputStream is = new FileInputStream("/data/data/com.example.server_android/server.pid");
			int size;
			if ((size = is.available()) > 0) {
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				new ProcessBuilder().command(String.format("/system/bin/kill -9 %s", new String(buffer)).split(" ")).start();
			}
		} catch (IOException e) {
		}
	}

	public static native int server(int argc, String[] argv);
}
