package com.chairul.pariwisatayogyakarta.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chairul.pariwisatayogyakarta.MainActivity;
import com.chairul.pariwisatayogyakarta.Model.Tourism;
import com.chairul.pariwisatayogyakarta.R;

import java.util.ArrayList;
import java.util.List;

public class ListTourismAdapter extends RecyclerView.Adapter<ListTourismAdapter.ListViewHolder> {
    private Context mContext;
    private List<Tourism> listTourism;
    private OnItemClickCallback onItemClickCallback;



    public ListTourismAdapter(Context mContext, List<Tourism> listTourism) {
        this.mContext = mContext;
        this.listTourism = listTourism;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Tourism tourism = listTourism.get(position);
        Glide.with(mContext).load(tourism.getGambar()).into(holder.imgPhoto);
        holder.tvName.setText(tourism.getNama());
        holder.tvAlamat.setText(tourism.getAlamat());
//        holder.tvDetail.setText(tourism.getDetail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listTourism.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTourism.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName,tvAlamat, tvDetail;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvAlamat = itemView.findViewById(R.id.tv_item_alamat);
            tvDetail = itemView.findViewById(R.id.tv_item_detail);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Tourism data);
    }
}
