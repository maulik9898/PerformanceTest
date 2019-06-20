package com.maulik.edm.okdownload;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tonyodev.fetch2.AbstractFetchListener;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.Downloader;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class fetch2Activity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 200;
    private static final long UNKNOWN_REMAINING_TIME = -1;
    private static final long UNKNOWN_DOWNLOADED_BYTES_PER_SECOND = 0;
    Fetch fetch;
    Context context;
    Request request1, request2, request3;
    String url1 = "https://excellmedia.dl.sourceforge.net/project/xiaomi-eu-multilang-miui-roms/xiaomi.eu/MIUI-STABLE-RELEASES/MIUIv10/xiaomi.eu_multi_HMNote7Pro_V10.3.5.0.PFHCNXM_v10-9.zip";
    @BindView(R.id.link1)
    TextView link1;
    @BindView(R.id.size1)
    TextView size1;
    @BindView(R.id.link2)
    TextView link2;
    @BindView(R.id.size2)
    TextView size2;
    @BindView(R.id.status1)
    TextView status1;
    @BindView(R.id.status2)
    TextView status2;
    @BindView(R.id.link3)
    TextView link3;
    @BindView(R.id.size3)
    TextView size3;
    @BindView(R.id.status3)
    TextView status3;
    @BindView(R.id.startall)
    Button startall;
    @BindView(R.id.stopall)
    Button stopall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch2);
        ButterKnife.bind(this);
        checkStoragePermissions();
        context = fetch2Activity.this;
        final OkHttpClient client = new OkHttpClient.Builder().build();
        final Downloader customDownloader = new CustomDownloader(client);
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context).enableRetryOnNetworkGain(true).setDownloadConcurrentLimit(3).setProgressReportingInterval(500).setHttpDownloader(customDownloader).build();
        fetch = Fetch.Impl.getInstance(fetchConfiguration);
        request1 = new Request(url1, getSaveDir() + "/" + "FILE1.zip");
        request1.setAutoRetryMaxAttempts(10);
        request1.setDownloadOnEnqueue(false);

        request2 = new Request(url1, getSaveDir() + "/" + "FILE2.zip");
        request2.setAutoRetryMaxAttempts(10);
        request2.setDownloadOnEnqueue(false);


        request3 = new Request(url1, getSaveDir() + "/" + "FILE3.zip");
        request3.setAutoRetryMaxAttempts(10);
        request3.setDownloadOnEnqueue(false);


        fetch.enqueue(request1,result -> {},error ->{});
        fetch.enqueue(request2,result -> {},error ->{});
        fetch.enqueue(request3,result -> {},error ->{});
        fetch.addListener(fetchListener);
        fetch.pause(request1.getId());
        fetch.pause(request2.getId());
        fetch.pause(request3.getId());


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
            Toast.makeText(fetch2Activity.this, "Please enable the Write External Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    public String getSaveDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/EDM";
    }

    FetchListener fetchListener =new AbstractFetchListener() {
        @Override
        public void onProgress(@NotNull Download download, long etaInMilliSeconds, long downloadedBytesPerSecond) {
            if(request1.getId()==download.getId()){

                link1.setText("FILE1.zip");
                status1.setText(download.getStatus().toString());
                size1.setText(String.format("%s/%s", format(download.getDownloaded(), 2), format(download.getTotal(), 2)));

            }
            if(request2.getId()==download.getId()){

                link2.setText("FILE2.zip");
                status2.setText(download.getStatus().toString());
                size2.setText(String.format("%s/%s", format(download.getDownloaded(), 2), format(download.getTotal(), 2)));

            }
            if(request3.getId()==download.getId()){

                link3.setText("FILE3.zip");
                status3.setText(download.getStatus().toString());
                size3.setText(String.format("%s/%s", format(download.getDownloaded(), 2), format(download.getTotal(), 2)));

            }
        }
    };
    public static String format(double bytes, int digits) {
        String[] dictionary = { "bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" };
        int index = 0;
        for (index = 0; index < dictionary.length; index++) {
            if (bytes < 1024) {
                break;
            }
            bytes = bytes / 1024;
        }
        String s=String.format("%." + digits + "f", bytes) + " " + dictionary[index];
        Log.d("SIZE",s);
        return String.format("%." + digits + "f", bytes) + " " + dictionary[index];
    }
    @OnClick({R.id.startall, R.id.stopall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startall:
                fetch.resume(request1.getId());
                fetch.resume(request2.getId());
                fetch.resume(request3.getId());
                break;
            case R.id.stopall:
                fetch.pause(request1.getId());
                fetch.pause(request2.getId());
                fetch.pause(request3.getId());
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        fetch.delete(request1.getId());
        fetch.delete(request3.getId());
        fetch.delete(request2.getId());
        fetch.removeListener(fetchListener);
        fetch.close();
    }
}
