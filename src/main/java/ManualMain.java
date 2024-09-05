import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.spi.MixerProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

public class ManualMain {

    public static void main(String[] args) {
        List<Mixer.Info> mixers = new ArrayList<>();
        ServiceLoader.load(MixerProvider.class).iterator()
                .forEachRemaining(provider -> Collections.addAll(mixers, provider.getMixerInfo()));

        if (mixers.isEmpty()) {
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
