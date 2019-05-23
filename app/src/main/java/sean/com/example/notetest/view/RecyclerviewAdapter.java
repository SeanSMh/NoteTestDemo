package sean.com.example.notetest.view;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sean.com.example.notetest.R;
import sean.com.example.notetest.entity.EventsInfo;

/**
 * @author Sean
 * @data 2019/5/21
 */
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private List<EventsInfo> mInfoList;
    private Context mContext;

    //构造函数
    public RecyclerviewAdapter(Context mContext, List<EventsInfo> list) {
        this.mInfoList = list;
        this.mContext = mContext;
    }

    //需要复写的方法
    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent  intent = new Intent(mContext, UpdateInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", mInfoList.get(position).getTitle());
                bundle.putString("time", mInfoList.get(position).getData());
                bundle.putString("content", mInfoList.get(position).getContent());
                bundle.putInt("id", position);
                intent.putExtra("bundle", bundle);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    //需要复写的方法
    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder viewHolder, int i) {
        EventsInfo eventsInfo = mInfoList.get(i);
        viewHolder.showTime.setText(eventsInfo.getData());
        viewHolder.showContent.setText(eventsInfo.getContent());
        viewHolder.showTitle.setText(eventsInfo.getTitle());

        if(eventsInfo.getDiff() >= 0) {
            viewHolder.lastData.setVisibility(View.VISIBLE);
            viewHolder.warning.setVisibility(View.GONE);
            viewHolder.lastDay.setText(String.valueOf(eventsInfo.getDay()));
            viewHolder.lastHour.setText(String.valueOf(eventsInfo.getHour()));
            viewHolder.lastMinute.setText(String.valueOf(eventsInfo.getMinute()));
            viewHolder.lastSeconds.setText(String.valueOf(eventsInfo.getSecond()));
        } else  {
            viewHolder.lastData.setVisibility(View.GONE);
            viewHolder.warning.setVisibility(View.VISIBLE);
        }

    }

    //需要复写的方法
    @Override
    public int getItemCount() {
        return mInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView showTitle;
        TextView showContent;
        TextView showTime;
        TextView lastDay;
        TextView lastHour;
        TextView lastMinute;
        TextView lastSeconds;
        ConstraintLayout lastData;
        TextView warning;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showTitle = (TextView) itemView.findViewById(R.id.showtitle);
            showContent = (TextView) itemView.findViewById(R.id.showcontent);
            showTime = (TextView) itemView.findViewById(R.id.showtime);
            lastDay = (TextView) itemView.findViewById(R.id.lastday);
            lastHour = (TextView) itemView.findViewById(R.id.lasthour);
            lastMinute = (TextView) itemView.findViewById(R.id.lastminute);
            lastSeconds = (TextView) itemView.findViewById(R.id.lastsecond);
            lastData = (ConstraintLayout) itemView.findViewById(R.id.lastData);
            warning = (TextView) itemView.findViewById(R.id.warning);
        }

    }
}
