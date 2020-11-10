package com.example.myximalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myximalaya.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {

    private List<Album> mData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 这里是载入 View
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        // 这里是设置数据
        holder.itemView.setTag(position); // 点击哪一条数据就可以知道了
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        // 返回要显示的个数
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (mData != null) {
            mData.clear();
            mData.addAll(albumList);
        }

        // 更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(Album album) {
            // 找到各个控件，设置数据。
            // 专辑封面
            ImageView albumCoverIv = itemView.findViewById(R.id.album_cover);
            // 标题
            TextView albumTitleTv = itemView.findViewById(R.id.album_title_tv);
            // 描述(副标题)
            TextView albumDescrTv = itemView.findViewById(R.id.album_description_tv);
            // 播放数量
            TextView albumPlayCountTv = itemView.findViewById(R.id.album_play_count);
            // 专辑数量
            TextView albumContentSizeTv = itemView.findViewById(R.id.album_content_size);


            albumTitleTv.setText(album.getAlbumTitle());
            albumDescrTv.setText(album.getAlbumIntro());
            albumPlayCountTv.setText(album.getPlayCount() + "");
            albumContentSizeTv.setText(album.getIncludeTrackCount() + "");

            Picasso.get().load(album.getCoverUrlLarge()).into(albumCoverIv);


        }
    }
}
