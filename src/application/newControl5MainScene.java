//￥向服务器询问信息
//￥向好友发送添加好友消息


package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class newControl5MainScene implements Initializable{
	public static Stage s1=null;
	String xinxi=null;
	String sjh=null;
	@FXML
	public ImageView tx1;
	@FXML
	private ImageView tx2;
	@FXML
	private ImageView tx3;
	@FXML
	private ImageView xzpf;
	@FXML
	private ImageView zjm_d;
	@FXML
	private ImageView zjm_pf;
	@FXML
	private ImageView sp;
	@FXML
	private ImageView hh;
	@FXML
	private ImageView tx0;
	@FXML
	public ImageView tjxx;
	@FXML
	private ImageView zjm_zxh;
	@FXML
	private ImageView zjm_gb;
	@FXML
	private Label nc;
	@FXML
	public Label ts;
	@FXML
	private Label qm;
	@FXML
	private Label hyxx1;
	@FXML
	private Label hyqm1;
	@FXML
	private Label hyxx2;
	@FXML
	private Label hyqm2;
	@FXML
	private Label hyxx3;
	@FXML
	private Label hyqm3;
	@FXML
	public static String zh;
	@FXML
	private ImageView kj;
	@FXML
	private ImageView qqyx;
	ServerSocket ssf=null;//为接受会话开启
	Socket sf=null;//为接受会话
	Socket s=null;//与服务器相连
	DataInputStream in=null;
	DataOutputStream out=null;//与服务器连接的in、out
	Thread t1=null;
	public static 初始化 csh=null;
	private String nic=null;
	private String toux=null;
	public static String h;
	static int bz1=0;//用于表示加号的意义
	static String hyzh=null;
	static String hync=null;
	public static String mdzh="null";//获得账号
	static int num=0;
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
			out.writeUTF("￥"+zh);//向服务器信息请求消息
			String mes=in.readUTF();
			nic=mes.split("[￥ ]")[2];
			nc.setText(mes.split("[￥ ]")[2]);
			qm.setText(mes.split("[￥ ]")[7]);
			tx0.setImage(new Image("头像/"+mes.split("[￥ ]")[3]+".jpg"));
			Integer i=new Integer(mes.split("[￥ ]")[5].split("年")[0]);
			Integer i1=new Integer(2017-i.intValue());
			xinxi=mes.split("[￥ ]")[4]+"  "+i1.toString();
			sjh=mes.split("[￥ ]")[6];//向服务器请求自己的信息并将信息显示在界面上
			csh=new 初始化();
			csh.rs=csh.st.executeQuery("select * from 好友 ");
			int mm=1;
			if(csh.rs.next()){//在本地的好友列表中寻找好友信息，然后显示在界面上
				tx1.setImage(new Image("头像/"+csh.rs.getString(3)+".jpg"));
				hyxx1.setText(csh.rs.getString(2)+" "+csh.rs.getString(1));
				System.out.println(mm);
				mm++;
				tx1.setOpacity(1);
				hyxx1.setOpacity(1);
			}
			if(csh.rs.next()){
				tx2.setImage(new Image("头像/"+csh.rs.getString(3)+".jpg"));
				hyxx2.setText(csh.rs.getString(2)+" "+csh.rs.getString(1));
				mm++;
				tx2.setOpacity(1);
				hyxx2.setOpacity(1);
			}
			if(csh.rs.next()){
				tx3.setImage(new Image("头像/"+csh.rs.getString(3)+".jpg"));
				hyxx3.setText(csh.rs.getString(2)+" "+csh.rs.getString(1));
				mm++;
				tx3.setOpacity(1);
				hyxx3.setOpacity(1);
			}

		}catch (Exception e) {
			System.out.println("主界面初始化");
		}

		new Thread(()->{//为一对多聊天做准备
			try{
				ssf=new ServerSocket(9696);//开放9696端口
				System.out.println("9696开启");
			}catch(Exception e){
				System.out.println("套接字问题");
			}
			while(true){
				try {
					sf=ssf.accept();//一旦接受到其他客户的连接请求，就将创建一个线程处理这个会话
					huihua h1=new huihua(sf);
					new Thread(h1).start();
				} catch (IOException e) {
					System.out.println("创建问题");
				}
			}
		}).start();
	}
	//会话类，每当检测到一个连接请求就创建一个会话与会话发起方建立连接。
	class huihua implements Runnable{
		Socket s1=null;
		DataInputStream in2=null;
		DataOutputStream out2=null;//聊天时使用的in、out
		public huihua(Socket s1){
			try{
				this.s1=s1;
				this.in2=new DataInputStream(s1.getInputStream());
				this.out2=new DataOutputStream(s1.getOutputStream());//根据传入的socket建立输入输出流
			}catch (Exception e) {
				System.out.println("创建会话类初始化出错");
			}
		}
		@Override
		public void run() {
			try{
				String mess=in2.readUTF();
				if(mess.matches("~.*")){//如果接受到的是会话消息
					ts.setOpacity(1);
					h=mess.split("~")[1];
					s1.close();
				}
				if(mess.matches("￥.*")){
					System.out.println("收到好友添加消息");
					tjxx.setOpacity(0.6);
					bz1=1;//将标志改为1
					hyzh=mess.split("[￥ ]")[1];
					hync=mess.split("[￥ ]")[2];
				}
			}catch (Exception e) {
				System.out.println("主界面的线程处理");
			}

		}
	}
	//----构造函数，创建与服务器的连接

	public void bz1(MouseEvent me){
		if(bz1==1){
			tjxx.setOpacity(0);
			控制7查看个人资料1 k=new  控制7查看个人资料1(hyzh);
			k.start();
		}
		else
			tjhy();
	}

	public void fsp(MouseEvent em){
		视频 spp=new 视频();
		spp.start();
	}

	public void tjhy(){
		控制10添加好友 tj=new 控制10添加好友();
		tj.start();
		new Thread(()-> {
			while(true){
				if(tj.fl==0)
					Thread.yield();
				else{
					System.out.println("开始添加");
					mdzh=new String(tj.zh0);
					System.out.println("添加好友触发"+mdzh);
					try{
						out.writeUTF("￥"+mdzh);//向服务器发出好友请求消息，请求消息长度2
					}catch(Exception e){
						System.out.println("添加好友");
					}
				}
			}
		}).start();

		t1=new Thread(()->{
			tx1.setImage(new Image("头像/2a.jpg"));
			String message=null;
			try {
				message=in.readUTF();//接受消息
				System.out.println("添加好友收到消息"+message);
				if(message.matches("￥.*")){
					String ip =message.split("[￥ ]")[1];
					String nic=message.split("[￥ ]")[2];
					String tou=message.split("[￥ ]")[3];
					num++;
					switch(num){
					case 1:{
						tx1.setImage(new Image("头像/"+tou+".jpg"));
						hyxx1.setText(nic+" "+mdzh);
						tx1.setOpacity(1);
						hyxx1.setOpacity(1);
						break;
					}
					case 2:{
						tx2.setImage(new Image("头像/"+tou+".jpg"));
						hyxx2.setText(nic+" "+mdzh);
						tx2.setOpacity(1);
						hyxx2.setOpacity(1);
						break;
					}
					case 3:{
						tx3.setImage(new Image("头像/"+tou+".jpg"));
						hyxx3.setText(nic+" "+mdzh);
						tx3.setOpacity(1);
						hyxx3.setOpacity(1);
						break;
					}
					}
					csh.st.execute("insert into 好友 values('"+mdzh+"','"+nic+"','"+tou+"')");
					Socket sk=new Socket(ip, 9696);//将得到的好友ip创建套接字，试图连接到好友
					DataOutputStream	out1=new DataOutputStream(s.getOutputStream());
					DataInputStream in1=new DataInputStream(s.getInputStream());
					csh.rs=csh.st.executeQuery("select 昵称,头像 from 客户机 where 账号= '"+zh+"'");
					csh.rs.next();
					out1.writeUTF("￥"+zh+" "+csh.rs.getString(1)+" "+csh.rs.getString(2));//发送自己的账号和其他信息给目标主机，看他是否同意
				}} catch (Exception e) {
				}
		});
		t1.start();

	}

	public void ckzl(MouseEvent me){
		控制7查看个人资料 k=new 控制7查看个人资料();
		k.set(nic,xinxi,sjh,zh,qm.getText());
		k.start();
	}

	public void gxhy(MouseEvent me){//鼠标事件，每当点击图标后，向服务器询问好友的状态，如果离线就跟新好友的头像
		try{
			if(hyxx1.getText().length()!=0){
				out.writeUTF("GX"+hyxx1.getText().split(" ")[1]);
				String mes=in.readUTF();
				System.out.println("更新收到"+mes);
				if(mes.split("GX")[1].matches("no")){
					tx1.setImage(new Image("头像/6b.jpg"));
				}
			}
			if(hyxx2.getText().length()!=0){//如果好友不在线就将好友头像改为灰色的
				out.writeUTF("GX"+hyxx2.getText().split(" ")[1]);
				String mes=in.readUTF();
				if(mes.split("GX")[1].matches("no")){
					tx2.setImage(new Image("头像/6b.jpg"));
				}
			}
			if(hyxx3.getText().length()!=0){
				out.writeUTF("GX"+hyxx3.getText().split(" ")[1]);
				String mes=in.readUTF();
				if(mes.split("GX")[1].matches("no")){
					tx3.setImage(new Image("头像/6b.jpg"));
				}
			}
		}catch (Exception e) {
		}
	}

	public void lt1(MouseEvent ae){
		if(hyxx1.getText().length()!=0){
			String zh=	hyxx1.getText().split(" ")[1];
			liaotian(zh);
		}
	}

	public void lt2(MouseEvent me){
		if(hyxx2.getText().length()!=0){
			String zh=	hyxx2.getText().split(" ")[1];
			liaotian(zh);
		}
	}

	public void lt3(MouseEvent me){
		if(hyxx3.getText().length()!=0){
			String zh=	hyxx3.getText().split(" ")[1];
			liaotian(zh);
		}
	}

	public void liaotian(String zh){
		String mes=null;//得到好友ip
		try{//向服务器询问好友IP地址信息，因为服务器的ip地址是最新的
			out.writeUTF("~"+zh);
			mes=in.readUTF();
		}catch (Exception e) {
			System.out.println("聊天初始化");
		}
		控制6聊天界面 k=new 控制6聊天界面();
		k.setip(mes.split("[~ ]")[1],mes.split("[~ ]")[2]);
		k.start();		
	}

	public void lll(MouseEvent me){
		ts.setOpacity(0);
		控制6聊天界面 k6=new 控制6聊天界面();
		k6.setip2(h);
		k6.start();
	}

	public void qliao(MouseEvent me){
		控制8群聊界面 q=new 控制8群聊界面();
		q.setzh(zh, nic);//初始化群聊
		q.start();
	}
	public void start()  {
		s1=new Stage();
		Parent root=null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/主界面.fxml"));
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
	public void set(String zh){
		this.zh=zh;
	}

	public newControl5MainScene(){
		try{
			//			s=new Socket("192.168.43.63", 8080);//与服务器相连
			s=new Socket("localhost", 8080);//与服务器相连
			out=new DataOutputStream(s.getOutputStream());
			in=new DataInputStream(s.getInputStream());
			System.err.println("服务器连接成功");
		}catch (Exception e) {
			System.out.println("连接服务器");
		}

	}

	public void dkkj(MouseEvent me) {
		//		// TODO Auto-generated method stub
		//		qq空间界面控制器 s = new qq空间界面控制器();
		//		s.start();
	}
	public void dkqqyx(MouseEvent me) {
		// TODO Auto-generated method stub
		Runtime r = Runtime.getRuntime();
		//创建线程，打开小霸王
		Process p=null;
		try {
			p=r.exec("D:\\OK\\正真的java课设\\小霸王\\smynesc.exe");//设置路径
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("打开游戏失败！");
		}
	}

	public void dkkjbs(MouseEvent me){
		kj.setOpacity(0.6);
	}
	public void dkkjqs(MouseEvent me){
		kj.setOpacity(0);
	}
	public void qqyxbs(MouseEvent me){
		qqyx.setOpacity(0.6);
	}
	public void qqyxqs(MouseEvent me){
		qqyx.setOpacity(0);
	}

	public void gb(MouseEvent me){
		try {
			out.writeUTF("E"+this.zh);
		} catch (IOException e) {
			System.out.println("发送结束回话消息");
		}
		System.exit(0);
	}
	public void forsp(MouseEvent me){
		sp.setOpacity(0.6);
	}
	public void forsp1(MouseEvent me){
		sp.setOpacity(0);
	}
	public void forhh(MouseEvent me){
		hh.setOpacity(0.6);
	}
	public void forhh1(MouseEvent me){
		hh.setOpacity(0);
	}
	public void pfyy(MouseEvent me){
		zjm_pf.setOpacity(0.6);
	}
	public void pfqcyy(MouseEvent me){
		zjm_pf.setOpacity(0);
	}
	public void zxhyy(MouseEvent me){
		zjm_zxh.setOpacity(0.6);
	}
	public void zxhqcyy(MouseEvent me){
		zjm_zxh.setOpacity(0);
	}
	public void gbyy(MouseEvent me){
		zjm_gb.setOpacity(0.6);
	}
	public void gbqcyy(MouseEvent me){
		zjm_gb.setOpacity(0);
	}

	public void ghpf(MouseEvent me){
		xzpf.setOpacity(1);

	}
	public void pf1(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("主界面/主界面皮肤1.jpg"));
	}
	public void pf2(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("主界面/主界面皮肤2.jpg"));
	}
	public void pf3(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("主界面/主界面皮肤3.jpg"));
	}
	public void pf4(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("主界面/主界面皮肤4.jpg"));
	}
	public void pf5(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("主界面/主界面皮肤6.jpg"));
	}
	public void pf6(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("主界面/主界面皮肤5.jpg"));
	}
	public void tx1bd(MouseEvent me){
		tx1.setFitHeight(110);
		tx1.setFitWidth(125);
	}
	public void tx1hf(MouseEvent me){
		tx1.setFitHeight(98);
		tx1.setFitWidth(111);
	}
	public void tx2bd(MouseEvent me){
		tx2.setFitHeight(110);
		tx2.setFitWidth(125);
	}
	public void tx2hf(MouseEvent me){
		tx2.setFitHeight(98);
		tx2.setFitWidth(111);
	}
	public void tx3bd(MouseEvent me){
		tx3.setFitHeight(110);
		tx3.setFitWidth(125);
	}
	public void tx3hf(MouseEvent me){
		tx3.setFitHeight(98);
		tx3.setFitWidth(111);
	}
}
