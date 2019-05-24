package uama.com.image.compress;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

/**
 * Author:ruchao.jiang
 * Created: 2019/5/23 13:51
 * Email:ruchao.jiang@uama.com.cn
 */
public class UamaCompressUtils {

    public static void compress(Context context,String path,final CompressedResult result){
        List<Uri> photos = new ArrayList<>();
        Uri uri = Uri.fromFile(new File(path));
        photos.add(uri);
        compressWithRx(context,photos,result);
    }


    public static void compress(Context context,List<String> paths,final CompressedResult result){
        List<Uri> photos = new ArrayList<>();
        for (String path : paths){
            Uri uri = Uri.fromFile(new File(path));
            photos.add(uri);
        }
        compressWithRx(context,photos,result);
    }


    public static void compressWithUri(Context context,List<Uri> paths,final CompressedResult result){
        compressWithRx(context,paths,result);
    }


    public static  <T> void compressWithRx(final Context context,final List<T> photos,final CompressedResult result) {
        final CompositeDisposable mDisposable = new CompositeDisposable();
        final String dir = context.getCacheDir().getPath()+File.separator+"compress_cache";
        File tmpFile =  new File(dir);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        mDisposable.add(Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(new Function<List<T>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<T> list) throws Exception {
                        return Luban.with(context)
                                .setTargetDir(dir)
                                .load(list)
                                .get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                       // Log.e(TAG, throwable.getMessage());
                        result.onResultError();
                    }
                })
                .onErrorResumeNext(Flowable.<List<File>>empty())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) {
                        if (list.size() == 1){
                            String path = list.get(0).getAbsolutePath();
                            result.onResultPath(path);
                        }else{
                            List<String> paths = new ArrayList<>();
                            for (File file : list) {
                                String path = file.getAbsolutePath();
                                if (file != null && !TextUtils.isEmpty(path)){
                                    paths.add(path);
                                }
                            }
                            result.onResultPaths(paths);
                        }


                    }
                }));
    }


    public interface CompressedResult{
        void onResultPath(String path);
        void onResultPaths(List<String> paths);
        void onResultError();
    }
}
