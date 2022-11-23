package com.example.mahasiswaku;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddMhs extends AppCompatActivity {
    private EditText inputnim, inputnama, inputTanggal, inputNomorHp, inputEmail, inputIpk, inputAlamat;
    private Spinner fakultas_spinner, prodi_spinner;
    private CheckBox goldar_a, goldar_b, goldar_ab, goldar_o;
    private RadioButton pria, wanita;
    private ImageView ic_cancel;
    private Button btnSaveTask, btnCancel;

    private String getNim, getNama, getTgl_lahir, getNomorhp,getEmail, getIpk, getAlamat,getJk, getGoldar, getFakultas, getProdi;


    DatabaseReference getReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mhs);

        inputnim = findViewById(R.id.inputnim);
        inputnama = findViewById(R.id.inputnama);
        inputTanggal = findViewById(R.id.inputTanggal);
        inputNomorHp = findViewById(R.id.inputNomorHp);
        inputEmail = findViewById(R.id.inputEmail);
        inputIpk = findViewById(R.id.inputIpk);
        inputAlamat = findViewById(R.id.inputAlamat);
        fakultas_spinner = findViewById(R.id.fakultas_spinner);
        prodi_spinner = findViewById(R.id.prodi_spinner);
        goldar_a = findViewById(R.id.goldar_a);
        goldar_b = findViewById(R.id.goldar_b);
        goldar_ab = findViewById(R.id.goldar_ab);
        goldar_o = findViewById(R.id.goldar_o);
        pria = findViewById(R.id.pria);
        wanita = findViewById(R.id.wanita);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        ic_cancel = findViewById(R.id.ic_cancel);

        // tombol cancel
        ic_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddMhs.this,"Data not saved",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddMhs.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // mengatur agar ketika mengklik tombol kembali tidak looping pada activity sebelumnya
                startActivity(intent);
            }
        });




        // Calender
        final Calendar calendar = Calendar.getInstance();
        inputTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMhs.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" +month+"/"+ year;
                        inputTanggal.setText(date);
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        // Tombol Save
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        getReference = database.getReference();
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNim = inputnim.getText().toString();
                getNama = inputnama.getText().toString();
                getFakultas = fakultas_spinner.getSelectedItem().toString();
                getProdi = prodi_spinner.getSelectedItem().toString();
                // Logic checkbox
                if(goldar_a.isChecked()){
                    getGoldar = goldar_a.getText().toString();

                }else if(goldar_b.isChecked() ) {
                    getGoldar = goldar_b.getText().toString();

                }else if(goldar_ab.isChecked()){
                    getGoldar = goldar_ab.getText().toString();
                } else {
                    getGoldar = goldar_o.getText().toString();
                }

                // Logic Radio button
                if(pria.isChecked()){
                    getJk = pria.getText().toString();
                }else{
                    getJk = wanita.getText().toString();
                }
                getTgl_lahir = inputTanggal.getText().toString();
                getNomorhp = inputNomorHp.getText().toString();
                getEmail = inputEmail.getText().toString();
                getIpk = inputIpk.getText().toString();
                getAlamat = inputAlamat.getText().toString();

                checkUser();

            }
        });

    }

    private void checkUser() {
        if(isEmpty(getNim) || isEmpty(getNama) || isEmpty(getTgl_lahir) || isEmpty(getNomorhp)|| isEmpty(getEmail)|| isEmpty(getIpk)|| isEmpty(getAlamat)|| isEmpty(getFakultas)|| isEmpty(getProdi)|| isEmpty(getGoldar)|| isEmpty(getJk)){
            Toast.makeText(AddMhs.this,"Data cannot be empty",Toast.LENGTH_SHORT).show();
        } else{
            getReference.child("MahasiswaKu").child("data").push()
                    .setValue(new data_mahasiswa(
                            getNim, getNama, getFakultas, getProdi, getGoldar, getJk, getTgl_lahir,getNomorhp,getEmail,getIpk,getAlamat))
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            fakultas_spinner.getSelectedItem().toString();
                            prodi_spinner.getSelectedItem().toString();
                            inputnim.setText("");
                            inputnama.setText("");
                            inputTanggal.setText("");
                            inputNomorHp.setText("");
                            inputTanggal.setText("");
                            inputNomorHp.setText("");
                            inputEmail.setText("");
                            inputIpk.setText("");
                            inputAlamat.setText("");
                            // metod radio button
                            if(pria.isChecked()){
                                getGoldar = pria.getText().toString();
                            }else{
                                getGoldar = wanita.getText().toString();
                            }
                            //CB
                            if(goldar_a.isChecked()){
                                getGoldar = goldar_a.getText().toString();
                            }else if(goldar_b.isChecked() ) {
                                getGoldar = goldar_b.getText().toString();
                            }else if(goldar_ab.isChecked()){
                                getGoldar = goldar_ab.getText().toString();
                            } else {
                                getGoldar = goldar_o.getText().toString();
                            }

                            Toast.makeText(AddMhs.this,"Successfully Added Mahasiswa", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddMhs.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // mengatur agar ketika mengklik tombol kembali tidak looping pada activity sebelumnya
                            startActivity(intent);
                        }
                    });
        }
    }

}