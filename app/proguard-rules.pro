# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/marco/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes Signature
-keepattributes *Annotation*

-dontwarn sun.misc.Unsafe

-keepclassmembers class com.appnerds.hrishikesh.firechat.conversation.data_model.** {*;}
-keepclassmembers class com.appnerds.hrishikesh.firechat.conversation_list.data_model.** {*;}
-keepclassmembers class com.appnerds.hrishikesh.firechat.global.data_model.** {*;}
-keepclassmembers class com.appnerds.hrishikesh.firechat.user.data_model.** {*;}

-keep public class com.appnerds.hrishikesh.firechat.login.**
