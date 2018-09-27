//%ע����Ϣ
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

public class ����3ע��  implements Initializable{
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
	
	public ����3ע��(){
		try{
//		this.s=new Socket("192.168.43.63", 8080);//���ӷ�������
		this.s=new Socket("localhost", 8080);
		this.out=new DataOutputStream(s.getOutputStream());
		this.in=new DataInputStream(s.getInputStream());//��ʼ�����������
		s1=new Stage();
		}catch(Exception e){
			System.out.println("���캯��");
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}
	public void ljzc(MouseEvent me){
		
		boolean lf=true;
		if(nic.getText().equals("")){//�����������ǳ�Ϊ����ʾ������ʾ
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
		if(tytk.isSelected()==false){//���Ƿ�ѡ��ͬ������
			tk.setOpacity(1);
			lf=false;
		}
		else
			tk.setOpacity(0);
		if(lf){
			try{
				
				String sb=new String();
				if(nbt.isScaleShape())
					sb="��";
				else
					sb="Ů";
				String bir=null;//���õ���¼�����ÿ��ѡ���Ӧ��ʾ���˵���
				if(nian.getText().matches("��")||yue.getText().matches("��")||ri.getText().matches("��"))
					bir="''";
				else
					bir=nian.getText()+yue.getText()+ri.getText();
				String ad=null;
				if(gji.getText().matches("����")||sf.getText().matches("ʡ��")||city.getText().matches("����"))
					ad="''";
				else
					ad=gji.getText()+sf.getText()+city.getText();
				out.writeUTF("%"+nic.getText()+" "+sb+" "+mima.getText()+" "+bir+" "+ad+" "+sjh.getText()+" "+s.getLocalAddress().toString().split("/")[1]);//����ע����Ϣ
				System.out.println();
			}catch(Exception e){
				System.out.println("������δ����");
			}
			����11��ʾ�˺� xs=new ����11��ʾ�˺�();//��ʾ�õ����˺�
			t1=new Thread(() ->{
					try {
						String line=null;
						if((line=in.readUTF())!=null){
							if(line.matches("%(\\S)+")){
								String zhanghao=line.split("%")[1];//�õ��˺�
								xs.setzh(zhanghao);
								xs.setzh1();
								xs.start();
								in.close();
								out.close();
								s.close();
								s1.close();//�Ͽ�����
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
			Parent root =FXMLLoader.load(getClass().getResource("/application/ע���˺�.fxml"));
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
		gl.setText("����");
	}
	public void guanbi(MouseEvent me){//�ر�������ڵ�����
		try{
		s1.close();
		this.in.close();
		this.out.close();
		this.s.close();
		}catch (Exception e) {
			System.out.println("3 �ر�");
		}
	}
	public void nian1(ActionEvent me){//���õ���¼����õ������ݿ�����ʾ�ڲ˵�����
		nian.setText("1996��");
	}
	public void nian2(ActionEvent me){
		nian.setText("1997��");
	}
	public void nian3(ActionEvent me){
		nian.setText("1998��");
	}
	public void nian4(ActionEvent me){
		nian.setText("1999��");
	}
	public void nian5(ActionEvent me){
		nian.setText("2000��");
	}
	public void yue1(ActionEvent me){
		yue.setText("1��");
	}
	public void yue2(ActionEvent me){
		yue.setText("2��");
	}
	public void yue3(ActionEvent me){
		yue.setText("3��");
	}
	public void yue4(ActionEvent me){
		yue.setText("4��");
	}
	public void yue5(ActionEvent me){
		yue.setText("5��");
	}
	public void yue6(ActionEvent me){
		yue.setText("6��");
	}public void yue7(ActionEvent me){
		yue.setText("7��");
	}
	public void yue8(ActionEvent me){
		yue.setText("8��");
	}
	public void yue9(ActionEvent me){
		yue.setText("9��");
	}
	public void yue10(ActionEvent me){
		yue.setText("10��");
	}
	public void yue11(ActionEvent me){
		yue.setText("11��");
	}
	public void yue12(ActionEvent me){
		yue.setText("12��");
	}
	public void r1(ActionEvent ae){
		ri.setText("1��");
	}
	public void r2(ActionEvent ae){
		ri.setText("2��");
	}
	public void r3(ActionEvent ae){
		ri.setText("3��");
	}
	public void r4(ActionEvent ae){
		ri.setText("4��");
	}
	public void r5(ActionEvent ae){
		ri.setText("5��");
	}
	public void r6(ActionEvent ae){
		ri.setText("6��");
	}
	public void r7(ActionEvent ae){
		ri.setText("7��");
	}
	public void r8(ActionEvent ae){
		ri.setText("8��");
	}
	public void r9(ActionEvent ae){
		ri.setText("9��");
	}
	public void r10(ActionEvent ae){
		ri.setText("10��");
	}
	public void r11(ActionEvent ae){
		ri.setText("11��");
	}
	public void r12(ActionEvent ae){
		ri.setText("12��");
	}
	public void r13(ActionEvent ae){
		ri.setText("13��");
	}
	public void r14(ActionEvent ae){
		ri.setText("14��");
	}
	public void r15(ActionEvent ae){
		ri.setText("15��");
	}
	public void gji(ActionEvent ae){
		gji.setText("�й�");
	}
	public void sf(ActionEvent ae){
		sf.setText("����");
	}
	public void city(ActionEvent ae){
		city.setText("����");
	}
}
