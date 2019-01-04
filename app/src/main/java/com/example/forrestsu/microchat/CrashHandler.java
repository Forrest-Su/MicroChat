package com.example.forrestsu.microchat;

import android.content.Context;
import android.content.Intent;

import com.example.forrestsu.microchat.logger.MyDiskLogAdapter;
import com.example.forrestsu.microchat.utils.AppUtils;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private static CrashHandler crashHandler = new CrashHandler();

//    //存储异常和参数信息
//    private Map<String, String> paramsMap = new HashMap<>();
//
//    //格式化时间
//    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

   //饿汉单例模式，获取CrashHandler实例
    public static synchronized CrashHandler getInstance() {
        return crashHandler;
    }

    public void init(Context context) {
        mContext = context;
        //系统默认UncaughtExceptionHandler
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override //uncaughtException 回调
    public void uncaughtException(Thread thread, Throwable ex) {

        if (!handleException(ex) && mDefaultHandler != null) {//如果用户没处理交给系统处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {//自己处理
            //注意注意注意！！！！！！需要清空所有已经启动的activity，否则无法正常退出
            ActivityManager.getInstance().finishAll();
            Intent intent = new Intent("com.example.forrestsu.microchat.ACTION_START");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("throwable", ex);
            mContext.startActivity(intent);
            System.exit(0);// 关闭已奔溃的app进程

//            try {//延迟3秒杀进程
//                Thread.sleep(3000);
//                Log.i(TAG, "uncaughtException: 自己处理");
//                Logger.d("自己处理");
//                android.os.Process.killProcess(android.os.Process.myPid());
//            } catch (InterruptedException e) {
////                log.error(e.getMessage());
//            }
//            System.exit(0);
        }
    }

    /**
     * 收集错误信息.发送到服务器
     *
     * @return 处理了该异常返回true, 否则false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        //添加自定义信息
        //addCustomInfo();
        //使用Toast来显示异常信息
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(mContext, "程序异常.", Toast.LENGTH_SHORT).show();
//                Looper.loop();
//            }
//        }.start();
//        if (mContext.getExternalCacheDir() != null) {
//            Logger.addLogAdapter(new MyDiskLogAdapter(mContext.getExternalCacheDir().getAbsolutePath()));
//        } else {
//            Logger.addLogAdapter(new DiskLogAdapter());
//        }

        //保存设备信息
        Logger.d(AppUtils.getDeviceInfo(mContext));
        //保存日志文件
        Logger.e(ex, "Exception");
        return true;
    }


    //添加自定义参数
    private void addCustomInfo() {
        //
    }


//    /**
//     * 保存错误信息到文件中
//     *
//     * @param ex
//     * @return 返回文件名称, 便于将文件传送到服务器
//     */
//    private void saveCrashInfo2File(Throwable ex) {
//
////        StringBuffer sb = new StringBuffer();
////        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
////            String key = entry.getKey();
////            String value = entry.getValue();
////            sb.append(key + "=" + value + "\n");
////        }
////
////        Writer writer = new StringWriter();
////        PrintWriter printWriter = new PrintWriter(writer);
////        ex.printStackTrace(printWriter);
////        Throwable cause = ex.getCause();
////        while (cause != null) {
////            cause.printStackTrace(printWriter);
////            cause = cause.getCause();
////        }
////        printWriter.close();
////        String result = writer.toString();
////        sb.append(result);
////        try {
////            long timestamp = System.currentTimeMillis();
////            String time = format.format(new Date());
////            String fileName = "crash-" + time + "-" + timestamp + ".log";
////            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
////                String path = Environment.getExternalStorageDirectory() + "/hzdongcheng/log/";
////                File dir = new File(path);
////                if (!dir.exists()) {
////                    dir.mkdirs();
////                }
////                FileOutputStream fos = new FileOutputStream(path + fileName);
////                fos.write(sb.toString().getBytes());
////                fos.close();
////            }
////            return fileName;
////        } catch (Exception e) {
////            log.error("an error occured while writing file...", e);
////        }
////        return null;
//        Logger.d(ex);
//    }

}
