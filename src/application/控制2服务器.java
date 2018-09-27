//#预处理阶段消息
//!登录验证消息
//%注册账号消息
//E结束回话消息
//￥添加好友
//~单播消息
//$登录验证消息
//X修改信息消息
//GX更新状态
//———————————————————— 接受消息、处理消息  ——-————————

package application;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class 控制2服务器 extends Application implements Initializable{
	public static Stage primaryStage=null;
	static ServerSocket ss=null;
	Socket s=null;
	public static 初始化 csh=null;
	static int i=1;//记录登录的在线的数量
	@FXML
	private TextArea xx;
	@FXML
	private Button bt1;
	@FXML
	private Button bt2;
	@FXML
	private Button bt3;
	@FXML
	private Button bt4;
	@FXML
	private Button bt5;
	@FXML
	private Button bt6;
	@FXML
	private Label la;
	public static String message=null;
	String ip=null;

	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		csh=new 初始化();
		xx.setEditable(false);
		//		------------------------------  创建线程实现服务多个客户机   --------------------------
		new Thread(()->{
			try{
				ss=new ServerSocket(8080);
				System.out.println("端口打开");
				while(true){
					s=ss.accept();
					System.out.println("收到连接消息");
					clxx x=new clxx(s);
					new Thread(x).start();
				}
			}catch (Exception e) {
			}
		}).start();
	}
	//创建线程对每个会话处理
	class clxx implements Runnable{
		Socket s=null;//每个会话的socket
		DataInputStream in=null;
		DataOutputStream out=null;
		private 初始化 csh1=null;
		public clxx(Socket s) {
			super();
			this.s = s;
			csh1=new 初始化();
		}
		@Override
		public void run() {
			try{			
				in=new DataInputStream(s.getInputStream());
				out=new DataOutputStream(s.getOutputStream());

				while(true){
					message=in.readUTF();//收到消息
					System.out.println(message);
					xx.appendText("收到"+message+"\r\n");
					//———————————————— 处理#消息 ————----————— — ————————
					if(message.matches("#.*")){//消息是预处理消息
						String[] str=message.split("[# ]");
						if(str.length==3){
							String zh=message.split("[# ]")[1];
							ip=message.split("[# ]")[2];
							csh1.rs=csh1.st.executeQuery("select 上次登录ip,密码 from 登录 where 账号= '"+zh+"'" );
							if(csh1.rs.next()){
								if(ip.equals(csh1.rs.getString(1))){
									out.writeUTF("#"+csh1.rs.getString(2));
									xx.appendText("发送"+"#"+csh1.rs.getString(2)+"\r\n");
								}
								else{
									out.writeUTF("#");//如果比对两次ip地址不相同，就发送空的预处理消息
									xx.appendText("发送"+"#"+"\r\n");
								}
							}
							else{
								out.writeUTF("#");//如果在本地找不到账号就发送#
								xx.appendText("发送"+"#"+"\r\n");
							}
							//显示消息，并指明来源
						}
						else if(str.length==2){
							ip =message.split("#")[1];
							out.writeUTF("#");//指明消息的来源
							xx.appendText("发送"+"#"+"\r\n");
						}
					}
					//———————————————— 处理#消息 ————----————— — ————————
					if(message.matches("GX.*")){
						String zh=message.split("GX")[1];
						csh1.rs=csh1.st.executeQuery("select 状态 from 登录 where 账号 = '"+zh+"'");
						System.out.println("select 状态 from 登录 where 账号 = '"+zh+"'");
						csh1.rs.next();
						if(csh1.rs.getString(1).matches("在线")){
							out.writeUTF("GXyes");
							xx.appendText("发送GXyes\r\n");
						}
						else{
							out.writeUTF("GXno");
							xx.appendText("发送GXno\r\n");
						}
					}
					//--------------处理!消息 -----------------------------------------------------------------------------------------------------——————————————————————
					System.out.println("s1");
					if(message.matches("C.*")){
						csh1.st.execute("update  登录 set 状态= '在线'  where 账号= '"+message.split("[C ]")[1]+"'");
						csh1.st.execute("update  登录 set 上次登录ip= '"+ip+"' where 账号= '"+message.split("[C ]")[1]+"'");//ip地址改为当前使用的ip
						csh1.st.execute("update  信息 set  ip= '"+message.split("[C ]")[2]+"' where 账号= '"+message.split("[C ]")[1]+"'");//ip地址改为当前使用的ip
						System.out.println("update  信息 set  ip= '"+message.split("[C ]")[2]+"' where 账号= '"+message.split("[C ]")[1]+"'");
					}
					if(message.matches("!.*")){//如果收到验证成功的消息，就将账号对应的状态修改为“在线"
						String zh=message.split("[! ]")[1];
						String mm=message.split("[! ]")[2];//如果收到的是验证消息，就将账号和对应的密码取出来进行比较
						csh1.rs=csh1.st.executeQuery("select 密码,状态 from 登录 where 账号='"+zh+"'" );

						if(csh1.rs.next()){
							if(!csh1.rs.getString(2).equals("离线")){
								out.writeUTF("!delete");//如果状态为离线就发送delete消息
								xx.appendText("发送"+"$delete"+"\r\n");
							}
							else	if(csh1.rs.getString(1).matches(mm)){
								csh1.rs=csh1.st.executeQuery("select 昵称,头像 from 信息 where 账号 ='"+zh+"'");
								csh1.rs.next();
								out.writeUTF("!yes"+" "+csh1.rs.getString(1)+" "+csh1.rs.getString(2));//验证成功就发送yes
								xx.appendText("发送"+"!yes"+" "+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+"\r\n");
							}
							else{
								out.writeUTF("!no");//否则发送no
								xx.appendText("发送"+"!no"+"\r\n");
							}
						}
						else{
							out.writeUTF("!refuse");//登录表中没有对应的账号就发送refuse
							xx.appendText("!refuse"+"\r\n");
						}
					}
					System.out.println("s2");
					//—————— 处理%消息 ————————————
					if (message.matches("%.*")){
						String zh=null;
						csh1.rs=csh1.st.executeQuery("select * from 申请");
						if(csh1.rs.next())
							zh=csh1.rs.getString(1);
						Integer y=new Integer(zh);
						y++;
						csh1.st.execute("update 申请 set 未分配账号 ="+y.toString());//将账号指向下一位
						String[] str=message.split("[% ]");
						System.out.println("insert into 信息 values('"+zh+"','','"+str[3]+"','"
								+str[7]+"','"+str[1]+"','"+str[2]+"','"+str[4]+"','"+str[5]+"','"+str[6]+"','0','')");
						csh1.st.execute("insert into 信息 values('"+zh+"','','"+str[3]+"','"
								+str[7]+"','"+str[1]+"','"+str[2]+"','"+str[4]+"','"+str[5]+"','"+str[6]+"','0','')");//将个人信息加入到信息表中
						csh1.st.execute("insert into 登录 values('"+zh+"','"+str[3]+"','"+str[7]+"','','离线')");//更新登录列表信息
						out.writeUTF("%"+zh);//处理完毕
						xx.appendText("发送"+"%"+zh+"\r\n");
					}
					System.out.println("s3");
					//————————————— — — — 处理￥消息 — — ——----—————— —— ——————
					if(message.matches("￥.*")){//收到请求消息
						String mdzh=message.split("￥")[1];
						csh1.rs=csh1.st.executeQuery("select ip,昵称,头像,性别,出生日期,手机号码,个性签名 from 信息 where 账号 = '"+mdzh+"'");
						csh1.rs.next();
						out.writeUTF("￥"+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+" "
								+csh1.rs.getString(3)+" "+csh1.rs.getString(4)+" "+csh1.rs.getString(5)+" "+csh1.rs.getString(6)+" "+csh1.rs.getString(7));
						xx.appendText("发送"+"￥"+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+" "+csh1.rs.getString(3)
						+" "+csh1.rs.getString(4)+" "+csh1.rs.getString(5)+" "+csh1.rs.getString(6)+" "+csh1.rs.getString(7)+"\r\n");
					}
					System.out.println("s4");
					//————————————— — — — 处理~消息 — — ——----——————————————
					if(message.matches("~.*")){//请求好友ip地址消息
						String mdzh=message.split("[~]")[1];
						csh1.rs=csh1.st.executeQuery("select ip,昵称 from 信息 where 账号 = '"+mdzh+"'");
						csh1.rs.next();
						out.writeUTF("~"+csh1.rs.getString(1)+" "+csh1.rs.getString(2));
						xx.appendText("发送"+"~"+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+"\r\n");
					}
					System.out.println("s5");
					//———————————————— 处理E消息 ————----——————————————
					if(message.matches("X.*")){
						xx.appendText("用户"+message.split("X")[1]+"修改了资料"+"\r\n");
					}
					//———————————————— 处理E消息 ————----——————————————
					if(message.matches("E.*")){//请求离线消息
						in.close();
						out.close();
						s.close();
					}
					//———————————————— 处理T消息 ————----——————————————
					if(message.matches("T")){//请求在线好友消息
						StringBuilder sb=new StringBuilder();
						csh1.rs=csh1.st.executeQuery("select 账号 from 登录 where 状态 = '在线' ");
						while(csh1.rs.next()){
							sb.append(csh1.rs.getString(1)+" ");
						}
						out.writeUTF(sb.toString());
						xx.appendText("发送"+sb.toString()+"\r\n");
						in.close();
						out.close();
						s.close();
					}
				}
			}catch (Exception e) {
				System.out.println("离开会话或出现错误");//将用户状态改为离线
				if(message.matches("E.*")){//如果检测到这个账号连接不存在
					try {
						csh1.st.execute("update  登录 set 状态= '离线'  where 账号= '"+message.split("E")[1]+"'");
					} catch (SQLException e1) {
					}
					//广播这个账号已经下线
				}
			}
		}
	}

	public void ebt1(MouseEvent me){//设置进入或退出按键时的特效
		bt1.setOpacity(0.8);
	}
	public void ebt2(MouseEvent me){
		bt2.setOpacity(0.8);
	}
	public void ebt3(MouseEvent me){
		bt3.setOpacity(0.8);
	}
	public void ebt4(MouseEvent me){
		bt4.setOpacity(0.8);
	}
	public void ebt5(MouseEvent me){
		bt5.setOpacity(0.8);
	}
	public void ebt6(MouseEvent me){
		bt6.setOpacity(0.8);
	}
	public void xbt1(MouseEvent me){
		bt1.setOpacity(0);
	}
	public void xbt2(MouseEvent me){
		bt2.setOpacity(0);
	}
	public void xbt3(MouseEvent me){
		bt3.setOpacity(0);
	}
	public void xbt4(MouseEvent me){
		bt4.setOpacity(0);
	}
	public void xbt5(MouseEvent me){
		bt5.setOpacity(0);
	}
	public void xbt6(MouseEvent me){
		bt6.setOpacity(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {//初始化并显示界面
		try {
			primaryStage=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/服务器界面.fxml"));
			primaryStage.setTitle("登录");
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.DECORATED);
			
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
		    	 @Override public void handle(javafx.scene.input.MouseEvent  m) {
		    		 primaryStage.setX(x_stage + m.getScreenX() - x1);
		    		 primaryStage.setY(y_stage + m.getScreenY() - y1);
		        }	               	                	              
		       });
		     scene.setOnDragEntered(null);
		     scene.setOnMousePressed(new EventHandler<MouseEvent>() {
		        @Override public void handle(javafx.scene.input.MouseEvent m) {
		        x1 =m.getScreenX();
		        y1 =m.getScreenY();
		        x_stage = primaryStage.getX();
		        y_stage = primaryStage.getY();
		          }	               
		       });
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
