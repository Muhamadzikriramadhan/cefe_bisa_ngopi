package com.example.cefe_bisa_ngopi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManajerActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static ArrayList<DBHelper> arrayList  = new ArrayList<>();
    private RecyclerView list;
    private Button btnCreate;
    public static Activity Fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajer);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Menu");
        getUsers();
        btnCreate = findViewById(R.id.btn_CreateMain);
        list = findViewById(R.id.list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), new LinearLayoutManager(this).getOrientation());
        list.addItemDecoration(dividerItemDecoration);
        list.setLayoutManager(new LinearLayoutManager(this));
        arrayList.clear();
        list.setAdapter(new ListAdapter(arrayList,this ));
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ManajerActivity.this,SecondActivity.class);
                String s;
                s="Create";
                i.putExtra("Button",s);
                startActivity(i);
                finish();
            }
        });
    }
    private void getUsers() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            String idMenu, namaMenu, hargaMenu;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    idMenu = dataSnapshot.child("idMenu").getValue().toString();
                    namaMenu = dataSnapshot.child("namaMenu").getValue().toString();
                    hargaMenu = dataSnapshot.child("hargaMenu").getValue().toString();
                    arrayList.add(new DBHelper(idMenu,namaMenu,hargaMenu));;
                }
                list.setAdapter(new ListAdapter(arrayList,ManajerActivity.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public static void cleanList(){
        arrayList.clear();
    }
}