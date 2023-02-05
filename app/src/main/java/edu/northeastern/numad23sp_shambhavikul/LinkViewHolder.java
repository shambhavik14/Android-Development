package edu.northeastern.numad23sp_shambhavikul;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinkViewHolder extends RecyclerView.ViewHolder {

    public TextView linkName;
    public TextView linkUrl;


    public LinkViewHolder(View itemView, final LinkViewAdapter.LinkClickListener linkClickListener) {
        super(itemView);
        linkName = itemView.findViewById(R.id.link_name_value_pair);
        linkUrl = itemView.findViewById(R.id.link_url_value_pair);

        itemView.setOnClickListener(v -> {
            int layoutPosition = getLayoutPosition();
            if(layoutPosition != RecyclerView.NO_POSITION) {
                linkClickListener.onLinkClick(layoutPosition);
            }
        });
    }
}