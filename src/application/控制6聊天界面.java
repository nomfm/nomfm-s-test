//~������Ϣ
//end�����ػ���Ϣ
//w�����ļ���Ϣ
//B���ͱ�����Ϣ
package application;

import java.awt.Container;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ����6�������  implements Initializable {
	public static Stage s1=null;
	public static String hyip=null;//���ѵ�ip���Ա��ҵ�����
	public static String nic1=null;
	static Socket s=null;
	ServerSocket se=null;
	static DataInputStream in=null;
	static DataOutputStream out=null;//����ѵ�����
	static Capture c1=null;
	static Playback p1=null;	
	@FXML
	private ImageView bqzh;

	@FXML
	private ImageView lt_bq;
	@FXML
	private ImageView bqxs;
	@FXML
	private ImageView lt_fswj;
	@FXML
	private ImageView lt_splt;
	@FXML
	private ImageView lt_zt;
	@FXML
	private ImageView lt_yylt;
	@FXML
	private ImageView lt_zdh;
	@FXML
	private ImageView lt_zxh;
	@FXML
	private ImageView lt_gb;
	@FXML
	private TextArea xx;
	@FXML
	private Label nic;
	@FXML
	private TextArea fxx;
	@FXML
	private Label tsxx;
	static int i=0;//ָ���˴λỰ�����
	public Socket ss=null;
	private static DecimalFormat df = null; 
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	static { 
		// �������ָ�ʽ������һλ��ЧС�� 
		df = new DecimalFormat("#0.0"); 
		df.setRoundingMode(RoundingMode.HALF_UP); 
		df.setMinimumFractionDigits(1); 
		df.setMaximumFractionDigits(1); 
	} 


	private String getFormatFileSize(long length) { //�����ļ���Сֵ
		double size = ((double) length) / (1 << 30); 
		if(size >= 1) { 
			return df.format(size) + "GB"; 
		} 
		size = ((double) length) / (1 << 20); 
		if(size >= 1) { 
			return df.format(size) + "MB"; 
		} 
		size = ((double) length) / (1 << 10); 
		if(size >= 1) { 
			return df.format(size) + "KB"; 
		} 
		return length + "B"; 
	} 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		xx.setEditable(false);
		nic.setText(nic1);
		new Thread(()->{//�����߳̽�����Ϣ
			String lin=null;
			Date d1=null;
			try {
				while((lin=in.readUTF())!=null){
					if(lin.split("~").length==0){
						xx.appendText("��ĺ������뿪�Ự\r\n");//��ʾ��Ϣ
					}
					else	if(lin.matches("yylt")){
						xx.appendText("�Է�������������"+"\r\n");
						d1=new Date();
						yylt();
					}
					else if(lin.matches("end")){//������������ʱ��
						Date d2=new Date();
						xx.appendText("�Ự����ʱ��Ϊ:"+(d2.getTime()-d1.getTime())/60000+"����"+"\r\n");
						c1.stop();
						p1.stop();
					}
					else	if(lin.matches("B.*")){//����
						System.out.println(lin);
						String bh=lin.split("B")[1];//���յ�����Ϣת��Ϊ������ʾ����
						switch(bh){
						case "1":{bqxs.setImage(new Image("����/����1.gif"));
						bqzh.setOpacity(0);
						break;
						}
						case "2":{bqxs.setImage(new Image("����/����2.gif"));
						bqzh.setOpacity(0);
						break;
						}
						case "3":{bqxs.setImage(new Image("����/����3.gif"));
						bqzh.setOpacity(0);
						break;
						}
						case "4":{bqxs.setImage(new Image("����/����4.gif"));
						bqzh.setOpacity(0);
						break;}
						case "5":{bqxs.setImage(new Image("����/����5.gif"));
						bqzh.setOpacity(0);
						break;}
						case "6":bqxs.setImage(new Image("����/����6.gif"));
						bqzh.setOpacity(0);
						break;
						case "7":{bqxs.setImage(new Image("����/����7.gif"));
						bqzh.setOpacity(0);
						break;}
						case "8":{bqxs.setImage(new Image("����/����8.gif"));
						bqzh.setOpacity(0);
						break;}
						case "9":{bqxs.setImage(new Image("����/����9.gif"));
						bqzh.setOpacity(0);
						break;}
						case "10":{bqxs.setImage(new Image("����/����10.gif"));
						bqzh.setOpacity(0);
						break;}
						}
					}
					else	if(lin.matches("W")){//�յ�����Ϣ��w����˵���Է�Ҫ���͵����ļ�
						FileOutputStream fos; 
						// �ļ����ͳ��� 
						String fileName = in.readUTF(); 
						long fileLength = in.readLong(); 
						long fileLength1 = fileLength;
						File directory = new File("D:\\������"); //�����ļ�����·��
						if(!directory.exists()) { 
							directory.mkdir(); 
						} 
						File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName); 
						fos = new FileOutputStream(file); 
						// ��ʼ�����ļ� 
						byte[] bytes = new byte[1024]; 
						int length = 0; 
						while(fileLength1!=0&&(length = in.read(bytes, 0, bytes.length)) != -1) { 
							fos.write(bytes, 0, length); 
							fos.flush(); 
							fileLength1-=length;//�����ļ���С�ж�ʲôʱ������ļ�����
						} 
						System.out.println("======== �ļ����ճɹ� [File Name��" + fileName + "] [Size��" + getFormatFileSize(fileLength) + "] ========"); 
						xx.appendText("���ܺ����ļ�:"+fileName+"��С��"+getFormatFileSize(fileLength) +"\r\n");//��ʾ�ļ�������Ϣ�ʹ�С����Ϣ
						fos.close();
					}
					else{
						xx.appendText("������Ϣ"+"\r\n"+lin.split("~")[1]+"\r\n");//��ʾ��Ϣ
					}
				}
			} catch (Exception e) {
				System.out.println("�İ�");
				xx.appendText("��ĺ������뿪�Ự\r\n");//��ʾ��Ϣ
			}
		}).start();
	}

	public void start(){
		try {
			s1=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/����.fxml"));
			s1.setTitle("��¼");
			Scene scene=new Scene(root);
			s1.setScene(scene);
			s1.initStyle(StageStyle.TRANSPARENT);
			
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
		    	 @Override public void handle(javafx.scene.input.MouseEvent  m) {
		    		 s1.setX(x_stage + m.getScreenX() - x1);
		    		 s1.setY(y_stage + m.getScreenY() - y1);
		        }	               	                	              
		       });
		     scene.setOnDragEntered(null);
		     scene.setOnMousePressed(new EventHandler<MouseEvent>() {
		        @Override public void handle(javafx.scene.input.MouseEvent m) {
		        x1 =m.getScreenX();
		        y1 =m.getScreenY();
		        x_stage = s1.getX();
		        y_stage = s1.getY();
		          }	               
		       });
			s1.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setip2(String ip){//�������ܺ��ѵĻỰ��Ϣ�����ú��ѵ�ip
		try{
			hyip=new String(ip);
			s=new Socket(ip, 6060);
			in=new DataInputStream(s.getInputStream());
			out=new DataOutputStream(s.getOutputStream());
		}catch (Exception e) {
			System.out.println("setip2");
		}
	}

	public void setip(String ip,String hynic){
		try{
			this.hyip=new String(ip);
			this.nic1=hynic;
			System.out.println("�õ��� ip �� "+ip+"���跢������");
			s=new Socket(hyip, 9696);//���ݺ��ѵ�ip��ַ��������ѽ�������
			in=new DataInputStream(s.getInputStream());
			out=new DataOutputStream(s.getOutputStream());//�������������
			out.writeUTF("~"+s.getLocalAddress().toString().split("/")[1]);//����һ����ʾ��������Ϣ���߻Ự�������Լ���ip
			try {
				se=new ServerSocket(6060);
				System.out.println("����6060�˿�");//�����˿ڵȴ����ѵ�����
				s=se.accept();
				in=new DataInputStream(s.getInputStream());
				out=new DataOutputStream(s.getOutputStream());
			} catch (IOException e1) {
				System.out.println("����6060�˿ڳ���");
			}
		}catch (Exception e) {
			System.out.println("set ip");

		}
	}

	public void wenjian(MouseEvent me){//�ļ�����
		FileInputStream fis; 
		try{
			out.writeUTF("W");//��֪�Է�Ҫ�����ļ�
			FileChooser fc=new FileChooser();
			File selcet=fc.showOpenDialog(s1);
			if(selcet.exists()) { 
				fis = new FileInputStream(selcet); 
				//				dos = new DataOutputStream(s.getOutputStream()); 
				out.writeUTF(selcet.getName()); 
				out.flush(); 
				out.writeLong(selcet.length()); 
				out.flush(); 
				// ��ʼ�����ļ� 
				byte[] bytes = new byte[1024]; 
				int length = 0; 
				long progress = 0; 
				while((length = fis.read(bytes, 0, bytes.length)) != -1) { 
					out.write(bytes, 0, length); 
					out.flush(); 
					progress += length; 
					System.out.print("| " + (100*progress/selcet.length()) + "% |"); 
				} 
				//======== �ļ�����ɹ� ======== 
				fis.close();
			} 
			xx.appendText("�����ļ���"+selcet.getName()+"\r\n");
		}catch (Exception e) {
			System.out.println("�ļ�����");
		}
	}
	public void connectVoice(MouseEvent me){//��������
		if(i==0){
			try{
				out.writeUTF("yylt");//��֪�Է�Ҫ������������
				yylt();//��������
				i=1;
			}catch (Exception e) {
				System.out.println("��������");
			}
		}
		else{
			shutDownvoice();
		}
	}
	public void yylt(){
		System.out.println(hyip);
		c1=new Capture(hyip);//��������
		p1=new Playback();//��������
		c1.start();
		p1.start();
		System.out.println("cg");
	}
	public void shutDownvoice(){//�ر���������
		try{
			out.writeUTF("end");
		}catch (Exception e) {
			System.out.println("������������");
		}
		c1.stop();
		p1.stop();
	}
	public void send1(MouseEvent me){
		send();
	}
	public void send(){
		String mes=fxx.getText();//�õ���Ϣ
		if(mes.length()!=0)
			try {
				xx.appendText("��"+"\r\n"+mes+"\r\n");
				fxx.setText("");//�����Ϳ���Ϊ��
				out.writeUTF("~"+mes);//���͵�����Ϣ
			} catch (IOException e) {
				System.out.println("������Ϣ����");
			}
		else{
			tsxx.setOpacity(1);//��Ϣ��Ϊ��
			new Thread(()->{
				try {
					Thread.sleep(2000);//������Ϣ��ʾ2�����ʧ
					tsxx.setOpacity(0);
				} catch (InterruptedException e) {
					System.out.println("����6 188");
				}
			}).start();
		}
	}
	public void send2(KeyEvent ke){
		if(ke.getCode()==KeyCode.ENTER)
			send();//���������������Ϣ
	}

	public void qx(MouseEvent me){
		try{
			s1.close();
			in.close();
			out.close();
			s.close();
			ss.close();

		}catch (Exception e) {
			System.out.println("6�ر�");
		}
	}
	public void zdhyy(MouseEvent me){
		lt_zdh.setOpacity(0.6);
	}
	public void zdhqcyy(MouseEvent me){
		lt_zdh.setOpacity(0);
	}
	public void zxhyy(MouseEvent me){
		lt_zxh.setOpacity(0.6);
	}
	public void zxhqcyy(MouseEvent me){
		lt_zxh.setOpacity(0);
	}
	public void gbyy(MouseEvent me){
		lt_gb.setOpacity(0.6);
	}
	public void gbqcyy(MouseEvent me){
		lt_gb.setOpacity(0);
	}
	public void bqyy(MouseEvent me){
		lt_bq.setOpacity(0.4);
	}
	public void bqqcyy(MouseEvent me){
		lt_bq.setOpacity(0);
	}
	public void yyltyy(MouseEvent me){
		lt_yylt.setOpacity(0.4);
	}
	public void yyltqcyy(MouseEvent me){
		lt_yylt.setOpacity(0);
	}
	public void spltyy(MouseEvent me){
		lt_splt.setOpacity(0.4);
	}
	public void spltqcyy(MouseEvent me){
		lt_splt.setOpacity(0);
	}
	public void ztyy(MouseEvent me){
		lt_zt.setOpacity(0.4);
	}
	public void ztqcyy(MouseEvent me){
		lt_zt.setOpacity(0);
	}
	public void fswjyy(MouseEvent me){
		lt_fswj.setOpacity(0.4);
	}
	public void fswjqcyy(MouseEvent me){
		lt_fswj.setOpacity(0);
	}
	class Capture implements Runnable //��Ƶ��׽�Լ����͵ĳ���
	{

		TargetDataLine line;          
		Thread thread;   
		DatagramPacket pacToSend;   
		DatagramSocket soc;   

		String ip;   

		/** Creates a new instance of UDPCapture */   
		public Capture(String ip) 
		{   
			this.ip=ip;   
		}   
		public void start() 
		{   

			thread = new Thread(this);   
			thread.setName("Capture");   
			thread.start();   
		}   

		public void stop() 
		{   
			thread = null;   
		}   

		public void run() 
		{   

			try 
			{   
				soc = new DatagramSocket();   
				//��������� �˴����Լ���ѹ��������ѹ������    
			} catch (Exception ex) {   
				return;   
			}   

			AudioFormat format = new AudioFormat(8000, 16, 2, true, true);   
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);   

			try 
			{   
				line = (TargetDataLine) AudioSystem.getLine(info);   
				//TargetDataLine �ӿ���DataLine�ӿڵ�һ�֣�ͨ�����Ϳ���ֱ�Ӵ���ƵӲ����ȡ������    
				line.open(format, line.getBufferSize());   
			}
			catch (Exception ex) 
			{   
				return;   
			}   

			byte[] data = new byte[1024];//�������1024Ӧ����һ��    
			int numbytesread = 0;   
			line.start();   

			while (thread != null) 
			{   
				numbytesread = line.read(data, 0, 1024);   
				System.out.println("eecevrvwrwc");
				try 
				{   
					pacToSend = new DatagramPacket(data,data.length,   
							InetAddress.getByName(ip),9091);   
					soc.send(pacToSend);//д��������    
				} catch (Exception ex) 
				{   
					break;   
				}   
			}   

			line.stop();   
			line.close();   
			line = null;   

		}   


	}

	class Playback implements Runnable 
	{

		final int bufsize = 16384; //�����С       
		SourceDataLine line;   
		Thread thread;   
		DatagramSocket socket;   

		byte buffer[] = new byte[1024];   
		DatagramPacket pac = new DatagramPacket(buffer , buffer.length);   
		/** Creates a new instance of UDPPlay */   
		public Playback() 
		{   
		}   
		public void start() 
		{   
			thread = new Thread(this);   
			thread.setName("Playback");   
			thread.start();   
		}   

		public void stop() 
		{   
			thread = null;   
		}   

		public void run() 
		{   

			try
			{   
				System.out.println("a1");
				socket = new DatagramSocket(9090);   
				System.out.println("aaaaaa");
			} 
			catch(Exception e) 
			{   
				System.out.println("Socket Error");   
			}   

			AudioFormat format = new AudioFormat(8000, 16, 2, true, true);   

			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);   
			try 
			{   
				line = (SourceDataLine) AudioSystem.getLine(info);   
				line.open(format, bufsize);   
			} catch (LineUnavailableException ex) 
			{   
				return;   
			}   

			int numbytesread = 0;   
			line.start();   

			while (thread != null) 
			{   
				System.out.println("bbbbbbbbbb");
				try 
				{   
					socket.receive(pac);   
					numbytesread = pac.getData().length;   
					line.write(pac.getData(), 0, numbytesread);   
					//write(byte[] b, int off, int len)    
				} catch (Exception e) 
				{   
					break;   
				}   
			}   

			if (thread != null) {   
				line.drain();   
			}   

			line.stop();   
			line.close();   
			line = null;   
		}   
	}
	public void bqzh(MouseEvent me){
		bqzh.setOpacity(1);
	}
	public void bq1(MouseEvent me){//��Ӧ����ÿ������ı��
		try{
			out.writeUTF("B1");
			bqxs.setImage(new Image("����/����1.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq2(MouseEvent me){
		try{
			out.writeUTF("B2");
			bqxs.setImage(new Image("����/����2.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq3(MouseEvent me){
		try{
			out.writeUTF("B3");
			bqxs.setImage(new Image("����/����3.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq4(MouseEvent me){
		try{
			out.writeUTF("B4");
			bqxs.setImage(new Image("����/����4.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq5(MouseEvent me){
		try{
			out.writeUTF("B5");
			bqxs.setImage(new Image("����/����5.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq6(MouseEvent me){
		try{
			out.writeUTF("B6");
			bqxs.setImage(new Image("����/����6.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq7(MouseEvent me){
		try{
			out.writeUTF("B7");
			bqxs.setImage(new Image("����/����7.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq8(MouseEvent me){
		try{
			out.writeUTF("B8");
			bqxs.setImage(new Image("����/����8.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq9(MouseEvent me){
		try{
			out.writeUTF("B9");
			bqxs.setImage(new Image("����/����9.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void bq10(MouseEvent me){
		try{
			out.writeUTF("B10");
			bqxs.setImage(new Image("����/����10.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("����");
		}
	}
	public void jt(MouseEvent me) {
		System.out.println("hehewfefwefewfe");
		Runtime r=Runtime.getRuntime();

		//�������̣���Windowsϵͳ�Դ���ͼ���SnippingTool.EXE
		Process p=null;
		Date d1=new Date();//����Date����d1����¼SnippingTool.EXE��ǰʱ��
		try {
			p=r.exec("SnippingTool.EXE");
			p.waitFor();//�ȴ���ǰ�����������
		} catch (Exception e) {
			System.out.println("Error executing SnippingTool");
		}
		Date d2=new Date();//����Date����d2����¼SnippingTool.EXE�򿪺�ʱ��
		long elipseTime=d2.getTime()-d1.getTime();//�����ͼ�ܹ�����ʱ��
		System.out.println("��ͼ�ܹ�����ʱ��Ϊ��"+elipseTime);
	}
}
