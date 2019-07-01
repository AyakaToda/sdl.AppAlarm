package jp.ac.titech.itpro.sdl.appalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.List;

class AppListAdapter extends ArrayAdapter<AppData> {
    private final LayoutInflater mInflater;

    public AppListAdapter(Context context, List<AppData> dataList) {
        super(context, R.layout.activity_main);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addAll(dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_main, parent, false);
            holder.textLabel = (TextView) convertView.findViewById(R.id.label);
            holder.imageIcon = (ImageView) convertView.findViewById(R.id.icon);
            //holder.packageName = (TextView) convertView.findViewById(R.id.pname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 表示データを取得
        final AppData data = getItem(position);
        // ラベルとアイコンをリストビューに設定
        holder.textLabel.setText(data.label);
        holder.imageIcon.setImageDrawable(data.icon);
        //holder.packageName.setText(data.pname);

        return convertView;
    }
}
