package sean.com.example.notetest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeoutException;

import sean.com.example.notetest.entity.EventsInfo;

/**
 * @author Sean
 * @data 2019/5/21
 */
public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {

    private List<EventsInfo> mInfoList;

    //构造函数
    public RecyclerviewAdapter(List<EventsInfo> list) {
        this.mInfoList = list;
    }

    //需要复写的方法
    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recyclerview_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //需要复写的方法
    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder viewHolder, int i) {
        EventsInfo eventsInfo = mInfoList.get(i);
        viewHolder.showTime.setText(eventsInfo.getData());
        viewHolder.showContent.setText(eventsInfo.getContent());
        viewHolder.showTitle.setText(eventsInfo.getTitle());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showTitle = (TextView) itemView.findViewById(R.id.showtitle);
            showContent = (TextView) itemView.findViewById(R.id.showcontent);
            showTime = (TextView) itemView.findViewById(R.id.showtime);
        }

    }
}
