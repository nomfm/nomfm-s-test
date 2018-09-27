package application;

import java.io.File;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class 视频 {
	MediaPlayer mediaPlayer;
	private Label time;
	Duration duration;
	Button fullScreenButton;
	Scene scene;
	Media media;
	double width;
	double height;
	MediaView mediaView;
	HBox hb;
	Stage primaryStage=new Stage();
	
	double x1;
	double y1;
	double x_stage ;
	double y_stage ;
	
	public void start()  {
		// TODO Auto-generated method stub
		scene=setScene(this.width,this.height);
		fullScreenButton=new Button("全屏");
		fullScreenButton.setOnAction((ActionEvent e) -> {
			if (primaryStage.isFullScreen()) {
				primaryStage.setFullScreen(false);
			} else {
				primaryStage.setFullScreen(true);
			}
			});
		
		time = new Label();
		time.setTextFill(Color.WHEAT);
		time.setPrefWidth(120);

		mediaPlayer.currentTimeProperty().addListener((Observable ov) -> {
			updateValues();
		});

		mediaPlayer.setOnReady(() -> {
			duration = mediaPlayer.getMedia().getDuration();
			updateValues();
		});
		
		hb.getChildren().add(fullScreenButton);
		hb.getChildren().add(time);
		primaryStage.setTitle("MediaPlayer!");
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
	}
	public Scene setScene(double width, double height) {
		this.height = height;
		this.width = width;
		String path = "D:/OK/正真的java课设/课设/bin/1.mp4";//设置文件路径

		media = new Media(new File(path).toURI().toString());

		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(false);
		mediaView = new MediaView(mediaPlayer);
		mediaView.setFitWidth(600);
		mediaView.setFitHeight(500);
		
		DropShadow dropshadow = new DropShadow();
		dropshadow.setOffsetY(5.0);
		dropshadow.setOffsetX(5.0);
		dropshadow.setColor(Color.WHITE);

		mediaView.setEffect(dropshadow);
		
//
//
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(mediaView);
		borderPane.setStyle("-fx-background-color: Black");
		
		Button playButton=new Button("开始");
		playButton.setOnAction((ActionEvent e) -> {//按钮对应事件
			mediaPlayer.play();
			});		
		Button pauseButton=new Button("暂停");
		pauseButton.setOnAction((ActionEvent e) -> {
			mediaPlayer.pause();
			});
		Button forwardButton=new Button("快进");
		forwardButton.setOnAction((ActionEvent e) -> {
			mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.5));
			});
		Button backButton=new Button("后退");
		backButton.setOnAction((ActionEvent e) -> {
			mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.5));
			});
		Button firstButton=new Button("上一个");
		firstButton.setOnAction((ActionEvent e) -> {
			mediaPlayer.seek(mediaPlayer.getStartTime());
			mediaPlayer.stop();
			});
		Button lastButton=new Button("下一个");
		lastButton.setOnAction((ActionEvent e) -> {
			mediaPlayer.seek(mediaPlayer.getTotalDuration());
			mediaPlayer.stop();
			});
		Button filesButton=new Button("打开文件");
		filesButton.setOnAction((ActionEvent e) -> {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp4", "*.mpeg","*.mp3"));
			File file = fc.showOpenDialog(null);
			String path1 = file.getAbsolutePath();
			path1 = path1.replace("\\", "/");
			media = new Media(new File(path1).toURI().toString());
			mediaPlayer.stop();
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setAutoPlay(true);
			mediaView.setMediaPlayer(mediaPlayer);
			});		
		
		
		
		hb=new HBox();
		hb.getChildren().add(playButton);
		hb.getChildren().add(pauseButton);
		hb.getChildren().add(forwardButton);
		hb.getChildren().add(backButton);
		hb.getChildren().add(firstButton);
		hb.getChildren().add(lastButton);
		hb.getChildren().add(filesButton);
		
		borderPane.setBottom(hb);
		

		
		scene = new Scene(borderPane, 600, 367);
		return scene;
	}
	
	protected void updateValues() {
//		if (time != null) {
//			runLater(() -> {
				Duration currentTime = mediaPlayer.getCurrentTime();
				time.setText(formatTime(currentTime, duration));
//			});
//		}
	}
	private static String formatTime(Duration elapsed, Duration duration) {
		int intElapsed = (int) elapsed.toSeconds();
		int elapsedHours = intElapsed / (60 * 60);
		if (elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed / 60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
		if (duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int) duration.toSeconds();
			int durationHours = intDuration / (60 * 60);
			if (durationHours > 0) {
				intDuration -= durationHours * 60 * 60;
			}
			int durationMinutes = intDuration / 60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {
				return String.format("     %d:%02d:%02d/%d:%02d:%02d",elapsedHours, elapsedMinutes, elapsedSeconds,durationHours, durationMinutes, durationSeconds);
			} 
			else {
				return String.format("     %02d:%02d/%02d:%02d",elapsedMinutes, elapsedSeconds, durationMinutes,durationSeconds);
			}	
		} 
		else {
			if (elapsedHours > 0) {
				return String.format("    %d:%02d:%02d", elapsedHours,elapsedMinutes, elapsedSeconds);
			} 
			else {
				return String.format("    %02d:%02d", elapsedMinutes,elapsedSeconds);
			}
		}
	}	 

}
