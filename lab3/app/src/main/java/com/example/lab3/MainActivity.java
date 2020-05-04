package com.example.lab3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private FragmentOutput output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output = (FragmentOutput) getFragmentManager().findFragmentById(R.id.fragment_output);
    }

    public void transferData(String author, String year) {
        output.outputSelectedItems(author, year);
        insertRecord(author, year);
    }

    public void insertRecord(String author, String year) {
        String db_name = getResources().getString(R.string.db_name);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        String table_name = getResources().getString(R.string.table_name);
        String create_table = getResources().getString(R.string.create_table);
        String author_column_name = getResources().getString(R.string.author_column_name);
        String year_column_name = getResources().getString(R.string.year_column_name);

        ContentValues cv = new ContentValues();
        cv.put(author_column_name, author);
        cv.put(year_column_name, year);

        db.execSQL(String.format(create_table, table_name, author_column_name, year_column_name));
        db.insert(table_name, null, cv);

        db.close();

        String msg = getResources().getString(R.string.record_was_added_msg);
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void clearTable() {
        String db_name = getResources().getString(R.string.db_name);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        String records_table_name = getResources().getString(R.string.table_name);
        String delete_from_table = getResources().getString(R.string.delete_from_table);

        db.execSQL(String.format(delete_from_table, records_table_name));

        db.close();

        String msg = getResources().getString(R.string.table_was_cleared_msg);
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public List<String> getAllRecords() {
        List<String> allRecords = new ArrayList<>();
        String db_name = getResources().getString(R.string.db_name);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase(db_name, MODE_PRIVATE, null);

        String select_all_from_table = getResources().getString(R.string.select_all_from_table);
        String records_table_name = getResources().getString(R.string.table_name);

        Cursor query = db.rawQuery(String.format(select_all_from_table, records_table_name), null);

        if(query.moveToFirst()){
            do {
                int id = query.getInt(0);
                String author = query.getString(1);
                String year = query.getString(2);

                allRecords.add("id: " + id + ", Author: " + author + ", Year: " + year + ";\n");
            } while(query.moveToNext());
        }

        query.close();
        db.close();

        return allRecords;
    }

    public void showTable() {
        Intent intent = new Intent(this, ShowTableActivity.class);

        intent.putStringArrayListExtra("allRecords", (ArrayList<String>) getAllRecords());

        startActivity(intent);
    }
}
