package com.example.mahasiswaku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.dataListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView gambar;
    private TextView logo;
    private ImageView ic_cari, ic_close;
    private SearchView etsearch;



    DatabaseReference getReference; // ini yg jadi dipake
    private ArrayList<data_mahasiswa> dataMahasiswa;


    private FloatingActionButton floatingButton,addMahasiswaButton;
    Animation fabOpen,fabClose,rotateForward, rotateBackward;
    boolean isOpen = false; // by default it is false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Image
        gambar = findViewById(R.id.gambar);
        logo = findViewById(R.id.logo);


        //  Floating Button
        floatingButton = findViewById(R.id.floatingButton);
        addMahasiswaButton = findViewById(R.id.addMahasiswaButton);
        //animation
        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_foward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);


        // fungsi ketika di click floating nya
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFab();
            }
        });

        // add Mahasiswa
        addMahasiswaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddMhs.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // mengatur agar ketika mengklik tombol kembali tidak looping pada activity sebelumnya
                startActivity(intent);
            }
        });
        // Search view
        etsearch = findViewById(R.id.etsearch);
        etsearch.setVisibility(View.GONE);
        etsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GetData(query.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // searchview agar menampilkan data per kata yang cocok
                Query query =getReference.child("MahasiswaKu").child("data");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataMahasiswa = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                            data_mahasiswa mahasiswa = snapshot.getValue(data_mahasiswa.class);
                            mahasiswa.setKey(snapshot.getKey());
                            String namaMhsFirstcapital = mahasiswa.getNama().substring(0, 1).toUpperCase() + mahasiswa.getNama().substring(1);
                            String fakultasMhsFirstcapital = mahasiswa.getNama().substring(0, 1).toUpperCase() + mahasiswa.getNama().substring(1);
                            String emailMhsFirstcapital = mahasiswa.getNama().substring(0, 1).toUpperCase() + mahasiswa.getNama().substring(1);
                            String alamatMhsFirstcapital = mahasiswa.getNama().substring(0, 1).toUpperCase() + mahasiswa.getNama().substring(1);
                            if (namaMhsFirstcapital.contains(newText)
                                    || mahasiswa.getNama().toLowerCase().contains(newText)
                                    || mahasiswa.getNama().contains(newText)
                                    || fakultasMhsFirstcapital.contains(newText)
                                    || mahasiswa.getFakultas().contains(newText)
                                    || mahasiswa.getFakultas().toLowerCase().contains(newText)
                                    || emailMhsFirstcapital.contains(newText)
                                    || mahasiswa.getEmail().contains(newText)
                                    || mahasiswa.getEmail().toLowerCase().contains(newText)
                                    || alamatMhsFirstcapital.contains(newText)
                                    || mahasiswa.getAlamat().toLowerCase().contains(newText)
                                    || mahasiswa.getAlamat().contains(newText)
                                    ){
                                dataMahasiswa.add(mahasiswa);
                            }
                        }

                        adapter = new RecyclerViewAdapter(dataMahasiswa, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return true;
            }
        });

        ic_cari=findViewById(R.id.ic_cari);
        ic_close=findViewById(R.id.ic_close);
        ic_close.setVisibility(View.GONE);

        ic_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etsearch.setVisibility(View.VISIBLE);
                ic_close.setVisibility(View.VISIBLE);
                ic_cari.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
            }
        });
        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etsearch.setVisibility(View.GONE);
                ic_close.setVisibility(View.GONE);
                ic_cari.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
            }
        });

        //    Show Data
        recyclerView = findViewById(R.id.datalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyRecyclerView();
        GetData("");
    }

    private void MyRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    public void GetData(String cari){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        getReference = database.getReference();

        if (cari.isEmpty() == true) {

            MyRecyclerView();
            getReference.child("MahasiswaKu").child("data")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataMahasiswa = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                data_mahasiswa mahasiswa = snapshot.getValue(data_mahasiswa.class);
                                mahasiswa.setKey(snapshot.getKey());
                                dataMahasiswa.add(mahasiswa);
                            }

                            // Agar data yang baru diposisi paling atas
                            adapter = new RecyclerViewAdapter(dataMahasiswa, MainActivity.this);
                            recyclerView.setAdapter(adapter);

                            int size = dataMahasiswa.size();
                            if(size>0){
                                gambar.setVisibility(View.GONE);
                            }else{
                                gambar.setVisibility(View.VISIBLE);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Task failed to load", Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity",databaseError.getDetails()+" "+databaseError.getMessage());

                        }
                    });
        }else{
            getReference.child("MahasiswaKu").child("data").orderByChild("nama").startAt(cari).endAt(cari)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            dataMahasiswa = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                data_mahasiswa mahasiswa = snapshot.getValue(data_mahasiswa.class);
                                mahasiswa.setKey(snapshot.getKey());
                                dataMahasiswa.add(mahasiswa);

                            }
                            adapter = new RecyclerViewAdapter(dataMahasiswa, MainActivity.this);
                            recyclerView.setAdapter(adapter);

                            int size = dataMahasiswa.size();
                            if(size>0){
                                gambar.setVisibility(View.GONE);
                            }else{
                                gambar.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Task failed to load", Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity",databaseError.getDetails()+" "+databaseError.getMessage());

                        }
                    });
        }
    }


    private void animateFab(){
        if (isOpen){
            addMahasiswaButton.startAnimation(fabClose);
            addMahasiswaButton.setClickable(false);
            isOpen=false;
        }else{
            addMahasiswaButton.startAnimation(fabOpen);
            addMahasiswaButton.setClickable(true);
            isOpen=true;
        }
    }


    @Override
    public void onDeleteData(data_mahasiswa data, int position) {
        /*
         * Kode ini akan diambil ketika method onDeleteData
         * dipanggil dari adapter pada RecyleView melalui Interface
         * kemudian akan menghapus data berdasarkan primary key dari data tersebut
         * jika berhasil, maka akan memunculkan toast
         * */
        if(getReference != null){
            getReference.child("MahasiswaKu")
                    .child("data")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(MainActivity.this,"Data deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}
