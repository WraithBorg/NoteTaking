package com.zxu.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.dao.PersonDao;
import com.zxu.demo.entity.Person;
import com.zxu.demo.activity.PersonEditActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonActivity extends Activity {
    List<Map<String, String>> list = new ArrayList<>();
    Person selectedPerson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_demo_person_main);

        listPerson();
    }

    private void listPerson() {

        List<Person> persons = PersonDao.getAll((GaiaApplication) getApplication());


        for (Person person : persons) {
            Map<String, String> map = new HashMap<>();
            map.put("name", person.getName());
            map.put("info", person.getInfo());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[]{"name", "info"}, new int[]{android.R.id.text1, android.R.id.text2});

        ListView listView = (ListView) findViewById(R.id.person_list_id);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPerson = new Person();
                selectedPerson.set_id(position);
                selectedPerson.setName(list.get(position).get("name"));
                selectedPerson.setInfo(list.get(position).get("info"));

            }
        });
    }

    void clickAdd(View view) {
        Intent intent = new Intent(this, PersonEditActivity.class);
        startActivity(intent);

    }

    void clickDel(View view) {

    }

    void clickEdit(View view) {
        Intent intent = new Intent(this, PersonEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", selectedPerson.get_id());
        bundle.putString("NAME", selectedPerson.getName());
        bundle.putString("INFO", selectedPerson.getInfo());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
