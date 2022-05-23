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

public class AdminActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private static ArrayList<DBHelperAkun> arrayList  = new ArrayList<>();
    private RecyclerView list_akun;
    private Button btnCreate;
    public static Activity Fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        getUsers();
        btnCreate = findViewById(R.id.btn_CreateMain);
        list_akun = findViewById(R.id.list_akun);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list_akun.getContext(), new LinearLayoutManager(this).getOrientation());
        list_akun.addItemDecoration(dividerItemDecoration);
        list_akun.setLayoutManager(new LinearLayoutManager(this));
        list_akun.setAdapter(new ListAdapterAkun(arrayList,this ));
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,SecondActivityAdmin.class);
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
            String idUser, namaUser, passwordUser, typeUser;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    idUser = dataSnapshot.child("idUser").getValue().toString();
                    namaUser = dataSnapshot.child("namaUser").getValue().toString();
                    passwordUser = dataSnapshot.child("passwordUser").getValue().toString();
                    typeUser = dataSnapshot.child("typeUser").getValue().toString();
                    arrayList.add(new DBHelperAkun(idUser,namaUser,passwordUser,typeUser));;
                }
                list_akun.setAdapter(new ListAdapterAkun(arrayList,AdminActivity.this));
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