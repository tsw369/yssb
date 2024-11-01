package com.ijiami.yssb;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.text.format.Formatter;
import com.orhanobut.logger.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AppInfoParser {
    private static String tag = "AppInfoParser";

    public static List<AppInfo> getAppInfos(Context context)
    {
        //首先获取到包的管理者
        PackageManager packageManager = context.getPackageManager();
        //获取到所有的安装包
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        int index = 0;
        for (PackageInfo installedPackage : installedPackages)
        {
            AppInfo appInfo = new AppInfo();
            int flags = installedPackage.applicationInfo.flags;
            //判断当前是否是系统app
            if((flags & ApplicationInfo.FLAG_SYSTEM) !=0){
                //那么就是系统app
                continue;
                //appInfo.setUserApp(false);
            }else{
                //那么就是用户app
                appInfo.setUserApp(true);
            }
            if((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE)!=0){
                //那么当前安装的就是sd卡
                appInfo.setSD(true);
            }else{
                //那么就是手机内存
                appInfo.setSD(false);
            }

            //版本代码
            int versionCode = installedPackage.versionCode;
            appInfo.setVersionCode(versionCode);
            //版本名字
            String versionName = installedPackage.versionName;
            appInfo.setVersionName(versionName);
            //signature
            //String signature = getApplicationPackage(packageManager, installedPackage.packageName);
            //appInfo.setSignature(signature);

            String sourceDir = installedPackage.applicationInfo.sourceDir;
            String signature = getFileMD5(sourceDir);
            appInfo.setSignature(signature);

            /*
            Signature[] signatures = installedPackage.signatures;
            if (signatures != null && signatures.length > 0)
            {
                String md5 = signatures[0].toCharsString();
                appInfo.setMd5(md5);
            }*/
            //程序包名
            String packageName = installedPackage.packageName;
            appInfo.setPackageName(packageName);
            //获取到图标
            //Drawable icon = installedPackage.applicationInfo.loadIcon(packageManager);
            //appInfo.setIcon(icon);
            //获取到应用的名字
            String appName = installedPackage.applicationInfo.loadLabel(packageManager).toString();
            appInfo.setAppName(appName);
            //获取到安装包的路径
            File file = new File(sourceDir);
            //获取到安装apk的大小
            long apkSize = file.length();
            //格式化apk的大小
            appInfo.setApkSize(Formatter.formatFileSize(context,apkSize));
            appInfos.add(appInfo);
            index++;
        }

        Logger.d(appInfos.toString());
        return appInfos;
    }

    public static String getFileMD5(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16).toUpperCase();
    }

    /*
    private static String getApplicationMD5(Context context, String sourceDir)
    {
        try
        {
            File file = new File(sourceDir);
            ApkParser apkParser = new ApkParser(file);
            ApkSignStatus signStatus = apkParser.verifyApk(); // not needed
            List<CertificateMeta> certs = apkParser.getCertificateMetaList();
            for (CertificateMeta certificateMeta : certs) {
                //System.out.println(certificateMeta.getCertMd5());
                Toast.makeText(context, certificateMeta.getCertMd5(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){}
        return "";
    }*/

    private static String getApplicationPackage(PackageManager packageManager, String packageName)
    {
        try {
            // 通过包管理器获取指定包名包含签名的信息
            Signature signature = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures[0];
            MessageDigest messageDigestMd5 = MessageDigest.getInstance("MD5");
            //MessageDigest messageDigestSHA1 = MessageDigest.getInstance("SHA1");

            // 通过 MessageDigest这个类来分别取出 Signature中存储的MD5和SHA1
            messageDigestMd5.update(signature.toByteArray());
            //messageDigestSHA1.update(signature.toByteArray());

            // 把MessageDigest中存储的md5和sha1转为字符串
            String md5 = toHextring(messageDigestMd5.digest());
            //String sha1 = toHextring(messageDigestSHA1.digest());
            //return "md5 = "+md5+ "\n sha1 = " + sha1;
            return md5;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //return "未找到这个签名" ;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //return "未找到对应的算法" ;
        }
        return "";
    }

    private static String toHextring(byte[] block)
    {
        StringBuffer buf = new StringBuffer();
        for (byte aBlock : block) {
            byte2Hex(aBlock, buf);
        }

        return buf.toString();
    }

    private static void byte2Hex(byte b, StringBuffer buf)
    {
        char[] hexChars = {'0','1' ,'2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);

    }
}
