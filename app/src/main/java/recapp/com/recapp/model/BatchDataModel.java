package recapp.com.recapp.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class BatchDataModel implements Serializable
{

    private String batchId;
    private String batchName;
    private String batchTime;
    private String batchSubject;
    private String classId;
    private String className;

    private String sectionId;
    private String tuitionClassId;
    private String tutionBatchName;

    private String subCategoryId;
    private String tuitonBatchId;
    private String timeId;
    private String sectionName;
    private String categoryId;
    private String categoryName;
    private String subCategoryName;


    private String yearId;
    private String year;

    private String semId;
    private String semName;

    private String branchId;
    private String branchName;

    private String clgSectionName;

    private String testPrepBatchId;
    private String testPrepBatchName;

    private String languageId;
    private String languageName;

    private String instrumentId;
    private String instrumentName;

    private String subjectId;
    private String subjectName;

    private String topicId;
    private String topicName;

    private String subtopicId;
    private String subtopicName;





    public BatchDataModel(JSONObject jsonObject)
    {

        if (jsonObject!=null)
        {

            batchId = jsonObject.optString("id");
            batchName = jsonObject.optString("name");
            batchTime = jsonObject.optString("time");
            batchSubject = jsonObject.optString("name");
            classId = jsonObject.optString("class_id");
            className = jsonObject.optString("class_name");
            sectionId = jsonObject.optString("section_id");
            sectionName = jsonObject.optString("section_name");
            categoryId = jsonObject.optString("category_id");
            categoryName = jsonObject.optString("category_name");
            subCategoryId = jsonObject.optString("sub_category_id");
            subCategoryName = jsonObject.optString("sub_category_name");
            tuitionClassId = jsonObject.optString("tution_class_id");
            tuitonBatchId = jsonObject.optString("tution_batch_id");
            timeId = jsonObject.optString("id");
            tutionBatchName = jsonObject.optString("batch_name");
            yearId = jsonObject.optString("year_id");
            year = jsonObject.optString("Year");
            semId = jsonObject.optString("sem_id");
            semName = jsonObject.optString("semester");
            branchId = jsonObject.optString("branch_id");
            branchName = jsonObject.optString("branch");
            clgSectionName = jsonObject.optString("section");
            testPrepBatchId = jsonObject.optString("batch_id");
            testPrepBatchName = jsonObject.optString("batch_name");
            languageId = jsonObject.optString("language_id");
            languageName = jsonObject.optString("language_name");
            instrumentId = jsonObject.optString("instrument_id");
            instrumentName = jsonObject.optString("instrument_name");
            subjectId = jsonObject.optString("subject_id");
            subjectName = jsonObject.optString("subject_name");
            topicId = jsonObject.optString("topic_id");
            topicName = jsonObject.optString("topic_name");
            subtopicId = jsonObject.optString("subtopic_id");
            subtopicName = jsonObject.optString("subtopic_name");

        }
    }

    public String getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getSubtopicId() {
        return subtopicId;
    }

    public String getSubtopicName() {
        return subtopicName;
    }


    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }


    public String getInstrumentId() {
        return instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public String getLanguageId() {
        return languageId;
    }

    public String getLanguageName() {
        return languageName;
    }


    public String getTestPrepBatchId() {
        return testPrepBatchId;
    }

    public String getTestPrepBatchName() {
        return testPrepBatchName;
    }


    public String getClgSectionName() {
        return clgSectionName;
    }

    public String getTutionBatchName() {
        return tutionBatchName;
    }

    public String getTuitionClassId() {
        return tuitionClassId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public String getTuitonBatchId() {
        return tuitonBatchId;
    }

    public String getTimeId() {
        return timeId;
    }

    public String getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }


    public String getSemId() {
        return semId;
    }

    public String getSemName() {
        return semName;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getBatchName() {
        return batchName;
    }


    public String getBatchTime() {
        return batchTime;
    }

    public String getBatchSubject() {
        return batchSubject;
    }


    public String getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getYearId() {
        return yearId;
    }

    public String getYear() {
        return year;
    }
}
