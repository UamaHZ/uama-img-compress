package com.uama.sample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import uama.com.image.compress.UamaCompressUtils;

/**
 * Author:ruchao.jiang
 * Created: 2019/5/23 09:55
 * Email:ruchao.jiang@uama.com.cn
 */
public class MainActivity extends AppCompatActivity {
    ImageView img1,img2,img3;
    TextView show;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_layout);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        show = findViewById(R.id.show);
        context = this;


        List<Uri> uris = assetsToUri();
        for (int i =0;i<uris.size();i++){
            switch (i){
                case 0:
                    img1.setImageURI(uris.get(0));
                    break;
                case 1:
                    img2.setImageURI(uris.get(1));
                    break;
                case 2:
                    img3.setImageURI(uris.get(2));
                    break;
            }

        }


        UamaCompressUtils.compressWithUri(context, uris, new UamaCompressUtils.CompressedResult() {
            @Override
            public void onResultPath(String path) {}
            @Override
            public void onResultPaths(List<String> paths) {}
            @Override
            public void onResultError() {}
        });


    }


    StringBuilder sb = new StringBuilder();


    private List<Uri> assetsToUri() {
        final List<Uri> uris = new ArrayList<>();
        final List<File> files = assetsToFiles();

        for (int i = 0; i < 3; i++) {
            Uri uri = Uri.fromFile(files.get(i));
            uris.add(uri);
        }

        return uris;
    }


    private List<File> assetsToFiles() {
        final List<File> files = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            try {
                InputStream is = getResources().getAssets().open("img_" + i);
                File file = new File(getExternalFilesDir(null), "test_" + i);
                FileOutputStream fos = new FileOutputStream(file);

                byte[] buffer = new byte[4096];
                int len = is.read(buffer);
                while (len > 0) {
                    fos.write(buffer, 0, len);
                    len = is.read(buffer);
                }
                fos.close();
                is.close();

                sb.append("img_"+i+" old is"+file.length()/1024 +"k"+"\n");

                files.add(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return files;
    }
}
