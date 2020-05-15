package com.example.letspartytogether;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.letspartytogether.Model.Song;

import java.util.ArrayList;

public class AddSongActivity extends AppCompatActivity {
    public ArrayList<Song> songs;
    private String code;
    public ListView songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//create ArrayList of String
        final ArrayList<String> arrayList = new ArrayList<>();

//Add elements to arraylist
        arrayList.add("android");
        arrayList.add("is");
        arrayList.add("great");
        arrayList.add("and I love it");
        arrayList.add("It");
        arrayList.add("is");
        arrayList.add("better");
        arrayList.add("then");
        arrayList.add("many");
        arrayList.add("other");
        arrayList.add("operating system.");
        arrayList.add("android");
        arrayList.add("is");
        arrayList.add("great");
        arrayList.add("and I love it");
        arrayList.add("It");
        arrayList.add("is");
        arrayList.add("better");
        arrayList.add("then");
        arrayList.add("many");
        arrayList.add("other");
        arrayList.add("operating system.");

////Create Adapter
//        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
//
////assign adapter to listview
//        listView.setAdapter(arrayAdapter);
//
////add listener to listview
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,"clicked item:"+i+" "+arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
