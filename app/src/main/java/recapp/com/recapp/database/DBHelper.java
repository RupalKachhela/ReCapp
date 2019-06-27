package recapp.com.recapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper
{

    private static final int DB_VERSION = 12;
    private static final String DB_NAME = "RecappAudioDatabse";
    private static final String TABLE_Users = "audiodetails";
    private static final String KEY_ID = "id";
    private static final String KEY_FILE_NAME = "file_name";
    private static final String KEY_SUBJECT_NAME = "subject_name";
    private static final String KEY_TOPIC_NAME = "topic_name";
    private static final String KEY_SUBTOPIC_NAME = "sub_topic_name";
    private static final String KEY_AUDIO_TYPE = "audio_type";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_AUDIO = "blob_audio";
    private static final String KEY_IMAGE = "blob_image";
    private static final String KEY_RECORD_ID = "record_id";
    private static final String KEY_CATEGORY_NAME = "category_name";


    public DBHelper(Context context)
    {
        super(context,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USER_ID + " TEXT,"
                + KEY_CATEGORY_NAME + " TEXT,"
                + KEY_SUBJECT_NAME + " TEXT,"
                + KEY_TOPIC_NAME + " TEXT,"
                + KEY_SUBTOPIC_NAME + " TEXT,"
                + KEY_AUDIO_TYPE + " TEXT,"
                + KEY_FILE_NAME + " TEXT,"
                + KEY_AUDIO + " BLOB NOT NULL,"
                + KEY_IMAGE + " BLOB NOT NULL "+ ")";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);

    }
    public void databseClear()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users,null,null);
        db.close();

    }

    // Adding new audio Details
    public  void insertUserDetails(String userid , String filename ,  String categoryname ,String subjectname , String topicname , String subtopicname , String audiotype , byte[]  audio,byte[] image )
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_USER_ID, userid);
        cValues.put(KEY_CATEGORY_NAME, categoryname);
        cValues.put(KEY_SUBJECT_NAME, subjectname);
        cValues.put(KEY_TOPIC_NAME, topicname);
        cValues.put(KEY_SUBTOPIC_NAME, subtopicname);
        cValues.put(KEY_AUDIO_TYPE, audiotype);
        cValues.put(KEY_FILE_NAME, filename);
        cValues.put(KEY_AUDIO,audio);
        cValues.put(KEY_IMAGE, image);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users,null, cValues);
        System.out.println("===new row :"+newRowId);
        db.close();

    }

    // Get Audio list  Details
    public ArrayList<HashMap<String, String>> GetRecordList()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_Users;
        Cursor cursor = db.rawQuery(query, null);


 /*       try {
            while (cursor.moveToNext()) {
                HashMap<String, String> user = new HashMap<>();
//            user.put("record_id", cursor.getString(cursor.getColumnIndex(KEY_RECORD_ID)));
                user.put("user_id", cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
                user.put("category_name", cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME)));
                user.put("subject_name", cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));
                user.put("topic_name", cursor.getString(cursor.getColumnIndex(KEY_TOPIC_NAME)));
                user.put("sub_topic_name", cursor.getString(cursor.getColumnIndex(KEY_SUBTOPIC_NAME)));
                user.put("audio_type", cursor.getString(cursor.getColumnIndex(KEY_AUDIO_TYPE)));
                user.put("file_name", cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME)));
                userList.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }*/

        while (cursor.moveToNext())
        {

            HashMap<String, String> user = new HashMap<>();
//            user.put("record_id", cursor.getString(cursor.getColumnIndex(KEY_RECORD_ID)));
            user.put("user_id", cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
            user.put("category_name", cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME)));
            user.put("subject_name", cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));
            user.put("topic_name", cursor.getString(cursor.getColumnIndex(KEY_TOPIC_NAME)));
            user.put("sub_topic_name", cursor.getString(cursor.getColumnIndex(KEY_SUBTOPIC_NAME)));
            user.put("audio_type", cursor.getString(cursor.getColumnIndex(KEY_AUDIO_TYPE)));
            user.put("file_name", cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME)));
            userList.add(user);

        }
        return userList;
    }

    public ArrayList<HashMap<String, String>> GetRecordListByCategory(String subjectname , String userId)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_RECORD_ID,KEY_USER_ID,KEY_CATEGORY_NAME,KEY_SUBJECT_NAME,KEY_TOPIC_NAME,KEY_SUBTOPIC_NAME,KEY_AUDIO_TYPE,KEY_FILE_NAME}, KEY_SUBJECT_NAME+ "=? AND " + KEY_USER_ID+ "=?",new String[]{subjectname , userId},null, null, null, null);

      //  String query = "SELECT * FROM " + TABLE_Users;
      //  Cursor cursor = db.query(true, TABLE_Users, new String[] {KEY_RECORD_ID,KEY_USER_ID,KEY_CATEGORY_NAME,KEY_SUBJECT_NAME,KEY_TOPIC_NAME,KEY_SUBTOPIC_NAME,KEY_AUDIO_TYPE,KEY_FILE_NAME}, null,null, KEY_TOPIC_NAME, null, null, null);

       // Cursor cursor = db.rawQuery(query, null);


        while (cursor.moveToNext())
        {

            HashMap<String, String> user = new HashMap<>();
//            user.put("record_id", cursor.getString(cursor.getColumnIndex(KEY_RECORD_ID)));
            user.put("user_id", cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
            user.put("category_name", cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME)));
            user.put("subject_name", cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));
            user.put("topic_name", cursor.getString(cursor.getColumnIndex(KEY_TOPIC_NAME)));
            user.put("sub_topic_name", cursor.getString(cursor.getColumnIndex(KEY_SUBTOPIC_NAME)));
            user.put("audio_type", cursor.getString(cursor.getColumnIndex(KEY_AUDIO_TYPE)));
            user.put("file_name", cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME)));
            userList.add(user);

        }
        return userList;
    }
    public void addAudioFile(int recordid , String userid, byte[]  audio,byte[] image)
    {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(KEY_AUDIO,  audio);
        cv.put(KEY_IMAGE,  image);

        long rowupdate = database.update(TABLE_Users,  cv ,KEY_RECORD_ID+"=? AND " + KEY_USER_ID+ "=?",new String[]{String.valueOf(recordid) , userid});
        System.out.println("===updated row :"+rowupdate);

    }
    public  ArrayList<HashMap<String, byte[]>> retreiveAudioFromDB()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, byte[]>> audioList = new ArrayList<>();
         String querdy = "SELECT * FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(querdy, null);
       // Cursor cursor = db.query(TABLE_Users, new String[]{KEY_RECORD_ID,KEY_USER_ID,KEY_CATEGORY_NAME,KEY_SUBJECT_NAME,KEY_TOPIC_NAME,KEY_SUBTOPIC_NAME,KEY_AUDIO_TYPE,KEY_FILE_NAME}, KEY_SUBJECT_NAME+ "=? AND " + KEY_CATEGORY_NAME+ "=?",new String[]{subjectname , categoryName},null, null, null, null);

        //String[] columns = new String[]{ KEY_USER_ID, KEY_CATEGORY_NAME, KEY_SUBJECT_NAME,KEY_TOPIC_NAME,KEY_SUBTOPIC_NAME,KEY_AUDIO_TYPE,KEY_FILE_NAME,KEY_AUDIO,KEY_IMAGE};
       // cursor = db.query(TABLE_Users, columns, null, null, null, null, null);

       // Cursor cursor = db.query(TABLE_Users, new String[]{KEY_AUDIO,KEY_IMAGE},  KEY_USER_ID+ "=?",new String[]{userid},null, null, null, null);

        while (cursor.moveToNext())
        {
            HashMap<String,byte[]> user = new HashMap<>();
            user.put("audio_file",cursor.getBlob(cursor.getColumnIndex(KEY_AUDIO)));
            user.put("image_file",cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE)));
            audioList.add(user);

        }

        return  audioList;

        /*
        if (cursor.moveToFirst())
        {
            byte[] audioBlob = cursor.getBlob(cursor.getColumnIndex(KEY_AUDIO));
            String  userID = cursor.getString(cursor.getColumnIndex(KEY_USER_ID));
            byte[]  imageBlob= cursor.getBlob(cursor.getColumnIndex(KEY_USER_ID));

            System.out.println("---userid : "+userID);
            cursor.close();
          //  return audioBlob;
        }*/

    }

    public  ArrayList<HashMap<String, byte[]>> retreiveAudioFromDBByCategory(String subjectname , String userId)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, byte[]>> audioList = new ArrayList<>();

         Cursor cursor = db.query(TABLE_Users, new String[]{KEY_AUDIO , KEY_IMAGE}, KEY_SUBJECT_NAME+ "=? AND " + KEY_USER_ID+ "=?",new String[]{subjectname , userId},null, null, null, null);

        //String[] columns = new String[]{ KEY_USER_ID, KEY_CATEGORY_NAME, KEY_SUBJECT_NAME,KEY_TOPIC_NAME,KEY_SUBTOPIC_NAME,KEY_AUDIO_TYPE,KEY_FILE_NAME,KEY_AUDIO,KEY_IMAGE};
        // cursor = db.query(TABLE_Users, columns, null, null, null, null, null);

        // Cursor cursor = db.query(TABLE_Users, new String[]{KEY_AUDIO,KEY_IMAGE},  KEY_USER_ID+ "=?",new String[]{userid},null, null, null, null);

        while (cursor.moveToNext())
        {
            HashMap<String,byte[]> user = new HashMap<>();
            user.put("audio_file",cursor.getBlob(cursor.getColumnIndex(KEY_AUDIO)));
            user.put("image_file",cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE)));
            audioList.add(user);

        }

        return  audioList;



    }

    // Get audio  Details based on userid
    public ArrayList<HashMap<String, String>> GetAudioByUserId( String userid)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
       // String querdy = "SELECT * FROM "+ TABLE_Users;
        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_RECORD_ID,KEY_USER_ID,KEY_CATEGORY_NAME,KEY_SUBJECT_NAME,KEY_TOPIC_NAME,KEY_SUBTOPIC_NAME,KEY_AUDIO_TYPE,KEY_FILE_NAME}, KEY_USER_ID+ "=?",new String[]{userid},null, null, null, null);

        if (cursor.moveToNext())
        {
            HashMap<String,String> user = new HashMap<>();
            user.put("record_id",cursor.getString(cursor.getColumnIndex(KEY_RECORD_ID)));
            user.put("user_id",cursor.getString(cursor.getColumnIndex(KEY_USER_ID)));
            user.put("category_name",cursor.getString(cursor.getColumnIndex(KEY_CATEGORY_NAME)));
            user.put("subject_name",cursor.getString(cursor.getColumnIndex(KEY_SUBJECT_NAME)));
            user.put("topic_name",cursor.getString(cursor.getColumnIndex(KEY_TOPIC_NAME)));
            user.put("sub_topic_name",cursor.getString(cursor.getColumnIndex(KEY_SUBTOPIC_NAME)));
            user.put("audio_type",cursor.getString(cursor.getColumnIndex(KEY_AUDIO_TYPE)));
            user.put("file_name",cursor.getString(cursor.getColumnIndex(KEY_FILE_NAME)));
            userList.add(user);

        }
        return  userList;

    }

    // Delete audio Details
    public void DeleteRecord(int userid)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Users, KEY_RECORD_ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }

}
