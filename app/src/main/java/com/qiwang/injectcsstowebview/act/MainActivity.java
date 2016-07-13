package com.qiwang.injectcsstowebview.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qiwang.injectcsstowebview.R;
/**
 * Created by qiwang on 16/7/13.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private android.widget.Button mButton1;
    private android.widget.Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClickMethod();

    }

    private void initClickMethod() {
        this.mButton1.setOnClickListener(this);
        this.mButton2.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        this.mButton2 = (Button) findViewById(R.id.button2);
        this.mButton1 = (Button) findViewById(R.id.button1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                startActivity(new Intent(this,WebViewActivity1.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this,WebViewActivity2.class));
                break;
        }

    }
}
