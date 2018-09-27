//~单播消息
//end结束回话消息
//w传输文件消息
//B发送表情信息
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

public class 控制6聊天界面  implements Initializable {
	public static Stage s1=null;
	public static String hyip=null;//好友的ip，以便找到好友
	public static String nic1=null;
	static Socket s=null;
	ServerSocket se=null;
	static DataInputStream in=null;
	static DataOutputStream out=null;//与好友的连接
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
	static int i=0;//指明此次会话的类别
	public Socket ss=null;
	private static DecimalFormat df = null; 
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	static { 
		// 设置数字格式，保留一位有效小数 
		df = new DecimalFormat("#0.0"); 
		df.setRoundingMode(RoundingMode.HALF_UP); 
		df.setMinimumFractionDigits(1); 
		df.setMaximumFractionDigits(1); 
	} 


	private String getFormatFileSize(long length) { //设置文件大小值
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
		new Thread(()->{//创建线程接受消息
			String lin=null;
			Date d1=null;
			try {
				while((lin=in.readUTF())!=null){
					if(lin.split("~").length==0){
						xx.appendText("你的好友已离开会话\r\n");//显示消息
					}
					else	if(lin.matches("yylt")){
						xx.appendText("对方请求语音聊天"+"\r\n");
						d1=new Date();
						yylt();
					}
					else if(lin.matches("end")){//计算语音聊天时间
						Date d2=new Date();
						xx.appendText("会话持续时间为:"+(d2.getTime()-d1.getTime())/60000+"分钟"+"\r\n");
						c1.stop();
						p1.stop();
					}
					else	if(lin.matches("B.*")){//表情
						System.out.println(lin);
						String bh=lin.split("B")[1];//将收到的消息转换为表情显示出来
						switch(bh){
						case "1":{bqxs.setImage(new Image("表情/表情1.gif"));
						bqzh.setOpacity(0);
						break;
						}
						case "2":{bqxs.setImage(new Image("表情/表情2.gif"));
						bqzh.setOpacity(0);
						break;
						}
						case "3":{bqxs.setImage(new Image("表情/表情3.gif"));
						bqzh.setOpacity(0);
						break;
						}
						case "4":{bqxs.setImage(new Image("表情/表情4.gif"));
						bqzh.setOpacity(0);
						break;}
						case "5":{bqxs.setImage(new Image("表情/表情5.gif"));
						bqzh.setOpacity(0);
						break;}
						case "6":bqxs.setImage(new Image("表情/表情6.gif"));
						bqzh.setOpacity(0);
						break;
						case "7":{bqxs.setImage(new Image("表情/表情7.gif"));
						bqzh.setOpacity(0);
						break;}
						case "8":{bqxs.setImage(new Image("表情/表情8.gif"));
						bqzh.setOpacity(0);
						break;}
						case "9":{bqxs.setImage(new Image("表情/表情9.gif"));
						bqzh.setOpacity(0);
						break;}
						case "10":{bqxs.setImage(new Image("表情/表情10.gif"));
						bqzh.setOpacity(0);
						break;}
						}
					}
					else	if(lin.matches("W")){//收到的消息是w，就说明对方要发送的是文件
						FileOutputStream fos; 
						// 文件名和长度 
						String fileName = in.readUTF(); 
						long fileLength = in.readLong(); 
						long fileLength1 = fileLength;
						File directory = new File("D:\\聊天器"); //设置文件保存路径
						if(!directory.exists()) { 
							directory.mkdir(); 
						} 
						File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName); 
						fos = new FileOutputStream(file); 
						// 开始接收文件 
						byte[] bytes = new byte[1024]; 
						int length = 0; 
						while(fileLength1!=0&&(length = in.read(bytes, 0, bytes.length)) != -1) { 
							fos.write(bytes, 0, length); 
							fos.flush(); 
							fileLength1-=length;//根据文件大小判定什么时候结束文件传输
						} 
						System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========"); 
						xx.appendText("接受好友文件:"+fileName+"大小："+getFormatFileSize(fileLength) +"\r\n");//显示文件接收消息和大小等信息
						fos.close();
					}
					else{
						xx.appendText("好友消息"+"\r\n"+lin.split("~")[1]+"\r\n");//显示消息
					}
				}
			} catch (Exception e) {
				System.out.println("聊吧");
				xx.appendText("你的好友已离开会话\r\n");//显示消息
			}
		}).start();
	}

	public void start(){
		try {
			s1=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/聊天.fxml"));
			s1.setTitle("登录");
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

	public void setip2(String ip){//被动接受好友的会话消息，设置好友的ip
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
			System.out.println("得到的 ip 是 "+ip+"课设发起聊天");
			s=new Socket(hyip, 9696);//根据好友的ip地址请求与好友建立连接
			in=new DataInputStream(s.getInputStream());
			out=new DataOutputStream(s.getOutputStream());//创建输入输出流
			out.writeUTF("~"+s.getLocalAddress().toString().split("/")[1]);//发送一个表示单播的消息告诉会话接受者自己的ip
			try {
				se=new ServerSocket(6060);
				System.out.println("开放6060端口");//开启端口等待好友的连接
				s=se.accept();
				in=new DataInputStream(s.getInputStream());
				out=new DataOutputStream(s.getOutputStream());
			} catch (IOException e1) {
				System.out.println("开放6060端口出错");
			}
		}catch (Exception e) {
			System.out.println("set ip");

		}
	}

	public void wenjian(MouseEvent me){//文件传输
		FileInputStream fis; 
		try{
			out.writeUTF("W");//告知对方要传送文件
			FileChooser fc=new FileChooser();
			File selcet=fc.showOpenDialog(s1);
			if(selcet.exists()) { 
				fis = new FileInputStream(selcet); 
				//				dos = new DataOutputStream(s.getOutputStream()); 
				out.writeUTF(selcet.getName()); 
				out.flush(); 
				out.writeLong(selcet.length()); 
				out.flush(); 
				// 开始传输文件 
				byte[] bytes = new byte[1024]; 
				int length = 0; 
				long progress = 0; 
				while((length = fis.read(bytes, 0, bytes.length)) != -1) { 
					out.write(bytes, 0, length); 
					out.flush(); 
					progress += length; 
					System.out.print("| " + (100*progress/selcet.length()) + "% |"); 
				} 
				//======== 文件传输成功 ======== 
				fis.close();
			} 
			xx.appendText("发送文件："+selcet.getName()+"\r\n");
		}catch (Exception e) {
			System.out.println("文件传输");
		}
	}
	public void connectVoice(MouseEvent me){//语音聊天
		if(i==0){
			try{
				out.writeUTF("yylt");//告知对方要进行语音聊天
				yylt();//启动聊天
				i=1;
			}catch (Exception e) {
				System.out.println("语音聊天");
			}
		}
		else{
			shutDownvoice();
		}
	}
	public void yylt(){
		System.out.println(hyip);
		c1=new Capture(hyip);//创建语音
		p1=new Playback();//接受语音
		c1.start();
		p1.start();
		System.out.println("cg");
	}
	public void shutDownvoice(){//关闭语音聊天
		try{
			out.writeUTF("end");
		}catch (Exception e) {
			System.out.println("结束语音聊天");
		}
		c1.stop();
		p1.stop();
	}
	public void send1(MouseEvent me){
		send();
	}
	public void send(){
		String mes=fxx.getText();//得到消息
		if(mes.length()!=0)
			try {
				xx.appendText("我"+"\r\n"+mes+"\r\n");
				fxx.setText("");//将发送框置为空
				out.writeUTF("~"+mes);//发送单播消息
			} catch (IOException e) {
				System.out.println("发送消息错误");
			}
		else{
			tsxx.setOpacity(1);//消息不为空
			new Thread(()->{
				try {
					Thread.sleep(2000);//错误消息显示2秒后消失
					tsxx.setOpacity(0);
				} catch (InterruptedException e) {
					System.out.println("控制6 188");
				}
			}).start();
		}
	}
	public void send2(KeyEvent ke){
		if(ke.getCode()==KeyCode.ENTER)
			send();//如果输入会出发送消息
	}

	public void qx(MouseEvent me){
		try{
			s1.close();
			in.close();
			out.close();
			s.close();
			ss.close();

		}catch (Exception e) {
			System.out.println("6关闭");
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
	class Capture implements Runnable //音频捕捉以及发送的程序
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
				//建立输出流 此处可以加套压缩流用来压缩数据    
			} catch (Exception ex) {   
				return;   
			}   

			AudioFormat format = new AudioFormat(8000, 16, 2, true, true);   
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);   

			try 
			{   
				line = (TargetDataLine) AudioSystem.getLine(info);   
				//TargetDataLine 接口是DataLine接口的一种，通过它就可以直接从音频硬件获取数据了    
				line.open(format, line.getBufferSize());   
			}
			catch (Exception ex) 
			{   
				return;   
			}   

			byte[] data = new byte[1024];//跟下面的1024应保持一致    
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
					soc.send(pacToSend);//写入网络流    
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

		final int bufsize = 16384; //缓存大小       
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
	public void bq1(MouseEvent me){//对应发送每个表情的编号
		try{
			out.writeUTF("B1");
			bqxs.setImage(new Image("表情/表情1.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq2(MouseEvent me){
		try{
			out.writeUTF("B2");
			bqxs.setImage(new Image("表情/表情2.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq3(MouseEvent me){
		try{
			out.writeUTF("B3");
			bqxs.setImage(new Image("表情/表情3.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq4(MouseEvent me){
		try{
			out.writeUTF("B4");
			bqxs.setImage(new Image("表情/表情4.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq5(MouseEvent me){
		try{
			out.writeUTF("B5");
			bqxs.setImage(new Image("表情/表情5.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq6(MouseEvent me){
		try{
			out.writeUTF("B6");
			bqxs.setImage(new Image("表情/表情6.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq7(MouseEvent me){
		try{
			out.writeUTF("B7");
			bqxs.setImage(new Image("表情/表情7.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq8(MouseEvent me){
		try{
			out.writeUTF("B8");
			bqxs.setImage(new Image("表情/表情8.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq9(MouseEvent me){
		try{
			out.writeUTF("B9");
			bqxs.setImage(new Image("表情/表情9.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void bq10(MouseEvent me){
		try{
			out.writeUTF("B10");
			bqxs.setImage(new Image("表情/表情10.gif"));
			bqzh.setOpacity(0);
		}catch (Exception e) {
			System.out.println("表情");
		}
	}
	public void jt(MouseEvent me) {
		System.out.println("hehewfefwefewfe");
		Runtime r=Runtime.getRuntime();

		//创建进程，打开Windows系统自带绘图软件SnippingTool.EXE
		Process p=null;
		Date d1=new Date();//创建Date对象d1，记录SnippingTool.EXE打开前时刻
		try {
			p=r.exec("SnippingTool.EXE");
			p.waitFor();//等待当前进程运行完毕
		} catch (Exception e) {
			System.out.println("Error executing SnippingTool");
		}
		Date d2=new Date();//创建Date对象d2，记录SnippingTool.EXE打开后时刻
		long elipseTime=d2.getTime()-d1.getTime();//计算绘图总共花费时间
		System.out.println("截图总共花费时间为："+elipseTime);
	}
}
