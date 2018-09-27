//#预处理阶段消息 3 2
//!登录验证消息 3 2
//


//      -------------------------------------------------------安全验证登录、注册账号、说明账户当前状态------------------------------------------------- 
package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class 控制1客户机 extends Application implements Initializable {
	public static Stage primaryStage=null;
	newControl5MainScene m5=null;
	 Socket s=null;
	DataInputStream in=null;
	DataOutputStream out=null;//保存与服务器的连接
	public String mima=null;
	public int a=0;
	public Thread t1=null;
	public Thread t2=null;//记录线程的名字
	boolean fl=false;//标识是否自动登录
	boolean fl1=true;//标识是否选出表项
	@FXML
	private TextField tf;
	@FXML
	private Label cuts;
	@FXML
	private PasswordField pwf;
	@FXML
	private CheckBox jzmm;
	@FXML
	private CheckBox zddl;
	int dlcs;
	int md=0;
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		初始化 k=new 初始化();//连库准备
		//从txt里取出账号和是否自动登录信息和记住密码的信息，将账号填入框中
		//如果既不记住密码也不自动登录，就跳过这一步
		//如果选择了任意一个则将ip 和账号发给服务器，注意这时的标识消息是#。然后服务器将密码发回来，选自动登录出发相应事件
		//-----------------------------------------------------------------	预处理阶段 -----------------------------------------------	
		try{
//			s=new Socket("192.168.43.63", 8080);
			s=new Socket("localhost", 8080);
			System.out.println(s.getInetAddress().toString());
			out=new DataOutputStream(s.getOutputStream());
			in=new DataInputStream(s.getInputStream());
			//选择登录次数最多的一条表项
			k.rs=k.st.executeQuery("select * from 客户机 order by 客户机.登陆次数 desc");
				if(fl1=k.rs.next()){//看是否还有更多的表项
					tf.setText(k.rs.getString(1));//将账号置为从库中挑选出的表项
					dlcs=k.rs.getInt(4);
					if(k.rs.getString(3).matches("是")){//如果选择了记住密码则发送信息
						out.writeUTF("#"+k.rs.getString(1)+" "+s.getLocalAddress().toString().split("/")[1]);//将ip地址交给服务器，然后服务器看是否可以自动登录
						if(k.rs.getString(2).matches("是"))//建立在记住密码的基础之上，看是否自动登录
							fl=true;
					}
					else
						out.writeUTF("#"+s.getLocalAddress().toString().split("/")[1]);//如果没有表象满足条件，就发送#
				}
				else{//没有找到任何表项，发送#+ip。置登陆次数为0
					dlcs=0;
					out.writeUTF("#"+s.getLocalAddress().toString().split("/")[1]);//
					System.out.println("#"+s.getLocalAddress().toString().split("/")[1]);
					System.out.println("aaaa");
				}
		}catch(Exception e){
			System.out.println("服务器未开启");
		}
		//	-----------------  创建一个线程接收消息 —————————— —— ————————————————————
		t1=new Thread(() ->{
			try {
				String line=null;
				while((line=in.readUTF())!=null){
					//等待接受服务器的消息,注意服务器收到消息后，进行ip地址的比对，相同返回#+密码，否则返回#
					System.out.println(line);
					if(line.matches("#(\\S)+")){
						mima=line.split("#")[1];//将收到的消息填入mima，即便是空的
						pwf.setText(mima);
						a=1;
					}
					else if(line.matches("#")){//如果收到消息，就将标志a置为1
						mima="";a=1;
						pwf.setText(mima);
					}
					//——————————————————— — 收尾阶段 — ————————————————— 
					else if(line.matches("!yes.*")){//收到来自服务器的消息，验证成功.服务器获得这个账号对应的好友列表，告知他们这个人上线了。
						System.out.println(fl1);
						try {
							k.rs=k.st.executeQuery("select * from 客户机 where 账号 = '"+tf.getText()+"'");
							fl1=k.rs.next();
							if(!fl1){//如果没有找到任何的表项，就在其中插入当前的一条信息
								String z="否";
								String m="否";
								if(zddl.isSelected())
									z="是";
								if(jzmm.isSelected())
									m="是";
								k.st.execute("insert into 客户机 values('"+tf.getText()+"','"+z+"','"+m+"',1,'"+line.split("[! ]")[1]+"','"+"')");
							}
							else{//如果有就将记录的dlcs加一然后写入数据库
								dlcs++;
								String z="否";
								String m="否";
								if(zddl.isSelected())
									z="是";
								if(jzmm.isSelected())
									m="是";
								k.st.execute("update 客户机 set 登陆次数 = "+dlcs+" where 账号 ='"+tf.getText()+"'");
								k.st.execute("update 客户机 set 是否自动登录 = '"+z+"'  where 账号 ='"+tf.getText()+"'");
								k.st.execute("update 客户机 set 是否记住密码 = '"+m+"'  where 账号 ='"+tf.getText()+"'");
							}
						} catch (SQLException e) {
							System.out.println("数据库错误");
						}
						out.writeUTF("C"+tf.getText()+" "+s.getLocalAddress().toString().split("/")[1]);//验证，以及一切工作成功，告诉服务器更新状态
//						out.writeUTF("E");//关闭连接
						md=1;
					}
					else if(line.matches("!no")){//no表示登录账号或密码错误
						cuts.setOpacity(1);
						new Thread(() ->{
							try {
								Thread.sleep(1500);
								cuts.setOpacity(0);
							} catch (InterruptedException e) {
								e.printStackTrace();//设计一个线程使得我们显示的错误提示过2秒后自动消失
							}
						}).start();//启动线程
						md=2;
					}
					else if(line.matches("!refuse")){//refuse标志着没有注册
						cuts.setText("你还没有注册");
						cuts.setOpacity(1);//主界面;
						new Thread(() ->{
							try {
								Thread.sleep(1500);
								cuts.setOpacity(0);
								cuts.setText("你输入的密码或账号有误");
							} catch (InterruptedException e) {
								e.printStackTrace();//设计一个线程使得我们显示的错误提示过2秒后自动消失
							}
						}).start();//启动线程
						md=2;
					}
					else if(line.matches("!delete")){//delete标志着重复登录
						cuts.setText("你已经在别处登录");
						cuts.setOpacity(1);//主界面;
						new Thread(() ->{
							try {
								Thread.sleep(1500);
								cuts.setOpacity(0);
								cuts.setText("你输入的密码或账号有误");
							} catch (InterruptedException e) {
								e.printStackTrace();//设计一个线程使得我们显示的错误提示过2秒后自动消失
							}
						}).start();//启动线程
						md=2;
					}
				}
			} catch (IOException e) {
				System.out.println("服务器断开");
			}
		});
		t1.start();
		t2=new Thread(() ->{//写一个线程看是否收到了服务器的回应
			if(a!=1)
				t2.yield();
			pwf.setText(mima);
			if(fl){
				deng();//专向登录验证阶段
			}
		});
		t2.start();
	}
	public void zhuce(MouseEvent me){//如果点击了注册，出发此事件
		控制3注册 zc=new 控制3注册();
		try {
			zc.start();
		} catch (Exception e) {
			System.out.println("这里");
		}
	}
	//--------------------------------------------------------- 登录验证阶段 -----------------------------------------------------
	public void deng(){
		String 用户名=tf.getText();
		String 密码=pwf.getText();
		if(用户名.equals("")||密码.equals("")){//首先判断密码框和账号框是否有一个为空，有一个为空肯定不对
			cuts.setOpacity(1);//显示错误
			Thread t3=new Thread(() ->{
				try {
					Thread.sleep(1500);
					cuts.setOpacity(0);
				} catch (InterruptedException e) {
					e.printStackTrace();//设计一个线程使得我们显示的错误提示过2秒后自动消失
				}
			});//启动线程
			t3.start();
			System.out.println(t3.getName());
		}
		else{//否则进入正真的验证阶段
			try{
				StringBuilder sb1=new StringBuilder();
				sb1.append("!");//消息标识为$
				sb1.append(用户名);
				sb1.append(" ");//空格隔开
				sb1.append(密码);
				//显示正在登陆界面
				out.writeUTF(sb1.toString());//写给服务器
				while(md==0)
				{
					Thread.yield();//由主线程启动界面
				}
				if(md==1){
					in.close();
					out.close();
					s.close();
					primaryStage.hide();//将当前页面隐藏
					m5=new newControl5MainScene();
					m5.set(tf.getText());//将账号传输过去
					m5.start();
				}
			}catch (Exception e) {
				System.out.println("服务器忙");
			}
		}
	}

	public void dl(MouseEvent me){
		deng();
	}
	public void dl1(KeyEvent ke){
		if(ke.getCode()==KeyCode.ENTER)
			deng();
	}
	public void guanbi(MouseEvent me){
		System.exit(0);
	}
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage pryStage) throws Exception {//初始化界面
		try {
			primaryStage=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/登录界面.fxml"));
			primaryStage.setTitle("登录");
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			
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
