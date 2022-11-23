package com.example.mahasiswaku;

public class data_mahasiswa {
    private String nim;
    private String nama;
    private String fakultas;
    private String prodi;
    private String goldar;
    private String jk;
    private String tgl_lahir;
    private String nomorhp;
    private String email;
    private String ipk;
    private String alamat;
    private String key;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getGoldar() {
        return goldar;
    }

    public void setGoldar(String goldar) {
        this.goldar = goldar;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getNomorhp() {
        return nomorhp;
    }

    public void setNomorhp(String nomorhp) {
        this.nomorhp = nomorhp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpk() {
        return ipk;
    }

    public void setIpk(String ipk) {
        this.ipk = ipk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public data_mahasiswa() {
    }

    public data_mahasiswa(String nim, String nama, String fakultas, String prodi, String goldar, String jk, String tgl_lahir, String nomorhp, String email, String ipk, String alamat) {
        this.nim = nim;
        this.nama = nama;
        this.fakultas = fakultas;
        this.prodi = prodi;
        this.goldar = goldar;
        this.jk = jk;
        this.tgl_lahir = tgl_lahir;
        this.nomorhp = nomorhp;
        this.email = email;
        this.ipk = ipk;
        this.alamat = alamat;
    }
}
