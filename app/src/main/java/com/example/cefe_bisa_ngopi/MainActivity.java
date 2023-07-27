package com.example.cefe_bisa_ngopi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText namaUser, passwordUser;
    private Button btn_login;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namaUser = findViewById(R.id.namaUser);
        passwordUser = findViewById(R.id.passwordUser);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("User").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        String input1 = namaUser.getText().toString();
                        String input2 = passwordUser.getText().toString();
                        if (datasnapshot.child(input1).exists()) {
                            if (datasnapshot.child(input1).child("passwordUser").getValue(String.class).equals(input2)) {
                                if (datasnapshot.child(input1).child("typeUser").getValue(String.class).equals("admin")) {
                                    preferences.setDataLogin(MainActivity.this, true);
                                    preferences.setDataAs(MainActivity.this, "admin");
                                    startActivity(new Intent(MainActivity.this, AdminActivity.class));
                                } else if (datasnapshot.child(input1).child("typeUser").getValue(String.class).equals("kasir")){
                                    preferences.setDataLogin(MainActivity.this, true);
                                    preferences.setDataAs(MainActivity.this, "kasir");
                                    startActivity(new Intent(MainActivity.this, KasirActivity.class));
                                } else if (datasnapshot.child(input1).child("typeUser").getValue(String.class).equals("manajer")){
                                    preferences.setDataLogin(MainActivity.this, true);
                                    preferences.setDataAs(MainActivity.this, "manajer");
                                    startActivity(new Intent(MainActivity.this, ManajerActivity.class));
                                }

                            } else {
                                Toast.makeText(MainActivity.this, "Kata sandi salah", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Data belum terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)) {
            if (preferences.getDataAs(this).equals("admin")) {
                startActivity(new Intent(this, AdminActivity.class));
                finish();
            } else if (preferences.getDataAs(this).equals("kasir")) {
                startActivity(new Intent(this, KasirActivity.class));
                finish();
            } else if (preferences.getDataAs(this).equals("manajer")) {
                startActivity(new Intent(this, ManajerActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, ManajerActivity.class));
                finish();
            }
        }
    }
}