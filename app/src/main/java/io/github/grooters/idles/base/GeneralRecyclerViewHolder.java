package io.github.grooters.idles.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GeneralRecyclerViewHolder extends RecyclerView.ViewHolder {

    private View itemView;

    private SparseArray<View> views;


    private GeneralRecyclerViewHolder(@NonNull View itemView) {

        super(itemView);

        this.itemView = itemView;

        views = new SparseArray<>();

    }

    static GeneralRecyclerViewHolder getHolder(Context context, int layoutId, ViewGroup parent){

        return new GeneralRecyclerViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false));

    }

    public <T extends View> T getView(int viewId){

        View view =views.get(viewId);

        if(view == null){

            view = itemView.findViewById(viewId);

            views.put(viewId, view);

        }

        return (T)view;

    }

}
