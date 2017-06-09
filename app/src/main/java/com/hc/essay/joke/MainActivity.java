package com.hc.essay.joke;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.navigationBar.DefaultNavigationBar;
import com.mrl.baselibrary.ExceptionCrashHandler;
import com.mrl.baselibrary.dialog.BaseDialog;
import com.mrl.baselibrary.ioc.OnClick;
import com.mrl.baselibrary.ioc.ViewById;

import java.io.File;

public class MainActivity extends BaseSkinActivity {

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar= new DefaultNavigationBar.
                Builder(this, (ViewGroup) findViewById(R.id.root_view))
                .setTitle("主页")
                .setRightIcon(R.mipmap.ic_launcher)
                .builder();

    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.button)
    public void onClick(View view){
        switch (view.getId()){
            //Dialog使用示例
            case R.id.button:
               BaseDialog dialog=new BaseDialog.Builder(this).setContentView(R.layout.detail_comment_dialog)
                       .setText(R.id.submit_btn,"send").fullWidth().fromBottom(true).show();
                final EditText editText=dialog.getView(R.id.comment_editor);
                dialog.setOnClickListener(R.id.submit_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,editText.getText().toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            break;
        }
    }

    @Override
    protected void initData() {
        // 获取上次的崩溃信息
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        if(crashFile.exists()){
        // 上传到服务器..............
        }
    }
}
