package sean.com.example.notetest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sean.com.example.notetest.entity.EventsInfo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton addInfo;
    private RecyclerviewAdapter mRecyclerviewAdapter;
    private List<EventsInfo> list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TextView mainTitle, lastDay, lastHour, lastMinute, lastSecond;
    private Button deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("TAG----->", "oncreate");

        //设置与状态栏融合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //加载主布局
        setContentView(R.layout.activity_main);

        deleteAll = findViewById(R.id.deleteAll);
        addInfo = findViewById(R.id.float_addInfo);
        mainTitle = findViewById(R.id.maintitle);

        lastDay = findViewById(R.id.lastday);
        lastHour = findViewById(R.id.lasthour);
        lastMinute = findViewById(R.id.lastminute);
        lastSecond = findViewById(R.id.lastsecond);

        //初始的时候，要添加数据库查询功能，把所有未过期的事件查询并显示出来
        initList();
        //加载RecyclerView
        initRecyclerView();
        //计算剩余时间
        computeTime();

        addInfo.setOnClickListener(this);
        deleteAll.setOnClickListener(this);

    }

    public void initList() {
        list.clear();
        list.addAll(queryFromDb());
        if (list.size() == 0) {
            mainTitle.setText("暂无待办事项");
        } else {
            mainTitle.setText("您有" + list.size() + "项事务待办");
        }
    }

    public void initRecyclerView() {
        mRecyclerView = findViewById(R.id.things);
        mRecyclerviewAdapter = new RecyclerviewAdapter(list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mRecyclerviewAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_addInfo:
                Intent intent = new Intent(this, AddInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteAll:
                DaoUtil.getInstance().deleteAll();
                initList();
                mRecyclerviewAdapter.notifyDataSetChanged();
            default:
                break;
        }
    }

    //从数据库查询数据
    public List<EventsInfo> queryFromDb() {
        List<EventsInfo> mList = DaoUtil.getInstance().queryInfo();
        for (EventsInfo eventsInfo : mList) {
            Log.d("TAGmi----->", eventsInfo.getData());
        }
        return mList;
    }

    public void computeTime() {

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    private Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d("TAG-----", "计算时间");
                        for (EventsInfo info : list) {
                            long diff = TimeUtil.getInstance().compute(info.getData());
                            long days = diff / (1000 * 60 * 60 * 24);
                            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                            long seconds = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60) -
                             minutes*(1000*60))/(1000* 60);

                            lastDay.setText(String.valueOf(days));
                            lastHour.setText(String.valueOf(hours));
                            lastMinute.setText(String.valueOf(minutes));
                            lastSecond.setText(String.valueOf(seconds));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //添加事件后，会走onRestart方法，所以在这里处理
    @Override
    public void onRestart() {
        Log.d("TAGmain----->", "onRestart");
        initList();
        mRecyclerviewAdapter.notifyDataSetChanged();
        super.onRestart();
    }

}
