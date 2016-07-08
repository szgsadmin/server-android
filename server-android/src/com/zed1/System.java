package com.zed1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;

/**
 * server.c 中需要 PACKAGENAME 添加进去编译
 * 
 * 
 * 
 */
@SuppressLint("SdCardPath")
public class System {
	static {
		java.lang.System.loadLibrary("server");
	}

	public static void start() {
		server(3,
				new String[] { "/data/data/com.example.server_android/server", "-p", "/data/data/com.example.server_android/server.pid"});
	}

	public static void stop() {
		try {
			InputStream is = new FileInputStream(
					"/data/data/com.example.server_android/server.pid");
			int size;
			if ((size = is.available()) > 0) {
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				new ProcessBuilder().command(
						String.format("/system/bin/kill -9 %s",
								new String(buffer)).split(" ")).start();
			}
		} catch (IOException e) {
		}
	}

	public static native int server(int argc, String[] argv);
}
