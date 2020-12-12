package id.oy.dijkstrafloyd;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class PolylineManager {

    public static void drawPolyline(GoogleMap map, List<Node> listNode, int colorLine, int widthLine){
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(colorLine);
        polylineOptions.width(widthLine);

        for(int i=0; i<listNode.size(); i++){
            Node node = listNode.get(i);
            polylineOptions.add(node.getPosisi());
        }


        map.addPolyline(polylineOptions);

    }
}
