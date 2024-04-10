package com.unipet7.mcommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unipet7.mcommerce.R;
import com.unipet7.mcommerce.models.IntroLayoutItems;

import java.util.List;

public class IntroViewPagerAdapter extends RecyclerView.Adapter<IntroViewPagerAdapter.ViewHolder> {
    List<IntroLayoutItems> introLayoutItems;


    public IntroViewPagerAdapter(List<IntroLayoutItems> introLayoutItems) {
        this.introLayoutItems = introLayoutItems;
    }

    @NonNull
    @Override
    public IntroViewPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: Inflate your layout and return your view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.intro_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewPagerAdapter.ViewHolder holder, int position) {
        IntroLayoutItems item = introLayoutItems.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.image.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return introLayoutItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleIntrotv);
            description = itemView.findViewById(R.id.contentIntrotv);
            image = itemView.findViewById(R.id.imgViewIntro);
        }
    }

}
