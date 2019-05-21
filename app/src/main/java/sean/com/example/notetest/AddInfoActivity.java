package sean.com.example.notetest;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


/*
 * 添加记录的界面
 * */
public class AddInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText title, content;
    private TextView time;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        time = findViewById(R.id.time);
        time.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.confirm:
                String strTitle = title.getText().toString();
                String strContent = content.getText().toString();
                String strTime = time.getText().toString();
                //添加往数据库写数据的功能
                DaoUtil.getInstance().addInfo(strTitle, strContent, strTime);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    //点击事件，弹出事件选择器
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time:
                View view = View.inflate(getApplicationContext(), R.layout.choostime, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datapicker);
                Button btnConfirm = (Button) view.findViewById(R.id.btnconfirm);
                Button btnCancel = (Button) view.findViewById(R.id.btncancel);
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                Log.d("TAG----->", mYear + ":" + mMonth + ":" + mDay + " ");
                datePicker.init(mYear, mMonth, mDay, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(view);
                final AlertDialog dialog = builder.create();

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mYear = datePicker.getYear();
                        mMonth = datePicker.getMonth() + 1;
                        mDay = datePicker.getDayOfMonth();
                        Log.d("TAG----->", mYear + ":" + mMonth + ":" + mDay + " ");
                        time.setText(mYear + "-" + mMonth + "-" + mDay);
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;
            default:
                break;
        }
    }

}
