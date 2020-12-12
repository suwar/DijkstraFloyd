package id.oy.dijkstrafloyd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListHasilPengujianAdapter extends ArrayAdapter<HasilPengujian> {
    private int[] color;
    private int[] idNodeTujuan;
    private int idNodeAwal;
    private Context context;

    public ListHasilPengujianAdapter(Context context, List<HasilPengujian> data, int resLayout, int[] color, int[] idNodeTujuan, int idNodeAwal){
        super(context,resLayout,data);
        this.context = context;
        this.color = color;
        this.idNodeTujuan = idNodeTujuan;
        this.idNodeAwal = idNodeAwal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hasil_pengujian,parent,false);
        }
        HasilPengujian hasilPengujian = getItem(position);

        convertView.setBackgroundColor(color[position]);
        ((TextView)convertView.findViewById(R.id.tvNamaAsal)).setText(MapManager.getNamaLokasiAwal(idNodeAwal));
        ((TextView)convertView.findViewById(R.id.tvNamaTujuan)).setText(MapManager.getNamaLokasiTujuan(idNodeTujuan[position]));
        ((TextView)convertView.findViewById(R.id.tvJarakRute)).setText(String.valueOf(hasilPengujian.getJarakRute()));
        ((TextView)convertView.findViewById(R.id.tvWaktuTempuh)).setText(String.valueOf(hasilPengujian.getCost()));

        return convertView;
    }
}
