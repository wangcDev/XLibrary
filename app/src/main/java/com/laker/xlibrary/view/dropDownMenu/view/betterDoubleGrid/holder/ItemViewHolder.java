package com.laker.xlibrary.view.dropDownMenu.view.betterDoubleGrid.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.laker.xlibrary.R;

import butterknife.ButterKnife;

/**
 * auther: baiiu
 * time: 16/6/5 05 23:35
 * description:
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final FilterCheckedTextView textView;
    private View.OnClickListener mListener;


    public ItemViewHolder(Context mContext, ViewGroup parent, View.OnClickListener mListener) {
        super(UIUtil.infalte(mContext, R.layout.holder_item, parent));
        textView = ButterKnife.findById(itemView, R.id.tv_item);
        this.mListener = mListener;
    }


    public void bind(String s,int pos) {
        textView.setText(s);
        textView.setTag(R.id.tag_content,s);
        textView.setTag(R.id.tag_pos,pos);
        textView.setOnClickListener(mListener);
    }
}
