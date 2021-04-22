package com.example.family_tree_temp.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.family_tree_temp.Activities.MainActivity;
import com.example.family_tree_temp.Fragments.HomeFragment;
import com.example.family_tree_temp.Models.FamilyMember;
import com.example.family_tree_temp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FamilyMemberAdaptor extends RecyclerView.Adapter<FamilyMemberAdaptor.MyViewHolder> implements Filterable {
    private List<FamilyMember> mDataset;
    private MainActivity mainActivity;
    private List<FamilyMember> mDatasetFiltered;
    HomeFragment.OnFamilyMemberItemClickedListener onClickListener;

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

    public FamilyMemberAdaptor(MainActivity mainActivity, HomeFragment.OnFamilyMemberItemClickedListener onClickListener) {
        this.mainActivity = mainActivity;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FamilyMemberAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_person_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mainActivity, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberAdaptor.MyViewHolder holder, int position) {
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
        //return mDataset.size();
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

        private MainActivity mainActivity;

        private FamilyMemberAdaptor familyMemberAdaptor;

        public MyViewHolder(@NonNull View itemView, MainActivity mainActivity, FamilyMemberAdaptor familyMemberAdaptor) {
            super(itemView);
            this.itemView = itemView;
            familyMemberName = itemView.findViewById(R.id.person_name);

            this.mainActivity = mainActivity;

            this.familyMemberAdaptor = familyMemberAdaptor;
        }

        private void bind(FamilyMember familyMember) {
            String name = familyMember.getFirstName() + " " + familyMember.getLastName();
            familyMemberName.setText(name);
        }
    }
}

