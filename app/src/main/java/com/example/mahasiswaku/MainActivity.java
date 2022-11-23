package com.example.mahasiswaku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingButton,addMahasiswaButton;
    Animation fabOpen,fabClose,rotateForward, rotateBackward;
    boolean isOpen = false; // by default it is false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
}