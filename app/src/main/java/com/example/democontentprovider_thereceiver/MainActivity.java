package com.example.democontentprovider_thereceiver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Student> listObjectStudent;
    private ArrayList<String> listStringStudent;
    private ArrayAdapter<String> adapterStudent;
    private ListView listViewStudent;

    static final String AUTHORITY = "com.example.demosqlite.MyProvider";
    static final String CONTENT_PATH = "backupdata";
    static final String URL = "content://"+AUTHORITY+"/"+CONTENT_PATH;
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int uriCode = 1;
    static UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH, uriCode);
        uriMatcher.addURI(AUTHORITY, CONTENT_PATH+"/*", uriCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        listViewStudent = findViewById(R.id.listViewStudent);
        listObjectStudent = getData();
        listStringStudent = new ArrayList<>();
        convertObjectToString();
        adapterStudent = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listStringStudent);
        listViewStudent.setAdapter(adapterStudent);
    }

    private void convertObjectToString() {
        listStringStudent.clear();
        for (int i=0;i<listObjectStudent.size(); i++){
            listStringStudent.add(listObjectStudent.get(i).toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private ArrayList<Student> getData() {
        ArrayList<Student> listResult = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(CONTENT_URI, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                Student student = new Student();
                student.setId(cursor.getInt(0));
                student.setName(cursor.getString(1));
                student.setScore(cursor.getDouble(2));
                listResult.add(student);
            }while (cursor.moveToNext());
        }
        return listResult;
    }
}
