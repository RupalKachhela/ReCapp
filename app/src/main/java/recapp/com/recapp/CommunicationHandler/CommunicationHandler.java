package recapp.com.recapp.CommunicationHandler;

import android.content.Context;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import recapp.com.recapp.api.API;
import recapp.com.recapp.helper.Const;
import recapp.com.recapp.helper.Logger;
import recapp.com.recapp.webservice.WebServiceHandler;

public class CommunicationHandler implements WebServiceHandler.WebServiceResponseCallBack
{

    private static final String TAG = CommunicationHandler.class.getSimpleName();
    /**
     * The m instanse.
     */
    public static CommunicationHandler mInstanse;
    /**
     * The current method.
     */
    static String currentMethod;
    /**
     * The callback.
     */
    static CommunicationHandlerCallBack callback;
    /**
     * The m context.
     */

    private Context mContext;

    /**
     * Gets the single instance of CommunicationHandler.
     *
     * @param callback the callback
     * @return single instance of CommunicationHandler
     */
    public static synchronized CommunicationHandler getInstance(CommunicationHandlerCallBack callback)
    {
        CommunicationHandler.callback = callback;
        if (mInstanse == null)
        {
            mInstanse = new CommunicationHandler();
            Log.i(TAG,"mInstanse=="+mInstanse);
        }
        return mInstanse;
    }

    public CommunicationHandler callForLogin(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("LOGIN_API ==> ",API.REGISTRATION);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.LOGIN, API.LOGIN, "", params);

