package com.maulik.edm.okdownload;


import androidx.annotation.Nullable;

import com.tonyodev.fetch2core.Downloader;
import com.tonyodev.fetch2okhttp.OkHttpDownloader;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import okhttp3.OkHttpClient;


public class CustomDownloader extends OkHttpDownloader {

    @Nullable
    @Override
    public Integer getFileSlicingCount(@NotNull Downloader.ServerRequest request, long contentLength) {
        return 9;

    }

    public CustomDownloader() {
        this(null);
    }

    public CustomDownloader(@Nullable OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    @NotNull
    @Override
    public Downloader.FileDownloaderType getRequestFileDownloaderType(@NotNull Downloader.ServerRequest request, @NotNull Set<? extends Downloader.FileDownloaderType> supportedFileDownloaderTypes) {
        int a = request.getExtras().getInt("thread", 5);
        if (a < 2) {
            return FileDownloaderType.SEQUENTIAL;
        } else return Downloader.FileDownloaderType.PARALLEL;
    }


}

