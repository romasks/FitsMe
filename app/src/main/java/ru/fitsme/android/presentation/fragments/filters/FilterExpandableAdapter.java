package ru.fitsme.android.presentation.fragments.filters;

import android.content.Context;
import android.content.res.Resources;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.clothes.ClotheFilter;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;
import ru.fitsme.android.domain.entities.clothes.FilterColor;
import ru.fitsme.android.domain.entities.clothes.FilterProductName;

import static ru.fitsme.android.presentation.fragments.filters.FiltersFragment.BRAND_NAME_NUMBER;
import static ru.fitsme.android.presentation.fragments.filters.FiltersFragment.COLOR_NUMBER;
import static ru.fitsme.android.presentation.fragments.filters.FiltersFragment.PRODUCT_NAME_NUMBER;

public class FilterExpandableAdapter extends BaseExpandableListAdapter {

    private FilterCallback filterCallback;
    private Context context;
    private ArrayList<String> groupNames = new ArrayList<>();
    private SparseArray<ArrayList<ClotheFilter>> filters;

    FilterExpandableAdapter(FilterCallback filterCallback, Context context,
                            SparseArray<ArrayList<ClotheFilter>> filters){
        this.filterCallback = filterCallback;
        this.context = context;
        getGroupNames();
        this.filters = filters;
    }

    private void getGroupNames() {
        groupNames.add(PRODUCT_NAME_NUMBER, context.getString(R.string.filter_clothe_types));
        groupNames.add(BRAND_NAME_NUMBER, context.getString(R.string.filter_brands));
        groupNames.add(COLOR_NUMBER, context.getString(R.string.filter_colors));
    }

    @Override
    public int getGroupCount() {
        return filters.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List list = filters.get(groupPosition);
        if (list != null){
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return filters.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ClotheFilter> list = (ArrayList) filters.get(groupPosition);
        return list.get(childPosition);
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
        if (groupPosition == COLOR_NUMBER){
            if (convertView == null || convertView.findViewById(R.id.item_filter_color_table) == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_filter_colors, null);
            }
            TableLayout tableLayout = (TableLayout) convertView.findViewById(R.id.item_filter_color_table);
            tableLayout.removeAllViews();

            int bottomMarginDp = 24;
            int bottomMarginPx = convertDpToPixels(bottomMarginDp);

            int numOfColumns = 4;
            FiltersFragment.FilterColorListWrapper wrapper = (FiltersFragment.FilterColorListWrapper) filters.get(COLOR_NUMBER).get(0);
            ArrayList<FilterColor> colorFilterArrayList = wrapper.getColorList();
            int colorListSize = colorFilterArrayList.size();
            int numOfRow = (int) Math.ceil((double) colorListSize / numOfColumns);
            for (int i = 0; i < numOfRow; i++) {
                TableRow tableRow = new TableRow(context);
                LinearLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, bottomMarginPx);
                tableRow.setLayoutParams(layoutParams);
                tableLayout.addView(tableRow);

                int numColonsInThisIteration;
                if (i == numOfRow - 1){
                    numColonsInThisIteration = colorListSize % numOfColumns;
                } else {
                    numColonsInThisIteration = numOfColumns;
                }
                for (int j = 0; j < numColonsInThisIteration; j++) {
                    FilterColor filterColor = colorFilterArrayList.get((numOfColumns * i) + j);
                    FilterColorImageView imageView = new FilterColorImageView(context, filterColor);
                    tableRow.addView(imageView);
                    imageView.setOnClickListener(view -> {
                        filterCallback.setFilterColor(filterColor);
                        imageView.toggle();
                    });
                }
            }
        } else {
            if (convertView == null || convertView.findViewById(R.id.item_filter_child_tv)  == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_filter_child, null);
            }
            ClotheFilter clotheFilter = (ClotheFilter) filters.get(groupPosition).get(childPosition);
            TextView textView = (TextView) convertView.findViewById(R.id.item_filter_child_tv);
            textView.setText(clotheFilter.getColorName());
            Switch switcher = (Switch) convertView.findViewById(R.id.item_filter_child_switch);
            switcher.setChecked(clotheFilter.isChecked());
            convertView.setOnClickListener(v -> {
                switch(groupPosition){
                    case PRODUCT_NAME_NUMBER:
                        FilterProductName filterProductName = (FilterProductName) clotheFilter;
                        filterProductName.setChecked(!filterProductName.isChecked());
                        filterCallback.setFilterProductName(filterProductName);
                        break;
                    case BRAND_NAME_NUMBER:
                        FilterBrand filterBrand = (FilterBrand) clotheFilter;
                        filterBrand.setChecked(!filterBrand.isChecked());
                        filterCallback.setFilterBrand(filterBrand);
                        break;
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private int convertDpToPixels(int dp){
        Resources r = context.getResources();
        return  (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
    }

    void swap(SparseArray<ArrayList<ClotheFilter>> filters){
        this.filters = filters;
        notifyDataSetChanged();
    }


    interface FilterCallback {
        void setFilterProductName(FilterProductName name);
        void setFilterBrand(FilterBrand brand);
        void setFilterColor(FilterColor color);
    }
}
