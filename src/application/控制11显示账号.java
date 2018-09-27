package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class øÿ÷∆11œ‘ æ’À∫≈ implements Initializable{
	public static Stage s1=null;
	@FXML
	private Button qd;
	@FXML
	private TextField zh;
	@FXML
	private ImageView gb;
	static String zh1;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	public void setzh(String zh){
		this.zh1=new String(zh);
	}
	public void start()  {
		try {
			s1=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/œ‘ æ’À∫≈.fxml"));
			Scene scene=new Scene(root);
			s1.setScene(scene);
			s1.initStyle(StageStyle.TRANSPARENT);
			s1.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setzh1(){
		zh.setText(zh1);
	}
	public void gb(MouseEvent me){
		s1.close();
	}
	public void eqd(MouseEvent me){
		qd.setOpacity(0.9);
	}
	public void xqd(MouseEvent me){
		qd.setOpacity(0.53);
	}
	public void egb(MouseEvent me){
		gb.setOpacity(0.8);
	}
	public void xgb(MouseEvent me){
		gb.setOpacity(0);
	}
	
}
