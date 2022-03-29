/*
 * File              : SmetaContentMenu.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 28.03.2022
 * Last Modified Date: 28.03.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.astroybat.R;
import com.example.astroybat.classes.Item;
import com.example.astroybat.classes.Smeta;
import com.example.astroybat.adapter.ItemAdapter;

import java.util.ArrayList;

public class SmetaContentMenu extends AppCompatActivity {

    private native Smeta getSmeta(String uuid);
    native void getAllItemsForSmeta(String smeta_uuid);
    native void removeItem(String item_uuid);

    Smeta smeta;
    String uuid;
    ArrayList<Item> items;

    Button add_button;
    TextView title;
    ListView contentView;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smeta_content_menu);

        //get extra
        Intent intent = getIntent();
        uuid = intent.getStringExtra("uuid");

        //smeta init
        smeta = getSmeta(uuid);

        //Set title
        title = findViewById(R.id.Smeta_title);
        title.setText(smeta.title);

        //Getting items list
        getAllItemsForSmeta(uuid);

        //Список
        contentView = findViewById(R.id.content_lv);
        items = new ArrayList<>();
        adapter = new ItemAdapter(this, items);
        contentView.setAdapter(adapter);

        //add new item
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            openItemListActivity();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_menu, menu);

        return true;
    }

    private void openItemListActivity(){
        Intent intent = new Intent(this, ItemList.class);
        startActivity(intent);
    }

    void getAllItemsForSmetaCallback(Item item){
        items.add(item);
        adapter.notifyDataSetChanged();
    }

}
