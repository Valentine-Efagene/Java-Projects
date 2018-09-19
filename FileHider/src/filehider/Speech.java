package filehider;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

/**
 *
 * @author Edesiri .V. Efagene
 */
public class Speech
{
    Voice voice;
    private String text;
    private final String kevin = "kevin16";
    
    public Speech()
    {
        //System.setProperty("mbrola.base", "C:\\Users\\Edesiri .V. Efagene\\Desktop\\mbrola\\mbrola");
        System.setProperty("mbrola.base", "C:\\Users\\Edesiri .V. Efagene\\Documents\\NetBeansProjects\\Speech\\src\\speech\\mbrola");
        VoiceManager voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(kevin);
        voice.allocate();
    }
    
    public void speak(String text)
    {
        voice.speak(text);
    }
}
