//%注册消息
package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class 控制3注册  implements Initializable{
	public static Stage s1=null;
	public  Socket s=null;
	public  DataInputStream in=null;
	public  DataOutputStream out=null;
	@FXML
	private TextField nic;
	@FXML
	private TextField sjh;
	@FXML
	private TextField mima;
	@FXML
	private TextField qrmima;
	@FXML
	private Label cwts;
	@FXML
	private Label cwts1;
	@FXML
	private Label cwts2;
	@FXML
	private Label tk;
	@FXML
	private RadioButton nbt;
	@FXML
	private RadioButton vbt;
	@FXML
	private ImageView im;
	@FXML
	private MenuButton gl;
	@FXML
	private MenuButton nian;
	@FXML
	private MenuButton yue;
	@FXML
	private MenuButton ri;
	@FXML
	private MenuButton gji;
	@FXML
	private MenuButton sf;
	@FXML
	private MenuButton city;
	@FXML
	private CheckBox tytk;
	@FXML
	private ListView<String> list;
	public Thread t1=null;
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	public 控制3注册(){
		try{
//		this.s=new Socket("192.168.43.63", 8080);//连接服务器端
		this.s=new Socket("localhost", 8080);
		this.out=new DataOutputStream(s.getOutputStream());
		this.in=new DataInputStream(s.getInputStream());//初始化输入输出流
		s1=new Stage();
		}catch(Exception e){
			System.out.println("构造函数");
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}
	public void ljzc(MouseEvent me){
		
		boolean lf=true;
		if(nic.getText().equals("")){//如果密码或者昵称为空显示错误提示
			cwts.setOpacity(1);
			lf=false;
		}
		else
			cwts.setOpacity(0);
		if(mima.getText().equals("")){
			cwts1.setOpacity(1);
			lf=false;
		}
		else
			cwts1.setOpacity(0);
		if(!mima.getText().equals(qrmima.getText())){
			cwts2.setOpacity(1);
			lf=false;
		}
		else
			cwts2.setOpacity(0);
		if(tytk.isSelected()==false){//看是否选择同意条款
			tk.setOpacity(1);
			lf=false;
		}
		else
			tk.setOpacity(0);
		if(lf){
			try{
				
				String sb=new String();
				if(nbt.isScaleShape())
					sb="男";
				else
					sb="女";
				String bir=null;//设置点击事件，将每个选项都对应显示到菜单中
				if(nian.getText().matches("年")||yue.getText().matches("月")||ri.getText().matches("日"))
					bir="''";
				else
					bir=nian.getText()+yue.getText()+ri.getText();
				String ad=null;
				if(gji.getText().matches("国籍")||sf.getText().matches("省份")||city.getText().matches("城市"))
					ad="''";
				else
					ad=gji.getText()+sf.getText()+city.getText();
				out.writeUTF("%"+nic.getText()+" "+sb+" "+mima.getText()+" "+bir+" "+ad+" "+sjh.getText()+" "+s.getLocalAddress().toString().split("/")[1]);//发送注册信息
				System.out.println();
			}catch(Exception e){
				System.out.println("服务器未开启");
			}
			控制11显示账号 xs=new 控制11显示账号();//显示得到的账号
			t1=new Thread(() ->{
					try {
						String line=null;
						if((line=in.readUTF())!=null){
							if(line.matches("%(\\S)+")){
								String zhanghao=line.split("%")[1];//得到账号
								xs.setzh(zhanghao);
								xs.setzh1();
								xs.start();
								in.close();
								out.close();
								s.close();
								s1.close();//断开连接
							}
						}
					}catch (Exception e) {
						System.out.println("here");
					}
			});
			t1.start();
		}
	}
	public void start()  {
		try {
			Parent root =FXMLLoader.load(getClass().getResource("/application/注册账号.fxml"));
			Scene scene=new Scene(root);
			s1.setScene(scene);
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
	public void xinn(MouseEvent me){
		vbt.setSelected(false);
	}
	public void xinv(MouseEvent me){
		nbt.setSelected(false);
	}
	public void gl(ActionEvent me){
		gl.setText("公历");
	}
	public void guanbi(MouseEvent me){//关闭这个窗口的连接
		try{
		s1.close();
		this.in.close();
		this.out.close();
		this.s.close();
		}catch (Exception e) {
			System.out.println("3 关闭");
		}
	}
	public void nian1(ActionEvent me){//设置点击事件，让点击的年份可以显示在菜单框中
		nian.setText("1996年");
	}
	public void nian2(ActionEvent me){
		nian.setText("1997年");
	}
	public void nian3(ActionEvent me){
		nian.setText("1998年");
	}
	public void nian4(ActionEvent me){
		nian.setText("1999年");
	}
	public void nian5(ActionEvent me){
		nian.setText("2000年");
	}
	public void yue1(ActionEvent me){
		yue.setText("1月");
	}
	public void yue2(ActionEvent me){
		yue.setText("2月");
	}
	public void yue3(ActionEvent me){
		yue.setText("3月");
	}
	public void yue4(ActionEvent me){
		yue.setText("4月");
	}
	public void yue5(ActionEvent me){
		yue.setText("5月");
	}
	public void yue6(ActionEvent me){
		yue.setText("6月");
	}public void yue7(ActionEvent me){
		yue.setText("7月");
	}
	public void yue8(ActionEvent me){
		yue.setText("8月");
	}
	public void yue9(ActionEvent me){
		yue.setText("9月");
	}
	public void yue10(ActionEvent me){
		yue.setText("10月");
	}
	public void yue11(ActionEvent me){
		yue.setText("11月");
	}
	public void yue12(ActionEvent me){
		yue.setText("12月");
	}
	public void r1(ActionEvent ae){
		ri.setText("1号");
	}
	public void r2(ActionEvent ae){
		ri.setText("2号");
	}
	public void r3(ActionEvent ae){
		ri.setText("3号");
	}
	public void r4(ActionEvent ae){
		ri.setText("4号");
	}
	public void r5(ActionEvent ae){
		ri.setText("5号");
	}
	public void r6(ActionEvent ae){
		ri.setText("6号");
	}
	public void r7(ActionEvent ae){
		ri.setText("7号");
	}
	public void r8(ActionEvent ae){
		ri.setText("8号");
	}
	public void r9(ActionEvent ae){
		ri.setText("9号");
	}
	public void r10(ActionEvent ae){
		ri.setText("10号");
	}
	public void r11(ActionEvent ae){
		ri.setText("11号");
	}
	public void r12(ActionEvent ae){
		ri.setText("12号");
	}
	public void r13(ActionEvent ae){
		ri.setText("13号");
	}
	public void r14(ActionEvent ae){
		ri.setText("14号");
	}
	public void r15(ActionEvent ae){
		ri.setText("15号");
	}
	public void gji(ActionEvent ae){
		gji.setText("中国");
	}
	public void sf(ActionEvent ae){
		sf.setText("河南");
	}
	public void city(ActionEvent ae){
		city.setText("洛阳");
	}
}