        return mInstanse;
    }

    public CommunicationHandler callForRegistarion(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("REGISTRATION_API ==> ",API.REGISTRATION);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.REGISTRATION, API.REGISTRATION, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForWithoutCodeRegister(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("REGISTRATION_API ==> ",API.REGISTRATION);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.WITHOUT_CODE_REGISTRATION, API.WITHOUT_CODE_REGISTRATION, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForGetClassList(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("BATCH_SUBJECTS_API ==> ",API.CLASS_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.CLASS_LIST, API.CLASS_LIST, "", params);
        return mInstanse;
    }
    public CommunicationHandler callForGetSectionList(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("BATCH_SECTION_API ==> ",API.CLASS_SECTION_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.CLASS_SECTION_LIST, API.CLASS_SECTION_LIST, "", params);
        return mInstanse;
    }
    public CommunicationHandler callForGetClassSubjectList(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("BATCH_SUBJECTS_API ==> ",API.CLASS_SECTION_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.CLASS_SUBJECT_LIST, API.CLASS_SUBJECT_LIST, "", params);
        return mInstanse;
    }
    public CommunicationHandler callForCategoryList(Context mContext)
    {
        this.mContext = mContext;
        HashMap<String,String> params = new HashMap<>();
        Logger.putLogError("CATEGORY_API ==> ",API.CATEGORY_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.CATEGORY_LIST, API.CATEGORY_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler  callForSubCategoryList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("CATEGORY_API ==> ",API.CATEGORY_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.SUB_CATEGORY_LIST, API.SUB_CATEGORY_LIST, "", params);
        return mInstanse;
    }



    public CommunicationHandler callForSchoolRegister(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("CATEGORY_API ==> ",API.SCHOOL_REG);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.SCHOOL_REG, API.SCHOOL_REG, "", params);
        return mInstanse;
    }

    //==================================================
    //   call for Tuition drop down list
    //==================================================

    public CommunicationHandler callForTuitionClassList(Context mContext , HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("TUITION_CLASS_LIST_API ==> ",API.TUITION_CLASS_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TUITION_CLASS_LIST, API.TUITION_CLASS_LIST, "", params);
        return mInstanse;

    }

    public CommunicationHandler callForTuitionBatchList(Context mContext , HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("TUITION_BATCH_LIST_API ==> ",API.TUITION_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TUITION_BATCH_LIST, API.TUITION_BATCH_LIST, "", params);
        return mInstanse;

    }
    public CommunicationHandler callForTuitionTiminingsList(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("TUITION_TIMINIGS_LIST_API ==> ",API.TUITION_TIMINIGS_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TUITION_TIMINIGS_LIST, API.TUITION_TIMINIGS_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForTuitionSubjectList(Context mContext, HashMap<String, String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("TUITION_SUBJECT_LIST_API ==> ",API.TUITION_SUBJECT_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TUITION_SUBJECT_LIST, API.TUITION_SUBJECT_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForTuitionRegister(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("TUITION_REG_API ==> ",API.TUITION_REG);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TUITION_REG, API.TUITION_REG, "", params);
        return mInstanse;
    }




    //==================================================
    //   call for college drop down list
    //==================================================

    public CommunicationHandler callForCollegeYearList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("COLLEGE YEAR LIST ==> ",API.COLLEGE_YEAR_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.COLLEGE_YEAR_LIST, API.COLLEGE_YEAR_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForCollegeSemesterList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("COLLEGE SEMESTER LIST ==> ",API.COLLEGE_SEMESTER_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.COLLEGE_SEMESTER_LIST, API.COLLEGE_SEMESTER_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForCollegeBranchList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("COLLEGE BRANCH LIST ==> ",API.COLLEGE_BRANCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.COLLEGE_BRANCH_LIST, API.COLLEGE_BRANCH_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForCollegeSectionList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("COLLEGE SECTION LIST ==> ",API.COLLEGE_SECTION_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.COLLEGE_SECTION_LIST, API.COLLEGE_SECTION_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForCollegeRegister(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("COLLEGE SECTION LIST ==> ",API.COLLEGE_SECTION_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.COLLEGE_REG, API.COLLEGE_REG, "", params);
        return mInstanse;
    }

    //==================================================
    //  API  for Test Prep Categoty
    //==================================================

    public CommunicationHandler callForTestPrepBatchList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TEST_PREP_BATCH_LIST, API.TEST_PREP_BATCH_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForTestPrepationRegister(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        //Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TEST_PREP_REG, API.TEST_PREP_REG, "", params);
        return mInstanse;
    }

    //==================================================
    //   API call for Training Categoty
    //==================================================

    public CommunicationHandler callForTrainingBatchList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("TRAINING BATCH LIST ==> ",API.TRAINING_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TRAINING_BATCH_LIST, API.TRAINING_BATCH_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForTrainingRegister(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        //Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TRAINING_REG, API.TRAINING_REG, "", params);
        return mInstanse;
    }


    //==================================================
    //   API call for Language Categoty
    //==================================================

    public CommunicationHandler callForLanguagelanguageList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("LANGUAGE LANGUAGE LIST==> ",API.LANGUAGE_LANGUAGE_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.LANGUAGE_LANGUAGE_LIST, API.LANGUAGE_LANGUAGE_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForLanguageLevelList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("LANGUAGE LEVEL LIST ==> ",API.LANGUAGE_LEVEL_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.LANGUAGE_LEVEL_LIST, API.LANGUAGE_LEVEL_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForLanguageBatchList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("LANGUAGE BATCH LIST ==> ",API.LANGUAGE_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.LANGUAGE_BATCH_LIST, API.LANGUAGE_BATCH_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForLanguageTimeList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("LANGUAGE TIME LIST ==> ",API.LANGUAGE_TIME_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.LANGUAGE_TIME_LIST, API.LANGUAGE_TIME_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForLanguageRegister(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        //Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.LANGUAGE_REG, API.LANGUAGE_REG, "", params);
        return mInstanse;
    }

    //==================================================
    //   API call for Language Categoty
    //==================================================

    public CommunicationHandler callForMusicInstrumentList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.MUSIC_INSTRUMENT_LIST, API.MUSIC_INSTRUMENT_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForMusicBatchList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.MUSIC_BATCH_LIST, API.MUSIC_BATCH_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForMusicTimeList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        Logger.putLogError("LANGUAGE TIME LIST ==> ",API.LANGUAGE_TIME_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.MUSIC_TIME_LIST, API.MUSIC_TIME_LIST, "", params);
        return mInstanse;
    }

    public CommunicationHandler callForMusicRegister(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        //Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.MUSIC_REG, API.MUSIC_REG, "", params);
        return mInstanse;
    }

    //subject list

    public CommunicationHandler callForSubjectList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        //Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.SUBJECT_LIST, API.SUBJECT_LIST, "", params);
        return mInstanse;
    }

    //topic list

    public CommunicationHandler callForTopicList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        //Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.TOPIC_LIST, API.TOPIC_LIST, "", params);
        return mInstanse;
    }


    //sub topic list

    public CommunicationHandler callForSubTopicList(Context mContext, HashMap<String,String> params)
    {
        this.mContext = mContext;
        //Logger.putLogError("TEST PREP BATCH LIST ==> ",API.TEST_PREP_BATCH_LIST);
        WebServiceHandler.getInstance(this).WebServicePostCall(mContext, Const.SUB_TOPIC_LIST, API.SUB_TOPIC_LIST, "", params);
        return mInstanse;
    }


    @Override
    public void onSuccess(String mMethod, String mResponse)
    {

        Log.i(TAG,"onSuccess");
        if (callback != null)
        {
            Log.i(TAG,"onSuccess callback");
            Log.i(TAG,"mResponse callback"+mResponse);

            try {

                if (mResponse.length() > 0)
                {
                    if (mResponse.startsWith("{"))
                    {
                        JSONObject jsonObject = new JSONObject(mResponse);
                        Log.i(TAG,"jsonObject"+ jsonObject);
                        callback.successCBWithMethod(mMethod, jsonObject, true);
                    }
                    else
                    {
                        String response = mResponse.substring(mResponse.indexOf("{"), mResponse.length());
                        if (response.startsWith("{"))
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            callback.successCBWithMethod(mMethod, jsonObject, true);
                        }
                    }
                }else
                {
                    callback.failureCBWithMethod(mMethod,mResponse);
                }

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(String mMethod, String mError)
    {
        Log.i(TAG,"onFailure");

        if (callback != null)
        {
            Log.i(TAG,"mError");

            callback.failureCBWithMethod(mMethod, mError);
        }

    }

    @Override
    public void onCache(String mMethod, String mResponse)
    {

        Log.i(TAG,"onCache");

        if (callback != null)
        {

            Log.i(TAG,"mResponse");

            callback.cacheCBWithMethod(mMethod, mResponse);

        }

    }
    /**
     * Url encode.
     *
     * @param str the str
     * @return the string
     */
    @SuppressWarnings("unused")
    private String urlEncode(String str)
    {
        if (str != null)
        {
            try
            {
                return URLEncoder.encode(str.trim(), "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return "";
            }

        }
        else
            return "";

    }


    /**
     * The Interface CommunicationHandlerCallBack.
     */
    public interface CommunicationHandlerCallBack
    {
        /**
         * Success c bwith method.
         *
         * @param mMethod    the m method
         * @param jsonObject the json object
         * @param isSuccess  the is success
         */
        void successCBWithMethod(String mMethod, JSONObject jsonObject, boolean isSuccess);

        /**
         * Failure cbwith method.
         *
         * @param mMethod the m method
         * @param mError  the m error
         */
        void failureCBWithMethod(String mMethod, String mError);

        /**
         * Cache cbwith method.
         *
         * @param mMethod   the m method
         * @param mResponse the m response
         */
        void cacheCBWithMethod(String mMethod, String mResponse);

    }
}
