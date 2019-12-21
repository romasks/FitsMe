package ru.fitsme.android.presentation.fragments.filters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.DialogCompat;
import androidx.core.graphics.drawable.DrawableCompat;

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
        if (groupPosition == 2){
            if (convertView == null || convertView.findViewById(R.id.item_filter_color_table) == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_filter_colors, null);
            }
            TableLayout tableLayout = (TableLayout) convertView.findViewById(R.id.item_filter_color_table);
            tableLayout.removeAllViews();

            int bottomMarginDp = 24;
            int bottomMarginPx = converDpToPixels(bottomMarginDp);

            int numOfColumns = 4;
            int FAKE_NUM_OF_ITEMS = 10;
            for (int i = 1; i <= FAKE_NUM_OF_ITEMS; i = i + numOfColumns) {
                TableRow tableRow = new TableRow(context);
                LinearLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, bottomMarginPx);
                tableRow.setLayoutParams(layoutParams);
                tableLayout.addView(tableRow);
                int numOfRow = (int) Math.ceil((double) FAKE_NUM_OF_ITEMS / numOfColumns);
                int numColonsInThisIteration;
                if (i > numOfColumns * (numOfRow - 1)){
                    numColonsInThisIteration = FAKE_NUM_OF_ITEMS % numOfColumns;
                } else {
                    numColonsInThisIteration = numOfColumns;
                }
                for (int j = 0; j < numColonsInThisIteration; j++) {
                    FilterColorImageView imageView = new FilterColorImageView(context, false, Color.RED);
                    tableRow.addView(imageView);
                    imageView.setOnClickListener(view -> imageView.toggle());
                }
            }
        } else {
            if (convertView == null || convertView.findViewById(R.id.item_filter_child_tv)  == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_filter_child, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.item_filter_child_tv);
            textView.setText(groups.get(groupPosition).get(childPosition));
            Switch switcher = (Switch) convertView.findViewById(R.id.item_filter_child_switch);
            switcher.setOnCheckedChangeListener((compoundButton, b) -> {

            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private int converDpToPixels(int dp){
        Resources r = context.getResources();
        return  (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }
}
