package id.oy.dijkstrafloyd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private Button btnCariRute;
    private Spinner spLokasiAwal;
    private RadioButton rbDjikstra, rbFloydWarshall;
    private CheckBox cbBkb, cbJsc, cbBukitSiguntang, cbKampungArab, cbKambangIwak;
    private CheckBox cbAmpera, cbPuntiKayu, cbBalaputeraDewa, cbMonpera, cbMuseumSongket;

    @Override //
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);//fungsi manggil layout

        btnCariRute = (Button) findViewById(R.id.btnCariRute); //untuk id
        spLokasiAwal = (Spinner) findViewById(R.id.spLokasiAwal);
        rbDjikstra = (RadioButton) findViewById(R.id.rbDjikstra);
        rbFloydWarshall = (RadioButton) findViewById(R.id.rbFloydWarshall);
        cbBkb = (CheckBox) findViewById(R.id.cbBkb);
        cbJsc = (CheckBox) findViewById(R.id.cbJsc);
        cbBukitSiguntang = (CheckBox) findViewById(R.id.cbBukitSiguntang);
        cbKampungArab = (CheckBox) findViewById(R.id.cbKampungArab);
        cbKambangIwak = (CheckBox) findViewById(R.id.cbKambangIwak);
        cbAmpera = (CheckBox) findViewById(R.id.cbAmpera);
        cbPuntiKayu = (CheckBox) findViewById(R.id.cbPuntiKayu);
        cbBalaputeraDewa = (CheckBox) findViewById(R.id.cbBalaputeraDewa);
        cbMonpera = (CheckBox) findViewById(R.id.cbMonpera);
        cbMuseumSongket = (CheckBox) findViewById(R.id.cbMuseumSongket);

        settingSpinner();
        MapManager.init(MenuActivity.this);

        btnCariRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //jika user mengclick button cari rute
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);

                int posisiLokasiAwalDipilih = spLokasiAwal.getSelectedItemPosition();
                Node nodeAwalDipilih = MapManager.getListLokasiAwal().get(posisiLokasiAwalDipilih);


                String algoritma = rbDjikstra.isChecked()?"djikstra":"floydwarshall";

                if(getIdLokasiTujuan().length>0) {

                    intent.putExtra("idNodeAwal", nodeAwalDipilih.getId());
                    intent.putExtra("idNodeTujuan", getIdLokasiTujuan());
                    intent.putExtra("algoritma", algoritma);


                    startActivity(intent);
                }else {
                    Toast.makeText(MenuActivity.this, "Tidak ada tujuan", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void settingSpinner(){
        //---Setting Spinner Lokasi Awal---
        ArrayList<Node>listLokasiAwal = MapManager.getListLokasiAwal(); //variabe penampung untuk listlokasi awal
        ArrayList<String> listNamaLokasiAwal = new ArrayList<>(); // untuk memunculkan nama di spiner
        for(int i=0; i<listLokasiAwal.size(); i++){ // untuk mengbil atribut nama di class node awal
            Node nodeI = listLokasiAwal.get(i);
            listNamaLokasiAwal.add(nodeI.getNama());
        }
        spLokasiAwal.setAdapter(new ArrayAdapter<String>(MenuActivity.this,R.layout.support_simple_spinner_dropdown_item,listNamaLokasiAwal)); // untuk menampilkan di spinner
    }

    private int[] getIdLokasiTujuan (){
        ArrayList<Integer> listId = new ArrayList<>();
        if(cbBkb.isChecked()){
            listId.add(MapManager.getIdLokasi(cbBkb.getText().toString()));
        }
        if(cbJsc.isChecked()){
            listId.add(MapManager.getIdLokasi(cbJsc.getText().toString()));
        }
        if(cbBukitSiguntang.isChecked()){
            listId.add(MapManager.getIdLokasi(cbBukitSiguntang.getText().toString()));
        }
        if(cbKampungArab.isChecked()){
            listId.add(MapManager.getIdLokasi(cbKampungArab.getText().toString()));
        }
        if(cbKambangIwak.isChecked()){
            listId.add(MapManager.getIdLokasi(cbKambangIwak.getText().toString()));
        }
        if(cbAmpera.isChecked()){
            listId.add(MapManager.getIdLokasi(cbAmpera.getText().toString()));
        }
        if(cbPuntiKayu.isChecked()){
            listId.add(MapManager.getIdLokasi(cbPuntiKayu.getText().toString()));
        }
        if(cbBalaputeraDewa.isChecked()){
            listId.add(MapManager.getIdLokasi(cbBalaputeraDewa.getText().toString()));
        }
        if(cbMonpera.isChecked()){
            listId.add(MapManager.getIdLokasi(cbMonpera.getText().toString()));
        }
        if(cbMuseumSongket.isChecked()){
            listId.add(MapManager.getIdLokasi(cbMuseumSongket.getText().toString()));
        }

        //---Convert Ke Array Int---
        int[] arrId = new int[listId.size()];
        for(int i=0; i<listId.size(); i++){
            arrId[i] = listId.get(i);
        }

        return arrId;
    }
}
