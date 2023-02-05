package edu.northeastern.numad23sp_shambhavikul;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LinkViewAdapter extends RecyclerView.Adapter<LinkViewHolder> {

    private ArrayList<LinkCard> linkCardList;
    private LinkClickListener linkClickListener;

    public LinkViewAdapter() {}

    public LinkViewAdapter(ArrayList<LinkCard> linkCardList) {
        this.linkCardList = linkCardList;
    }

    public void setOnLinkClickListener(LinkClickListener linkClickListener) {
        this.linkClickListener = linkClickListener;
    }

    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_link_card, parent, false);
        return new LinkViewHolder(view, linkClickListener);
    }

    @Override
    public void onBindViewHolder(LinkViewHolder linkViewHolder, int position) {
        LinkCard currentLinkItem = linkCardList.get(position);
        linkViewHolder.linkName.setText(currentLinkItem.getLinkName());
        linkViewHolder.linkUrl.setText(currentLinkItem.getLinkUrl());

    }

    @Override
    public int getItemCount() {
        return linkCardList.size();
    }

    public interface LinkClickListener {
        void onLinkClick(int position);
    }
}