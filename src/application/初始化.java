package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class 初始化 {
	public Connection conn=null;
	public Statement st=null;
	public ResultSet rs=null;
	public String url=null;
	public 初始化()
	{
		try{
			url="jdbc:sqlserver://localhost:1433;DatabaseName=高仿QQ";//完成连库的功能
			conn=DriverManager.getConnection(url,"sa","123");
			st=conn.createStatement();//初始化
		}catch(Exception e){
			System.out.println("here");
		}
	}
}
