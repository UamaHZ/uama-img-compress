# 使用方法：

### 1.添加依赖  
##### 首先需要在项目的 build.gradle 文件中配置 https://jitpack.io 的 maven 库：
```allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
##### 其次在需要引用该的 module 的 build.gradle 中添加依赖：
```
compile 'com.uama.utils:image-compress:1.0.4'

```
### 2.压缩用法：
```
UamaCompressUtils.compress(context, path, new UamaCompressUtils.CompressedResult() {
            @Override
            public void onResultPath(String path) {}
            @Override
            public void onResultPaths(List<String> paths) {}
            @Override
            public void onResultError() {}
        });

UamaCompressUtils.compress(context, paths, new UamaCompressUtils.CompressedResult() {
            @Override
            public void onResultPath(String path) {}
            @Override
            public void onResultPaths(List<String> paths) {}
            @Override
            public void onResultError() {}
        });

UamaCompressUtils.compressWithUri(context, uris, new UamaCompressUtils.CompressedResult() {
            @Override
            public void onResultPath(String path) {}
            @Override
            public void onResultPaths(List<String> paths) {}
            @Override
            public void onResultError() {}
        });

UamaCompressUtils.compressWithRx(context, files, new UamaCompressUtils.CompressedResult() {
            @Override
            public void onResultPath(String path) {}
            @Override
            public void onResultPaths(List<String> paths) {}
            @Override
            public void onResultError() {}
        });
```

#####2.1.UamaCompressUtils提供了四个压缩方法

  UamaCompressUtils.compress(Context context,String path,final CompressedResult result)
  UamaCompressUtils.compress(Context context,List<String> paths,final CompressedResult result)
  UamaCompressUtils.compressWithUri(Context context,List<Uri> paths,final CompressedResult result)
  UamaCompressUtils.compressWithRx(final Context context,final List<T> photos,final CompressedResult result)
  compressWithRx方法T可以传File和Uri
```
#####2.2CompressedResult是压缩方法的回调接口

```
  public interface CompressedResult{
         void onResultPath(String path);
         void onResultPaths(List<String> paths);
         void onResultError();
     }
 ```
 #####单张压缩回调onResultPath方法，多张压缩回调onResultPaths方法，压缩失败回调onResultError方法


### 3.删除所有压缩生成的缓存文件
 ```
 UamaCompressUtils.clearCacheFiles(context);
 ```




