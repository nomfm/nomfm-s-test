//X�޸�����



package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ����9�޸����� implements Initializable{
	��ʼ�� c=null;
	public String zh=null;
	@FXML
	private ImageView xgzl_zxh;
	@FXML
	private TextArea nic;
	@FXML
	private MenuButton xb;
	@FXML
	private TextArea sr;
	@FXML
	private TextArea gx;
	@FXML
	private TextArea sjh;
	@FXML
	private TextArea gxqm;
	@FXML
	private ImageView xgzl_gb;
	public static Stage s1=null;
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	Socket s=null;
	DataInputStream in=null;
	DataOutputStream out=null;//�����������
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		c=new ��ʼ��();
		try{
			s=new Socket("localhost", 8080);
			//			s=new Socket("192.168.43.63", 8080);
			System.out.println(s.getInetAddress().toString());
			out=new DataOutputStream(s.getOutputStream());
			in=new DataInputStream(s.getInputStream());//��ʼ�����������
		}catch (Exception e) {
			System.out.println("����9����");
		}
	}
	public void qd(MouseEvent me){
		try {
			out.writeUTF("X"+zh);//�޸ķ�����
			c.st.execute("update  ��Ϣ set �ǳ� = '"+nic.getText()+"' where �˺� = '"+zh+"'");//�޸��˺Ŷ�Ӧ���ǳ�
			c.st.execute("update  ��Ϣ set ����ǩ�� = '"+gxqm.getText()+"' where �˺� = '"+zh+"'");//�޸��˺Ŷ�Ӧ���ǳ�
			c.st.execute("update  ��Ϣ set �ֻ����� = '"+sjh.getText()+"' where �˺� = '"+zh+"'");//�޸��˺Ŷ�Ӧ���ǳ�
			out.writeUTF("E");//��֪�������ر�����
			in.close();
			out.close();
			s.close();
			s1.close();
		} catch (Exception e) {
			System.out.println("�޸����ϳ���");
		}
	}
	public void setzh(String z){
		this.zh=z;
	}
	public void start(){
		s1=new Stage();
		Parent root=null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/�޸�����.fxml"));
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
		xgzl_zxh.setOpacity(0.4);
	}
	public void zxhqcyy(MouseEvent me){
		xgzl_zxh.setOpacity(0);
	}
	public void gbyy(MouseEvent me){
		xgzl_gb.setOpacity(0.4);
	}
	public void gbqcyy(MouseEvent me){
		xgzl_gb.setOpacity(0);
	}
}
