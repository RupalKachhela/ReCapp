package recapp.com.recapp.model;

import java.io.Serializable;

public class SubjectListData implements Serializable
{
    String shortName;
    String subjectName;

    public SubjectListData(String aShortName , String aSubjectName)
    {
        this.shortName = aShortName;
        this.subjectName= aSubjectName;

    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String aShortName) {
        shortName = aShortName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String aSubjectName) {
        subjectName = aSubjectName;
    }

}
