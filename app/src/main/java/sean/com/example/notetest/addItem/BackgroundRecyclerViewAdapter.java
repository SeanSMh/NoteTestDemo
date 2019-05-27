package sean.com.example.notetest.addItem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.List;

import sean.com.example.notetest.R;
import sean.com.example.notetest.entity.BackgroundBean;

/**
 * @author Sean
 * @data 2019/5/24
 */
public class BackgroundRecyclerViewAdapter extends RecyclerView.Adapter<
        BackgroundRecyclerViewAdapter.ViewHolder> {

    private List<BackgroundBean> mBeanList;
    private Context mContext;
    private SendColorListener mSendColorListener;

    public BackgroundRecyclerViewAdapter(Context mContext, List<BackgroundBean> list) {
        this.mContext = mContext;
        this.mBeanList = list;
    }

    @NonNull
    @Override
    public BackgroundRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.background_item,
                viewGroup, false);

        final ViewHolder holder = new ViewHolder(view);

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (holder.isChecked.isChecked()) {
                    //holder.check_icon.setVisibility(View.VISIBLE);
                    Log.d("TAG-----", "点击");
                }
            }
        });*/

        holder.isChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isChecked.isChecked()) {
                    int position = holder.getAdapterPosition();
                    mSendColorListener.sendColor(position);
                    Log.d("TAG-----", "点击");
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BackgroundBean backgroundBean = mBeanList.get(i);
        viewHolder.check.setBackgroundColor(backgroundBean.getColorType());
    }


    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView check;
        CheckBox isChecked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check = (ImageView) itemView.findViewById(R.id.check_img);
            isChecked = (CheckBox) itemView.findViewById(R.id.ischecked);
        }
    }

    public interface SendColorListener {
        void sendColor(int colorId);
    }

    public void setSendColorListener(SendColorListener listener) {
        this.mSendColorListener = listener;
    }
}
