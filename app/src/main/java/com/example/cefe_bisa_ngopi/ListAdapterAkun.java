package com.example.cefe_bisa_ngopi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAdapterAkun extends RecyclerView.Adapter<ListAdapterAkun.ListHolder> {
    private ArrayList<DBHelperAkun> arrayList;
    private Context context;
    private DBHelperAkun dbHelperAkun;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_akun,parent,false);
        return new ListHolder(view);
    }

    public ListAdapterAkun(ArrayList<DBHelperAkun> arrayList,Context  context) {
        this.arrayList = arrayList;
        this.context= context;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterAkun.ListHolder holder, @SuppressLint("RecyclerView") int position) {
        Intent i = new Intent(context, SecondActivityAdmin.class);
        String s1 = arrayList.get(position).getIdUser();
        holder.textList.setText(s1);
        holder.editList_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelperAkun = arrayList.get(position);
                i.putExtra("Button","Edit");
                i.putExtra("idUser",dbHelperAkun.getIdUser());
                i.putExtra("namaUser",dbHelperAkun.getNamaUser());
                i.putExtra("passwordUser",dbHelperAkun.getPasswordUser());
                i.putExtra("typeUser",dbHelperAkun.getTypeUser());
                context.startActivity(i);
                AdminActivity.Fa.finish();
            }
        });
        holder.readList_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelperAkun = arrayList.get(position);
                i.putExtra("Button","Read");
                i.putExtra("idUser",dbHelperAkun.getIdUser());
                i.putExtra("namaUser",dbHelperAkun.getNamaUser());
                i.putExtra("passwordUser",dbHelperAkun.getPasswordUser());
                i.putExtra("typeUser",dbHelperAkun.getTypeUser());
                context.startActivity(i);
                AdminActivity.Fa.finish();
            }
        });
        holder.deleteList_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelperAkun = arrayList.get(position);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference =firebaseDatabase.getReference("User");
                String temp =dbHelperAkun.getIdUser();
                databaseReference.child(temp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context,"Data Removed",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Failed to remove data",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AdminActivity.cleanList();
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ListHolder extends RecyclerView.ViewHolder{
        private TextView textList;
        private Button editList_akun,deleteList_akun,readList_akun;
        public ListHolder(@NonNull View itemView ) {
            super(itemView);
            textList =(TextView) itemView.findViewById(R.id.textAkun);
            editList_akun =(Button) itemView.findViewById(R.id.editList_akun);
            deleteList_akun = (Button) itemView.findViewById(R.id.deleteList_akun);
            readList_akun = (Button) itemView.findViewById(R.id.readList_akun);
        }
    }
}
