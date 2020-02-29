package ru.fitsme.android.presentation.fragments.profile.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

import ru.fitsme.android.R;

class TableFiller {

    static void fillButtons(Context context,
                                         SizeObserver topSizeObserver,
                                         List<String> list,
                                         TableLayout tableLayout) {
        int numOfColumns = 4;
        int length = list.size();
        int numOfRow = (int) Math.ceil((double) length / numOfColumns);
        int eightDp = (int) context.getResources().getDimension(R.dimen.margin_8dp);
        for (int i = 0; i < numOfRow; i++) {
            TableRow tableRow = new TableRow(context);
            LinearLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, eightDp);
            tableRow.setLayoutParams(layoutParams);
            tableLayout.addView(tableRow);

            int numColonsInThisIteration;
            if (i == numOfRow - 1){
                numColonsInThisIteration = length % numOfColumns;
            } else {
                numColonsInThisIteration = numOfColumns;
            }
            for (int j = 0; j < numColonsInThisIteration; j++) {
                int index = (numOfColumns * i) + j;
                String size = list.get(index);
                SizeButton button = new SizeButton(context, null, R.style.FlatButtonStyle);
                button.setText(size);
                button.setId(index);
                tableRow.addView(button);
                button.setOnClickListener(view -> {
                    button.toggle();
                });
                button.setCallback(topSizeObserver);
            }
        }
    }
}
