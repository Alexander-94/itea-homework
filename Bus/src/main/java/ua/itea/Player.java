package ua.itea;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Player {

	private static final String SOUND_PATH = "sounds/driving.mp3";
	private static final double VOLUME = 0.3;
	private MediaPlayer mediaPlayer;

	public Player() {
		try {
			mediaPlayer = new MediaPlayer(
					new Media(this.getClass().getClassLoader().getResource(SOUND_PATH).toURI().toString()));
		} catch (Exception e) {
			System.out.println("Player() exception");
		}
		mediaPlayer.setVolume(VOLUME);

	}

	public void play() {
		mediaPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				mediaPlayer.seek(Duration.ZERO);
			}
		});

		mediaPlayer.setOnError(() -> System.out.println("Error : " + mediaPlayer.getError().toString()));

		mediaPlayer.play();

	}

	public void stop() {
		mediaPlayer.stop();
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

}
