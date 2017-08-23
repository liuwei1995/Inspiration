# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\anzhuang\androidstudio\androidStudio_2.2_SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

 #指定代码的压缩级别
-optimizationpasses 5

#包明不混合大小写
-dontusemixedcaseclassnames

#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

 #优化  不优化输入的类文件
-dontoptimize

 #不做预校验
-dontpreverify

 #混淆时是否记录日志
-verbose

 # 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#保护注解
-keepattributes *Annotation*

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService


#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

#忽略警告
-ignorewarning

##记录生成的日志数据,gradle build时在本项目根目录输出##

#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt


#####混淆保护自己项目的部分代码以及引用的第三方jar包library#######

#-libraryjars libs/umeng-analytics-v5.2.4.jar

#三星应用市场需要添加:sdk-v1.0.0.jar,look-v1.0.1.jar
#-libraryjars libs/sdk-v1.0.0.jar
#-libraryjars libs/look-v1.0.1.jar



#如果不想混淆 keep 掉   --------------------------------------------------------------------
#-keep class com.lippi.recorder.iirfilterdesigner.** {*; }
#项目特殊处理代码

#忽略警告
#-dontwarn com.lippi.recorder.utils**
#保留一个完整的包
#-keep class com.lippi.recorder.utils.** {
#*;
#}

#-keep class  com.lippi.recorder.utils.AudioRecorder{*;}

#如果引用了v4或者v7包
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**






-keep public class * extends android.view.View {
public <init>(android.content.Context);
public <init>(android.content.Context, android.util.AttributeSet);
public <init>(android.content.Context, android.util.AttributeSet, int);
public void set*(...);
}

#保持 native 方法不被混淆
#           -keepclasseswithmembernames class * {
#               native <methods>;
#           }

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}


#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
private static final java.io.ObjectStreamField[] serialPersistentFields;
!static !transient <fields>;
!private <fields>;
!private <methods>;
private void writeObject(java.io.ObjectOutputStream);
private void readObject(java.io.ObjectInputStream);
java.lang.Object writeReplace();
java.lang.Object readResolve();
}

#保持枚举 enum 类不被混淆 如果混淆报错，建议直接使用上面的 -keepclassmembers class * implements java.io.Serializable即可
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}

-keepclassmembers class * {
public void *onClick(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
public static <fields>;
}


 #不混淆H5交互
-keepattributes *JavascriptInterface*


  #ClassName是类名，H5_Object是与javascript相交互的object，建议以内部类形式书写
 -keepclassmembers   class **.ClassName$H5_Object{
     *;
 }




# start---------------------zhaoyaoHealthy\app\src\main\java\com\alibaba\android\vlayout---------start------------------------


-keepattributes InnerClasses
-keep class android.support.v7.widget.RecyclerView$LayoutParams {
    *;
}

-keep class android.support.v7.widget.RecyclerView$ViewHolder {
    *;
}

-keep class android.support.v7.widget.ChildHelper {
    *;
}

-keep class android.support.v7.widget.RecyclerView$LayoutManager {
    *;
}

-dontwarn **

# end---------------------zhaoyaoHealthy\app\src\main\java\com\alibaba\android\vlayout------end---------------------------



#start-----------------------------------qiniu----------------------------------------------start
-keep class com.qiniu.**{*;}
-keep class com.qiniu.**{public <init>();}
-ignorewarnings
#end---------------------------------------qiniu----------------------------------------------end

#start-----------------------------------tencent----------------------------------------------start

-keep class com.tencent.open.TDialog$*
-keep class com.tencent.open.TDialog$* {*;}
-keep class com.tencent.open.PKDialog
-keep class com.tencent.open.PKDialog {*;}
-keep class com.tencent.open.PKDialog$*
-keep class com.tencent.open.PKDialog$* {*;}

#end---------------------------------------tencent----------------------------------------------end

-keep class com.tencent.mm.sdk. {*;}
-keep class com.tencent.mm.sdk.** implements com.tencent.mm.sdk.* {*;}

-keep class com.tencent.mm.opensdk. {*;}
-keep class com.tencent.mm.opensdk.** implements com.tencent.mm.opensdk.* {*;}

-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}

-dontwarn com.tencent.mm.**
-keep class com.tencent.mm.**{*;}

-keep class com.tencent.tauth.**{*;}

#end---------------------------------------tencent----------------------------------------------end




#start-----------------------------------jpush----------------------------------------------start

-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}
#end---------------------------------------jpush----------------------------------------------end


#start---------------------------------------高德地图----------------------------------------------start
#3D 地图 V5.0.0之前：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
-keep   class com.amap.api.trace.**{*;}

#3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}

#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}

#搜索
-keep   class com.amap.api.services.**{*;}

#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}

#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
#end---------------------------------------高德地图----------------------------------------------end





#start---------------------------------------阿里热修复----------------------------------------------start
-keep class * extends java.lang.annotation.Annotation
-keepclasseswithmembernames class * {
    native <methods>;
}

#end-----------------------------------------阿里热修复----------------------------------------------end


#start--------------------------------------- compile 'com.yanzhenjie:album:1.0.7'----------------------------------------------start
-dontwarn com.yanzhenjie.album.**
-keep class com.yanzhenjie.album.**{*;}
#end----------------------------------------- compile 'com.yanzhenjie:album:1.0.7'----------------------------------------------end




#start--------------------------------------- Glide----------------------------------------------start
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#end----------------------------------------- Glide----------------------------------------------end
