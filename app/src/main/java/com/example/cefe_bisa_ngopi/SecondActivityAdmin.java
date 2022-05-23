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

public class SecondActivityAdmin extends AppCompatActivity {
    private Intent i ;
    private String s;
    private EditText txtIdUser,txtNamaUser,txtPasswordUser,txtTypeUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String user="User";
    private String idUser, namaUser, passwordUser, typeUser;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_admin);
        button= findViewById(R.id.btnSubmit);
        txtIdUser = (EditText)findViewById(R.id.editTextIdUser);
        txtNamaUser =(EditText)findViewById(R.id.editTextNamaUser);
        txtPasswordUser =(EditText)findViewById(R.id.editTextPasswordUser);
        txtTypeUser =(EditText)findViewById(R.id.editTextTypeUser);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference(user);
        button.setText("Submit");
        i = getIntent();
        s=i.getStringExtra("Button");
        if (s.equals("Create")){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    idUser = txtIdUser.getText().toString();
                    namaUser = txtNamaUser.getText().toString();
                    passwordUser = txtPasswordUser.getText().toString();
                    typeUser = txtTypeUser.getText().toString();
                    databaseReference.child(idUser).setValue(new DBHelperAkun(idUser,namaUser,passwordUser,typeUser));
                    Toast.makeText(SecondActivityAdmin.this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    i = new Intent(SecondActivityAdmin.this,AdminActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }else if (s.equals("Edit")){
            String temp;
            HashMap hashMap =  new HashMap();
            idUser = i.getStringExtra("idUser");
            temp = idUser;
            namaUser = i.getStringExtra("namaUser");
            passwordUser = i.getStringExtra("passwordUser");
            typeUser = i.getStringExtra("typeUser");
            txtIdUser.setText(idUser);
            txtNamaUser.setText(namaUser);
            txtPasswordUser.setText(passwordUser);
            txtTypeUser.setText(typeUser);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idUser = txtIdUser.getText().toString();
                    namaUser = txtNamaUser.getText().toString();
                    passwordUser = txtPasswordUser.getText().toString();
                    typeUser = txtTypeUser.getText().toString();
                    hashMap.put("idUser",idUser);
                    hashMap.put("namaUser",namaUser);
                    hashMap.put("passwordUser",passwordUser);
                    hashMap.put("typeUser",typeUser);
                    databaseReference.child(temp).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            i = new Intent(SecondActivityAdmin.this,AdminActivity.class);
                            if (task.isSuccessful()){
                                Toast.makeText(SecondActivityAdmin.this,"Data Updated",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SecondActivityAdmin.this,"Failed to update data",Toast.LENGTH_SHORT).show();
                            }
                            startActivity(i);
                            finish();
                        }
                    });
                }
            });
        }else if (s.equals("Read")){
            idUser = i.getStringExtra("idUser");
            namaUser = i.getStringExtra("namaUser");
            passwordUser = i.getStringExtra("passwordUser");
            typeUser = i.getStringExtra("typeUser");
            button.setText("OK");
            txtIdUser.setText(idUser);
            txtNamaUser.setText(namaUser);
            txtPasswordUser.setText(passwordUser);
            txtTypeUser.setText(typeUser);
            txtTypeUser.setEnabled(false);
            txtPasswordUser.setEnabled(false);
            txtNamaUser.setEnabled(false);
            txtIdUser.setEnabled(false);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i = new Intent(SecondActivityAdmin.this,AdminActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}