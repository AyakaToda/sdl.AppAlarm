package jp.ac.titech.itpro.sdl.appalarm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String NAME_DATA= "jp.ac.titech.itpro.sdl.appalarm.AppNameData";
    public static final String TIME_DATA= "jp.ac.titech.itpro.sdl.appalarm.AlertTimeData";
    public static final int REQUEST_CODE = 1001;
    private SharedPreferences saveData;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("application","start");

        saveData = getSharedPreferences("AppAlertTimeData", MODE_PRIVATE);
        editor = saveData.edit();

        // 端末にインストール済のアプリケーション一覧情報を取得
        final PackageManager pm = getPackageManager();
        //final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_UNTIL_USED_COMPONENTS;
        //final int flags = PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_DISABLED_COMPONENTS;
        final int flags = PackageManager.GET_META_DATA;
        final List<ApplicationInfo> installedAppList = pm.getInstalledApplications(flags);

        // リストに一覧データを格納する
        final List<AppData> dataList = new ArrayList<>();
        for (ApplicationInfo app : installedAppList) {
            String appname = app.loadLabel(pm).toString();
            long alerttime = saveData.getLong(appname, -1);
            //alerttimeが作られてなかったアプリは作る
            if(alerttime == -1){
                editor.putLong(appname, 0);
                alerttime = 0;
                editor.commit();
            }
            AppData data = new AppData(appname, app.loadIcon(pm), app.packageName, alerttime);
            dataList.add(data);
        }

        ArrayAdapter adapter = new AppListAdapter(this, dataList);

        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("action","click");
                ApplicationInfo item = installedAppList.get(position);
                AppData app = dataList.get(position);
                PackageManager pManager = getPackageManager();
                //Intent intent = pManager.getLaunchIntentForPackage(item.packageName);
                //Intent intent = new Intent(MainActivity.this, ClickActivity.class);
                Intent intent = new Intent(getApplicationContext(), ClickActivity.class);
                //Intent intent = new Intent();
                //intent.putExtra(NAME_DATA, item.loadLabel(pm).toString());
                intent.putExtra(NAME_DATA, app.label);
                intent.putExtra(TIME_DATA, app.alerttime);
                Log.d("app name",item.loadLabel(pm).toString());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        setContentView(listView);
    }

    public void onActivityResult( int requestCode, int resultCode, Intent intent ) {
        if( requestCode == REQUEST_CODE ){
            if( resultCode == Activity.RESULT_OK ){
                String label = intent.getStringExtra(MainActivity.NAME_DATA);
                long time = intent.getLongExtra(MainActivity.TIME_DATA, -1);
                editor.putLong(label, time);
                editor.commit();
                Log.d("label", label);
                Log.d("time", Long.toString(time));
                Log.d("savetime", Long.toString(saveData.getLong(label, -1)));
            }
        }
    }
}
