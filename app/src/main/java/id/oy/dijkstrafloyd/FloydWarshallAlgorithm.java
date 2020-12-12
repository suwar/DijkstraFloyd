package id.oy.dijkstrafloyd;

import java.util.ArrayList;

public class FloydWarshallAlgorithm {
    private static double jarakRute;
    private static int jumlahIterasi;
    private static int jumlahNodeChecked;

    private static double[][] InitializeCost(double[][] graph) {
        double[][] cost = new double[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                cost[i][j] = graph[i][j] == 0 ? Double.POSITIVE_INFINITY : graph[i][j];
            }
        }

        return cost;
    }

    private static int[][] InitializeNext(double[][] graph) {
        int[][] next = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                next[i][j] = graph[i][j] == 0 ? -1 : j;
            }
        }

        return next;
    }

    private static Object[] UpdateState(double[][] cost, int[][] next, int i, int j, int k) {
        cost[i][j] = cost[i][k]+cost[k][j]; // jadi cost ke i ke j = i ke k + k ke j
        next[i][j] = next[i][k]; // dan next i ke j = i ke k
        
        return new Object[] { cost, next };
    }

    public static HasilKesuluruhanPengujian searchPath(double[][] graph, int idNodeAwal, int[] idNodeTujuan){
        long startTime = System.currentTimeMillis(); // untuk mendapatkan waktu proses awal

        jumlahIterasi = 0;
        jumlahNodeChecked=0;
        jarakRute = 0;
        double cost[][] = new double[graph.length][graph.length]; //untuk membuat jaraknya
        int next [][] = new int[graph.length][graph.length]; // untuk ketitik selanjutnya

        //---Inisialisasi---   // untuk memasukan nilai awal u -> titik awal   v -> titik tujuan
        // for(int u=0; u<graph.length; u++){
        //     for(int v=0; v<graph.length; v++){
        //         cost[u][v] = graph[u][v] == 0 ? Double.POSITIVE_INFINITY : graph[u][v]; // untuk penanda tidak bisa ketujuan dia positive infinity, kalau bisa ke v = nilainya v
        //         next[u][v] = graph[u][v] == 0 ? -1 : v; // untuk parent u ke node v jika bisa nilainya v kalau tidak bisa -1
        //                                                 // ? itu if else

        //     }
        // }

        cost = InitializeCost(graph);
        next = InitializeNext(graph);

        //---Floyd Warshall Implementation--- //implementasi algoritma floyd warshall dilakukan dengan 3 kali perulangan, dan dilakukan dengan kondisi
        for(int k=0; k<graph.length; k++){     // if i ke j itu lebih besar dari i ke k + k ke j
            jumlahNodeChecked++;
            for(int i=0; i<graph.length; i++){
                for(int j=0; j<graph.length; j++){
                    jumlahIterasi++;
                    if(cost[i][j]>cost[i][k]+cost[k][j]){  //pengkondisian untuk menuju node yang dituju, jika i ke j lebih besar dari i ke k + k ke j
                        // cost[i][j] = cost[i][k]+cost[k][j]; // jadi cost ke i ke j = i ke k + k ke j
                        // next[i][j] = next[i][k]; // dan next i ke j = i ke k
                        Object[] state = UpdateState(cost, next, i, j, k);
                        cost = (double[][])state[0];
                        next = (int[][])state[1];
                    }
                }
            }
        }


        //--- Buat Jalur---
        ArrayList<HasilPengujian> listHasilPengujian = new ArrayList<>();
        for(int i=0; i<idNodeTujuan.length; i++) {
            ArrayList<Node> jalur = buatJalur(next, idNodeAwal, idNodeTujuan[i]);  //buat jalur di tampung di arraylist<node>

            //---Hasil Pengujian---
            HasilPengujian hasilPengujian = new HasilPengujian(); // buat clas hasil pengujian
            hasilPengujian.setJalur(jalur); // untuk mendapatkan jalur tujuan
            hasilPengujian.setCost(cost[idNodeAwal][idNodeTujuan[i]]);   // untuk mendapatkan cost waktu
            hasilPengujian.setJarakRute(jarakRute); //untuk menghitung jarak

            listHasilPengujian.add(hasilPengujian);
        }

        long endTime = System.currentTimeMillis();
        long waktuPencarian = endTime - startTime;

        HasilKesuluruhanPengujian hasilKesuluruhanPengujian = new HasilKesuluruhanPengujian(listHasilPengujian,waktuPencarian,jumlahIterasi,jumlahNodeChecked); // untuk membuat list hasil pengujian

        return hasilKesuluruhanPengujian;
    }

    private static ArrayList<Node> buatJalur(int[][] next, int u, int v){
        if(next[u][v] == -1){ // jika u ke v tidak bisa berarti null
            return null;
        }
        else{
            jarakRute=0;  // untuk tidak bertambah
            ArrayList<Node> path = new ArrayList<>();
            path.add(MapManager.nodeMap.get(u));  //mengambil node map di u
            while(u != v){ // jika u bukan sampe v
                Node asal = MapManager.nodeMap.get(u); // node awal u

                u = next[u][v];  // dibikin u ke u tujuan
                Node tujuan = MapManager.nodeMap.get(u);  // node tujuan jadi u

                jarakRute += MapManager.distance_in_meter(asal,tujuan);  // menghitung distance antar node

                path.add(tujuan); // untuk menambahkan titik tujuan baru
            }
            return path;
        }
    }

}
