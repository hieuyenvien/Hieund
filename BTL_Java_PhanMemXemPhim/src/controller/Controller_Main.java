package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import application.ParseJsonYoutube;

public class Controller_Main implements Initializable {
	private boolean endOfMedia = false;
	private Media pick;
	public MediaPlayer player;
	private Duration duration;
	private double volume;
	String str;
	String link = null;
	private List<Item> list;
	ObservableList<Item> observableList = FXCollections.observableArrayList();
	private Stage stage = null;
	private Scene scene;
	private Pane pane;

	/***********************************************************
	 * 
	 * 1. Hàm khởi tạo
	 * 
	 **********************************************************/
	public Controller_Main () {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/LayoutMain.fxml"));
		fxmlLoader.setController(this);
		try {
			pane = fxmlLoader.load();
			scene = new Scene(pane);
			scene.getStylesheets().add(getClass().getResource("/application/aMain.css").toExternalForm());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void launch(String str1, String str2) {
		link = str2;
		if (link != null) {
			final WebEngine eng = idWebView.getEngine();
			eng.load("https://www.youtube.com/embed/8TBPdJHKZYo");
		}
		Stage primaryStage = new Stage();
		primaryStage.setTitle(str1);
		Image iconSoftWare = new Image("/layout/iconMovie.png");
		primaryStage.getIcons().add(iconSoftWare);
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println(str2);
	}

	/*****************************************************************
	 * 
	 * 2. Lập trình sự kiện
	 * 
	 ****************************************************************/
	@FXML
	private TextField idSearchText;
	@FXML
	private Button idSearch;
	@FXML
	private Button idPlay;
	@FXML
	private MediaView idMediaView;
	@FXML
	private Button idStop;
	@FXML
	private Button idVolumeDown;
	@FXML
	private Button idVolumeUp;
	@FXML
	private Button idMute;
	@FXML
	private ProgressIndicator idPIn;
	@FXML
	private Button idFullScreen;
	@FXML
	private Slider idSliderTime;
	@FXML
	private Label idTime;
	@FXML
	private Pane idPaneMedia;
	@FXML
	private WebView idWebView;

	/************************************************************************************
	 * 
	 * 3. CONTROL MENU
	 * 
	 *************************************************************************************/
	@FXML
	private MenuItem idMClose;
	@FXML
	private MenuItem idMStop;
	@FXML
	private MenuItem idMPlay;
	@FXML
	private MenuItem idMPause;
	@FXML
	private MenuItem idMVolumeDown;
	@FXML
	private MenuItem idMVolumeUp;
	@FXML
	private MenuItem idMVolumeMute;
	@FXML
	private MenuItem idMBalance1;
	@FXML
	private MenuItem idMBalance2;
	@FXML
	private MenuItem idMBalance3;
	@FXML
	private MenuItem idMBalance4;
	@FXML
	private MenuItem idMBalance5;
	@FXML
	private MenuItem idMAbout;

	@FXML
	private void mStop() {
		player.stop();
		idPlay.setText(">");
	}

	@FXML
	private void mPlay() {
		player.play();
		idPlay.setText("||");

		player.currentTimeProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable ov) {
				updateTime();
			}
		});
	}

	@FXML
	private void mPause() {
		player.pause();
		idPlay.setText(">");
	}

	@FXML
	private void mVolumeDown() {
		volume = player.getVolume() - 0.05;
		player.setVolume(volume);
		idPIn.setProgress(volume);
	}

	@FXML
	private void mVolumeUp() {
		volume = player.getVolume() + 0.05;
		player.setVolume(volume);
		idPIn.setProgress(volume);
	}

	@FXML
	private void mVolumeMute() {
		if (player.isMute() == true)
			player.setMute(false);
		else
			player.setMute(true);
	}

	@FXML
	private void mCloseScene() {
		Platform.exit();
	}

	@FXML
	private void mBalance1() {
		player.setBalance(1.0);
	}

	@FXML
	private void mBalance2() {
		player.setBalance(0.5);
	}

	@FXML
	private void mBalance3() {
		player.setBalance(0.0);
	}

	@FXML
	private void mBalance4() {
		player.setBalance(-0.5);
	}

	@FXML
	private void mBalance5() {
		player.setBalance(-1.0);
	}

	@FXML
	private void mAboutTeam() {
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("/layout/Team.fxml"));
			Scene scene = new Scene(root);
			Stage primaryStage = new Stage();
			primaryStage.setTitle("Imformation");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**********************************************************************************
	 * 
	 * 5. CÁC CONTROL KHÁC ĐƯỢC XỬ LÝ TRONG HÀM KHỞI TẠO initialize()
	 * 
	 **********************************************************************************/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		idSearchText.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					str = idSearchText.getText();
					System.out.println(str);
					ParseJsonYoutube parser = new ParseJsonYoutube(str);
					list = parser.ParseYoutube();
					System.out.println(list);
					
					Controller_Search ctrl = new Controller_Search(list);
					if (stage != null)
						stage.close();
					stage = ctrl.launch();
				}
			}
		});
		/*****************************************************************************
		 * 
		 * Lập trình sự kiện cho nút search Xử lý lấy địa chỉ link video ở đây
		 * 
		 *****************************************************************************/
		idSearch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// /* cách lấy dữ liệu video trên mạng */
				// final String MEDIA_URL =
				// "youtube.com/v/5CU6_Yxz76I?version=3&f=videos&app=youtube_gdata";
				// pick = new Media(MEDIA_URL);
				
				/* cách lấy dữ liệu trong máy tính */
				URL resource = getClass().getResource("/layout/ab.mp3");
				pick = new Media(resource.toString());
				
				player = new MediaPlayer(pick);
				idMediaView.setMediaPlayer(player);

				player.setVolume(0.5);
				idPIn.setProgress(0.5);
				
				str = idSearchText.getText();
				System.out.println(str);
				ParseJsonYoutube parser = new ParseJsonYoutube(str);
				list = parser.ParseYoutube();
				System.out.println(list);

				Controller_Search ctrl = new Controller_Search(list);
				if (stage != null)
					stage.close();
				stage = ctrl.launch();

			}
		});

		// Sự kiện nút Play/Pause
		idPlay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				Status status = player.getStatus();
				if (status == Status.UNKNOWN || status == Status.HALTED) {
					// don't do anything in these states
					return;
				}

				if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
					// rewind the movie if we're sitting at the end

					player.play();
				} else {
					player.pause();
				}

				player.setOnPaused(new Runnable() {
					public void run() {
						idPlay.setText(">");
					}
				});

				player.setOnPlaying(new Runnable() {
					public void run() {
						idPlay.setText("||");
					}
				});

				// Lập trình cho thời gian chạy khi xem phim
				duration = player.getMedia().getDuration();
				player.currentTimeProperty().addListener(new InvalidationListener() {
					public void invalidated(Observable ov) {
						updateTime();
					}
				});
			}
		});

		// Sự kiện khi Click vào màn hình phim
		idMediaView.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				Status status = player.getStatus();
				if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
					// rewind the movie if we're sitting at the end
					if (endOfMedia) {
						player.seek(player.getStartTime()); // lấy thời gian tại
															// lúc dừng
						endOfMedia = false;
					}
					player.play();
				} else {
					player.pause();
				}
			}

		});

		// Sự kiện nút Stop
		idStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				player.stop();
				idPlay.setText(">");
			}
		});

		idFullScreen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		// Sự kiện khi kéo thanh thời gian
		idSliderTime.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				updateTime();
				if (idSliderTime.isValueChanging()) {
					player.seek(duration.multiply(idSliderTime.getValue() / 100.0));
				}
			}
		});

		/******************************************************************************
		 * 
		 * Setup Volume
		 *
		 ******************************************************************************/
		idVolumeDown.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				volume = player.getVolume() - 0.05;
				player.setVolume(volume);
				idPIn.setProgress(volume);
			}
		});

		idVolumeUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				volume = player.getVolume() + 0.05;
				player.setVolume(volume);
				idPIn.setProgress(volume);
			}
		});

		idMute.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (player.isMute() == true)
					player.setMute(false);
				else
					player.setMute(true);
			}
		});
	}

	protected void updateTime() {
		if (idTime != null && idSliderTime != null) {
			Platform.runLater(new Runnable() {

				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Duration currentTime = player.getCurrentTime();
					idTime.setText(setLabelTime(currentTime, duration));
					idSliderTime.setDisable(duration.isUnknown());
					if (!idSliderTime.isDisable() && duration.greaterThan(Duration.ZERO)
							&& !idSliderTime.isValueChanging()) {
						idSliderTime.setValue(currentTime.divide(duration).toMillis() * 100);
					}
				}
			});
		}
	}

	private String setLabelTime(Duration count, Duration length) {
		int intCount = (int) Math.floor(count.toSeconds());
		int intCountH = intCount / 3600;
		int intCountM = (intCount - intCountH * 3600) / 60;
		int intCountS = intCount - intCountH * 3600 - intCountM * 60;

		int intLength = (int) Math.floor(length.toSeconds());
		int intLengthH = intLength / 3600;
		int intLengthM = (intLength - intLengthH * 3600) / 60;
		int intLengthS = intLength - intLengthH * 3600 - intLengthM * 60;

		if (intLengthH > 0)
			return String.format("%02d:%02d:%02d/%02d:%02d:%02d", intCountH, intCountM, intCountS, intLengthH,
					intLengthM, intLengthS);
		else
			return String.format("%02d:%02d/%02d:%02d", intCountM, intCountS, intLengthM, intLengthS);
	}
}
