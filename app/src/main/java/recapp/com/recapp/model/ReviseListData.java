package recapp.com.recapp.model;

import java.io.File;
import java.io.Serializable;

public class ReviseListData implements Serializable
{

    byte[] url;
    byte[] audio;
    String topicName;


    public ReviseListData(String topicName ,byte[] aUrl ,byte[] aAudio)
    {
        this.topicName = topicName;

        this.url = aUrl;
        this.audio= aAudio;

    }


    public  byte[]  getUrl() {
        return url;
    }

    public void setUrl( byte[]  aUrl) {
        url = aUrl;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] aAudio) {
        audio = aAudio;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String aTopicName) {
        topicName = aTopicName;
    }

}
