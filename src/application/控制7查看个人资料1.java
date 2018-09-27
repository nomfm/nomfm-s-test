package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class 控制7查看个人资料1 implements Initializable{
	Socket s=null;//与服务器相连
	DataInputStream in=null;
	DataOutputStream out=null;//与服务器连接的in、out
	public static String zh1=null;
	@FXML
	private ImageView ckgrzl_zxh;
	@FXML
	private Label nic;
	@FXML
	private Label sjh;
	@FXML
	private Label zh;
	@FXML
	private Label xinxi;
	@FXML
	private ImageView ckgrzl_gb;
	public static Stage s1=null;
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
//			s=new Socket("192.168.43.63", 8080);
			s=new Socket("localhost", 8080);
			out=new DataOutputStream(s.getOutputStream());
			in=new DataInputStream(s.getInputStream());
			out.writeUTF("￥"+zh1);//向服务器信息请求消息，得到好友的信息
			String mes=in.readUTF();
			nic.setText(mes.split("[￥ ]")[2]);
			Integer i=new Integer(mes.split("[￥ ]")[5].split("年")[0]);
			Integer i1=new Integer(2017-i.intValue());
			xinxi.setText(mes.split("[￥ ]")[4]+"  "+i1.toString());
			sjh.setText(mes.split("[￥ ]")[6]);
		}catch (Exception e) {
			System.out.println("主界面初始化");
		}
	}
	
	public  控制7查看个人资料1(String zh){
		this.zh1=zh;
	}
	
	public void start(){
		
		s1=new Stage();
		Parent root=null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/查看个人资料1.fxml"));
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
	public void gb(MouseEvent me){
		try{
		in.close();
		out.close();
		s.close();
		s1.close();
		}catch (Exception e) {
			System.out.println("7 关闭还有错");
		}
	}
	public void zxhyy(MouseEvent me){
		ckgrzl_zxh.setOpacity(0.4);
	}
	public void zxhqcyy(MouseEvent me){
		ckgrzl_zxh.setOpacity(0);
	}
	public void gbyy(MouseEvent me){
		ckgrzl_gb.setOpacity(0.4);
	}
	public void gbqcyy(MouseEvent me){
		ckgrzl_gb.setOpacity(0);
	}
}
