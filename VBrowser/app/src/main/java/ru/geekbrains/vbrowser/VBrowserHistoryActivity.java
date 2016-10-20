package ru.geekbrains.vbrowser;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import ru.geekbrains.vbrowser.adapters.ListRecyclerAdapter;

public class VBrowserHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private String HISTORY_FILE_NAME;
    private Button clearHistoryBtn, backBrowserBtn;
    private Resources res;

    private RecyclerView recyclerViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vbrowser_history);
        initViews();
        initVars();
        setListeners();
        inflateRecyclerView();
    }

    private void initViews(){
        clearHistoryBtn = (Button)findViewById(R.id.clearHistoryBtn);
        backBrowserBtn = (Button)findViewById(R.id.backBrowserBtn);
    }

    private void initVars(){
        res = getResources();
        HISTORY_FILE_NAME = res.getString(R.string.history_file_name);
    }

    private void setListeners(){
        clearHistoryBtn.setOnClickListener(this);
        backBrowserBtn.setOnClickListener(this);
    }

    private void inflateRecyclerView(){
        ArrayList<String> history = openHistoryFile();
        recyclerViewHistory = (RecyclerView)findViewById(R.id.rvh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        ListRecyclerAdapter historyAdapter = new ListRecyclerAdapter(history,this);

        recyclerViewHistory.setLayoutManager(layoutManager);
        recyclerViewHistory.setAdapter(historyAdapter);
    }

    private ArrayList<String> openHistoryFile(){
        ArrayList<String> historyStringArray = new ArrayList<String>();
        try {
            InputStream inputStream = openFileInput(HISTORY_FILE_NAME);

            if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;

                while ((line = reader.readLine()) != null) {
                    historyStringArray.add(line);
                }

                inputStream.close();
            }
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    res.getText(R.string.exception) + t.toString(), Toast.LENGTH_LONG).show();
        }
        return historyStringArray;
    }

    private void clearURLHistory(){
        try {
            OutputStream outputStream = openFileOutput(HISTORY_FILE_NAME, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    res.getText(R.string.exception) + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.clearHistoryBtn:
                clearURLHistory();
                inflateRecyclerView();
                break;
            case R.id.backBrowserBtn:
                Intent intent = new Intent(VBrowserHistoryActivity.this,VBrowserActivity.class);
                startActivity(intent);
                break;
        }
    }
}
