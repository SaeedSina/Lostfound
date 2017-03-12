package com.ssdev.saeedsina.lostfound.MyClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.squareup.picasso.Picasso;
import com.ssdev.saeedsina.lostfound.MyFragments.AdListFragment;
import com.ssdev.saeedsina.lostfound.MyViews.LoadingDialog;
import com.ssdev.saeedsina.lostfound.R;

import java.util.List;

/**
 * Created by Saeedek on 20-Jan-17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<Item> itemList;
    private final OnItemClickListener listener;

    public ItemAdapter(List<Item> itemList,OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(Item item,String type);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder contactViewHolder, int i) {
        final Item item = itemList.get(i);
        contactViewHolder.bind(item, listener);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_layout, viewGroup, false);
        return new ItemViewHolder(itemView);
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView vName;
        TextView vDesc;
        TextView vDate;
        ImageView vImg;
        ImageButton vDelBtn;

        ItemViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txt_adlist_name);
            vDesc = (TextView)  v.findViewById(R.id.txt_adlist_desc);
            vDate = (TextView)  v.findViewById(R.id.txt_adlist_date);
            vImg = (ImageView) v.findViewById(R.id.img_adlist_itemimage);
            vDelBtn = (ImageButton) v.findViewById(R.id.btn_list_del);
        }

        public void bind(final Item item, final OnItemClickListener listener) {
            vName.setText(item.getName());
            vDesc.setText(item.getDescription());
            vDate.setText(item.getLostDate());
            if(item.getOwnerId().equals(Backendless.UserService.loggedInUser())){
                vDelBtn.setVisibility(View.VISIBLE);
                vDelBtn.setTag(item);
                vDelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(item,"delete");
                    }
                });
            }
            Picasso.with(itemView.getContext()).load(item.getPicPath()).into(vImg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item,"detail");
                }
            });
        }
    }
}
