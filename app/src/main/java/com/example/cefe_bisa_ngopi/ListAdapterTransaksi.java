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

public class ListAdapterTransaksi extends RecyclerView.Adapter<ListAdapterTransaksi.ListHolder> {
    private ArrayList<DBHelperTransaksi> arrayList;
    private Context context;
    private DBHelperTransaksi dbHelperTransaksi;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @NonNull
    @Override
    public ListAdapterTransaksi.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_transaksi,parent,false);
        return new ListHolder(view);
    }

    public ListAdapterTransaksi(ArrayList<DBHelperTransaksi> arrayList,Context  context) {
        this.arrayList = arrayList;
        this.context= context;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterTransaksi.ListHolder holder, @SuppressLint("RecyclerView") int position) {
        Intent i = new Intent(context, SecondActivityAdmin.class);
        String s1 = arrayList.get(position).getIdTransaksi();
        holder.textList.setText(s1);
        holder.editList_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelperTransaksi = arrayList.get(position);
                i.putExtra("Button","Edit");
                i.putExtra("idTransaksi",dbHelperTransaksi.getIdTransaksi());
                i.putExtra("menu",dbHelperTransaksi.getMenu());
                i.putExtra("total",dbHelperTransaksi.getTotal());
                i.putExtra("idKasir",dbHelperTransaksi.getIdKasir());
                i.putExtra("timeDate",dbHelperTransaksi.getTimeDate());
                context.startActivity(i);
                KasirActivity.Fa.finish();
            }
        });
        holder.readList_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelperTransaksi = arrayList.get(position);
                i.putExtra("Button","Read");
                i.putExtra("idTransaksi",dbHelperTransaksi.getIdTransaksi());
                i.putExtra("menu",dbHelperTransaksi.getMenu());
                i.putExtra("total",dbHelperTransaksi.getTotal());
                i.putExtra("idKasir",dbHelperTransaksi.getIdKasir());
                i.putExtra("timeDate",dbHelperTransaksi.getTimeDate());
                context.startActivity(i);
                KasirActivity.Fa.finish();
            }
        });
        holder.deleteList_transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelperTransaksi = arrayList.get(position);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference =firebaseDatabase.getReference("Transaksi");
                String temp = dbHelperTransaksi.getIdTransaksi();
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
                KasirActivity.cleanList();
            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ListHolder extends RecyclerView.ViewHolder{
        private TextView textList;
        private Button editList_transaksi,deleteList_transaksi,readList_transaksi;
        public ListHolder(@NonNull View itemView ) {
            super(itemView);
            textList =(TextView) itemView.findViewById(R.id.textTransaksi);
            editList_transaksi =(Button) itemView.findViewById(R.id.editList_transaksi);
            deleteList_transaksi = (Button) itemView.findViewById(R.id.deleteList_transaksi);
            readList_transaksi = (Button) itemView.findViewById(R.id.readList_transaksi);
        }
    }
}
