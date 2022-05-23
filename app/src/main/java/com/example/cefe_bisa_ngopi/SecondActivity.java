package com.example.cefe_bisa_ngopi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class SecondActivity extends AppCompatActivity {
    private Intent i ;
    private String s;
    private EditText txtIdMenu,txtNamaMenu,txtHargaMenu;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String user="Menu";
    private String idMenu, namaMenu, hargaMenu;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button= findViewById(R.id.btnSubmit);
        txtIdMenu = (EditText)findViewById(R.id.editTextIdMenu);
        txtNamaMenu =(EditText)findViewById(R.id.editTextNamaMenu);
        txtHargaMenu =(EditText)findViewById(R.id.editTextHargaMenu);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference(user);
        button.setText("Submit");
        i = getIntent();
        s=i.getStringExtra("Button");
        if (s.equals("Create")){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idMenu = txtIdMenu.getText().toString();
                    namaMenu = txtNamaMenu.getText().toString();
                    hargaMenu = txtHargaMenu.getText().toString();
                    databaseReference.child(idMenu).setValue(new DBHelper(idMenu,namaMenu,hargaMenu));
                    Toast.makeText(SecondActivity.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    i = new Intent(SecondActivity.this,ManajerActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }else if (s.equals("Edit")){
            String temp;
            HashMap hashMap =  new HashMap();
            idMenu = i.getStringExtra("idMenu");
            temp = idMenu;
            namaMenu = i.getStringExtra("namaMenu");
            hargaMenu = i.getStringExtra("hargaMenu");
            txtIdMenu.setText(idMenu);
            txtNamaMenu.setText(namaMenu);
            txtHargaMenu.setText(hargaMenu);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idMenu = txtIdMenu.getText().toString();
                    namaMenu = txtNamaMenu.getText().toString();
                    hargaMenu = txtHargaMenu.getText().toString();
                    hashMap.put("idMenu",idMenu);
                    hashMap.put("namaMenu",namaMenu);
                    hashMap.put("hargaMenu",hargaMenu);
                    databaseReference.child(temp).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            i = new Intent(SecondActivity.this,ManajerActivity.class);
                            if (task.isSuccessful()){
                                Toast.makeText(SecondActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SecondActivity.this,"Failed to update data",Toast.LENGTH_SHORT).show();
                            }
                            startActivity(i);
                            finish();
                        }
                    });
                }
            });
        }else if (s.equals("Read")){
            idMenu = i.getStringExtra("idMenu");
            namaMenu = i.getStringExtra("namaMenu");
            hargaMenu = i.getStringExtra("hargaMenu");
            button.setText("OK");
            txtIdMenu.setText(idMenu);
            txtNamaMenu.setText(namaMenu);
            txtHargaMenu.setText(hargaMenu);
            txtHargaMenu.setEnabled(false);
            txtNamaMenu.setEnabled(false);
            txtIdMenu.setEnabled(false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = new Intent(SecondActivity.this,ManajerActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}