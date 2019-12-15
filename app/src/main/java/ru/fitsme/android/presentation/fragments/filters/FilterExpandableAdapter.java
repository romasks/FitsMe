package ru.fitsme.android.presentation.fragments.filters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import ru.fitsme.android.R;

public class FilterExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> groupNames;
    private ArrayList<ArrayList<String>> groups;

    FilterExpandableAdapter(Context context,
                            ArrayList<String> groupNames,
                            ArrayList<ArrayList<String>> groups){
        this.context = context;
        this.groupNames = groupNames;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_filter_group, null);
        }
        convertView.findViewById(R.id.item_filter_group_indicator_im).setSelected(isExpanded);
        TextView textView = (TextView) convertView.findViewById(R.id.item_filter_group_tv);
        textView.setText(groupNames.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_filter_child, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.item_filter_child_tv);
        textView.setText(groups.get(groupPosition).get(childPosition));
        Switch switcher = (Switch) convertView.findViewById(R.id.item_filter_child_switch);
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
