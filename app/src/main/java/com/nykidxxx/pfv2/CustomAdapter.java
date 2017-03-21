package com.nykidxxx.pfv2;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

public class CustomAdapter extends CursorAdapter {

    DBHandlerNY db;

    boolean isClicked = true;
    PopupWindow popUpWindow;

    public CustomAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        TextView viewID = (TextView) view.findViewById(R.id.viewID);
        TextView viewAmount=(TextView) view.findViewById(R.id.viewAmount);
        TextView viewCategory=(TextView) view.findViewById(R.id.viewCategory);
        TextView viewMonth=(TextView) view.findViewById(R.id.viewMonth);
        Button buttonDelete = (Button) view.findViewById(R.id.buttonDelete);

        db = new DBHandlerNY(context, null, null, 1);

        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String amount = cursor.getString(cursor.getColumnIndexOrThrow("amount"));
        final String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
        String month = cursor.getString(cursor.getColumnIndexOrThrow("month"));

        viewID.setText(String.valueOf(id));
        viewAmount.setText(amount);
        viewCategory.setText(category);
        viewMonth.setText(month);

        popUpWindow = new PopupWindow(context);
        LinearLayout containerLayout = new LinearLayout(context);

        containerLayout.addView(myView(context));

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                /*ContentResolver cr = context.getContentResolver();
                int rowsDeleted = cr.delete(
                        TransactionProvider.CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build(),
                        null, null);

                Toast.makeText(context, "(Followup) Deleted " + rowsDeleted + " row in the database", Toast.LENGTH_SHORT).show();
                //======
                v.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });*/
                if (isClicked) {
                    isClicked = false;
                    popUpWindow.showAtLocation(v, Gravity.BOTTOM, 10, 10);
                    popUpWindow.update(400, 400, -1, -1);
                } else {
                    isClicked = true;
                    popUpWindow.dismiss();
                }
            }
        });

        popUpWindow.setContentView(containerLayout);

    }

    public View myView(Context context){
        View v; // Creating an instance for View Object
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.popup_edit_item, null);

        return v;
    }


}
