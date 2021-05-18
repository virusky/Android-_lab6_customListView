package com.example.room_bai02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ItemDao itemDao;
    private AppDatabase db;
    private RecyclerView rcvThing;
    private ItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_02);
        db = Room.databaseBuilder(this, AppDatabase.class, "itemDB").allowMainThreadQueries().build();
        itemDao = db.itemDao();
        rcvThing = findViewById(R.id.rcvThing);
        getDBConnection();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcvThing.setLayoutManager(linearLayoutManager);

        Button btnAdd = findViewById(R.id.btnSave);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });
    }
    public void getDBConnection() {
        List<Item> items = itemDao.getAll();
        itemAdapter=new ItemAdapter(this,items);
        rcvThing.setAdapter(itemAdapter);
    }
    public void addItem(View view) {
        EditText name = findViewById(R.id.edtName);
        int id = itemDao.getAll().size();
        Item item = new Item(id,String.valueOf(name.getText()));
        itemDao.insert(item);
        itemAdapter.notifyDataSetChanged();
        getDBConnection();
    }

}