import javax.sound.sampled.*;

public class Main {

    public static void main(String[] args) {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();

        if (mixers.length == 0) {
            System.out.println("No mixer found");
        }

        for (Mixer.Info mixerInfo : mixers) {
            System.out.println("Found mixer " + mixerInfo.getName());
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] targetLines = mixer.getTargetLineInfo();
            for (Line.Info lineInfo : targetLines) {
                System.out.println("Found line " + lineInfo);
            }
        }
    }

}
