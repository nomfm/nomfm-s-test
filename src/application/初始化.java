package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class ��ʼ�� {
	public Connection conn=null;
	public Statement st=null;
	public ResultSet rs=null;
	public String url=null;
	public ��ʼ��()
	{
		try{
			url="jdbc:sqlserver://localhost:1433;DatabaseName=�߷�QQ";//�������Ĺ���
			conn=DriverManager.getConnection(url,"sa","123");
			st=conn.createStatement();//��ʼ��
		}catch(Exception e){
			System.out.println("here");
		}
	}
}
