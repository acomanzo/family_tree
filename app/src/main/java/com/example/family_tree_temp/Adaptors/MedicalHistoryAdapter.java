package com.example.family_tree_temp.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.family_tree_temp.Activities.MainActivity;
import com.example.family_tree_temp.Fragments.FamilyMemberDetailFragment;
import com.example.family_tree_temp.Models.MedicalHistory;
import com.example.family_tree_temp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MedicalHistoryAdapter extends RecyclerView.Adapter<MedicalHistoryAdapter.MedicalHistoryViewHolder> {

    private List<MedicalHistory> mDataset;
    private MainActivity mainActivity;
    private FamilyMemberDetailFragment.OnMedicalHistoryItemClickedListener onClickListener;

    public MedicalHistoryAdapter(MainActivity mainActivity, FamilyMemberDetailFragment.OnMedicalHistoryItemClickedListener onClickListener) {
        this.mainActivity = mainActivity;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MedicalHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_medical_history_item, parent, false);
        return new MedicalHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalHistoryViewHolder holder, int position) {
        MedicalHistory medicalHistory = mDataset.get(position);
        holder.bind(medicalHistory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataset != null)
            return mDataset.size();
        else
            return 0;
    }

    public void setDataset(List<MedicalHistory> medicalHistories) {
        mDataset = medicalHistories;
        notifyDataSetChanged();
    }

    public static class MedicalHistoryViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView diagnosisName;
        private TextView diagnosisDate;

        public MedicalHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            diagnosisName = itemView.findViewById(R.id.mh_diagnosis_name);
            diagnosisDate = itemView.findViewById(R.id.mh_diagnosis_date);
        }

        private void bind(MedicalHistory medicalHistory) {
            diagnosisName.setText(medicalHistory.getDiagnosis());
            diagnosisDate.setText(medicalHistory.getDateDiagnosed());
        }
    }
}
