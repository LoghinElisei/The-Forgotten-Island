package PaooGame.Music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    private static Clip clip;
    private static SoundPlayer instance;
    private static String  path;

    private SoundPlayer() {
        path = "res/sounds/button.wav";
    }

    public static SoundPlayer getInstance() {
        if (instance == null) {
            instance = new SoundPlayer();
        }
        return instance;
    }

    public static void playSound() {
        try {
            File musicPath = new File(path);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            } else {
                System.out.println("Fișierul nu a fost găsit: " + path);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public void setVolume(float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = gainControl.getMinimum(); // Nivel minim în dB
            float max = gainControl.getMaximum(); // Nivel maxim în dB

            // Aplicați scalarea logaritmică
            float scaledVolume = (float) (min + (max - min) * Math.log10(1 + 9 * volume) / Math.log10(10));
            gainControl.setValue(scaledVolume);
        }
    }
}
