//#Ԥ����׶���Ϣ
//!��¼��֤��Ϣ
//%ע���˺���Ϣ
//E�����ػ���Ϣ
//����Ӻ���
//~������Ϣ
//$��¼��֤��Ϣ
//X�޸���Ϣ��Ϣ
//GX����״̬
//���������������������������������������� ������Ϣ��������Ϣ  ����-����������������

package application;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ����2������ extends Application implements Initializable{
	public static Stage primaryStage=null;
	static ServerSocket ss=null;
	Socket s=null;
	public static ��ʼ�� csh=null;
	static int i=1;//��¼��¼�����ߵ�����
	@FXML
	private TextArea xx;
	@FXML
	private Button bt1;
	@FXML
	private Button bt2;
	@FXML
	private Button bt3;
	@FXML
	private Button bt4;
	@FXML
	private Button bt5;
	@FXML
	private Button bt6;
	@FXML
	private Label la;
	public static String message=null;
	String ip=null;

	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		csh=new ��ʼ��();
		xx.setEditable(false);
		//		------------------------------  �����߳�ʵ�ַ������ͻ���   --------------------------
		new Thread(()->{
			try{
				ss=new ServerSocket(8080);
				System.out.println("�˿ڴ�");
				while(true){
					s=ss.accept();
					System.out.println("�յ�������Ϣ");
					clxx x=new clxx(s);
					new Thread(x).start();
				}
			}catch (Exception e) {
			}
		}).start();
	}
	//�����̶߳�ÿ���Ự����
	class clxx implements Runnable{
		Socket s=null;//ÿ���Ự��socket
		DataInputStream in=null;
		DataOutputStream out=null;
		private ��ʼ�� csh1=null;
		public clxx(Socket s) {
			super();
			this.s = s;
			csh1=new ��ʼ��();
		}
		@Override
		public void run() {
			try{			
				in=new DataInputStream(s.getInputStream());
				out=new DataOutputStream(s.getOutputStream());

				while(true){
					message=in.readUTF();//�յ���Ϣ
					System.out.println(message);
					xx.appendText("�յ�"+message+"\r\n");
					//�������������������������������� ����#��Ϣ ��������----���������� �� ����������������
					if(message.matches("#.*")){//��Ϣ��Ԥ������Ϣ
						String[] str=message.split("[# ]");
						if(str.length==3){
							String zh=message.split("[# ]")[1];
							ip=message.split("[# ]")[2];
							csh1.rs=csh1.st.executeQuery("select �ϴε�¼ip,���� from ��¼ where �˺�= '"+zh+"'" );
							if(csh1.rs.next()){
								if(ip.equals(csh1.rs.getString(1))){
									out.writeUTF("#"+csh1.rs.getString(2));
									xx.appendText("����"+"#"+csh1.rs.getString(2)+"\r\n");
								}
								else{
									out.writeUTF("#");//����ȶ�����ip��ַ����ͬ���ͷ��Ϳյ�Ԥ������Ϣ
									xx.appendText("����"+"#"+"\r\n");
								}
							}
							else{
								out.writeUTF("#");//����ڱ����Ҳ����˺žͷ���#
								xx.appendText("����"+"#"+"\r\n");
							}
							//��ʾ��Ϣ����ָ����Դ
						}
						else if(str.length==2){
							ip =message.split("#")[1];
							out.writeUTF("#");//ָ����Ϣ����Դ
							xx.appendText("����"+"#"+"\r\n");
						}
					}
					//�������������������������������� ����#��Ϣ ��������----���������� �� ����������������
					if(message.matches("GX.*")){
						String zh=message.split("GX")[1];
						csh1.rs=csh1.st.executeQuery("select ״̬ from ��¼ where �˺� = '"+zh+"'");
						System.out.println("select ״̬ from ��¼ where �˺� = '"+zh+"'");
						csh1.rs.next();
						if(csh1.rs.getString(1).matches("����")){
							out.writeUTF("GXyes");
							xx.appendText("����GXyes\r\n");
						}
						else{
							out.writeUTF("GXno");
							xx.appendText("����GXno\r\n");
						}
					}
					//--------------����!��Ϣ -----------------------------------------------------------------------------------------------------��������������������������������������������
					System.out.println("s1");
					if(message.matches("C.*")){
						csh1.st.execute("update  ��¼ set ״̬= '����'  where �˺�= '"+message.split("[C ]")[1]+"'");
						csh1.st.execute("update  ��¼ set �ϴε�¼ip= '"+ip+"' where �˺�= '"+message.split("[C ]")[1]+"'");//ip��ַ��Ϊ��ǰʹ�õ�ip
						csh1.st.execute("update  ��Ϣ set  ip= '"+message.split("[C ]")[2]+"' where �˺�= '"+message.split("[C ]")[1]+"'");//ip��ַ��Ϊ��ǰʹ�õ�ip
						System.out.println("update  ��Ϣ set  ip= '"+message.split("[C ]")[2]+"' where �˺�= '"+message.split("[C ]")[1]+"'");
					}
					if(message.matches("!.*")){//����յ���֤�ɹ�����Ϣ���ͽ��˺Ŷ�Ӧ��״̬�޸�Ϊ������"
						String zh=message.split("[! ]")[1];
						String mm=message.split("[! ]")[2];//����յ�������֤��Ϣ���ͽ��˺źͶ�Ӧ������ȡ�������бȽ�
						csh1.rs=csh1.st.executeQuery("select ����,״̬ from ��¼ where �˺�='"+zh+"'" );

						if(csh1.rs.next()){
							if(!csh1.rs.getString(2).equals("����")){
								out.writeUTF("!delete");//���״̬Ϊ���߾ͷ���delete��Ϣ
								xx.appendText("����"+"$delete"+"\r\n");
							}
							else	if(csh1.rs.getString(1).matches(mm)){
								csh1.rs=csh1.st.executeQuery("select �ǳ�,ͷ�� from ��Ϣ where �˺� ='"+zh+"'");
								csh1.rs.next();
								out.writeUTF("!yes"+" "+csh1.rs.getString(1)+" "+csh1.rs.getString(2));//��֤�ɹ��ͷ���yes
								xx.appendText("����"+"!yes"+" "+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+"\r\n");
							}
							else{
								out.writeUTF("!no");//������no
								xx.appendText("����"+"!no"+"\r\n");
							}
						}
						else{
							out.writeUTF("!refuse");//��¼����û�ж�Ӧ���˺žͷ���refuse
							xx.appendText("!refuse"+"\r\n");
						}
					}
					System.out.println("s2");
					//������������ ����%��Ϣ ������������������������
					if (message.matches("%.*")){
						String zh=null;
						csh1.rs=csh1.st.executeQuery("select * from ����");
						if(csh1.rs.next())
							zh=csh1.rs.getString(1);
						Integer y=new Integer(zh);
						y++;
						csh1.st.execute("update ���� set δ�����˺� ="+y.toString());//���˺�ָ����һλ
						String[] str=message.split("[% ]");
						System.out.println("insert into ��Ϣ values('"+zh+"','','"+str[3]+"','"
								+str[7]+"','"+str[1]+"','"+str[2]+"','"+str[4]+"','"+str[5]+"','"+str[6]+"','0','')");
						csh1.st.execute("insert into ��Ϣ values('"+zh+"','','"+str[3]+"','"
								+str[7]+"','"+str[1]+"','"+str[2]+"','"+str[4]+"','"+str[5]+"','"+str[6]+"','0','')");//��������Ϣ���뵽��Ϣ����
						csh1.st.execute("insert into ��¼ values('"+zh+"','"+str[3]+"','"+str[7]+"','','����')");//���µ�¼�б���Ϣ
						out.writeUTF("%"+zh);//�������
						xx.appendText("����"+"%"+zh+"\r\n");
					}
					System.out.println("s3");
					//�������������������������� �� �� �� ������Ϣ �� �� ����----������������ ���� ������������
					if(message.matches("��.*")){//�յ�������Ϣ
						String mdzh=message.split("��")[1];
						csh1.rs=csh1.st.executeQuery("select ip,�ǳ�,ͷ��,�Ա�,��������,�ֻ�����,����ǩ�� from ��Ϣ where �˺� = '"+mdzh+"'");
						csh1.rs.next();
						out.writeUTF("��"+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+" "
								+csh1.rs.getString(3)+" "+csh1.rs.getString(4)+" "+csh1.rs.getString(5)+" "+csh1.rs.getString(6)+" "+csh1.rs.getString(7));
						xx.appendText("����"+"��"+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+" "+csh1.rs.getString(3)
						+" "+csh1.rs.getString(4)+" "+csh1.rs.getString(5)+" "+csh1.rs.getString(6)+" "+csh1.rs.getString(7)+"\r\n");
					}
					System.out.println("s4");
					//�������������������������� �� �� �� ����~��Ϣ �� �� ����----����������������������������
					if(message.matches("~.*")){//�������ip��ַ��Ϣ
						String mdzh=message.split("[~]")[1];
						csh1.rs=csh1.st.executeQuery("select ip,�ǳ� from ��Ϣ where �˺� = '"+mdzh+"'");
						csh1.rs.next();
						out.writeUTF("~"+csh1.rs.getString(1)+" "+csh1.rs.getString(2));
						xx.appendText("����"+"~"+csh1.rs.getString(1)+" "+csh1.rs.getString(2)+"\r\n");
					}
					System.out.println("s5");
					//�������������������������������� ����E��Ϣ ��������----����������������������������
					if(message.matches("X.*")){
						xx.appendText("�û�"+message.split("X")[1]+"�޸�������"+"\r\n");
					}
					//�������������������������������� ����E��Ϣ ��������----����������������������������
					if(message.matches("E.*")){//����������Ϣ
						in.close();
						out.close();
						s.close();
					}
					//�������������������������������� ����T��Ϣ ��������----����������������������������
					if(message.matches("T")){//�������ߺ�����Ϣ
						StringBuilder sb=new StringBuilder();
						csh1.rs=csh1.st.executeQuery("select �˺� from ��¼ where ״̬ = '����' ");
						while(csh1.rs.next()){
							sb.append(csh1.rs.getString(1)+" ");
						}
						out.writeUTF(sb.toString());
						xx.appendText("����"+sb.toString()+"\r\n");
						in.close();
						out.close();
						s.close();
					}
				}
			}catch (Exception e) {
				System.out.println("�뿪�Ự����ִ���");//���û�״̬��Ϊ����
				if(message.matches("E.*")){//�����⵽����˺����Ӳ�����
					try {
						csh1.st.execute("update  ��¼ set ״̬= '����'  where �˺�= '"+message.split("E")[1]+"'");
					} catch (SQLException e1) {
					}
					//�㲥����˺��Ѿ�����
				}
			}
		}
	}

	public void ebt1(MouseEvent me){//���ý�����˳�����ʱ����Ч
		bt1.setOpacity(0.8);
	}
	public void ebt2(MouseEvent me){
		bt2.setOpacity(0.8);
	}
	public void ebt3(MouseEvent me){
		bt3.setOpacity(0.8);
	}
	public void ebt4(MouseEvent me){
		bt4.setOpacity(0.8);
	}
	public void ebt5(MouseEvent me){
		bt5.setOpacity(0.8);
	}
	public void ebt6(MouseEvent me){
		bt6.setOpacity(0.8);
	}
	public void xbt1(MouseEvent me){
		bt1.setOpacity(0);
	}
	public void xbt2(MouseEvent me){
		bt2.setOpacity(0);
	}
	public void xbt3(MouseEvent me){
		bt3.setOpacity(0);
	}
	public void xbt4(MouseEvent me){
		bt4.setOpacity(0);
	}
	public void xbt5(MouseEvent me){
		bt5.setOpacity(0);
	}
	public void xbt6(MouseEvent me){
		bt6.setOpacity(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {//��ʼ������ʾ����
		try {
			primaryStage=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/����������.fxml"));
			primaryStage.setTitle("��¼");
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.DECORATED);
			
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
