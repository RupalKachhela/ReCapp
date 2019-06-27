package recapp.com.recapp.model;

import java.io.Serializable;

public class ContentDataList implements Serializable
{
    private String parentName;
    private int parentId;
    private int childId;
    private String childName;

    public ContentDataList(String paretnName , int parentId )
    {

        this.setParentName(paretnName);
        this.setParentId(parentId);


    }
    public ContentDataList(String paretnName , int parentId , int aChildId)
    {

      this.setChildId(aChildId);
      this.setParentName(paretnName);
      this.setParentId(parentId);


    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String aParentName) {
        parentName = aParentName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int aParentId) {
        parentId = aParentId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int aChildId) {
        childId = aChildId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String aChildName) {
        childName = aChildName;
    }
}
