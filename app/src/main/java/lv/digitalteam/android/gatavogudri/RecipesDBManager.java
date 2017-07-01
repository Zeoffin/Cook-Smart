package lv.digitalteam.android.gatavogudri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipesDBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipes.db";
    private static final String TABLE_NAME="recipes";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_TITLE="title";
    private static final String COLUMN_DESC="description";
    private static final String COLUMN_INGR="ingredients";
    private static final String COLUMN_PREP="preperations";
    private static final String COLUMN_IMG="image";


    public RecipesDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_INGR + " TEXT, " +
                COLUMN_PREP + " TEXT, " +
                COLUMN_IMG + " BLOB " +
                ");";

        db.execSQL(query);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    //Create a new data
    public boolean newMessage(String title, String description, String ingredients, String preperations, byte[] image) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESC, description);
        contentValues.put(COLUMN_INGR, ingredients);
        contentValues.put(COLUMN_PREP, preperations);
        contentValues.put(COLUMN_IMG, image);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;

    }


    //Gets all data
    public Cursor getAll() {

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return cursor;

    }


    //To update
    public boolean update(String id, String title, String description, String ingredients, String preperations, String image) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESC, description);
        contentValues.put(COLUMN_INGR, ingredients);
        contentValues.put(COLUMN_PREP, preperations);
        contentValues.put(COLUMN_IMG, image);

        database.update(TABLE_NAME, contentValues, "id = ?", new String[] {id});

        return true;
    }

    
    //Delete
    public Integer delete(String id) {

        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, "id = ?", new String[] {id});

    }

}
