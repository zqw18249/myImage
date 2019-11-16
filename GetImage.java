package com.zqw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetImage {

	
	//返回图片集合
	public static List<MyImage> getImage(String url,String referer) throws Exception{
		//获取url的网页连接
		HttpURLConnection connection = getConnection(url,referer);
		//获取url网页的流
		BufferedReader bis = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		//用来储存网页
		StringBuffer sb = new StringBuffer();
		String s = "";
		while((s = bis.readLine())!=null) {
			sb.append(s+"\n");
		}
		//获取图片连接
		List<String> img = getImg(sb);
		//用来保存图片
		ArrayList<MyImage> arrayList = new ArrayList<MyImage>();
		MyImage myImage;
		for(String item : img) {
			//判断是否是图片
			if(item.lastIndexOf(".jpg")>0||item.lastIndexOf(".png")>0) {
				myImage = new MyImage();
				myImage.setPath(item);
				myImage.setName(item.substring(item.lastIndexOf("/")+1, item.lastIndexOf(".")));
				myImage.setType(item.substring(item.lastIndexOf(".")+1));
				arrayList.add(myImage);
			}
		}
		return arrayList;
	}
	
	//获取图片链接集合
	private static List<String> getImg(StringBuffer str){
		String patt = "<img.*?src=\"(.*?)\"";
		Pattern pa = Pattern.compile(patt);
		Matcher matcher = pa.matcher(str.toString());
		ArrayList<String> arrayList = new ArrayList<>();
		while(matcher.find()) {
			arrayList.add(matcher.group(1));
		}
		return arrayList;
	}
	
	//获取连接
	public static HttpURLConnection getConnection(String url,String referer) throws Exception {
		URL url2 = new URL(url);
		URLConnection openConnection = url2.openConnection();
		openConnection.setRequestProperty("Referer", referer);
		openConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
		return (HttpURLConnection)openConnection;
	}
	
}
