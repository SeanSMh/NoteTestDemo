package sean.com.example.notetest.addItem;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sean.com.example.notetest.R;
import sean.com.example.notetest.entity.BackgroundBean;
import sean.com.example.notetest.util.DaoUtil;


/*
 * 添加记录的界面
 * */
public class AddInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText title, content;
    private TextView time;
    private int mYear, mMonth, mDay, backgroundColor;
    private long hour, minute;

    private String realMinute = null;
    private String realHour = null;
    private String realMonth = null;
    private String realDay = null;

    private BackgroundRecyclerViewAdapter mAdapter;
    private RecyclerView background_recy;
    private List<BackgroundBean> backgrounds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置与状态栏融合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

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

        initList();
        initRecyclerview();
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
                if (TextUtils.isEmpty(strTitle)) {
                    Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    DaoUtil.getInstance().addInfo(strTitle, strContent, strTime, backgroundColor);
                    finish();
                }
                break;
            default:
                break;
        }
        return true;
    }

    public void initList() {
        backgrounds.add(new BackgroundBean(getResources().getColor(R.color.colorOrange)));
        backgrounds.add(new BackgroundBean(getResources().getColor(R.color.colorBlue)));
        backgrounds.add(new BackgroundBean(getResources().getColor(R.color.colorYellow)));
    }

    public void initRecyclerview() {
        background_recy = findViewById(R.id.background_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mAdapter = new BackgroundRecyclerViewAdapter(this, backgrounds);
        background_recy.setLayoutManager(manager);
        background_recy.setAdapter(mAdapter);

        mAdapter.setSendColorListener(new BackgroundRecyclerViewAdapter.SendColorListener() {
            @Override
            public void sendColor(int colorId) {
                backgroundColor = colorId;
                Log.d("TAGcolor----->", "被选中的颜色" + colorId);
            }
        });
    }

    //点击事件，弹出事件选择器
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time:
                View view = View.inflate(getApplicationContext(), R.layout.choostime, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datapicker);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timepicker);
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
                        hour = timePicker.getHour();
                        minute = timePicker.getMinute();

                        if (minute < 10) {
                            realMinute = "0" + String.valueOf(minute);
                        } else {
                            realMinute = String.valueOf(minute);
                        }
                        if (hour < 10) {
                            realHour = "0" + String.valueOf(hour);
                        } else {
                            realHour = String.valueOf(hour);
                        }
                        if (mDay < 10) {
                            realDay = "0" + String.valueOf(mDay);
                        } else {
                            realDay = String.valueOf(mDay);
                        }
                        if (mMonth < 10) {
                            realMonth = "0" + String.valueOf(mMonth);
                        } else {
                            realMonth = String.valueOf(mMonth);
                        }
                        Log.d("TAG----->", mYear + ":" + mMonth + ":" + mDay + " ");
                        time.setText(mYear + "-" + realMonth + "-" + realDay + " " + realHour + ":" + realMinute
                                + ":" + "00");
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
