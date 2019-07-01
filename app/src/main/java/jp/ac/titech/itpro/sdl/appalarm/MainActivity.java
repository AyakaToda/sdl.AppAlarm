package jp.ac.titech.itpro.sdl.appalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 端末にインストール済のアプリケーション一覧情報を取得
        final PackageManager pm = getPackageManager();
        //final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_UNTIL_USED_COMPONENTS;
        //final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS;
        final int flags = PackageManager.GET_META_DATA;
        final List<ApplicationInfo> installedAppList = pm.getInstalledApplications(flags);

        // リストに一覧データを格納する
        final List<AppData> dataList = new ArrayList<>();
        for (ApplicationInfo app : installedAppList) {
            AppData data = new AppData();
            data.label = app.loadLabel(pm).toString();
            data.icon = app.loadIcon(pm);
            data.pname = app.packageName;
            dataList.add(data);
        }

        ArrayAdapter adapter = new AppListAdapter(this, dataList);

        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplicationInfo item = installedAppList.get(position);
                PackageManager pManager = getPackageManager();
                Intent intent = pManager.getLaunchIntentForPackage(item.packageName);
                startActivity(intent);
            }
        });
        setContentView(listView);
    }
}
