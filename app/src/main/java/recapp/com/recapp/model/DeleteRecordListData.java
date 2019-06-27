package recapp.com.recapp.model;

import java.io.Serializable;

public class DeleteRecordListData implements Serializable
{
    private String chapterName;
    private String topicName;
    private String fileName;


    public DeleteRecordListData(String chapterName , String topicName,String fileName)
    {
        this.setChapterName(chapterName);
        this.setTopicName(topicName);
        this.setFileName(fileName);


    }


    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String aChapterName) {
        chapterName = aChapterName;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String aTopicName) {
        topicName = aTopicName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String aFileName) {
        fileName = aFileName;
    }
}
