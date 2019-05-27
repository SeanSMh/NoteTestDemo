package sean.com.example.notetest.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import sean.com.example.notetest.R;
import sean.com.example.notetest.entity.EventsInfo;
import sean.com.example.notetest.util.DaoUtil;
import sean.com.example.notetest.util.TimeUtil;
import sean.com.example.notetest.addItem.AddInfoActivity;
import sean.com.example.notetest.view.IfDeleteAllFragment;
import sean.com.example.notetest.view.MyAnimation;
import sean.com.example.notetest.view.VibratorFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton addInfo;
    private RecyclerviewAdapter mRecyclerviewAdapter;
    private List<EventsInfo> list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TextView mainTitle, day, hour, minute, second;
    private Button deleteAll;
    private Disposable mDisposable;
    private VibratorFragment mFragment;
    private IfDeleteAllFragment mIfDeleteAllFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG----->", "oncreate");

        //设置与状态栏融合
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //加载主布局
        setContentView(R.layout.activity_main);

        day = findViewById(R.id.top_last_day);
        hour = findViewById(R.id.top_last_hour);
        minute = findViewById(R.id.top_last_minute);
        second = findViewById(R.id.top_last_second);

        deleteAll = findViewById(R.id.deleteAll);
        addInfo = findViewById(R.id.float_addInfo);
        mainTitle = findViewById(R.id.maintitle);
        mRecyclerView = findViewById(R.id.things);

        //初始的时候，要添加数据库查询功能，把所有未过期的事件查询并显示出来
        initList();
        //加载RecyclerView
        initRecyclerView();

        //计算2019已经过去了多久
        initLastTime();

        addInfo.setOnClickListener(this);
        deleteAll.setOnClickListener(this);

        //申请权限
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

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
        mRecyclerviewAdapter = new RecyclerviewAdapter(this, list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mRecyclerviewAdapter);

        //处理RecyclerView的侧滑删除
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swiped = ItemTouchHelper.RIGHT;  //设置为只能向右滑动
                //第一个参数拖动，第二个参数侧滑
                return makeMovementFlags(0, swiped);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                DaoUtil.getInstance().deleteInfo(list.get(position).getId());
                initList();
                mRecyclerviewAdapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(mRecyclerView);

        mRecyclerviewAdapter.setShareListener(new RecyclerviewAdapter.ShareListener() {
            @Override
            public void showShare() {
                UMImage image = new UMImage(MainActivity.this, R.drawable.timeicon);
                new ShareAction(MainActivity.this).withText("hello")
                        .withMedia(image)
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .open();
            }
        });
        //计算剩余时间
        computeTime();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_addInfo:
                Intent intent = new Intent(this, AddInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteAll:
                mIfDeleteAllFragment = new IfDeleteAllFragment();
                mIfDeleteAllFragment.show(getSupportFragmentManager(), "ifdelete");
                mIfDeleteAllFragment.setDeleteListener(new IfDeleteAllFragment.DeleteListener() {
                    @Override
                    public void deleteAll() {
                        initList();
                        mRecyclerviewAdapter.notifyDataSetChanged();
                    }
                });

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

    //计算单个事项剩余时间
    public void computeTime() {

        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d("TAG-----", "计算时间");
                        for (EventsInfo info : list) {
                            String endData = info.getData();
                            Log.d("TAGendData----->", endData);
                            long diff = TimeUtil.getInstance().compute(endData);
                            if (diff != 0) {
                                long days = diff / (1000 * 60 * 60 * 24);
                                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                                long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) -
                                        minutes * (1000 * 60)) / 1000;

                                info.setDay(days);
                                info.setHour(hours);
                                info.setMinute(minutes);
                                info.setSecond(seconds);
                                info.setDiff(diff);
                                mRecyclerviewAdapter.notifyDataSetChanged();
                            } else {
                                //使手机振动
                                phoneVibrator();
                            }
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

    //开启另外一个DialogFragment振动
    public void phoneVibrator() {
        mFragment = new VibratorFragment();
        mFragment.show(getSupportFragmentManager(), "dialog");
    }

    //主页计算离2020还有多长时间
    public void initLastTime() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        String endtime = "2020-01-01 00:00:00";
                        long diff = TimeUtil.getInstance().compute(endtime);
                        if (diff != 0) {
                            long days = diff / (1000 * 60 * 60 * 24);
                            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                            long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) -
                                    minutes * (1000 * 60)) / 1000;

                            day.setText(String.valueOf(days));
                            hour.setText(String.valueOf(hours));
                            minute.setText(String.valueOf(minutes));
                            second.setText(String.valueOf(seconds));
                            MyAnimation myAnimation = new MyAnimation();
                            second.setAnimation(myAnimation);
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
        Log.d("TAG-----", "a");
        mRecyclerviewAdapter.notifyDataSetChanged();
        super.onRestart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
    }

    //分享接口回调
    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.d("TAG分享回调----->", "onStart");
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,"成功了",Toast.LENGTH_LONG).show();
            Log.d("TAG分享回调----->", "onResult");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
            Log.d("TAG分享回调-----", t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        mDisposable.dispose();
        super.onDestroy();
    }

}
