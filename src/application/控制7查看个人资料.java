package application;

import java.io.IOException;
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

public class 控制7查看个人资料 implements Initializable{
private static 	String nic1=null;
private static 	String j=null;
private static String sjh1=null;
private static 	String zh1=null;
private static 	String qm1=null;
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
		// TODO Auto-generated method stub
		nic.setText(nic1);
		xinxi.setText(j);
		sjh.setText(sjh1);
		zh.setText(zh1);
	}
	public void set(String nic,String j,String sjh1,String zh1,String qm1){//根据传送过来的数据建立界面
		this.nic1=nic;
		this.j=j;
		this.sjh1=sjh1;
		this.zh1=zh1;
		this.qm1=qm1;
	}
	public void start(){
		
		s1=new Stage();
		Parent root=null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/查看个人资料.fxml"));
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
		s1.close();
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
	public void bjzl(MouseEvent me){
		控制9修改资料 d=new 控制9修改资料();
		d.setzh(zh1);
		d.start();
	}
}
