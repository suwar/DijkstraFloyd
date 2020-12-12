package id.oy.dijkstrafloyd;

import java.util.List;

public class HasilKesuluruhanPengujian {
    private List<HasilPengujian> listHasilPengujian;
    private long waktuPencarian;
    private int jumlahIterasi;
    private int jumlahNodeChecked;

    public HasilKesuluruhanPengujian(List<HasilPengujian> listHasilPengujian, long waktuPencarian, int jumlahIterasi, int jumlahNodeChecked) {
        this.listHasilPengujian = listHasilPengujian;
        this.waktuPencarian = waktuPencarian;
        this.jumlahIterasi = jumlahIterasi;
        this.jumlahNodeChecked = jumlahNodeChecked;
    }

    public List<HasilPengujian> getListHasilPengujian() {
        return listHasilPengujian;
    }

    public void setListHasilPengujian(List<HasilPengujian> listHasilPengujian) {
        this.listHasilPengujian = listHasilPengujian;
    }

    public long getWaktuPencarian() {
        return waktuPencarian;
    }

    public void setWaktuPencarian(long waktuPencarian) {
        this.waktuPencarian = waktuPencarian;
    }

    public int getJumlahIterasi() {
        return jumlahIterasi;
    }

    public void setJumlahIterasi(int jumlahIterasi) {
        this.jumlahIterasi = jumlahIterasi;
    }

    public int getJumlahNodeChecked() {
        return jumlahNodeChecked;
    }

    public void setJumlahNodeChecked(int jumlahNodeChecked) {
        this.jumlahNodeChecked = jumlahNodeChecked;
    }
}
