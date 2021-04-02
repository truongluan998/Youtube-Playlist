package vn.aptech.youtubeplayerplaylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoApdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<VideoYoutube> youtubeList;

    public VideoApdapter(Context context, int layout, List<VideoYoutube> youtubeList) {
        this.context = context;
        this.layout = layout;
        this.youtubeList = youtubeList;
    }

    @Override
    public int getCount() {
        return youtubeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgvThumbnail;
        TextView tvNameVideo;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvNameVideo = view.findViewById(R.id.tv_name_video);
            holder.imgvThumbnail = view.findViewById(R.id.imgv_video);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        VideoYoutube videoYoutube = youtubeList.get(i);

        holder.tvNameVideo.setText(videoYoutube.getTitle());
        Picasso.with(context).load(videoYoutube.getThumbnail()).into(holder.imgvThumbnail);

        return view;
    }
}
