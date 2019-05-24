package sean.com.example.notetest.view;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import sean.com.example.notetest.R;

/**
 * @author Sean
 * @data 2019/5/23
 */
public class VibratorFragment extends DialogFragment {

    private TextView mContent;
    private ImageView cancelShake;
    private Intent mStartIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vibrator, container, false);

        //设置点击外部不消失
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        //启动振动
        mStartIntent = new Intent(getActivity(), VibratorService.class);
        getActivity().startService(mStartIntent);

        mContent = view.findViewById(R.id.fragment_content);
        cancelShake = view.findViewById(R.id.cancelshake);

        //mStopIntent = new Intent(getActivity(), VibratorService.class);
        Animation shake = AnimationUtils.loadAnimation(getActivity().getApplication(),
                R.anim.shake);
        cancelShake.startAnimation(shake);
        //振动按钮振动动画，点击取消振动,并关闭弹窗
        cancelShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mVibrator.cancel();
                //停止振动
                getActivity().stopService(mStartIntent);
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = getDialog().getWindow();
        //设置backaground，否则window属性无效
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = win.getAttributes();
        //params.gravity = Gravity.BOTTOM;

        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        win.setAttributes(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
