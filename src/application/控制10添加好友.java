package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class 控制10添加好友 implements Initializable{
	public static Stage s1=null;
	public static String zh0;
	static int fl=0;
	@FXML
	private Button zh1;
	@FXML
	private Button zh2;
	@FXML
	private Button zh3;
	@FXML
	private TextField zh;
	Socket s=null;
	DataInputStream in=null;
	DataOutputStream out=null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
			s=new Socket("localhost", 8080);
			System.out.println(s.getInetAddress().toString());
			out=new DataOutputStream(s.getOutputStream());
			in=new DataInputStream(s.getInputStream());
			out.writeUTF("T");
			String[] s=in.readUTF().split(" ");
			switch(s.length){
			case 3:zh3.setText(s[2]);
			case 2:zh2.setText(s[1]);
			case 1:zh1.setText(s[0]);
			}
		}catch (Exception e) {
			System.out.println("10初始化");
		}
	}
	public void start(){
		s1=new Stage();
		Parent root=null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/添加好友.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene s=new Scene(root);
		s1.initStyle(StageStyle.TRANSPARENT);
		s1.setScene(s);
		s1.show();
	}

	public void gb(MouseEvent me){
		try{
			s1.close();
			in.close();
			out.close();
			s.close();
		}catch (Exception e) {
			System.out.println("10 关闭");
		}
	}


	public void qd(MouseEvent me){
		zh0=new String(zh.getText());
		fl=1;
		System.out.println("okokok1");
		try{
			s1.close();
			in.close();
			out.close();
			s.close();
		}catch (Exception e) {
			System.out.println("10 关闭");
		}
	}

	public void eb1(MouseEvent me){
		zh1.setOpacity(0.6);
	}
	public void exb1(MouseEvent me){
		zh1.setOpacity(0);
	}
	public void eb2(MouseEvent me){
		zh2.setOpacity(0.6);
	}
	public void exb2(MouseEvent me){
		zh2.setOpacity(0);
	}
	public void eb3(MouseEvent me){
		zh3.setOpacity(0.6);
	}
	public void exb3(MouseEvent me){
		zh3.setOpacity(0);
	}
}
