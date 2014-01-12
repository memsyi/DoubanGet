package com.example.doubanget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetHelper {
	public static String getData(String strUrl){
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(strUrl);
			URLConnection conn = null;
			InputStream is = null;
			InputStreamReader rd = null;
			BufferedReader bf = null;
			try {
				conn = url.openConnection();
				is = conn.getInputStream();
				rd = new InputStreamReader(is);
				bf = new BufferedReader(rd);
				String line = bf.readLine();
				while(line != null) {
					buffer.append(line);
					line = bf.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(bf != null)
						bf.close();
					if(rd != null)
						rd.close();
					if(is != null)
						is.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	public static Bitmap getImage(String Url) throws Exception {

		try {

			URL url = new URL(Url);

			String responseCode = url.openConnection().getHeaderField(0);

			if (responseCode.indexOf("200") < 0)

				throw new Exception("图片文件不存在或路径错误，错误代码：" + responseCode);


			return BitmapFactory.decodeStream(url.openStream());

		} catch (IOException e) {

			// TODO Auto-generated catch block

			throw new Exception(e.getMessage());

		}

	}


}
