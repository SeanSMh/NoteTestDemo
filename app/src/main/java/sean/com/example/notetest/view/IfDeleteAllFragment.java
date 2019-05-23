package sean.com.example.notetest.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import sean.com.example.notetest.R;
import sean.com.example.notetest.util.DaoUtil;

/**
 * @author Sean
 * @data 2019/5/23
 */
public class IfDeleteAllFragment extends DialogFragment implements View.OnClickListener {

    private TextView cancle;
    private TextView confirm;
    private DeleteListener mDeleteListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ifdeleteall, container, false);
        //设置点击外部不消失
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        cancle = view.findViewById(R.id.btn_cancel);
        confirm = view.findViewById(R.id.btn_confirm);

        cancle.setOnClickListener(this);
        confirm.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                DaoUtil.getInstance().deleteAll();
                mDeleteListener.deleteAll();
                dismiss();
                break;
            default:
                break;
        }
    }

    public interface DeleteListener{
        void deleteAll();
    }

    public void setDeleteListener(DeleteListener listener) {
        this.mDeleteListener = listener;
    }

}
