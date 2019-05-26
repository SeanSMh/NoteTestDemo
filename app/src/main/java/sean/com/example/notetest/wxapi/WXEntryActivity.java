package sean.com.example.notetest.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.socialize.weixin.view.WXCallbackActivity;

import sean.com.example.notetest.R;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }
}
