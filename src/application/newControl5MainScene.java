//���������ѯ����Ϣ
//������ѷ�����Ӻ�����Ϣ


package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class newControl5MainScene implements Initializable{
	public static Stage s1=null;
	String xinxi=null;
	String sjh=null;
	@FXML
	public ImageView tx1;
	@FXML
	private ImageView tx2;
	@FXML
	private ImageView tx3;
	@FXML
	private ImageView xzpf;
	@FXML
	private ImageView zjm_d;
	@FXML
	private ImageView zjm_pf;
	@FXML
	private ImageView sp;
	@FXML
	private ImageView hh;
	@FXML
	private ImageView tx0;
	@FXML
	public ImageView tjxx;
	@FXML
	private ImageView zjm_zxh;
	@FXML
	private ImageView zjm_gb;
	@FXML
	private Label nc;
	@FXML
	public Label ts;
	@FXML
	private Label qm;
	@FXML
	private Label hyxx1;
	@FXML
	private Label hyqm1;
	@FXML
	private Label hyxx2;
	@FXML
	private Label hyqm2;
	@FXML
	private Label hyxx3;
	@FXML
	private Label hyqm3;
	@FXML
	public static String zh;
	@FXML
	private ImageView kj;
	@FXML
	private ImageView qqyx;
	ServerSocket ssf=null;//Ϊ���ܻỰ����
	Socket sf=null;//Ϊ���ܻỰ
	Socket s=null;//�����������
	DataInputStream in=null;
	DataOutputStream out=null;//����������ӵ�in��out
	Thread t1=null;
	public static ��ʼ�� csh=null;
	private String nic=null;
	private String toux=null;
	public static String h;
	static int bz1=0;//���ڱ�ʾ�Ӻŵ�����
	static String hyzh=null;
	static String hync=null;
	public static String mdzh="null";//����˺�
	static int num=0;
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try{
			out.writeUTF("��"+zh);//���������Ϣ������Ϣ
			String mes=in.readUTF();
			nic=mes.split("[�� ]")[2];
			nc.setText(mes.split("[�� ]")[2]);
			qm.setText(mes.split("[�� ]")[7]);
			tx0.setImage(new Image("ͷ��/"+mes.split("[�� ]")[3]+".jpg"));
			Integer i=new Integer(mes.split("[�� ]")[5].split("��")[0]);
			Integer i1=new Integer(2017-i.intValue());
			xinxi=mes.split("[�� ]")[4]+"  "+i1.toString();
			sjh=mes.split("[�� ]")[6];//������������Լ�����Ϣ������Ϣ��ʾ�ڽ�����
			csh=new ��ʼ��();
			csh.rs=csh.st.executeQuery("select * from ���� ");
			int mm=1;
			if(csh.rs.next()){//�ڱ��صĺ����б���Ѱ�Һ�����Ϣ��Ȼ����ʾ�ڽ�����
				tx1.setImage(new Image("ͷ��/"+csh.rs.getString(3)+".jpg"));
				hyxx1.setText(csh.rs.getString(2)+" "+csh.rs.getString(1));
				System.out.println(mm);
				mm++;
				tx1.setOpacity(1);
				hyxx1.setOpacity(1);
			}
			if(csh.rs.next()){
				tx2.setImage(new Image("ͷ��/"+csh.rs.getString(3)+".jpg"));
				hyxx2.setText(csh.rs.getString(2)+" "+csh.rs.getString(1));
				mm++;
				tx2.setOpacity(1);
				hyxx2.setOpacity(1);
			}
			if(csh.rs.next()){
				tx3.setImage(new Image("ͷ��/"+csh.rs.getString(3)+".jpg"));
				hyxx3.setText(csh.rs.getString(2)+" "+csh.rs.getString(1));
				mm++;
				tx3.setOpacity(1);
				hyxx3.setOpacity(1);
			}

		}catch (Exception e) {
			System.out.println("�������ʼ��");
		}

		new Thread(()->{//Ϊһ�Զ�������׼��
			try{
				ssf=new ServerSocket(9696);//����9696�˿�
				System.out.println("9696����");
			}catch(Exception e){
				System.out.println("�׽�������");
			}
			while(true){
				try {
					sf=ssf.accept();//һ�����ܵ������ͻ����������󣬾ͽ�����һ���̴߳�������Ự
					huihua h1=new huihua(sf);
					new Thread(h1).start();
				} catch (IOException e) {
					System.out.println("��������");
				}
			}
		}).start();
	}
	//�Ự�࣬ÿ����⵽һ����������ʹ���һ���Ự��Ự���𷽽������ӡ�
	class huihua implements Runnable{
		Socket s1=null;
		DataInputStream in2=null;
		DataOutputStream out2=null;//����ʱʹ�õ�in��out
		public huihua(Socket s1){
			try{
				this.s1=s1;
				this.in2=new DataInputStream(s1.getInputStream());
				this.out2=new DataOutputStream(s1.getOutputStream());//���ݴ����socket�������������
			}catch (Exception e) {
				System.out.println("�����Ự���ʼ������");
			}
		}
		@Override
		public void run() {
			try{
				String mess=in2.readUTF();
				if(mess.matches("~.*")){//������ܵ����ǻỰ��Ϣ
					ts.setOpacity(1);
					h=mess.split("~")[1];
					s1.close();
				}
				if(mess.matches("��.*")){
					System.out.println("�յ����������Ϣ");
					tjxx.setOpacity(0.6);
					bz1=1;//����־��Ϊ1
					hyzh=mess.split("[�� ]")[1];
					hync=mess.split("[�� ]")[2];
				}
			}catch (Exception e) {
				System.out.println("��������̴߳���");
			}

		}
	}
	//----���캯���������������������

	public void bz1(MouseEvent me){
		if(bz1==1){
			tjxx.setOpacity(0);
			����7�鿴��������1 k=new  ����7�鿴��������1(hyzh);
			k.start();
		}
		else
			tjhy();
	}

	public void fsp(MouseEvent em){
		��Ƶ spp=new ��Ƶ();
		spp.start();
	}

	public void tjhy(){
		����10��Ӻ��� tj=new ����10��Ӻ���();
		tj.start();
		new Thread(()-> {
			while(true){
				if(tj.fl==0)
					Thread.yield();
				else{
					System.out.println("��ʼ���");
					mdzh=new String(tj.zh0);
					System.out.println("��Ӻ��Ѵ���"+mdzh);
					try{
						out.writeUTF("��"+mdzh);//���������������������Ϣ��������Ϣ����2
					}catch(Exception e){
						System.out.println("��Ӻ���");
					}
				}
			}
		}).start();

		t1=new Thread(()->{
			tx1.setImage(new Image("ͷ��/2a.jpg"));
			String message=null;
			try {
				message=in.readUTF();//������Ϣ
				System.out.println("��Ӻ����յ���Ϣ"+message);
				if(message.matches("��.*")){
					String ip =message.split("[�� ]")[1];
					String nic=message.split("[�� ]")[2];
					String tou=message.split("[�� ]")[3];
					num++;
					switch(num){
					case 1:{
						tx1.setImage(new Image("ͷ��/"+tou+".jpg"));
						hyxx1.setText(nic+" "+mdzh);
						tx1.setOpacity(1);
						hyxx1.setOpacity(1);
						break;
					}
					case 2:{
						tx2.setImage(new Image("ͷ��/"+tou+".jpg"));
						hyxx2.setText(nic+" "+mdzh);
						tx2.setOpacity(1);
						hyxx2.setOpacity(1);
						break;
					}
					case 3:{
						tx3.setImage(new Image("ͷ��/"+tou+".jpg"));
						hyxx3.setText(nic+" "+mdzh);
						tx3.setOpacity(1);
						hyxx3.setOpacity(1);
						break;
					}
					}
					csh.st.execute("insert into ���� values('"+mdzh+"','"+nic+"','"+tou+"')");
					Socket sk=new Socket(ip, 9696);//���õ��ĺ���ip�����׽��֣���ͼ���ӵ�����
					DataOutputStream	out1=new DataOutputStream(s.getOutputStream());
					DataInputStream in1=new DataInputStream(s.getInputStream());
					csh.rs=csh.st.executeQuery("select �ǳ�,ͷ�� from �ͻ��� where �˺�= '"+zh+"'");
					csh.rs.next();
					out1.writeUTF("��"+zh+" "+csh.rs.getString(1)+" "+csh.rs.getString(2));//�����Լ����˺ź�������Ϣ��Ŀ�������������Ƿ�ͬ��
				}} catch (Exception e) {
				}
		});
		t1.start();

	}

	public void ckzl(MouseEvent me){
		����7�鿴�������� k=new ����7�鿴��������();
		k.set(nic,xinxi,sjh,zh,qm.getText());
		k.start();
	}

	public void gxhy(MouseEvent me){//����¼���ÿ�����ͼ����������ѯ�ʺ��ѵ�״̬��������߾͸��º��ѵ�ͷ��
		try{
			if(hyxx1.getText().length()!=0){
				out.writeUTF("GX"+hyxx1.getText().split(" ")[1]);
				String mes=in.readUTF();
				System.out.println("�����յ�"+mes);
				if(mes.split("GX")[1].matches("no")){
					tx1.setImage(new Image("ͷ��/6b.jpg"));
				}
			}
			if(hyxx2.getText().length()!=0){//������Ѳ����߾ͽ�����ͷ���Ϊ��ɫ��
				out.writeUTF("GX"+hyxx2.getText().split(" ")[1]);
				String mes=in.readUTF();
				if(mes.split("GX")[1].matches("no")){
					tx2.setImage(new Image("ͷ��/6b.jpg"));
				}
			}
			if(hyxx3.getText().length()!=0){
				out.writeUTF("GX"+hyxx3.getText().split(" ")[1]);
				String mes=in.readUTF();
				if(mes.split("GX")[1].matches("no")){
					tx3.setImage(new Image("ͷ��/6b.jpg"));
				}
			}
		}catch (Exception e) {
		}
	}

	public void lt1(MouseEvent ae){
		if(hyxx1.getText().length()!=0){
			String zh=	hyxx1.getText().split(" ")[1];
			liaotian(zh);
		}
	}

	public void lt2(MouseEvent me){
		if(hyxx2.getText().length()!=0){
			String zh=	hyxx2.getText().split(" ")[1];
			liaotian(zh);
		}
	}

	public void lt3(MouseEvent me){
		if(hyxx3.getText().length()!=0){
			String zh=	hyxx3.getText().split(" ")[1];
			liaotian(zh);
		}
	}

	public void liaotian(String zh){
		String mes=null;//�õ�����ip
		try{//�������ѯ�ʺ���IP��ַ��Ϣ����Ϊ��������ip��ַ�����µ�
			out.writeUTF("~"+zh);
			mes=in.readUTF();
		}catch (Exception e) {
			System.out.println("�����ʼ��");
		}
		����6������� k=new ����6�������();
		k.setip(mes.split("[~ ]")[1],mes.split("[~ ]")[2]);
		k.start();		
	}

	public void lll(MouseEvent me){
		ts.setOpacity(0);
		����6������� k6=new ����6�������();
		k6.setip2(h);
		k6.start();
	}

	public void qliao(MouseEvent me){
		����8Ⱥ�Ľ��� q=new ����8Ⱥ�Ľ���();
		q.setzh(zh, nic);//��ʼ��Ⱥ��
		q.start();
	}
	public void start()  {
		s1=new Stage();
		Parent root=null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/������.fxml"));
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
	public void set(String zh){
		this.zh=zh;
	}

	public newControl5MainScene(){
		try{
			//			s=new Socket("192.168.43.63", 8080);//�����������
			s=new Socket("localhost", 8080);//�����������
			out=new DataOutputStream(s.getOutputStream());
			in=new DataInputStream(s.getInputStream());
			System.err.println("���������ӳɹ�");
		}catch (Exception e) {
			System.out.println("���ӷ�����");
		}

	}

	public void dkkj(MouseEvent me) {
		//		// TODO Auto-generated method stub
		//		qq�ռ��������� s = new qq�ռ���������();
		//		s.start();
	}
	public void dkqqyx(MouseEvent me) {
		// TODO Auto-generated method stub
		Runtime r = Runtime.getRuntime();
		//�����̣߳���С����
		Process p=null;
		try {
			p=r.exec("D:\\OK\\�����java����\\С����\\smynesc.exe");//����·��
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("����Ϸʧ�ܣ�");
		}
	}

	public void dkkjbs(MouseEvent me){
		kj.setOpacity(0.6);
	}
	public void dkkjqs(MouseEvent me){
		kj.setOpacity(0);
	}
	public void qqyxbs(MouseEvent me){
		qqyx.setOpacity(0.6);
	}
	public void qqyxqs(MouseEvent me){
		qqyx.setOpacity(0);
	}

	public void gb(MouseEvent me){
		try {
			out.writeUTF("E"+this.zh);
		} catch (IOException e) {
			System.out.println("���ͽ����ػ���Ϣ");
		}
		System.exit(0);
	}
	public void forsp(MouseEvent me){
		sp.setOpacity(0.6);
	}
	public void forsp1(MouseEvent me){
		sp.setOpacity(0);
	}
	public void forhh(MouseEvent me){
		hh.setOpacity(0.6);
	}
	public void forhh1(MouseEvent me){
		hh.setOpacity(0);
	}
	public void pfyy(MouseEvent me){
		zjm_pf.setOpacity(0.6);
	}
	public void pfqcyy(MouseEvent me){
		zjm_pf.setOpacity(0);
	}
	public void zxhyy(MouseEvent me){
		zjm_zxh.setOpacity(0.6);
	}
	public void zxhqcyy(MouseEvent me){
		zjm_zxh.setOpacity(0);
	}
	public void gbyy(MouseEvent me){
		zjm_gb.setOpacity(0.6);
	}
	public void gbqcyy(MouseEvent me){
		zjm_gb.setOpacity(0);
	}

	public void ghpf(MouseEvent me){
		xzpf.setOpacity(1);

	}
	public void pf1(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("������/������Ƥ��1.jpg"));
	}
	public void pf2(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("������/������Ƥ��2.jpg"));
	}
	public void pf3(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("������/������Ƥ��3.jpg"));
	}
	public void pf4(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("������/������Ƥ��4.jpg"));
	}
	public void pf5(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("������/������Ƥ��6.jpg"));
	}
	public void pf6(MouseEvent me){
		xzpf.setOpacity(0);
		zjm_d.setImage(new Image("������/������Ƥ��5.jpg"));
	}
	public void tx1bd(MouseEvent me){
		tx1.setFitHeight(110);
		tx1.setFitWidth(125);
	}
	public void tx1hf(MouseEvent me){
		tx1.setFitHeight(98);
		tx1.setFitWidth(111);
	}
	public void tx2bd(MouseEvent me){
		tx2.setFitHeight(110);
		tx2.setFitWidth(125);
	}
	public void tx2hf(MouseEvent me){
		tx2.setFitHeight(98);
		tx2.setFitWidth(111);
	}
	public void tx3bd(MouseEvent me){
		tx3.setFitHeight(110);
		tx3.setFitWidth(125);
	}
	public void tx3hf(MouseEvent me){
		tx3.setFitHeight(98);
		tx3.setFitWidth(111);
	}
}
