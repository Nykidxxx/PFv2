package com.nykidxxx.pfv2.ui;
//Created March 6th 2017

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;

import com.nykidxxx.pfv2.Adapters.CustomAdapterHistory;
import com.nykidxxx.pfv2.model.DBHandlerNY;
import com.nykidxxx.pfv2.R;
import com.nykidxxx.pfv2.model.TransactionProvider;

import static com.nykidxxx.pfv2.model.DBHandlerNY.DATABASE_VERSION;

public class HistoryPage extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    CustomAdapterHistory cAdapter;
    DBHandlerNY dbHandler;
    ListView listviewHistory;
    Button buttonGoBackFromHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history_page);
        dbHandler = new DBHandlerNY(this, null, null, DATABASE_VERSION);

        listviewHistory = (ListView)findViewById(R.id.listviewHistory);
        cAdapter = new CustomAdapterHistory(this, null, 0);
        listviewHistory.setAdapter(cAdapter);

        getLoaderManager().initLoader(0, null, this);

        buttonGoBackFromHistory = (Button)findViewById(R.id.buttonGoBackFromHistory);
        buttonGoBackFromHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { DBHandlerNY.COLUMN_ID,
                                DBHandlerNY.COLUMN_PAYEE,
                                DBHandlerNY.COLUMN_AMOUNT,
                                DBHandlerNY.COLUMN_CATEGORY,
                                DBHandlerNY.COLUMN_MONTH };
        return new CursorLoader(this, TransactionProvider.CONTENT_URI_T, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cData) {
        cAdapter.swapCursor(cData);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cAdapter.swapCursor(null);
    }
}










