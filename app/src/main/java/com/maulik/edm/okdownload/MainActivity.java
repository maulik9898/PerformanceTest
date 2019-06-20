package com.maulik.edm.okdownload;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 200;
    private static final long UNKNOWN_REMAINING_TIME = -1;
    private static final long UNKNOWN_DOWNLOADED_BYTES_PER_SECOND = 0;
    @BindView(R.id.okd)
    Button okd;
    @BindView(R.id.fetch2)
    Button fetch2;

    String url1 = "https://excellmedia.dl.sourceforge.net/project/xiaomi-eu-multilang-miui-roms/xiaomi.eu/MIUI-STABLE-RELEASES/MIUIv10/xiaomi.eu_multi_HMNote7Pro_V10.3.5.0.PFHCNXM_v10-9.zip";

    String url2 = "https://excellmedia.dl.sourceforge.net/project/xiaomi-eu-multilang-miui-roms/xiaomi.eu/MIUI-STABLE-RELEASES/MIUIv10/xiaomi.eu_multi_HMNote7Pro_V10.3.5.0.PFHCNXM_v10-9.zip";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkStoragePermissions();




    }

    private void checkStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        } else {
            Toast.makeText(MainActivity.this, "Please enable the Write External Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.okd, R.id.fetch2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.okd:
                Intent okintent= new Intent(this, okdownloadActivity.class);
                startActivity(okintent);
                break;
            case R.id.fetch2:
                Intent fetch2= new Intent(this, fetch2Activity.class);
                startActivity(fetch2);
                break;
        }
    }
}
