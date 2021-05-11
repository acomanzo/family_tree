package com.example.family_tree_temp.Adaptors;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TreeViewAdapter extends RecyclerView.Adapter<TreeViewAdapter.ViewHolder> {

    private List<FamilyMember> localDataSet;

    public TreeViewAdapter(List<FamilyMember> dataSet) {
        this.localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_tree_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setDataSet(List<FamilyMember> dataSet) {
        this.localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private final TextView name;
        private final ImageButton imageButton;

        private TreeViewAdapter treeViewAdapter;
        private final RecyclerView recyclerView;
        private List<FamilyMember> children;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            name = itemView.findViewById(R.id.tree_item_name);
            imageButton = itemView.findViewById(R.id.tree_item_button);

            recyclerView = itemView.findViewById(R.id.tree_item_recycler_view);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(itemView.getContext());
            recyclerView.setLayoutManager(manager);
            recyclerView.addItemDecoration(new DividerItemDecoration(itemView.getContext(), LinearLayoutManager.VERTICAL));
        }

        private void bind(FamilyMember familyMember) {
            String n = familyMember.getFirstName() + " " + familyMember.getLastName();
            name.setText(n);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TreeViewAdapter", "Hello from name");
                }
            });
            imageButton.setOnClickListener(v -> {
                Log.d("TreeViewAdapter", "Hello from image button");
                switch (recyclerView.getVisibility()) {
                    case View.GONE:
                        recyclerView.setVisibility(View.VISIBLE);
                        break;
                    case View.VISIBLE:
                        recyclerView.setVisibility(View.GONE);
                }
            });

            children = familyMember.getChildren();
            treeViewAdapter = new TreeViewAdapter(children);
            recyclerView.setAdapter(treeViewAdapter);
        }
    }
}
