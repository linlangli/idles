package io.github.grooters.idles.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public abstract class GeneralRecyclerViewAdapter<T> extends RecyclerView.Adapter<GeneralRecyclerViewHolder> {

    private Context context;
    private int layoutId;
    private SparseArray<Integer> layoutId_position;
    private List<T> dataList;

    public GeneralRecyclerViewAdapter(Context context, int layoutId, List<T> dataList){
        this.context = context;
        this.layoutId = layoutId;
        layoutId_position = setType(layoutId_position);
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public GeneralRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return GeneralRecyclerViewHolder.getHolder(context, viewType, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralRecyclerViewHolder holder, int position) {
        handle(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(layoutId_position == null){
            return layoutId;
        }
        return layoutId_position.get(position);
    }

    public abstract void handle(GeneralRecyclerViewHolder holder, T data, int position);

    public abstract SparseArray<Integer> setType(SparseArray<Integer> layoutId_position);
}
