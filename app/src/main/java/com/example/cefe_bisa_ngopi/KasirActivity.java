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

public class KasirActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static ArrayList<DBHelperTransaksi> arrayList  = new ArrayList<>();
    private RecyclerView list_transaksi;
    private Button btn_createTransaksi;
    public static Activity Fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasir);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Transaksi");
        getUsers();
        btn_createTransaksi = findViewById(R.id.btn_createTransaksi);
        list_transaksi = findViewById(R.id.list_transaksi);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list_transaksi.getContext(), new LinearLayoutManager(this).getOrientation());
        list_transaksi.addItemDecoration(dividerItemDecoration);
        list_transaksi.setLayoutManager(new LinearLayoutManager(this));
        list_transaksi.setAdapter(new ListAdapterTransaksi(arrayList,this ));
        btn_createTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KasirActivity.this,SecondActivityTransaksi.class);
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
            String idTransaksi, menu, total, idKasir, timeDate;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    idTransaksi =dataSnapshot.child("idTransaksi").getValue().toString();
                    menu = dataSnapshot.child("menu").getValue().toString();
                    total = dataSnapshot.child("total").getValue().toString();
                    idKasir = dataSnapshot.child("idKasir").getValue().toString();
                    timeDate = dataSnapshot.child("timeDate").getValue().toString();
                    arrayList.add(new DBHelperTransaksi(idTransaksi,menu,total, idKasir,timeDate));;
                }
                list_transaksi.setAdapter(new ListAdapterTransaksi(arrayList,KasirActivity.this));
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