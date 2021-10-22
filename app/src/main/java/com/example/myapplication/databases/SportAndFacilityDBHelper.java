package com.example.myapplication.databases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.Sport;

/**
 * Helper class for {@link Sport} and {@link Facility} databases.
 *
 * @author Li Xingjian
 * @author Zhong Ruoyu
 * @see <a href="/data/data.db">data.db</a>
 */
public class SportAndFacilityDBHelper {
    private final int BUFFER_SIZE = 400000;
    public static final String DB_NAME = "data.db";
    public static final String PACKAGE_NAME = "com.example.myapplication";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME;

    private SQLiteDatabase database;
    private Context context;

    /**
     * Constructor for class {@code SportAndFacilityDBHelper}.
     * @param context current activity {@link Context}
     */
    public SportAndFacilityDBHelper(Context context) {
        this.context = context;
    }

    /**
     * Assign {@code database} by call {@code openDatabase(String)}.
     *
     * @see SportAndFacilityDBHelper#openDatabase(String)
     */
    public void openDatabase() {
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    /**
     * Open SQLite database in {@code DB_PATH}
     * @param dbFile database file path in {@link String}
     * @reutrn the SQLiteDatabase in path, {@code null} if not exists
     */
    private SQLiteDatabase openDatabase(String dbFile) {
        try {
            if (!(new File(dbFile).exists())){
                InputStream is = this.context.getResources().openRawResource(R.raw.data);
                FileOutputStream fos = new FileOutputStream(dbFile);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbFile,null);
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all data of sport in a list.
     *
     * @return list of {@link Sport}, {@code null} if {@code cursor} pointer null
     */
    public List<Sport> getSports() {
        Cursor cursor = database.rawQuery("SELECT * FROM sports", null);
        if (cursor != null) {
            List<Sport> sportList = new ArrayList<Sport>();
            if (cursor.getCount()>0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String alternativeName = cursor.getString(cursor.getColumnIndexOrThrow("alternative_name"));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    Sport sport = new Sport(id, name, alternativeName, Sport.SportType.getType(type));
                    sportList.add(sport);
                } while (cursor.moveToNext());
            }
            return sportList;
        } else {
            return null;
        }
    }

    /**
     * Get all data of facility in a list.
     *
     * @return list of {@link Facility}, {@code null} if {@code cursor} pointer null
     */
    public List<Facility> getFacilities() {
        Cursor cursor = database.rawQuery("SELECT * FROM facilities", null);
        if (cursor != null) {
            List<Sport> sportList = getSports();
            List<Facility> facilityList = new ArrayList<Facility>();
            if (cursor.getCount() > 0) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
                    String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String postalCode = cursor.getString(cursor.getColumnIndexOrThrow("postal_code"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("description")).replace(";","\n");
                    double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude"));
                    double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude"));
                    List<Integer> sportIDs = Stream.of(cursor.getString(cursor.getColumnIndexOrThrow("sports")).split(",")).map(Integer::parseInt).collect(Collectors.toList());
                    Facility facility = new Facility(id, name, url, address, postalCode, description, latitude, longitude);
                    sportList.stream().filter(sport->sportIDs.contains(sport.getId())).forEach(facility::addSport);
                } while (cursor.moveToNext());
            }
            return facilityList;
        }else{
            return null;
        }
    }

    /**
     * Close current SQLite database.
     */
    public void closeDatabase() {
        this.database.close();
    }
}