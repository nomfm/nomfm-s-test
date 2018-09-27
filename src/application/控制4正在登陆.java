package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class 控制4正在登陆 implements Initializable{
	public static Stage s1=null;
	public static int a=0;
	@FXML
	private ImageView zzdl_xl;
	@FXML
	private ImageView zzdl_zxh;
	@FXML
	private ImageView zzdl_gb;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	public void qx(MouseEvent me){
		a=1;
		s1.close();
	}
	public void gb(MouseEvent me){
		s1.close();
	}
	public void xlyy(MouseEvent me){
		zzdl_xl.setOpacity(0.6);
	}
	public void xlqcyy(MouseEvent me){
		zzdl_xl.setOpacity(0);
	}
	public void zxhyy(MouseEvent me){
		zzdl_zxh.setOpacity(0.6);
	}
	public void zxhqcyy(MouseEvent me){
		zzdl_zxh.setOpacity(0);
	}
	public void gbyy(MouseEvent me){
		zzdl_gb.setOpacity(0.6);
	}
	public void gbqcyy(MouseEvent me){
		zzdl_gb.setOpacity(0);
	}
	public void h(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s1.close();
	}
	public void start(){
		try {
			s1=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/正在登录.fxml"));
			s1.setTitle("登录");
			s1.setScene(new Scene(root));
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
