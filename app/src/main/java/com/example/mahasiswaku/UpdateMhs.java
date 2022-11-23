package com.example.mahasiswaku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UpdateMhs extends AppCompatActivity {


    private EditText updateNim, updateNama, updateTanggal, updateNomorHp, updateEmail, updateIpk, updateAlamat;
    private Spinner fakultasUpdate, prodiUpdate;
    private CheckBox goldar_a, goldar_b, goldar_ab, goldar_o;
    private RadioButton pria, wanita;
    private Button btnUpdateTask;
    private ImageView ic_cancel;
    private String cekNim, cekNama, cekFakultas,cekProdi,cekGoldar, cekJk, cekTgl_Lahir,cekNomorHp,cekEmail,cekIpk,cekAlamat;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mhs);

        updateNim = findViewById(R.id.updateNim);
        updateNama = findViewById(R.id.updateNama);
        updateTanggal = findViewById(R.id.updateTanggal);
        updateNomorHp = findViewById(R.id.updateNomorHp);
        updateEmail = findViewById(R.id.updateEmail);
        updateIpk = findViewById(R.id.updateIpk);
        updateAlamat = findViewById(R.id.UpdateAlamat);
        fakultasUpdate = findViewById(R.id.fakultasUpdate);
        prodiUpdate = findViewById(R.id.prodiUpdate);
        goldar_a = findViewById(R.id.goldar_a);
        goldar_b = findViewById(R.id.goldar_b);
        goldar_ab = findViewById(R.id.goldar_ab);
        goldar_o = findViewById(R.id.goldar_o);
        pria = findViewById(R.id.pria);
        wanita = findViewById(R.id.wanita);
        btnUpdateTask = findViewById(R.id.btnUpdateTask);
        ic_cancel = findViewById(R.id.ic_cancel);

        // tombol cancel
        ic_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateMhs.this,"Data not saved",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateMhs.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // mengatur agar ketika mengklik tombol kembali tidak looping pada activity sebelumnya
                startActivity(intent);
            }
        });

        // menampilkan kalender
        final Calendar calendar = Calendar.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        GetData();
        updateTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateMhs.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" +month+"/"+ year;
                        updateTanggal.setText(date);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        btnUpdateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cekFakultas = fakultasUpdate.getSelectedItem().toString();
                cekProdi = prodiUpdate.getSelectedItem().toString();
                cekNim = updateNim.getText().toString();
                cekNama = updateNama.getText().toString();
                cekTgl_Lahir = updateTanggal.getText().toString();
                cekNomorHp = updateNomorHp.getText().toString();
                cekEmail = updateEmail.getText().toString();
                cekIpk = updateIpk.getText().toString();
                cekAlamat = updateAlamat.getText().toString();

                if(goldar_a.isChecked()){
                    cekGoldar = goldar_a.getText().toString();
                }else if(goldar_b.isChecked() ) {
                    cekGoldar = goldar_b.getText().toString();
                }else if(goldar_ab.isChecked()){
                    cekGoldar = goldar_ab.getText().toString();
                } else {
                    cekGoldar = goldar_o.getText().toString();
                }

                // Rb
                if(pria.isChecked()){
                    cekJk = pria.getText().toString();
                }else{
                    cekJk = wanita.getText().toString();
                }

                if(isEmpty(cekNim) || isEmpty(cekNama) || isEmpty(cekFakultas)|| isEmpty(cekProdi)|| isEmpty(cekGoldar)|| isEmpty(cekJk)|| isEmpty(cekTgl_Lahir)|| isEmpty(cekNomorHp)|| isEmpty(cekEmail)|| isEmpty(cekIpk)|| isEmpty(cekAlamat)){
                    Toast.makeText(UpdateMhs.this,"data cannot be empty ",Toast.LENGTH_SHORT).show();
                }else{

                    data_mahasiswa setMahasiswa = new data_mahasiswa();
                    setMahasiswa.setNim(updateNim.getText().toString());
                    setMahasiswa.setNama(updateNama.getText().toString());
                    setMahasiswa.setFakultas(fakultasUpdate.getSelectedItem().toString());
                    setMahasiswa.setProdi(prodiUpdate.getSelectedItem().toString());
                    //CB
                    if(goldar_a.isChecked()){
                        setMahasiswa.setGoldar(goldar_a.getText().toString());
                    }else if(goldar_b.isChecked() ) {
                        setMahasiswa.setGoldar(goldar_b.getText().toString());
                    }else if(goldar_ab.isChecked()){
                        setMahasiswa.setGoldar(goldar_ab.getText().toString());
                    } else {
                        setMahasiswa.setGoldar(goldar_o.getText().toString());
                    }
                    // Rb
                    if(pria.isChecked()){
                        setMahasiswa.setJk(pria.getText().toString());
                    }else{
                        setMahasiswa.setJk(wanita.getText().toString());
                    }
                    setMahasiswa.setTgl_lahir(updateTanggal.getText().toString());
                    setMahasiswa.setNomorhp(updateNomorHp.getText().toString());
                    setMahasiswa.setEmail(updateEmail.getText().toString());
                    setMahasiswa.setIpk(updateIpk.getText().toString());
                    setMahasiswa.setAlamat(updateAlamat.getText().toString());


                    updateMahasiswa(setMahasiswa);
                }
            }
        });

    }



    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    private void GetData() {

        final String getNim = getIntent().getExtras().getString("dataNim");
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getFakultas = getIntent().getExtras().getString("dataFakultas");
        final String getProdi = getIntent().getExtras().getString("dataProdi");
        final String getGoldar = getIntent().getExtras().getString("dataGoldar");
        final String getJk = getIntent().getExtras().getString("dataJk");
        final String getTgl_lahir = getIntent().getExtras().getString("dataTanggal");
        final String getNomorhp = getIntent().getExtras().getString("dataNomorHp");
        final String getEmail = getIntent().getExtras().getString("dataEmail");
        final String getIpk = getIntent().getExtras().getString("dataIpk");
        final String getAlamat = getIntent().getExtras().getString("dataAlamat");


        // Spinner
        if (getFakultas.indexOf("Ilmu") != -1)
        {
            fakultasUpdate.setSelection(0);
        }else if(getFakultas.indexOf("Bisnis") != -1){
            fakultasUpdate.setSelection(1);
        }

        if (getProdi.indexOf("Info") != -1)
        {
            prodiUpdate.setSelection(0);
        }else if(getProdi.indexOf("Sistem") != -1){
            prodiUpdate.setSelection(1);
        }else if(getProdi.indexOf("Ilmu") != -1){
            prodiUpdate.setSelection(2);
        }else if(getProdi.indexOf("Bisnis") != -1){
            prodiUpdate.setSelection(3);
        }
        //CB
        if (getGoldar.indexOf("A") != -1)
        {
            goldar_a.setChecked(true);
            goldar_b.setChecked(false);
            goldar_ab.setChecked(false);
            goldar_o.setChecked(false);
        }else if(getGoldar.indexOf("B") != -1){
            goldar_a.setChecked(false);
            goldar_b.setChecked(true);
            goldar_ab.setChecked(false);
            goldar_o.setChecked(false);
        }else if(getGoldar.indexOf("AB") != -1){
            goldar_a.setChecked(false);
            goldar_b.setChecked(false);
            goldar_ab.setChecked(true);
            goldar_o.setChecked(false);
        }else if(getGoldar.indexOf("O") != -1){
            goldar_a.setChecked(false);
            goldar_b.setChecked(false);
            goldar_ab.setChecked(false);
            goldar_o.setChecked(true);
        }

        // RB
        if (getJk.indexOf("Pria") != -1){
            pria.setChecked(true);
            wanita.setChecked(false);
        }else if (getJk.indexOf("Wanita") != -1){
            pria.setChecked(false);
            wanita.setChecked(true);
        }

        fakultasUpdate.getSelectedItem().toString();
        prodiUpdate.getSelectedItem().toString();
        updateNim.setText(getNim);
        updateNama.setText(getNama);
        updateTanggal.setText(getTgl_lahir);
        updateNomorHp.setText(getNomorhp);
        updateEmail.setText(getEmail);
        updateIpk.setText(getIpk);
        updateAlamat.setText(getAlamat);

//        RB
        if(pria.isChecked()){
            cekJk = pria.getText().toString();
        }else{
            cekJk = wanita.getText().toString();
        }

        if(goldar_a.isChecked()){
            cekGoldar = goldar_a.getText().toString();

        }else if(goldar_b.isChecked() ) {
            cekGoldar = goldar_b.getText().toString();

        }else if(goldar_ab.isChecked()){
            cekGoldar = goldar_ab.getText().toString();
        } else {
            cekGoldar = goldar_o.getText().toString();
        }


    }

    private void updateMahasiswa(data_mahasiswa mahasiswa) {
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("MahasiswaKu")
                .child("data")
                .child(getKey)
                .setValue(mahasiswa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        fakultasUpdate.getSelectedItem().toString();
                        prodiUpdate.getSelectedItem().toString();
                        updateNim.setText("");
                        updateNama.setText("");
                        updateTanggal.setText("");
                        updateNomorHp.setText("");
                        updateEmail.setText("");
                        updateIpk.setText("");
                        updateAlamat.setText("");

                        //        RB
                        if(pria.isChecked()){
                            cekJk = pria.getText().toString();
                        }else{
                            cekJk = wanita.getText().toString();
                        }

                        if(goldar_a.isChecked()){
                            cekGoldar = goldar_a.getText().toString();

                        }else if(goldar_b.isChecked() ) {
                            cekGoldar = goldar_b.getText().toString();

                        }else if(goldar_ab.isChecked()){
                            cekGoldar = goldar_ab.getText().toString();
                        } else {
                            cekGoldar = goldar_o.getText().toString();
                        }
                        Toast.makeText(UpdateMhs.this,"Task edited successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


    }
}