package PaooGame.Music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//SINGLETON
public class Music {
    private Clip clip;
    private static Music instance;

    private Music(){}

    public static Music getInstance(){
        if(instance == null)
        {
            instance = new Music();
        }
        return instance;
    }
    public void playMusic(String filePath) {
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetă melodia până la sfârșit
                clip.start();
            } else {
                System.out.println("Fișierul nu a fost găsit: " + filePath);
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