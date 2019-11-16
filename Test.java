package com.zqw;

import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.util.List;

import javax.swing.JFrame;

public class Test extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//界面初始化
	public void display() {
		//设置界面位置大小
		this.setBounds(500,300,500,500);
		//设置布局为空
		this.setLayout(null);
		//设置窗口可见
		this.setVisible(true);
		//输入文本
		TextArea textArea = new TextArea();
		Label label = new Label("请输入地址:");
		//显示记录文本
		TextArea jTextArea = new TextArea();
		Button button = new Button("下载");
		this.add(textArea);
		this.add(label);
		this.add(jTextArea);
		this.add(button);
		//设置位置及大小
		textArea.setBounds(200, 50, 150, 20);
		label.setBounds(120, 50,100,20);
		jTextArea.setBounds(20, 150,460,300);
		jTextArea.setBackground(Color.white);
		jTextArea.setText("在输入框输入要下载的地址，地址中的图片需要是静态的..........");
		button.setBounds(200, 100,50,20);
		//设置关闭事件
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		//设置按钮事件
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取输入框的内容
				String url = textArea.getText();
				//判断输入框是否有内容
				if(url!=null&&url.length()>0) {
					try {
						//获取该网站图片信息
						List<MyImage> image = GetImage.getImage(url,url);
						BufferedOutputStream bos;
						BufferedInputStream bis;
						HttpURLConnection conn;
						byte[] b = new byte[1024];
						int len = 0;
						File file ;
						for(MyImage img : image) {
							conn = GetImage.getConnection(img.getPath(),url);
							file = new File(img.getName()+"."+img.getType());
							bos = new BufferedOutputStream(new FileOutputStream(file));
							bis = new BufferedInputStream(conn.getInputStream());
							System.out.println(img.getPath());
							while((len = bis.read(b))!=-1) {
								bos.write(b, 0, len);
							}
							bos.flush();
							//将下载信息放入信息框里
							jTextArea.setText(jTextArea.getText()+"下载完成:"+img.getPath()+"\n本地连接:"+file.getAbsolutePath()+"\n\n\n");
							bos.close();
							bis.close();
						}
					} catch (Exception e1) {
						jTextArea.setText("链接不可用");
					}
				}else {
					jTextArea.setText("链接不可用");
				}
			}
		});
	}
	
	public static void main(String... args) {
		new Test().display();;
	}
	

}
