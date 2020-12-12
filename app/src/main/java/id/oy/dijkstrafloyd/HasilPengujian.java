package id.oy.dijkstrafloyd;

import java.util.ArrayList;

public class HasilPengujian {
    private double cost;
    private ArrayList<Node> jalur;
    private double jarakRute;

    public HasilPengujian(

    ){}

    public double getJarakRute(){
        return jarakRute;
    }

    public void setJarakRute(double jarakRute){
        this.jarakRute = jarakRute;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ArrayList<Node> getJalur() {
        return jalur;
    }

    public void setJalur(ArrayList<Node> jalur) {
        this.jalur = jalur;
    }

}
