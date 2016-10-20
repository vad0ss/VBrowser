package ru.geekbrains.vbrowser;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Prilepishev Vadim on 17.10.2016.
 */

public class VBrowserActivity extends AppCompatActivity implements View.OnClickListener {

    private Resources res;

    private String HTTP;
    private String HISTORY_FILE_NAME;
    private String FAVORITES_FILE_NAME;
    private String REDIRECT_SEARCH_URL;

    private StringBuffer address = new StringBuffer();
    private Button enterBtn,refreshBtn,backBtn,nextBtn,historyBtn,addFavBtn,favBtn;
    private WebView webView;
    private EditText addressBar;
    public static ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vbrowser);
        initVars();
        viewInit();
        setListeners();
        webView.setWebViewClient(new VWebViewClient()); //убираем открытие линка браузером по умолчанию
        webView.getSettings().setJavaScriptEnabled(true); // включаем java script
        progress.setVisibility(View.GONE);
    }

    private void setListeners(){
        enterBtn.setOnClickListener(this);
        refreshBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
        addFavBtn.setOnClickListener(this);
        favBtn.setOnClickListener(this);
    }

    private void initVars(){
        res = getResources();
        HTTP = res.getString(R.string.http_protocol);
        HISTORY_FILE_NAME = res.getString(R.string.history_file_name);
        FAVORITES_FILE_NAME = res.getString(R.string.favorites_file_name);
        REDIRECT_SEARCH_URL = res.getString(R.string.redirect_search_url);
    }

    private void viewInit(){
        enterBtn = (Button)findViewById(R.id.enterBtn);
        refreshBtn = (Button)findViewById(R.id.refreshBtn);
        backBtn = (Button)findViewById(R.id.backBtn);
        nextBtn = (Button)findViewById(R.id.nextBtn);
        historyBtn = (Button)findViewById(R.id.historyBtn);
        addFavBtn = (Button)findViewById(R.id.addFavBtn);
        favBtn = (Button)findViewById(R.id.favBtn);
        webView = (WebView)findViewById(R.id.webView);
        addressBar = (EditText)findViewById(R.id.addressBar);
        progress = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void loadAddress(){
        address.append(HTTP);
        address.append(addressBar.getText().toString());
        webView.loadUrl(address.toString());
        address.delete(0,address.length());
    }

    private void saveURLToFile(String fileName){
        try {
            OutputStream outputStream = openFileOutput(fileName, Context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(VWebViewClient.title);
            outputStreamWriter.write(" ");
            outputStreamWriter.write(VWebViewClient.address);
            outputStreamWriter.write("\n");
            outputStreamWriter.close();
        } catch (Throwable t) {
            Toast.makeText(getApplicationContext(),
                    res.getText(R.string.exception) + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    //переопределяем нажатие кнопки назад на устройстве
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
         switch(view.getId()){
             case R.id.enterBtn:
                 if(Patterns.WEB_URL.matcher(addressBar.getText().toString()).matches()) {
                     loadAddress();
                     saveURLToFile(HISTORY_FILE_NAME);
                 }
                 else
                 {
                     Toast.makeText(this, res.getText(R.string.search_query), Toast.LENGTH_SHORT).show();
                     addressBar.setText(REDIRECT_SEARCH_URL + addressBar.getText().toString());
                     loadAddress();
                 }
                 break;
             case R.id.refreshBtn:
                 webView.reload();
                 break;
             case R.id.backBtn:
                 webView.goBack();
                 break;
             case R.id.nextBtn:
                 webView.goForward();
                 break;
             case R.id.addFavBtn:
                 saveURLToFile(FAVORITES_FILE_NAME);
                 Toast.makeText(this,res.getText(R.string.favourite_add) + webView.getTitle(),Toast.LENGTH_SHORT).show();
                 break;
             case R.id.historyBtn:
                 Intent historyIntent = new Intent(VBrowserActivity.this,VBrowserHistoryActivity.class);
                 startActivity(historyIntent);
                 break;
             case R.id.favBtn:
                 Intent favoritesIntent = new Intent(VBrowserActivity.this,VBrowserFavoritesActivity.class);
                 startActivity(favoritesIntent);
                 break;
         }
    }
}
