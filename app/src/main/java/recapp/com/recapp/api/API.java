package recapp.com.recapp.api;

public class API
{


    private static final String BASE_URL = "https://write2speak.com/recapp/apitest/";

    public static final String LOGIN = BASE_URL + "login";
    public static final String REGISTRATION = BASE_URL + "registration";
    public static final String WITHOUT_CODE_REGISTRATION = BASE_URL + "registration";
    public static final String CATEGORY_LIST = BASE_URL + "registration/get_category";
    public static final String SUB_CATEGORY_LIST = BASE_URL + "registration/get_subcategory";
    public static final String SUBJECT_LIST = BASE_URL + "dropdown/get_subject_dd";
    public static final String TOPIC_LIST = BASE_URL + "dropdown/get_topic_dd";
    public static final String SUB_TOPIC_LIST = BASE_URL + "dropdown/get_subtopic_dd";


    // School Registration Apis
    public static final String SCHOOL_REG = BASE_URL + "registration/reg_school_form";
    public static final String CLASS_LIST = BASE_URL + "registration/get_class";
    public static final String CLASS_SECTION_LIST = BASE_URL + "registration/get_class_section";
    public static final String CLASS_SUBJECT_LIST = BASE_URL + "registration/get_class_subject";


    // Tuition Registration Apis
    public static final String TUITION_REG = BASE_URL + "registration/reg_tuition_form";
    public static final String TUITION_CLASS_LIST = BASE_URL + "dropdown/get_class_dd";
    public static final String TUITION_BATCH_LIST = BASE_URL + "dropdown/get_batch_dd";
    public static final String TUITION_TIMINIGS_LIST = BASE_URL + "registration/get_timing";
    public static final String TUITION_SUBJECT_LIST = BASE_URL + "dropdown/get_subject";


    // College Registration Apis
    public static final String COLLEGE_REG = BASE_URL + "registration/reg_college_form";
    public static final String COLLEGE_YEAR_LIST = BASE_URL + "dropdown/get_college_year_dd";
    public static final String COLLEGE_SEMESTER_LIST = BASE_URL + "dropdown/get_college_sem_dd";
    public static final String COLLEGE_BRANCH_LIST = BASE_URL + "dropdown/get_college_branch_dd";
    public static final String COLLEGE_SECTION_LIST = BASE_URL + "dropdown/get_college_section_dd";

    // Test Prep Registration Apis
    public static final String TEST_PREP_REG = BASE_URL + "registration/reg_testprepare_training_form";
    public static final String TEST_PREP_BATCH_LIST = BASE_URL + "dropdown/get_test_prepare_batch_dd";

    // Training Registration Apis
    public static final String TRAINING_REG = BASE_URL + "registration/reg_testprepare_training_form";
    public static final String TRAINING_BATCH_LIST = BASE_URL + "dropdown/get_training_batch_dd";

    // Langugage Registration Apis
    public static final String LANGUAGE_REG = BASE_URL + "registration/reg_languages_reg_form";
    public static final String LANGUAGE_LANGUAGE_LIST = BASE_URL + "dropdown/get_languages_dd";
    public static final String LANGUAGE_LEVEL_LIST = BASE_URL + "registration/get_timing";
    public static final String LANGUAGE_BATCH_LIST = BASE_URL + "dropdown/get_languages_batch_dd";
    public static final String LANGUAGE_TIME_LIST = BASE_URL + "dropdown/get_timing";


    // Music Registration Apis
    public static final String MUSIC_REG = BASE_URL + "registration/reg_music_reg_form";
    public static final String MUSIC_INSTRUMENT_LIST = BASE_URL + "dropdown/get_instrument_dd";
    public static final String MUSIC_BATCH_LIST = BASE_URL + "dropdown/get_instrument_batch_dd";
    public static final String MUSIC_TIME_LIST = BASE_URL + "dropdown/get_timing";
}
