package jp.ac.titech.itpro.sdl.appalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class ClickActivity extends AppCompatActivity {

    TextView app_name;
    TextView alert_time;
    private EditText input;
    private Button button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        app_name = findViewById(R.id.app_name);
        alert_time= findViewById(R.id.textView2);
        input = findViewById(R.id.editText2);
        button = findViewById(R.id.button);

        intent = this.getIntent();
        String label = intent.getStringExtra(MainActivity.NAME_DATA);
        long alerttime = intent.getLongExtra(MainActivity.TIME_DATA, -1);
        app_name.setText(label);
        alert_time.setText(Long.toString(TimeUnit.MILLISECONDS.toMinutes(alerttime)));
    }

    public void onClickChangeButton(View v) {
        long time;
        try {
            time = Long.parseLong(input.getText().toString().trim());
        } catch (NumberFormatException nfex) {
            Toast.makeText(this, R.string.num_error, Toast.LENGTH_SHORT).show();
            input.getEditableText().clear();
            return;
        }

        alert_time.setText(Long.toString(time));
        intent.putExtra(MainActivity.TIME_DATA, TimeUnit.MINUTES.toMillis(time));
        Toast.makeText(this, R.string.num_change, Toast.LENGTH_SHORT).show();
        setResult( Activity.RESULT_OK, intent );
        Log.d("alert time change to", Long.toString(intent.getLongExtra(MainActivity.TIME_DATA, -1)));
        input.getEditableText().clear();
        finish();
    }

}
