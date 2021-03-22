package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Tekstas su A raidėmis");
        arrayList.add("Be ieškomo simbolio");
        arrayList.add("Čia yra ieškomas simbolis");

        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arrayList.get(position).contains("a") || arrayList.get(position).contains("A")) {
                    showFragment(new SymbolSearchFragment(), arrayList.get(position));
                } else {
                    showFragment(new TextStatisticsFragment(), arrayList.get(position));
                }
            }
        });
    }

    private void showFragment(Fragment fragment, String text){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack("back");
        fragmentTransaction.commit();
        Bundle bundle = new Bundle();
        bundle.putString("editText", text);
        fragment.setArguments(bundle);
    }
}