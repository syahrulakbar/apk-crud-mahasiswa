package com.example.mahasiswaku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<data_mahasiswa> listMahasiswa;
    private Context context;


    public interface dataListener{
        void onDeleteData(data_mahasiswa data, int position);
    }
    dataListener listener;
    public RecyclerViewAdapter(ArrayList<data_mahasiswa> listMahasiswa, Context context){
        this.listMahasiswa = listMahasiswa;
        this.context = context;
        listener = (MainActivity)context;

    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        final String nim = listMahasiswa.get(position).getNim();
        final String nama = listMahasiswa.get(position).getNama();
        final String fakultas = listMahasiswa.get(position).getFakultas();
        final String prodi = listMahasiswa.get(position).getProdi();
        final String goldar = listMahasiswa.get(position).getGoldar();
        final String jk = listMahasiswa.get(position).getJk();
        final String tgl_lahir = listMahasiswa.get(position).getTgl_lahir();
        final String nomorhp = listMahasiswa.get(position).getNomorhp();
        final String email = listMahasiswa.get(position).getEmail();
        final String ipk = listMahasiswa.get(position).getIpk();
        final String alamat = listMahasiswa.get(position).getAlamat();

        holder.nimTextView.setText(nim);
        holder.namaTextView.setText(nama);
        holder.fakultasTextView.setText(fakultas);
        holder.prodiTextView.setText(prodi);
        holder.ipkTextView.setText(ipk);
        holder.emailTextView.setText(email);
        holder.noHpTextView.setText(nomorhp);
        holder.jkTextView.setText(jk);
        holder.tanggalTextView.setText(tgl_lahir);
        holder.goldarTextView.setText(goldar);
        holder.alamatTextView.setText(alamat);

        // Metode alert dialog
        holder.ic_dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] action = {"Update","Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
//                                getNim, getNama, getFakultas, getProdi, getGoldar, getJk, getTgl_lahir,getNomorhp,getEmail,getIpk,getAlamat
                                Bundle bundle = new Bundle();
                                bundle.putString("dataNim", listMahasiswa.get(position).getNim());
                                bundle.putString("dataNama", listMahasiswa.get(position).getNama());
                                bundle.putString("dataFakultas", listMahasiswa.get(position).getFakultas());
                                bundle.putString("dataProdi", listMahasiswa.get(position).getProdi());
                                bundle.putString("dataGoldar", listMahasiswa.get(position).getGoldar());
                                bundle.putString("dataJk", listMahasiswa.get(position).getJk());
                                bundle.putString("dataTanggal", listMahasiswa.get(position).getTgl_lahir());
                                bundle.putString("dataNomorHp", listMahasiswa.get(position).getNomorhp());
                                bundle.putString("dataEmail", listMahasiswa.get(position).getEmail());
                                bundle.putString("dataIpk", listMahasiswa.get(position).getIpk());
                                bundle.putString("dataAlamat", listMahasiswa.get(position).getAlamat());
                                bundle.putString("getPrimaryKey", listMahasiswa.get(position).getKey());
                                Intent intent = new Intent(v.getContext(), UpdateMhs.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                listener.onDeleteData(listMahasiswa.get(position),position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();



            }
        });

    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nimTextView,namaTextView,fakultasTextView,prodiTextView,ipkTextView,emailTextView,
                noHpTextView,jkTextView,tanggalTextView,goldarTextView,alamatTextView;
        private LinearLayout ListItem;
        private ImageView ic_dots;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nimTextView = itemView.findViewById(R.id.nimTextView);
            namaTextView = itemView.findViewById(R.id.namaTextView);
            fakultasTextView = itemView.findViewById(R.id.fakultasTextView);
            prodiTextView = itemView.findViewById(R.id.prodiTextView);
            ipkTextView = itemView.findViewById(R.id.ipkTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            noHpTextView = itemView.findViewById(R.id.noHpTextView);
            jkTextView = itemView.findViewById(R.id.jkTextView);
            tanggalTextView = itemView.findViewById(R.id.tanggalTextView);
            goldarTextView = itemView.findViewById(R.id.goldarTextView);
            alamatTextView = itemView.findViewById(R.id.alamatTextView);
            ListItem = itemView.findViewById(R.id.list_item);
            ic_dots = itemView.findViewById(R.id.ic_dots);

        }
    }
}
