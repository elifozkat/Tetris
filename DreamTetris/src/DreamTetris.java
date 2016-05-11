import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class DreamTetris {

	public static void main(String[] args) {

		StartWindow frame = new StartWindow();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(
					"Game_Boy_Tetris_Music3.wav")));
			clip.loop(-1);
			clip.start();
		} catch (Exception e) {

		}
	}
}