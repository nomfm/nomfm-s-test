package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class 控制8群聊界面 implements Initializable{
	public static Stage s1=null;
	public static String zh=null;
	private static String nic=null;

	@FXML
	private ImageView lt_bq;
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
	public TextArea xx;
	@FXML
	public TextArea fxx;
	@FXML
	private Label tsxx;
	static Socket client =null;//建立与服务器的连接
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;

	public void setzh(String zh,String nic){
		this.zh=zh;
		this.nic=nic;
	}
	public void start(){
		s1=new Stage();
		Parent root=null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/群聊.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		xx.setEditable(false);
		try{
//			client = new Socket("192.168.43.1", 3000);//建立与服务器的连接
			client = new Socket("localhost", 3000);//建立与服务器的连接
			PrintStream ps = new PrintStream(client.getOutputStream());
			ps.println(nic);
			new ClientReceive(client).start();
		}catch (Exception e) {
			System.out.println("8 群聊");
		}

	}

	class ClientReceive extends Thread {
		private Socket socket;

		public ClientReceive(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				// 按行接收信息
				String line = "";
				while ((line = br.readLine()) != null) {
					xx.appendText(line+"\r\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void send1(KeyEvent k){
		if(k.getCode()==KeyCode.ENTER)
		fa(this.client);
	}
	public void send(MouseEvent me){
		fa(this.client);
	}
	public void fa(Socket s){
		String mes=fxx.getText();//得到消息
		if(mes.length()!=0)
			try {
				PrintStream ps = new PrintStream(s.getOutputStream());
				ps.println(mes);//发送消息
				System.out.println(mes);
				xx.appendText("我:"+"\r\n"+mes+"\r\n");//将消息显示在界面上
				fxx.setText("");
			} catch (Exception e) {
				System.out.println("发送消息错误");
			}
		else{
			tsxx.setOpacity(1);//消息为空
			new Thread(()->{
				try {
					Thread.sleep(2000);//错误消息显示2秒后消失
					tsxx.setOpacity(0);
				} catch (InterruptedException e) {
					System.out.println("控制8 188");
				}
			}).start();
		}
	}
	public void qx(MouseEvent me){
		s1.close();
		try {
			client.close();
		} catch (IOException e) {
			System.out.println("8 关闭");
		}
	}
	public void gb(MouseEvent me){
		s1.close();
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
}
