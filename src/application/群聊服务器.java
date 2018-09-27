package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Ⱥ�ķ����� extends Application implements Initializable {
	public static Stage primaryStage=null;
	static ServerSocket ss=null;
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	Socket s=null;
	@FXML
	public   TextArea xx;
	@FXML
	private Label qb;
	

	public static ��ʼ�� csh=null;

	public static void main(String[] args) {
		launch(args);

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		xx.setEditable(false);
		new Thread(()->{
			try{
				List<Socket> list = new ArrayList<>();
				// �����������˵��׽���
				System.out.println("�ռ�������׽��ִ�");
				ServerSocket server = new ServerSocket(3000);
				System.out.println("���ӳɹ�");
				while (true) {
					// ���տͻ��˵���������
					Socket socket = server.accept();
					// ��Ƶ�����߳̿��ܻ�Լ��Ͻ�����ɾ�Ĳ�����Ҫ����ͬ���Ĵ���
					synchronized (list) {
						list.add(socket);
					}
					// ����һ���µ��߳�ȥ��������ͻ��˵Ľ���
					new HandleSocket(socket, list).start();
				}
			}catch (Exception e) {
				System.out.println("�ռ������");
			}
		}
				).start();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			primaryStage=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/�ռ����������.fxml"));
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

	public void qb1(MouseEvent me){
		ȫ������ qb=new ȫ������();
		try {
			qb.start(new Stage());
		} catch (Exception e) {
			System.out.println("��ȫ������");
			e.printStackTrace();
		}
	}
	
	class HandleSocket extends Thread {

	    private Socket socket;
	    private List<Socket> list;

	    /**
	     * ���췽��
	     * 
	     */
	    public HandleSocket(Socket socket, List<Socket> list) {
	        this.socket = socket;
	        this.list = list;
	    }
	    @Override
	    public void run() {
	        InetAddress address = socket.getInetAddress(); // ��ȡ���ӵ�����������Ŀͻ��˵ĵ�ַ
	        String ip = address.getHostAddress();
	       xx.appendText(ip + "�����ˣ�");//��ʾ������Ϣ
	       FileWriter fw=null;
	        try {
	            BufferedReader br = new BufferedReader(new InputStreamReader(
	                    socket.getInputStream(), "gbk"));
	            String nic=br.readLine();
	            String line = "";
	            fw=new FileWriter(new File("D:\\study\\������\\������¾��ֲ�\\�ı�.txt"),true);
	            
	            while ((line = br.readLine()) != null) {
	                String msg = nic + ":"+"\r\n" + line;//���õ����ǳƺ���Ϣ���͸�������
	                fw.write(line+"\n");
	                
	                xx.appendText(msg); // ������������˵Ŀ���̨
	                // ������ͻ���˵�Ļ��������������еĿͻ���
	                sendToAll(msg);
	            }

	        } catch (IOException e) {
	            // e.printStackTrace();
	            System.out.println(ip + "�����ˣ�");
	            synchronized (list) {
	                list.remove(socket);
	            }
	        }
	        finally {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }

	    /**
	     * ����Ϣ���͸����еĿͻ��ˣ�ȥ����ǰsocket
	     * 
	     * @param msg
	     *            ���͵���Ϣ
	     */
	    private void sendToAll(String msg) {//����ʽ����
	        synchronized (list) {
	            for (Socket s : list) {
	                if (s != socket) {
	                    try {
	                        PrintStream ps = new PrintStream(s.getOutputStream());
	                        ps.println(msg);
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }

	}

}
