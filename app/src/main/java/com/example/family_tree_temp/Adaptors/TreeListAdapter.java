package com.example.family_tree_temp.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.family_tree_temp.Fragments.TreeEditor.SelectTreeFragment;
import com.example.family_tree_temp.Models.FamilyTree;
import com.example.family_tree_temp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TreeListAdapter extends RecyclerView.Adapter<TreeListAdapter.TreeListViewHolder> {

    private List<FamilyTree> mDataset;
    private SelectTreeFragment.OnFamilyTreeItemClickedListener onClickListener;

    public TreeListAdapter(SelectTreeFragment.OnFamilyTreeItemClickedListener onClickListener) {
        this(new ArrayList<>(), onClickListener);
    }

    public TreeListAdapter(List<FamilyTree> mDataset, SelectTreeFragment.OnFamilyTreeItemClickedListener onClickListener) {
        this.mDataset = mDataset;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TreeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_tree_list_item, parent, false);
        return new TreeListAdapter.TreeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeListViewHolder holder, int position) {
        FamilyTree familyTree = mDataset.get(position);
        holder.bind(familyTree);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setDataset(List<FamilyTree> dataset) {
        mDataset = dataset;
        notifyDataSetChanged();
    }

    public static class TreeListViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView treeName;

        public TreeListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            treeName = itemView.findViewById(R.id.tree_list_item_name);
        }

        private void bind(FamilyTree familyTree) {
            treeName.setText(familyTree.getTreeName());
        }
    }
}
