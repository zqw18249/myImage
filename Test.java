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

	//�����ʼ��
	public void display() {
		//���ý���λ�ô�С
		this.setBounds(500,300,500,500);
		//���ò���Ϊ��
		this.setLayout(null);
		//���ô��ڿɼ�
		this.setVisible(true);
		//�����ı�
		TextArea textArea = new TextArea();
		Label label = new Label("�������ַ:");
		//��ʾ��¼�ı�
		TextArea jTextArea = new TextArea();
		Button button = new Button("����");
		this.add(textArea);
		this.add(label);
		this.add(jTextArea);
		this.add(button);
		//����λ�ü���С
		textArea.setBounds(200, 50, 150, 20);
		label.setBounds(120, 50,100,20);
		jTextArea.setBounds(20, 150,460,300);
		jTextArea.setBackground(Color.white);
		jTextArea.setText("�����������Ҫ���صĵ�ַ����ַ�е�ͼƬ��Ҫ�Ǿ�̬��..........");
		button.setBounds(200, 100,50,20);
		//���ùر��¼�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		//���ð�ť�¼�
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ȡ����������
				String url = textArea.getText();
				//�ж�������Ƿ�������
				if(url!=null&&url.length()>0) {
					try {
						//��ȡ����վͼƬ��Ϣ
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
							//��������Ϣ������Ϣ����
							jTextArea.setText(jTextArea.getText()+"�������:"+img.getPath()+"\n��������:"+file.getAbsolutePath()+"\n\n\n");
							bos.close();
							bis.close();
						}
					} catch (Exception e1) {
						jTextArea.setText("���Ӳ�����");
					}
				}else {
					jTextArea.setText("���Ӳ�����");
				}
			}
		});
	}
	
	public static void main(String... args) {
		new Test().display();;
	}
	

}
