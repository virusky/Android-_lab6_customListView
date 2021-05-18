package com.example.room_bai02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ThingViewHolder>{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Item> items;



    public ItemAdapter(Context context, List<Item> items) {
        layoutInflater=LayoutInflater.from(context);
        this.context = context;
        this.items = items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ThingViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ThingViewHolder holder, int position) {

        Item item =items.get(position);
        holder.tvName.setText(position+1 + ". "+item.getName());
       

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemDao itemDao;
                AppDatabase db;
                db = Room.databaseBuilder(context, AppDatabase.class, "itemDB").allowMainThreadQueries().build();
                itemDao = db.itemDao();

                itemDao.delete(items.get(position));
                items.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ThingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        Button buttonEdit;
        Button buttonDelete;
        private ItemClickListener itemClickListener;
        ItemAdapter itemAdapter;
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
        public ThingViewHolder(@NonNull View itemView, ItemAdapter adapter) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            buttonEdit=itemView.findViewById(R.id.buttonEdit);
            buttonDelete=itemView.findViewById(R.id.buttonDelete);
            this.itemAdapter=adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
