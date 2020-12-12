package id.oy.dijkstrafloyd;


import java.util.ArrayList;

public class DjikstraAlgorithm {
    private static double jarakRute;
    private static int jumlahIterasi;
    private static int jumlahNodeChecked;

    private static double[] InitializeCost(int graphLength) {
        double[] cost = new double[graphLength];
        for (int i = 0; i < graphLength; i++) {
            cost[i] = Double.POSITIVE_INFINITY;
        }

        return cost;
    }

    private static int[] InitializeParents(int graphLength) {
        int[] parents = new int[graphLength];
        for (int i = 0; i < graphLength; i++) {
            parents[i] = -1;
        }

        return parents;
    }

    private static boolean[] InitializeVisited(int graphLength) {
        boolean[] isVisited = new boolean[graphLength];
        for (int i = 0; i < graphLength; i++) {
            isVisited[i] = false;
        }

        return isVisited;
    }

    private static int[] Search(double[][] graph, double[] cost, int[] parents, boolean[] isVisited, int idNodeTujuan) {
        //---Searching Path---
        for (int i = 0; i < graph.length; i++) {
            jumlahNodeChecked++;
            jumlahIterasi++;

            int idNodeTerdekat = -1;
            double costTerkecil = Double.POSITIVE_INFINITY;

            //---Mencari Node Dengan Cost Terkecil---   //untuk uodate posisi skrng
            for (int j = 0; j < graph.length; j++) {
                if (!isVisited[j] && cost[j] < costTerkecil) { //
                    idNodeTerdekat = j;
                    costTerkecil = cost[j];
                }
            }

            isVisited[idNodeTerdekat] = true; //memberikan tanda bahwa node it pernah dikunjungi

            if (idNodeTerdekat == idNodeTujuan)
                break;

            //---Update Cost---
            for (int j = 0; j < graph.length; j++) {
                if (graph[idNodeTerdekat][j] > 0 && ((costTerkecil + graph[idNodeTerdekat][j]) < cost[j])) {  //if graft terdekat > 0 dan kost terkecil (bisa keselanjutnya) + node selanjutnya
                   //untuk mengetahui bisa tidak ketujuan selanjutnya      //cost terkecil + selanjutnya untuk update
                    parents[j] = idNodeTerdekat;                                                                //untuk update cost
                    cost[j] = costTerkecil + graph[idNodeTerdekat][j];

                }
            }
        }

        return parents;
    }

    public static HasilKesuluruhanPengujian searchPath(double[][] graph, int idNodeAwal, int[] idNodeTujuan){
        long startTime = System.currentTimeMillis(); //atribut menghitung waktu jalan algoritma fungsi dari android
        jumlahIterasi = 0;
        jumlahNodeChecked = 0;
        jarakRute = 0;
        double[] cost = new double[graph.length]; //Untuk mencatat cost
        boolean[] isVisited = new boolean[graph.length]; //untuk mencatat node yang sudah dikunjungi
        int[] parents = new int[graph.length]; // untuk mencatat orang tua dari node ke-i


        ArrayList<HasilPengujian> listHasilPengujian = new ArrayList<>();

        for(int k=0; k<idNodeTujuan.length; k++) { //untuk membuat banyak tujuan (multi destination)
            //---Inisialisasi---  // di inisialisaikan untuk cost, isvisited, parents
            // for (int i = 0; i < graph.length; i++) {
            //     cost[i] = Double.POSITIVE_INFINITY;  // cosh dibuat infiniti, belum diketahui, biaya ke tempat tujuan
            //     isVisited[i] = false; // is visited false, setiap node blm dikunjungi
            //     parents[i] = -1; // parent -> rute yg setelah dituju. kalau udh dpet parent dia dpet nilai 1
            // }

            cost = InitializeCost(graph.length);
            isVisited = InitializeVisited(graph.length);
            parents = InitializeParents(graph.length);

            cost[idNodeAwal] = 0;
            parents[idNodeAwal] = -1;

            //---Searching Path---
            // for (int i = 0; i < graph.length; i++) {
            //     jumlahNodeChecked++;
            //     jumlahIterasi++;

            //     int idNodeTerdekat = -1;
            //     double costTerkecil = Double.POSITIVE_INFINITY;

            //     //---Mencari Node Dengan Cost Terkecil---   //untuk uodate posisi skrng
            //     for (int j = 0; j < graph.length; j++) {
            //         if (!isVisited[j] && cost[j] < costTerkecil) { //
            //             idNodeTerdekat = j;
            //             costTerkecil = cost[j];
            //         }
            //     }

            //     isVisited[idNodeTerdekat] = true; //memberikan tanda bahwa node it pernah dikunjungi

            //     if (idNodeTerdekat == idNodeTujuan[k])
            //         break;

            //     //---Update Cost---
            //     for (int j = 0; j < graph.length; j++) {
            //         if (graph[idNodeTerdekat][j] > 0 && ((costTerkecil + graph[idNodeTerdekat][j]) < cost[j])) {  //if graft terdekat > 0 dan kost terkecil (bisa keselanjutnya) + node selanjutnya
            //            //untuk mengetahui bisa tidak ketujuan selanjutnya      //cost terkecil + selanjutnya untuk update
            //             parents[j] = idNodeTerdekat;                                                                //untuk update cost
            //             cost[j] = costTerkecil + graph[idNodeTerdekat][j];

            //         }
            //     }
            // }

            parents = Search(graph, cost, parents, isVisited, idNodeTujuan[k]);

            //---Buat Jalur---
            ArrayList<Node> jalur = buatJalur(parents, idNodeAwal, idNodeTujuan[k]);

            //---Hasil Pengujian---
            HasilPengujian hasilPengujian = new HasilPengujian();
            hasilPengujian.setJalur(jalur); // untuk beken jalur
            hasilPengujian.setCost(cost[idNodeTujuan[k]]); // untuk waktu tempuh
            hasilPengujian.setJarakRute(jarakRute); // jarak tempuh

            listHasilPengujian.add(hasilPengujian);
        }

        long endTime = System.currentTimeMillis();
        long waktuPencarian = endTime - startTime;

        HasilKesuluruhanPengujian hasilKesuluruhanPengujian = new HasilKesuluruhanPengujian(listHasilPengujian,waktuPencarian,jumlahIterasi,jumlahNodeChecked);

        return hasilKesuluruhanPengujian;
    }

    private static ArrayList<Node> buatJalur(int[] parents, int u, int v) {
        jarakRute=0;
        ArrayList<Node> path = new ArrayList<>();
        path.add(MapManager.nodeMap.get(v));

        while(u != v){  // jika node awal bukan tujuan
            Node asal = MapManager.nodeMap.get(v); // node asal nyo v , buat jalurnya dari tujuan ke asal

            v = parents[v];
            Node tujuan = MapManager.nodeMap.get(v);

            jarakRute += MapManager.distance_in_meter(asal,tujuan);

            path.add(tujuan);
        }
        return path;
    }
}
