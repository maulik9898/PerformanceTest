package com.maulik.edm.okdownload;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.Util;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class okdownloadActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 200;
    private static final long UNKNOWN_REMAINING_TIME = -1;
    private static final long UNKNOWN_DOWNLOADED_BYTES_PER_SECOND = 0;
    @BindView(R.id.link1)
    TextView link1;
    @BindView(R.id.size1)
    TextView size1;


    @BindView(R.id.link2)
    TextView link2;
    @BindView(R.id.size2)
    TextView size2;


    String url1 = "https://excellmedia.dl.sourceforge.net/project/xiaomi-eu-multilang-miui-roms/xiaomi.eu/MIUI-STABLE-RELEASES/MIUIv10/xiaomi.eu_multi_HMNote7Pro_V10.3.5.0.PFHCNXM_v10-9.zip";

    String url2 = "https://excellmedia.dl.sourceforge.net/project/xiaomi-eu-multilang-miui-roms/xiaomi.eu/MIUI-STABLE-RELEASES/MIUIv10/xiaomi.eu_multi_HMNote7Pro_V10.3.5.0.PFHCNXM_v10-9.zip";

    TextView per2;
    @BindView(R.id.status1)
    TextView status1;
    @BindView(R.id.status2)
    TextView status2;


    DownloadTask task1, task2, task3;

    TextView a;
    @BindView(R.id.link3)
    TextView link3;
    @BindView(R.id.size3)
    TextView size3;
    @BindView(R.id.status3)
    TextView status3;
    @BindView(R.id.startall)
    Button startall;
    @BindView(R.id.stopall)
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okdownload);
        ButterKnife.bind(this);
        checkStoragePermissions();
        Util.enableConsoleLog();
        task1 = new DownloadTask.Builder(url1, DemoUtil.getParentFile(okdownloadActivity.this)).setFilename("FILE1.zip").setMinIntervalMillisCallbackProcess(500).setPassIfAlreadyCompleted(true).setConnectionCount(9).build();

        task2 = new DownloadTask.Builder(url2, DemoUtil.getParentFile(okdownloadActivity.this)).setFilename("FILE2.zip").setMinIntervalMillisCallbackProcess(500).setPassIfAlreadyCompleted(true).setConnectionCount(9).build();

        task3=new DownloadTask.Builder(url2, DemoUtil.getParentFile(okdownloadActivity.this)).setFilename("FILE3.zip").setMinIntervalMillisCallbackProcess(500).setPassIfAlreadyCompleted(true).setConnectionCount(9).build();


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
            Toast.makeText(okdownloadActivity.this, "Please enable the Write External Storage Permission", Toast.LENGTH_SHORT).show();
        }
    }


    DownloadListener4WithSpeed downloadListener4WithSpeed = new DownloadListener4WithSpeed() {
        @Override
        public void taskStart(@NonNull DownloadTask task) {
            status1.setText("Starting Task1");

        }

        @Override
        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

        }

        @Override
        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

        }

        @Override
        public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
            status1.setText("Ready");
            link1.setText(task.getFilename());
            long size = info.getTotalLength();
            String t = Util.humanReadableBytes(size, false);
            String r = Util.humanReadableBytes(info.getTotalOffset(), false);
            size1.setText(r + "/" + t);


            Log.d("COUNT1", task.getConnectionCount() + "");

        }

        @Override
        public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {

        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
            status1.setText("Ready");
            link1.setText(task.getFilename());
            long size = task.getInfo().getTotalLength();
            String t = Util.humanReadableBytes(size, true);
            String r = Util.humanReadableBytes(task.getInfo().getTotalOffset(), true);
            size1.setText(r + "/" + t);



        }

        @Override
        public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
            status1.setText("Task end");

        }
    };

    DownloadListener4WithSpeed downloadListener4WithSpeed1 = new DownloadListener4WithSpeed() {
        @Override
        public void taskStart(@NonNull DownloadTask task) {
            status2.setText("Statring task2");

        }

        @Override
        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

        }

        @Override
        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

        }

        @Override
        public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
            status2.setText("Ready");
            link2.setText(task.getFilename());
            long size = info.getTotalLength();
            String t = Util.humanReadableBytes(size, true);
            String r = Util.humanReadableBytes(info.getTotalOffset(), true);
            size2.setText(r + "/" + t);


            Log.d("COUNT2", task.getConnectionCount() + "");


        }

        @Override
        public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {

        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {

            status2.setText("Ready");
            link2.setText(task.getFilename());
            long size = task.getInfo().getTotalLength();
            String t = Util.humanReadableBytes(size, true);
            String r = Util.humanReadableBytes(task.getInfo().getTotalOffset(), true);
            size2.setText(r + "/" + t);



            String s = Util.humanReadableBytes(taskSpeed.getBytesPerSecondFromBegin(), true);


        }

        @Override
        public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {

        }
    };

    DownloadListener4WithSpeed downloadListener4WithSpeed3 = new DownloadListener4WithSpeed() {
        @Override
        public void taskStart(@NonNull DownloadTask task) {

        }

        @Override
        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

        }

        @Override
        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

        }

        @Override
        public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {

            status3.setText("Ready");
            link3.setText(task.getFilename());
            long size = info.getTotalLength();
            String t = Util.humanReadableBytes(size, true);
            String r = Util.humanReadableBytes(info.getTotalOffset(), true);



        }

        @Override
        public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {

        }

        @Override
        public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
            status3.setText("Ready");
            link3.setText(task.getFilename());
            long size = task.getInfo().getTotalLength();
            String t = Util.humanReadableBytes(size, true);
            String r = Util.humanReadableBytes(task.getInfo().getTotalOffset(), true);
            size3.setText(r + "/" + t);


        }

        @Override
        public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {

        }
    };


    @OnClick({R.id.startall, R.id.stopall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startall:
                task1.enqueue(downloadListener4WithSpeed);
                task2.enqueue(downloadListener4WithSpeed1);
                task3.enqueue(downloadListener4WithSpeed3);
                break;
            case R.id.stopall:
                task1.cancel();
                task2.cancel();
                task3.cancel();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        task1.cancel();
        task2.cancel();
        task3.cancel();
        task1.getFile().delete();
        task2.getFile().delete();
        task3.getFile().delete();


    }

    @Override
    protected void onStop() {
        super.onStop();
        task1.cancel();
        task2.cancel();
        task3.cancel();
    }
}
