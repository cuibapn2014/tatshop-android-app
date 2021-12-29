package com.example.tatshop.module;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Helper {
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setGridViewHeight(GridView gridView) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int heightElement = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            heightElement = listItem.getMeasuredHeight();
        }


        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        if (listAdapter.getCount() % 2 == 0)
            params.height = totalHeight / 2 + (gridView.getVerticalSpacing() * (listAdapter.getCount() / 2)) + 30;
        else if (listAdapter.getCount() > 1 && listAdapter.getCount() % 2 != 0)
            params.height = totalHeight / gridView.getNumColumns() + heightElement + (gridView.getVerticalSpacing() * ((listAdapter.getCount() + 1) / 2)) + 30;
        else
            params.height = totalHeight + gridView.getVerticalSpacing() + 30;
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
