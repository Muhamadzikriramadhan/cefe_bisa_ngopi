package com.example.cefe_bisa_ngopi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SecondActivityTransaksi extends AppCompatActivity {
    private Intent i ;
    private String s;
    private EditText txtIdTransaksi,txtMenu,txtTotal,txtIdKasir,txtTimeDate;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String transaksi="Transaksi";
    private String idTransaksi, menu, total, idKasir , timeDate;
    private Button button;
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_transaksi);
        button= findViewById(R.id.btnSubmit);
        txtIdTransaksi = (EditText)findViewById(R.id.editTextIdTransaksi);
        txtMenu =(EditText)findViewById(R.id.editTextMenu);
        txtTotal =(EditText)findViewById(R.id.editTextTotal);
        txtIdKasir =(EditText)findViewById(R.id.editTextIdKasir);
        txtTimeDate =(EditText)findViewById(R.id.editTextTimeDate);
        list = (RecyclerView)findViewById(R.id.list);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference(transaksi);
        button.setText("Submit");
        i = getIntent();
        s=i.getStringExtra("Button");
        if (s.equals("Create")){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idTransaksi = txtIdTransaksi.getText().toString();
                    menu = txtMenu.getText().toString();
                    total = txtTotal.getText().toString();
                    idKasir = txtIdKasir.getText().toString();
                    timeDate = txtTimeDate.getText().toString();
                    databaseReference.child(idTransaksi).setValue(new DBHelperTransaksi(idTransaksi,menu,total,idKasir,timeDate));
                    Toast.makeText(SecondActivityTransaksi.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    i = new Intent(SecondActivityTransaksi.this,KasirActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }else if (s.equals("Edit")){
            String temp;
            HashMap hashMap =  new HashMap();
            idTransaksi = i.getStringExtra("idTransaksi");
            temp = idTransaksi;
            menu = i.getStringExtra("menu");
            total = i.getStringExtra("total");
            idKasir = i.getStringExtra("idKasir");
            timeDate = i.getStringExtra("timeDate");
            txtIdTransaksi.setText(idTransaksi);
            txtMenu.setText(menu);
            txtTotal.setText(total);
            txtIdKasir.setText(idKasir);
            txtTimeDate.setText(timeDate);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idTransaksi = txtTotal.getText().toString();
                    menu = txtTotal.getText().toString();
                    total = txtTotal.getText().toString();
                    idKasir = txtIdKasir.getText().toString();
                    timeDate = txtTimeDate.getText().toString();
                    hashMap.put("idTransaksi",idTransaksi);
                    hashMap.put("menu",menu);
                    hashMap.put("total",total);
                    hashMap.put("idKasir",idKasir);
                    hashMap.put("timeDate",timeDate);
                    databaseReference.child(temp).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            i = new Intent(SecondActivityTransaksi.this,KasirActivity.class);
                            if (task.isSuccessful()){
                                Toast.makeText(SecondActivityTransaksi.this,"Data Updated",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SecondActivityTransaksi.this,"Failed to update data",Toast.LENGTH_SHORT).show();
                            }
                            startActivity(i);
                            finish();
                        }
                    });
                }
            });
        }else if (s.equals("Read")){
            idTransaksi = i.getStringExtra("idTransaksi");
            menu = i.getStringExtra("menu");
            total = i.getStringExtra("total");
            idKasir = i.getStringExtra("idKasir");
            timeDate = i.getStringExtra("timeDate");
            button.setText("OK");
            txtIdTransaksi.setText(idTransaksi);
            txtMenu.setText(menu);
            txtTotal.setText(total);
            txtIdKasir.setText(idKasir);
            txtTimeDate.setText(timeDate);
            txtIdTransaksi.setEnabled(false);
            txtMenu.setEnabled(false);
            txtTotal.setEnabled(false);
            txtIdKasir.setEnabled(false);
            txtTimeDate.setEnabled(false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = new Intent(SecondActivityTransaksi.this,KasirActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}