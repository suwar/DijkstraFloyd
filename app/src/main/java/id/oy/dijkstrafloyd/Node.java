package id.oy.dijkstrafloyd;

import com.google.android.gms.maps.model.LatLng;



public class Node {
    private int id;
    private String nama;
    private LatLng posisi;

    public Node(int id,String nama, LatLng posisi){
        this.id = id;
        this.nama = nama;
        this.posisi = posisi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama){
        this.nama = nama;
    }
    public String getNama(){
        return nama;
    }

    public void setPosisi(LatLng posisi){
        this.posisi = posisi;
    }
    public LatLng getPosisi(){
        return posisi;
    }
}
