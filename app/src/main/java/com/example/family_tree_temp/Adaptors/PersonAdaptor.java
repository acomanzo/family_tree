package com.example.family_tree_temp.Adaptors;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.family_tree_temp.Activities.MainActivity;
import com.example.family_tree_temp.Fragments.HomeFragment;
import com.example.family_tree_temp.Models.Address;
//import com.example.family_tree_temp.DetailDump;
import com.example.family_tree_temp.R;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonAdaptor extends RecyclerView.Adapter<PersonAdaptor.MyViewHolder> implements Filterable {
    private List<Person> mDataset;
    private MainActivity mainActivity;
    private List<Person> mDatasetFiltered;
    //private View.OnClickListener onClickListener;
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
                    List<Person> filteredList = new ArrayList<>();
                    for (Person person : mDataset) {
                        if (person.getFirstName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(person);
                        }
                        List<Person> children = person.getChildren();
                        for (Person child : children) {
                            if (child.getFirstName().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(person);
                            }
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
                mDatasetFiltered = (ArrayList<Person>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView personName;
        private LinearLayout subItem;
        private TextInputEditText streetNumber;
        private TextInputEditText streetName;
        private TextInputEditText town;
        private TextInputEditText state;
        private TextInputEditText zipcode;
        private TextInputEditText gender;
        private TextView descendants;

        private Button updateButton;
        private Button addRelativeButton;

        private MainActivity mainActivity;

        private PersonAdaptor personAdaptor;

        public MyViewHolder(@NonNull View itemView, MainActivity mainActivity, PersonAdaptor personAdaptor) {
            super(itemView);
            this.itemView = itemView;
            personName = itemView.findViewById(R.id.person_name);
            subItem = itemView.findViewById(R.id.sub_item);
            streetNumber = itemView.findViewById(R.id.person_street_number);
            streetName = itemView.findViewById(R.id.person_street_name);
            town = itemView.findViewById(R.id.person_town);
            state = itemView.findViewById(R.id.person_state);
            zipcode = itemView.findViewById(R.id.person_zipcode);
            gender = itemView.findViewById(R.id.person_gender);
            descendants = itemView.findViewById(R.id.person_descendants);

            updateButton = itemView.findViewById(R.id.update_button);
            addRelativeButton = itemView.findViewById(R.id.add_relationship);

            this.mainActivity = mainActivity;

            this.personAdaptor = personAdaptor;
        }

        private void bind(Person person) {
            boolean expanded = person.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

            String name = person.getFirstName() + " " + person.getLastName();
            personName.setText(name);
//            Address address = person.getAddress();
//            streetNumber.setText(address.getStreetNumber());
//            configureTextEdit(streetNumber, "streetNumber");
//            streetName.setText(address.getStreetName());
//            configureTextEdit(streetName, "streetName");
//            town.setText(address.getTownCity());
//            configureTextEdit(town, "townCity");
//            state.setText(address.getState());
//            configureTextEdit(state, "state");
//            zipcode.setText(address.getZipcode());
//            configureTextEdit(zipcode, "zipcode");
            gender.setText(String.valueOf(person.getGenderId()));

            ArrayList<Person> children = person.getChildren();
            if (children.size() > 0) {
                String descendantsStr = "";
                for (Person p : children) {
                    descendantsStr = descendantsStr + p.toString() + ", ";
                }
                descendantsStr = descendantsStr.substring(0, descendantsStr.length() - 1);
                descendants.setText(descendantsStr);
            } else {
                descendants.setText("No children.");
            }

            updateButton.setOnClickListener(v -> {
                String updatedFirstName = personName.getText().toString();
                String updatedStreetNumber = streetNumber.getText().toString();
                String updatedStreetName = streetName.getText().toString();
                String updatedTown = town.getText().toString();
                String updatedState = state.getText().toString();
                String updatedZipcode = zipcode.getText().toString();
                String updatedGender = gender.getText().toString();


            });

            addRelativeButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                //mainActivity.toggleHomeFragmentToAddPersonFragment(true, position);
            });
        }

        private void configureTextEdit(TextInputEditText textInputEditText, String field) {
            textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH
                            || actionId == EditorInfo.IME_ACTION_DONE
                            || event != null
                            && event.getAction() == KeyEvent.ACTION_DOWN
                            && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            // the user is done typing
                            switch (field) {
                                case "streetNumber":
                                    //DetailDump.setAddressStreetNumber(getAdapterPosition(), v.getText().toString());
                                    break;
                                case "streetName":
                                    //DetailDump.setAddressStreetName(getAdapterPosition(), v.getText().toString());
                                    break;
                                case "townCity":
                                    //DetailDump.setAddressTownCity(getAdapterPosition(), v.getText().toString());
                                    break;
                                case "state":
                                    //DetailDump.setAddressState(getAdapterPosition(), v.getText().toString());
                                    break;
                                case "country":
                                    //DetailDump.setAddressCountry(getAdapterPosition(), v.getText().toString());
                                    break;
                                case "zipcode":
                                    //DetailDump.setAddressZipcode(getAdapterPosition(), v.getText().toString());
                                    break;
                                case "gender":
                                    break;
                            }

                            personAdaptor.notifyItemChanged(getAdapterPosition());
                            return true; // consume
                        }
                    }
                    return false;
                }
            });
        }
    }

//    public PersonAdaptor(ArrayList<Person> mDataset, MainActivity mainActivity) {
//        this.mDataset = mDataset;
//        this.mDatasetFiltered = mDataset;
//        this.mainActivity = mainActivity;
//    }

    public PersonAdaptor(MainActivity mainActivity, HomeFragment.OnFamilyMemberItemClickedListener onClickListener) {
//        this.mDataset = mDataset;
//        this.mDatasetFiltered = mDataset;
        this.mainActivity = mainActivity;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public PersonAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptor_person_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v, mainActivity, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdaptor.MyViewHolder holder, int position) {
        //Person person = mDataset.get(position);
        Person person = mDatasetFiltered.get(position);
        holder.bind(person);

//        holder.itemView.setOnClickListener(v -> {
//            boolean expanded = person.isExpanded();
//            person.setExpanded(!expanded);
//            notifyItemChanged(position);
////            boolean show = toggleLayout(!person.isExpanded(), v, holder.subItem);
////            person.setExpanded(show);
////            notifyItemChanged(position);
//        });
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

//    public void add(Person person) {
//        int position = person.getFutureRelativePosition();
//        if (position != -1) {
//            String relationship = person.getFutureRelativeRelationship();
//            switch (relationship) {
//                case "Parent":
//                    person.addChild(mDataset.get(position));
//                    break;
//                case "Child":
//                    mDataset.get(position).addChild(person);
//                    break;
//            }
//            notifyItemChanged(position);
//        }
//        DetailDump.addData(person);
//
//
//        //mDataset.add(person);
//        //notifyItemInserted(mDataset.size() - 1);
//        //notifyItemChanged();
//
//    }

    public void setDataset(List<Person> people) {
        mDataset = people;
        mDatasetFiltered = people;
        notifyDataSetChanged();
    }
}
