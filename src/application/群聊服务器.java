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

public class 群聊服务器 extends Application implements Initializable {
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
	

	public static 初始化 csh=null;

	public static void main(String[] args) {
		launch(args);

	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		xx.setEditable(false);
		new Thread(()->{
			try{
				List<Socket> list = new ArrayList<>();
				// 创建服务器端的套接字
				System.out.println("空间服务器套接字打开");
				ServerSocket server = new ServerSocket(3000);
				System.out.println("连接成功");
				while (true) {
					// 接收客户端的阻塞方法
					Socket socket = server.accept();
					// 设计到多个线程可能会对集合进行增删的操作，要进行同步的处理
					synchronized (list) {
						list.add(socket);
					}
					// 启动一个新的线程去处理这个客户端的交流
					new HandleSocket(socket, list).start();
				}
			}catch (Exception e) {
				System.out.println("空间服务器");
			}
		}
				).start();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		try {
			primaryStage=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/空间服务器界面.fxml"));
			primaryStage.setTitle("登录");
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
		全部分析 qb=new 全部分析();
		try {
			qb.start(new Stage());
		} catch (Exception e) {
			System.out.println("打开全部分析");
			e.printStackTrace();
		}
	}
	
	class HandleSocket extends Thread {

	    private Socket socket;
	    private List<Socket> list;

	    /**
	     * 构造方法
	     * 
	     */
	    public HandleSocket(Socket socket, List<Socket> list) {
	        this.socket = socket;
	        this.list = list;
	    }
	    @Override
	    public void run() {
	        InetAddress address = socket.getInetAddress(); // 获取连接到服务器的这的客户端的地址
	        String ip = address.getHostAddress();
	       xx.appendText(ip + "上线了！");//显示上线消息
	       FileWriter fw=null;
	        try {
	            BufferedReader br = new BufferedReader(new InputStreamReader(
	                    socket.getInputStream(), "gbk"));
	            String nic=br.readLine();
	            String line = "";
	            fw=new FileWriter(new File("D:\\study\\大三下\\软件创新俱乐部\\文本.txt"),true);
	            
	            while ((line = br.readLine()) != null) {
	                String msg = nic + ":"+"\r\n" + line;//将得到的昵称和消息发送给所有人
	                fw.write(line+"\n");
	                
	                xx.appendText(msg); // 输出到服务器端的控制台
	                // 把这个客户端说的话，发给其他所有的客户端
	                sendToAll(msg);
	            }

	        } catch (IOException e) {
	            // e.printStackTrace();
	            System.out.println(ip + "下线了！");
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
	     * 把信息发送给所有的客户端，去除当前socket
	     * 
	     * @param msg
	     *            发送的信息
	     */
	    private void sendToAll(String msg) {//遍历式发送
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
