package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class 全部分析 extends Application implements Initializable {
	public static Stage primaryStage=null;
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	@FXML
	private TextArea jg;
	
	public static void main(String[] args) {
		launch(args);

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		try{
				 String[] args1=new String[]{"python","pythonFile/去停用词测试.py"};
				 
		         Process pr=Runtime.getRuntime().exec(args1);
		         
		         Thread.sleep(2000);
		         
		        Scanner s=new Scanner(new File("D:\\study\\大三下\\软件创新俱乐部\\result.txt"));
		        while (s.hasNextLine()){
		        	jg.appendText(s.nextLine());
		        }
		        
		    
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		try {
			primaryStage=new Stage();
			Parent root =FXMLLoader.load(getClass().getResource("/application/全部分析.fxml"));
			primaryStage.setTitle("分析");
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
