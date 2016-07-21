-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keepattributes *Annotation*
-keepattributes Signature

#-dontusemixedcaseclassnames      # 是否使用大小写混合
-skipnonpubliclibraryclasses      # 不混淆第三方jar
#-dontskipnonpubliclibraryclasses      # 是否混淆第三方jar
#-dontpreverify     # 混淆时是否做预校验
#-verbose      # 混淆时是否记录日志
-keep class * extends java.lang.annotation.Annotation  # 不混淆注解

#-printmapping mapping.txt
-ignorewarnings

-dontwarn base.**
-keep class base.** { *; }
-dontwarn entities.**
-keep class entities.** { *; }

-dontwarn ch.**
-keep class ch.**{*;}
-dontwarn org.**
-keep class org.**{*;}
-dontwarn **.R$*
-keep class **.R$* {*;}
-dontwarn **.R
-keep class **.R{*;}
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends java.io.Serializable
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.baidu.** { *; }
-keep class com.alipay.android.app.IALiPay{*;}
-keep class com.alipay.android.app.IALixPay{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-dontwarn com.alipay.android.app.**
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class com.bank.pingan.model.** { *; }
-libraryjars libs/

-keep class com.tencent.wxop.** { *; }
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
-keep class m.framework.**{*;}
-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class com.mob.tools.utils
-keep class com.xxx.share.onekey.theme.classic.EditPage