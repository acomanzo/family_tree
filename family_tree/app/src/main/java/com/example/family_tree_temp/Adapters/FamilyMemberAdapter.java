package com.example.family_tree_temp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.family_tree_temp.Activities.TreeEditorActivity;
import com.example.family_tree_temp.Fragments.TreeEditor.HomeFragment;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.MyViewHolder> implements Filterable {
    private List<FamilyMember> mDataset;
    private TreeEditorActivity treeEditorActivity;
    private List<FamilyMember> mDatasetFiltered;
    private HomeFragment.OnFamilyMemberItemClickedListener onClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mDatasetFiltered = mDataset;
                } else {
                    List<FamilyMember> filteredList = new ArrayList<>();
                    for (FamilyMember familyMember : mDataset) {
                        if (familyMember.getFirstName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(familyMember);
                        } else if (familyMember.getLastName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(familyMember);
                        }
                    }
                    mDatasetFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDatasetFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDatasetFiltered = (ArrayList<FamilyMember>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public FamilyMemberAdapter(TreeEditorActivity treeEditorActivity, HomeFragment.OnFamilyMemberItemClickedListener onClickListener) {
        this.treeEditorActivity = treeEditorActivity;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FamilyMemberAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_person_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, treeEditorActivity, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberAdapter.MyViewHolder holder, int position) {
        FamilyMember familyMember = mDatasetFiltered.get(position);
        holder.bind(familyMember);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDatasetFiltered.size();
        }
        else return 0;
    }

    public void setDataset(List<FamilyMember> people) {
        mDataset = people;
        mDatasetFiltered = people;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView familyMemberName;

        private TreeEditorActivity treeEditorActivity;

        private FamilyMemberAdapter familyMemberAdapter;

        public MyViewHolder(@NonNull View itemView, TreeEditorActivity treeEditorActivity, FamilyMemberAdapter familyMemberAdapter) {
            super(itemView);
            this.itemView = itemView;
            familyMemberName = itemView.findViewById(R.id.person_name);

            this.treeEditorActivity = treeEditorActivity;

            this.familyMemberAdapter = familyMemberAdapter;
        }

        private void bind(FamilyMember familyMember) {
            String name = familyMember.getFirstName() + " " + familyMember.getLastName();
            familyMemberName.setText(name);
        }
    }
}

