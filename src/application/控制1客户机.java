//#Ԥ����׶���Ϣ 3 2
//!��¼��֤��Ϣ 3 2
//


//      -------------------------------------------------------��ȫ��֤��¼��ע���˺š�˵���˻���ǰ״̬------------------------------------------------- 
package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ����1�ͻ��� extends Application implements Initializable {
	public static Stage primaryStage=null;
	newControl5MainScene m5=null;
	 Socket s=null;
	DataInputStream in=null;
	DataOutputStream out=null;//�����������������
	public String mima=null;
	public int a=0;
	public Thread t1=null;
	public Thread t2=null;//��¼�̵߳�����
	boolean fl=false;//��ʶ�Ƿ��Զ���¼
	boolean fl1=true;//��ʶ�Ƿ�ѡ������
	@FXML
	private TextField tf;
	@FXML
	private Label cuts;
	@FXML
	private PasswordField pwf;
	@FXML
	private CheckBox jzmm;
	@FXML
	private CheckBox zddl;
	int dlcs;
	int md=0;
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		��ʼ�� k=new ��ʼ��();//����׼��
		//��txt��ȡ���˺ź��Ƿ��Զ���¼��Ϣ�ͼ�ס�������Ϣ�����˺��������
		//����Ȳ���ס����Ҳ���Զ���¼����������һ��
		//���ѡ��������һ����ip ���˺ŷ�����������ע����ʱ�ı�ʶ��Ϣ��#��Ȼ������������뷢������ѡ�Զ���¼������Ӧ�¼�
		//-----------------------------------------------------------------	Ԥ����׶� -----------------------------------------------	
		try{
//			s=new Socket("192.168.43.63", 8080);
			s=new Socket("localhost", 8080);
			System.out.println(s.getInetAddress().toString());
			out=new DataOutputStream(s.getOutputStream());
			in=new DataInputStream(s.getInputStream());
			//ѡ���¼��������һ������
			k.rs=k.st.executeQuery("select * from �ͻ��� order by �ͻ���.��½���� desc");
				if(fl1=k.rs.next()){//���Ƿ��и���ı���
					tf.setText(k.rs.getString(1));//���˺���Ϊ�ӿ�����ѡ���ı���
					dlcs=k.rs.getInt(4);
					if(k.rs.getString(3).matches("��")){//���ѡ���˼�ס����������Ϣ
						out.writeUTF("#"+k.rs.getString(1)+" "+s.getLocalAddress().toString().split("/")[1]);//��ip��ַ������������Ȼ����������Ƿ�����Զ���¼
						if(k.rs.getString(2).matches("��"))//�����ڼ�ס����Ļ���֮�ϣ����Ƿ��Զ���¼
							fl=true;
					}
					else
						out.writeUTF("#"+s.getLocalAddress().toString().split("/")[1]);//���û�б��������������ͷ���#
				}
				else{//û���ҵ��κα������#+ip���õ�½����Ϊ0
					dlcs=0;
					out.writeUTF("#"+s.getLocalAddress().toString().split("/")[1]);//
					System.out.println("#"+s.getLocalAddress().toString().split("/")[1]);
					System.out.println("aaaa");
				}
		}catch(Exception e){
			System.out.println("������δ����");
		}
		//	-----------------  ����һ���߳̽�����Ϣ �������������������� ���� ����������������������������������������
		t1=new Thread(() ->{
			try {
				String line=null;
				while((line=in.readUTF())!=null){
					//�ȴ����ܷ���������Ϣ,ע��������յ���Ϣ�󣬽���ip��ַ�ıȶԣ���ͬ����#+���룬���򷵻�#
					System.out.println(line);
					if(line.matches("#(\\S)+")){
						mima=line.split("#")[1];//���յ�����Ϣ����mima�������ǿյ�
						pwf.setText(mima);
						a=1;
					}
					else if(line.matches("#")){//����յ���Ϣ���ͽ���־a��Ϊ1
						mima="";a=1;
						pwf.setText(mima);
					}
					//�������������������������������������� �� ��β�׶� �� ���������������������������������� 
					else if(line.matches("!yes.*")){//�յ����Է���������Ϣ����֤�ɹ�.�������������˺Ŷ�Ӧ�ĺ����б���֪��������������ˡ�
						System.out.println(fl1);
						try {
							k.rs=k.st.executeQuery("select * from �ͻ��� where �˺� = '"+tf.getText()+"'");
							fl1=k.rs.next();
							if(!fl1){//���û���ҵ��κεı���������в��뵱ǰ��һ����Ϣ
								String z="��";
								String m="��";
								if(zddl.isSelected())
									z="��";
								if(jzmm.isSelected())
									m="��";
								k.st.execute("insert into �ͻ��� values('"+tf.getText()+"','"+z+"','"+m+"',1,'"+line.split("[! ]")[1]+"','"+"')");
							}
							else{//����оͽ���¼��dlcs��һȻ��д�����ݿ�
								dlcs++;
								String z="��";
								String m="��";
								if(zddl.isSelected())
									z="��";
								if(jzmm.isSelected())
									m="��";
								k.st.execute("update �ͻ��� set ��½���� = "+dlcs+" where �˺� ='"+tf.getText()+"'");
								k.st.execute("update �ͻ��� set �Ƿ��Զ���¼ = '"+z+"'  where �˺� ='"+tf.getText()+"'");
								k.st.execute("update �ͻ��� set �Ƿ��ס���� = '"+m+"'  where �˺� ='"+tf.getText()+"'");
							}
						} catch (SQLException e) {
							System.out.println("���ݿ����");
						}
						out.writeUTF("C"+tf.getText()+" "+s.getLocalAddress().toString().split("/")[1]);//��֤���Լ�һ�й����ɹ������߷���������״̬
//						out.writeUTF("E");//�ر�����
						md=1;
					}
					else if(line.matches("!no")){//no��ʾ��¼�˺Ż��������
						cuts.setOpacity(1);
						new Thread(() ->{
							try {
								Thread.sleep(1500);
								cuts.setOpacity(0);
							} catch (InterruptedException e) {
								e.printStackTrace();//���һ���߳�ʹ��������ʾ�Ĵ�����ʾ��2����Զ���ʧ
							}
						}).start();//�����߳�
						md=2;
					}
					else if(line.matches("!refuse")){//refuse��־��û��ע��
						cuts.setText("�㻹û��ע��");
						cuts.setOpacity(1);//������;
						new Thread(() ->{
							try {
								Thread.sleep(1500);
								cuts.setOpacity(0);
								cuts.setText("�������������˺�����");
							} catch (InterruptedException e) {
								e.printStackTrace();//���һ���߳�ʹ��������ʾ�Ĵ�����ʾ��2����Զ���ʧ
							}
						}).start();//�����߳�
						md=2;
					}
					else if(line.matches("!delete")){//delete��־���ظ���¼
						cuts.setText("���Ѿ��ڱ𴦵�¼");
						cuts.setOpacity(1);//������;
						new Thread(() ->{
							try {
								Thread.sleep(1500);
								cuts.setOpacity(0);
								cuts.setText("�������������˺�����");
							} catch (InterruptedException e) {
								e.printStackTrace();//���һ���߳�ʹ��������ʾ�Ĵ�����ʾ��2����Զ���ʧ
							}
						}).start();//�����߳�
						md=2;
					}
				}
			} catch (IOException e) {
				System.out.println("�������Ͽ�");
			}
		});
		t1.start();
		t2=new Thread(() ->{//дһ���߳̿��Ƿ��յ��˷������Ļ�Ӧ
			if(a!=1)
				t2.yield();
			pwf.setText(mima);
			if(fl){
				deng();//ר���¼��֤�׶�
			}
		});
		t2.start();
	}
	public void zhuce(MouseEvent me){//��������ע�ᣬ�������¼�
		����3ע�� zc=new ����3ע��();
		try {
			zc.start();
		} catch (Exception e) {
			System.out.println("����");
		}
	}
	//--------------------------------------------------------- ��¼��֤�׶� -----------------------------------------------------
	public void deng(){
		String �û���=tf.getText();
		String ����=pwf.getText();
		if(�û���.equals("")||����.equals("")){//�����ж��������˺ſ��Ƿ���һ��Ϊ�գ���һ��Ϊ�տ϶�����
			cuts.setOpacity(1);//��ʾ����
			Thread t3=new Thread(() ->{
				try {
					Thread.sleep(1500);
					cuts.setOpacity(0);
				} catch (InterruptedException e) {
					e.printStackTrace();//���һ���߳�ʹ��������ʾ�Ĵ�����ʾ��2����Զ���ʧ
				}
			});//�����߳�
			t3.start();
			System.out.println(t3.getName());
		}
		else{//��������������֤�׶�
			try{
				StringBuilder sb1=new StringBuilder();
				sb1.append("!");//��Ϣ��ʶΪ$
				sb1.append(�û���);
				sb1.append(" ");//�ո����
				sb1.append(����);
				//��ʾ���ڵ�½����
				out.writeUTF(sb1.toString());//д��������
				while(md==0)
				{
					Thread.yield();//�����߳���������
				}
				if(md==1){
					in.close();
					out.close();
					s.close();
					primaryStage.hide();//����ǰҳ������
					m5=new newControl5MainScene();
					m5.set(tf.getText());//���˺Ŵ����ȥ
					m5.start();
				}
			}catch (Exception e) {
				System.out.println("������æ");
			}
		}
	}

	public void dl(MouseEvent me){
		deng();
	}
	public void dl1(KeyEvent ke){
		if(ke.getCode()==KeyCode.ENTER)
			deng();
	}
	public void guanbi(MouseEvent me){
		System.exit(0);
	}
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage pryStage) throws Exception {//��ʼ������
		try {
			primaryStage=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/��¼����.fxml"));
			primaryStage.setTitle("��¼");
			Scene scene=new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			
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
