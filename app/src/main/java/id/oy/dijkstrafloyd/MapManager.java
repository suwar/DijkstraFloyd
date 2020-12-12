package id.oy.dijkstrafloyd;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class MapManager {
    private static final double KECEPATAN_MAKSIMUM_0 = 11.111;  // 40 meter / second
    private static final double KECEPATAN_MAKSIMUM_1 = 8.333;  //30 meter / second
    private static final double KECEPATAN_MAKSIMUM_2 = 2.777;  // 10 meter / second

    private static ArrayList<Node> listLokasiAwal;
    private static ArrayList<Node> listLokasiTujuan;

    public static HashMap<Integer,Node> nodeMap;
    public static double[][] graph;
    public static double[][] graphK;


    private static void initLokasiAwal(){
        listLokasiAwal = new ArrayList<>();
        listLokasiAwal.add(new Node(404,"Hotel Fave",new LatLng(-2.977837,104.7501743)));
        listLokasiAwal.add(new Node(405,"Hotel Exelton",new LatLng(-2.9509177,104.7816408)));
        listLokasiAwal.add(new Node(402,"Hotel Swarna",new LatLng(-2.990672,104.7453772)));
        listLokasiAwal.add(new Node(400,"Hotel Batiqa",new LatLng(-2.984494,104.7419453)));
        listLokasiAwal.add(new Node(401,"Hotel Santika",new LatLng(-2.981256,104.7456)));
        listLokasiAwal.add(new Node(406,"Hotel Horison",new LatLng(-2.984177,104.7587402)));
        listLokasiAwal.add(new Node(398,"Hotel Aryaduta",new LatLng(-2.977477,104.7388603)));
        listLokasiAwal.add(new Node(399,"Hotel Arista",new LatLng(-2.977817,104.7442363)));
        listLokasiAwal.add(new Node(403,"Hotel Amaris",new LatLng(-2.962767,104.7339312)));
    }

    private static void initLokasiTujuan(){ //inisialisasi data lokasi tujuan
        listLokasiTujuan = new ArrayList<>();
        listLokasiTujuan.add(new Node(407,"BKB", new LatLng(-2.992212, 104.759708)));
        listLokasiTujuan.add(new Node(412,"JSC", new LatLng(-3.021883, 104.788129)));
        listLokasiTujuan.add(new Node(409,"Bukit Siguntang", new LatLng(-2.995809, 104.725868)));
        listLokasiTujuan.add(new Node(411,"Kampung Arab", new LatLng(-3.017970, 104.731677)));
        listLokasiTujuan.add(new Node(410,"Kambang Iwak", new LatLng(-2.990128, 104.747736)));
        listLokasiTujuan.add(new Node(413,"Ampera", new LatLng(-2.991704, 104.763456)));
        listLokasiTujuan.add(new Node(415,"Punti Kayu", new LatLng(-2.993252, 104.726984)));
        listLokasiTujuan.add(new Node(414,"Museum Balaputra Dewa", new LatLng(-2.987865, 104.760100)));
        listLokasiTujuan.add(new Node(408,"Monpera", new LatLng(-2.989386, 104.760346)));
        listLokasiTujuan.add(new Node(416,"Museum Songket", new LatLng(-2.989386, 104.760346)));
    }

    public static ArrayList<Node> getListLokasiAwal(){ //untuk membuat listlokasi awal pertama
        if(listLokasiAwal == null){// kalu null -> bikin dl
            initLokasiAwal();
        }
        return listLokasiAwal;
    }

    public static ArrayList<Node> getListLokasiTujuan(){
        if(listLokasiTujuan == null) {
            initLokasiTujuan();
        }
        return listLokasiTujuan;
    }

    public static int getIdLokasi(String nama){
        if(listLokasiTujuan == null) {
            initLokasiTujuan();
        }

        for(int i=0; i<listLokasiTujuan.size(); i++){
            Node nodeTujuan = listLokasiTujuan.get(i);
            if(nodeTujuan.getNama().equalsIgnoreCase(nama))
                return nodeTujuan.getId();
        }

        return -1;
    }

    public static String getNamaLokasiTujuan(int id){
        if(listLokasiTujuan == null) {
            initLokasiTujuan();
        }

        for(int i=0; i<listLokasiTujuan.size(); i++){
            Node nodeTujuan = listLokasiTujuan.get(i);
            if(nodeTujuan.getId() == id)
                return nodeTujuan.getNama();
        }

        return "";
    }

    public static String getNamaLokasiAwal(int id){
        if(listLokasiAwal == null) {
            initLokasiTujuan();
        }

        for(int i=0; i<listLokasiAwal.size(); i++){
            Node nodeTujuan = listLokasiAwal.get(i);
            if(nodeTujuan.getId() == id)
                return nodeTujuan.getNama();
        }

        return "";
    }

    public static void init(Context context){
        settingNodeMap(context); // untuk seting node
        settingGraph(context);// untuk seting graph
    }

    private static void settingNodeMap(Context context){ //untuk setting node map dari database untuk listing node map
        nodeMap = new HashMap<>(); //buat variabel map baru
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        try{
            databaseHelper.createDataBase();
            databaseHelper.openDataBase();
            Cursor cursor = databaseHelper.getAllNode();
            if(cursor.getCount()!=0){ //
                Log.v("NodeMap","Cursor "+cursor.getCount());
                graph = new double[cursor.getCount()+1][cursor.getCount()+1];
                graphK = new double[cursor.getCount()+1][cursor.getCount()+1];
                if(cursor.moveToFirst()){
                    do{
                        int id = cursor.getInt(0); //i = kolom
                        double lat = cursor.getDouble(1);
                        double lng = cursor.getDouble(2);
                        String name = cursor.getString(3);

                        Node nodeBaru = new Node(id,name, new LatLng(lat,lng));
                        nodeMap.put(id, nodeBaru);

                    }while(cursor.moveToNext());
                }
            }else{
                Log.v("NodeMap","Cursor 0");
            }
            databaseHelper.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void settingGraph(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        try{
            databaseHelper.createDataBase();
            databaseHelper.openDataBase();
            Cursor cursor = databaseHelper.getAllEdge();
            if(cursor.getCount()!=0){
                Log.v("Graph","Cursor "+cursor.getCount());

                if(cursor.moveToFirst()){
                    do{
                        int id = cursor.getInt(0);
                        int idNodeAwal = cursor.getInt(1);
                        int idNodeTujuan = cursor.getInt(2);
                        int status = cursor.getInt(3);
                        int konstanta = cursor.getInt(4);

                        Log.v("Graph-"+id,"Node ke-"+idNodeAwal+" ->" +idNodeTujuan + " Dengan Status: "+status + " Konstanta "+konstanta);

                        Node nodeAwal = nodeMap.get(idNodeAwal);
                        Node nodeTujuan = nodeMap.get(idNodeTujuan);

                        double jarak = distance_in_meter(nodeAwal,nodeTujuan); // untuk menghitung jarak antar node

                        //---Graph Tanpa Konstanta---
                        double waktu = jarak / KECEPATAN_MAKSIMUM_0;
                        graph[idNodeAwal][idNodeTujuan] = waktu;

                        if(status == 0){
                            graph[idNodeTujuan][idNodeAwal] = waktu;
                        }


                        //---Graph Menggunakan Konstanta---
                        double kecepatan;
                        if(konstanta >= 0 && konstanta <=3)
                            kecepatan = KECEPATAN_MAKSIMUM_0;
                        else if(konstanta >= 4 && konstanta <=6)
                            kecepatan = KECEPATAN_MAKSIMUM_1;
                        else
                            kecepatan = KECEPATAN_MAKSIMUM_2;

                        double waktuK = jarak/kecepatan;

                        graphK[idNodeAwal][idNodeTujuan] = waktuK;
                        //---- untuk membuat jalan one way
                        if(status == 0){ // jika status 0 -> 2 arah / 1 -> 1 arah
                            graphK[idNodeTujuan][idNodeAwal] = waktuK;
                        }



                    }while(cursor.moveToNext());
                }
            }else{
                Log.v("Graph","Cursor 0");
            }
            databaseHelper.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static double distance_in_meter(Node awal, Node tujuan) {
        double lat1 = awal.getPosisi().latitude;
        double lon1 = awal.getPosisi().longitude;

        double lat2 = tujuan.getPosisi().latitude;
        double lon2 = tujuan.getPosisi().longitude;

        double R = 6371000f; // Radius of the earth in m
        double dLat = (lat1 - lat2) * Math.PI / 180f;
        double dLon = (lon1 - lon2) * Math.PI / 180f;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1 * Math.PI / 180f) * Math.cos(lat2 * Math.PI / 180f) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2f * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }
}
