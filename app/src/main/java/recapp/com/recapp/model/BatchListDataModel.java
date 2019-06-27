package recapp.com.recapp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BatchListDataModel implements Serializable {

    private int result;
    private String message;
    private List<BatchDataModel> batchList;
    private List<BatchDataModel> timiningsList;
    private List<BatchDataModel> subjectsList;
    private List<BatchDataModel> categoryList;
    private List<BatchDataModel> subcategoryList;
    private List<BatchDataModel> sectionList;
    private List<BatchDataModel> classList;
    private List<BatchDataModel> classSubjectList;
    private List<BatchDataModel> clgYearList;
    private List<BatchDataModel> clgSemesterList;
    private List<BatchDataModel> clgBranchList;
    private List<BatchDataModel> clgSectionList;
    private List<BatchDataModel> testPrepBatchList;
    private List<BatchDataModel> trainingBatchList;
    private List<BatchDataModel> languagesList;
    private List<BatchDataModel> languagesBatchList;
    private List<BatchDataModel> languagesTimeList;
    private List<BatchDataModel> musicInstrumentList;
    private List<BatchDataModel> musicBatchList;
    private List<BatchDataModel> musicTimeList;
    private List<BatchDataModel> subjectList;
    private List<BatchDataModel> topicList;
    private List<BatchDataModel> subtopicList;


    public List<BatchDataModel> getTopicList() { return topicList; }

    public List<BatchDataModel> getSubtopicList() { return subtopicList; }

    public List<BatchDataModel> getSubjectList() { return subjectList; }

    public List<BatchDataModel> getSubcategoryList() { return subcategoryList; }

    public List<BatchDataModel> getMusicBatchList() { return musicBatchList; }

    public List<BatchDataModel> getMusicTimeList() { return musicTimeList; }

    public List<BatchDataModel> getMusicInstrumentList() { return musicInstrumentList; }

    public List<BatchDataModel> getLanguagesBatchList() { return languagesBatchList; }

    public List<BatchDataModel> getLanguagesTimeList() { return languagesTimeList; }

    public List<BatchDataModel> getLanguagesList() { return languagesList; }

    public List<BatchDataModel> getTrainingBatchList() { return trainingBatchList; }

    public List<BatchDataModel> getTestPrepBatchList() { return testPrepBatchList; }

    public List<BatchDataModel> getClgSectionList() { return clgSectionList; }

    public List<BatchDataModel> getClgBranchList() { return clgBranchList; }

    public List<BatchDataModel> getClgSemesterList() { return clgSemesterList; }

    public List<BatchDataModel> getClgYearList() { return clgYearList; }

    public List<BatchDataModel> getClassSubjectList() {
        return classSubjectList;
    }

    public List<BatchDataModel> getCategoryList() {
        return categoryList;
    }

    public List<BatchDataModel> getClassList() {
        return classList;
    }

    public List<BatchDataModel> getSectionList() {
        return sectionList;
    }



    public BatchListDataModel(JSONObject aJSONObject) throws JSONException
    {
        batchList = new ArrayList<>();
        timiningsList = new ArrayList<>();
        subjectsList = new ArrayList<>();
        classList = new ArrayList<>();
        sectionList = new ArrayList<>();
        categoryList = new ArrayList<>();
        subcategoryList = new ArrayList<>();
        classSubjectList = new ArrayList<>();
        clgYearList = new ArrayList<>();
        clgSemesterList = new ArrayList<>();
        clgBranchList = new ArrayList<>();
        clgSectionList = new ArrayList<>();
        testPrepBatchList = new ArrayList<>();
        trainingBatchList = new ArrayList<>();
        languagesList = new ArrayList<>();
        languagesBatchList = new ArrayList<>();
        languagesTimeList = new ArrayList<>();
        musicInstrumentList = new ArrayList<>();
        musicBatchList = new ArrayList<>();
        musicTimeList = new ArrayList<>();
        subjectList = new ArrayList<>();
        topicList = new ArrayList<>();
        subtopicList = new ArrayList<>();

        if (aJSONObject!=null)
        {
            result = aJSONObject.optInt("Result");
            message = aJSONObject.optString("Message");

            if (result == 1)
            {
                JSONArray jsonBatchArray = null;
                JSONArray jsonTimeArray = null;
                JSONArray jsonSubjectArray = null;
                JSONArray jsonClassArray = null;
                JSONArray jsonClassSectionArray = null;
                JSONArray jsonCategoryArray = null;
                JSONArray jsonsubCategoryArray = null;
                JSONArray jsonClassSubjectArray = null;
                JSONArray jsonYearArray = null;
                JSONArray jsonSemesterArray = null;
                JSONArray jsonBranchArray = null;
                JSONArray jsonSectionArray = null;
                JSONArray jsonTestPrepArray = null;
                JSONArray jsonTrainingArray = null;
                JSONArray jsonlanguagesArray = null;
                JSONArray jsonLanguageBatchArray = null;
                JSONArray jsonLanguageTimeArray = null;
                JSONArray jsonMusicInstrumentArray = null;
                JSONArray jsonMusicBatchArray = null;
                JSONArray jsonMusicTimeArray = null;
                JSONArray jsonsubjectArray = null;
                JSONArray jsontopicArray = null;
                JSONArray jsonsubtopicArray = null;


                if (aJSONObject.has("batch_list") && aJSONObject.get("batch_list") instanceof JSONArray)
                {
                    jsonBatchArray = aJSONObject.getJSONArray("batch_list");
                }
                if (jsonBatchArray != null)
                {
                    for (int i = 0; i < jsonBatchArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonBatchArray.getJSONObject(i));
                        batchList.add(bannersData);

                    }
                }

                if (aJSONObject.has("timing_list") && aJSONObject.get("timing_list") instanceof JSONArray)
                {
                    jsonTimeArray = aJSONObject.getJSONArray("timing_list");
                }
                if (jsonTimeArray != null)
                {
                    for (int i = 0; i < jsonTimeArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonTimeArray.getJSONObject(i));
                        timiningsList.add(bannersData);

                    }
                }

                if (aJSONObject.has("subject_list") && aJSONObject.get("subject_list") instanceof JSONArray)
                {
                    jsonSubjectArray = aJSONObject.getJSONArray("subject_list");
                }
                if (jsonSubjectArray != null)
                {
                    for (int i = 0; i < jsonSubjectArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonSubjectArray.getJSONObject(i));
                        subjectsList.add(bannersData);

                    }
                }

                //=========================
                // class list
                //=========================


                if (aJSONObject.has("class_list") && aJSONObject.get("class_list") instanceof JSONArray)
                {
                    jsonClassArray = aJSONObject.getJSONArray("class_list");
                }
                if (jsonClassArray != null)
                {
                    for (int i = 0; i < jsonClassArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonClassArray.getJSONObject(i));
                        classList.add(bannersData);

                    }
                }

                //=========================
                // section list
                //=========================

                if (aJSONObject.has("section_list") && aJSONObject.get("section_list") instanceof JSONArray)
                {
                    jsonClassSectionArray = aJSONObject.getJSONArray("section_list");
                }
                if (jsonClassSectionArray != null)
                {
                    for (int i = 0; i < jsonClassSectionArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonClassSectionArray.getJSONObject(i));
                        sectionList.add(bannersData);

                    }
                }

                //=========================
                // category list
                //=========================

                if (aJSONObject.has("category_list") && aJSONObject.get("category_list") instanceof JSONArray)
                {
                    jsonCategoryArray = aJSONObject.getJSONArray("category_list");
                }
                if (jsonCategoryArray != null)
                {
                    for (int i = 0; i < jsonCategoryArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonCategoryArray.getJSONObject(i));
                        categoryList.add(bannersData);

                    }
                }

                //=========================
                // class subject list
                //=========================

                if (aJSONObject.has("class_subject_list") && aJSONObject.get("class_subject_list") instanceof JSONArray)
                {
                    jsonClassSubjectArray = aJSONObject.getJSONArray("class_subject_list");
                }
                if (jsonClassSubjectArray != null)
                {
                    subjectsList.clear();
                    for (int i = 0; i < jsonClassSubjectArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonClassSubjectArray.getJSONObject(i));
                        classSubjectList.add(bannersData);

                    }
                }

                //=========================
                // clg YEAR list
                //=========================

                if (aJSONObject.has("year_list") && aJSONObject.get("year_list") instanceof JSONArray)
                {
                    jsonYearArray = aJSONObject.getJSONArray("year_list");
                }
                if (jsonYearArray != null)
                {
                    clgYearList.clear();
                    for (int i = 0; i < jsonYearArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonYearArray.getJSONObject(i));
                        clgYearList.add(bannersData);

                    }
                }

                //=========================
                // clg SEMESTER list
                //=========================

                if (aJSONObject.has("sem_list") && aJSONObject.get("sem_list") instanceof JSONArray)
                {
                    jsonSemesterArray = aJSONObject.getJSONArray("sem_list");
                }
                if (jsonSemesterArray != null)
                {
                    clgSemesterList.clear();
                    for (int i = 0; i < jsonSemesterArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonSemesterArray.getJSONObject(i));
                        clgSemesterList.add(bannersData);

                    }
                }


                //=========================
                // clg BRANCH list
                //=========================

                if (aJSONObject.has("branch_list") && aJSONObject.get("branch_list") instanceof JSONArray)
                {
                    jsonBranchArray = aJSONObject.getJSONArray("branch_list");
                }
                if (jsonBranchArray != null)
                {
                    clgBranchList.clear();
                    for (int i = 0; i < jsonBranchArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonBranchArray.getJSONObject(i));
                        clgBranchList.add(bannersData);

                    }
                }

                //=========================
                // clg SECTION list
                //=========================

                if (aJSONObject.has("section_list") && aJSONObject.get("section_list") instanceof JSONArray)
                {
                    jsonSectionArray = aJSONObject.getJSONArray("section_list");
                }
                if (jsonSectionArray != null)
                {
                    clgSectionList.clear();
                    for (int i = 0; i < jsonSectionArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonSectionArray.getJSONObject(i));
                        clgSectionList.add(bannersData);

                    }
                }

                //=========================
                // TEST PREP BATCH list
                //=========================

                if (aJSONObject.has("test_prepare_batch_list") && aJSONObject.get("test_prepare_batch_list") instanceof JSONArray)
                {
                    jsonTestPrepArray = aJSONObject.getJSONArray("test_prepare_batch_list");
                }
                if (jsonTestPrepArray != null)
                {
                    testPrepBatchList.clear();
                    for (int i = 0; i < jsonTestPrepArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonTestPrepArray.getJSONObject(i));
                        testPrepBatchList.add(bannersData);

                    }
                }

                //=========================
                // Training BATCH list
                //=========================

                if (aJSONObject.has("training_batch_list") && aJSONObject.get("training_batch_list") instanceof JSONArray)
                {
                    jsonTrainingArray = aJSONObject.getJSONArray("training_batch_list");
                }
                if (jsonTrainingArray != null)
                {
                    trainingBatchList.clear();
                    for (int i = 0; i < jsonTrainingArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonTrainingArray.getJSONObject(i));
                        trainingBatchList.add(bannersData);

                    }
                }

                //=========================
                // Languages list
                //=========================

                if (aJSONObject.has("languages_list") && aJSONObject.get("languages_list") instanceof JSONArray)
                {
                    jsonlanguagesArray = aJSONObject.getJSONArray("languages_list");
                }
                if (jsonlanguagesArray != null)
                {
                    languagesList.clear();
                    for (int i = 0; i < jsonlanguagesArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonlanguagesArray.getJSONObject(i));
                        languagesList.add(bannersData);

                    }
                }

                //=========================
                // Languages Batch list
                //=========================

                if (aJSONObject.has("batch_list") && aJSONObject.get("batch_list") instanceof JSONArray)
                {
                    jsonLanguageBatchArray = aJSONObject.getJSONArray("batch_list");
                }
                if (jsonLanguageBatchArray != null)
                {
                    languagesBatchList.clear();
                    for (int i = 0; i < jsonLanguageBatchArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonLanguageBatchArray.getJSONObject(i));
                        languagesBatchList.add(bannersData);

                    }
                }

                //=========================
                // Languages Time list
                //=========================

                if (aJSONObject.has("timing_list") && aJSONObject.get("timing_list") instanceof JSONArray)
                {
                    jsonLanguageTimeArray = aJSONObject.getJSONArray("timing_list");
                }
                if (jsonLanguageTimeArray != null)
                {
                    languagesTimeList.clear();
                    for (int i = 0; i < jsonLanguageTimeArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonLanguageTimeArray.getJSONObject(i));
                        languagesTimeList.add(bannersData);

                    }
                }

                //=========================
                // MUSIC INSTRUMENT list
                //=========================

                if (aJSONObject.has("instrument_list") && aJSONObject.get("instrument_list") instanceof JSONArray)
                {
                    jsonMusicInstrumentArray = aJSONObject.getJSONArray("instrument_list");
                }
                if (jsonMusicInstrumentArray != null)
                {
                    musicInstrumentList.clear();
                    for (int i = 0; i < jsonMusicInstrumentArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonMusicInstrumentArray.getJSONObject(i));
                        musicInstrumentList.add(bannersData);

                    }
                }

                //=========================
                // MUSIC Batch list
                //=========================

                if (aJSONObject.has("batch_list") && aJSONObject.get("batch_list") instanceof JSONArray)
                {
                    jsonMusicBatchArray = aJSONObject.getJSONArray("batch_list");
                }
                if (jsonMusicBatchArray != null)
                {
                    musicBatchList.clear();
                    for (int i = 0; i < jsonMusicBatchArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonMusicBatchArray.getJSONObject(i));
                        musicBatchList.add(bannersData);

                    }
                }

                //=========================
                // MUSIC Time list
                //=========================

                if (aJSONObject.has("timing_list") && aJSONObject.get("timing_list") instanceof JSONArray)
                {
                    jsonMusicTimeArray = aJSONObject.getJSONArray("timing_list");
                }
                if (jsonMusicTimeArray != null)
                {
                    musicTimeList.clear();
                    for (int i = 0; i < jsonMusicTimeArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonMusicTimeArray.getJSONObject(i));
                        musicTimeList.add(bannersData);

                    }
                }

                //=========================
                // SUB CATEGORY list
                //=========================

                if (aJSONObject.has("category_list") && aJSONObject.get("category_list") instanceof JSONArray)
                {
                    jsonsubCategoryArray = aJSONObject.getJSONArray("category_list");
                }
                if (jsonsubCategoryArray != null)
                {
                    for (int i = 0; i < jsonsubCategoryArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonsubCategoryArray.getJSONObject(i));
                        subcategoryList.add(bannersData);

                    }
                }

                //=========================
                // SUBJECT list
                //=========================

                if (aJSONObject.has("subject_list") && aJSONObject.get("subject_list") instanceof JSONArray)
                {
                    jsonsubjectArray = aJSONObject.getJSONArray("subject_list");
                }
                if (jsonsubjectArray != null)
                {
                    for (int i = 0; i < jsonsubjectArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonsubjectArray.getJSONObject(i));
                        subjectList.add(bannersData);

                    }
                }

                //=========================
                // Topic list
                //=========================

                if (aJSONObject.has("topic_list") && aJSONObject.get("topic_list") instanceof JSONArray)
                {
                    jsontopicArray = aJSONObject.getJSONArray("topic_list");
                }
                if (jsontopicArray != null)
                {
                    for (int i = 0; i < jsontopicArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsontopicArray.getJSONObject(i));
                        topicList.add(bannersData);

                    }
                }

                //=========================
                // SUB Topic list
                //=========================

                if (aJSONObject.has("subtopic_list") && aJSONObject.get("subtopic_list") instanceof JSONArray)
                {
                    jsonsubtopicArray = aJSONObject.getJSONArray("subtopic_list");
                }
                if (jsonsubtopicArray != null)
                {
                    for (int i = 0; i < jsonsubtopicArray.length(); i++)
                    {
                        BatchDataModel bannersData = new BatchDataModel(jsonsubtopicArray.getJSONObject(i));
                        subtopicList.add(bannersData);

                    }
                }
            }
        }
    }

    public int getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public List<BatchDataModel> getBatchList() {
        return batchList;
    }

    public List<BatchDataModel> getTiminingsList() {
        return timiningsList;
    }

    public List<BatchDataModel> getSubjectsList() {
        return subjectsList;
    }


}
