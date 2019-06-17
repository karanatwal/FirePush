# FirePush - A Lightweight Kotlin Library for sending FCM push notification
[![](https://jitpack.io/v/karanatwal/FirePush.svg)](https://jitpack.io/#karanatwal/FirePush)   ![](https://img.shields.io/badge/dependencies-up%20to%20date-brightgreen.svg)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-FirePush-green.svg?style=flat )]( https://android-arsenal.com/details/1/7713 )

Hi, I made this Library for a Chat based project I was working on. So I decided to make it publicly available. It is light weight Library. I have used [Java's HttpURLConnection](https://developer.android.com/reference/kotlin/java/net/HttpURLConnection) for network requests along with [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html).


# Dependencies

**Step 1.** Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
	  ...
	  maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency
```
dependencies {
	  implementation 'com.github.karanatwal:FirePush:1.0.0'
}
```

## Usage

![Server Key](https://i.imgur.com/sOtEHh5.png)

**Step 1.** Get Server Key and add below in your Application's/Activity's `onCreate` :

```
 override fun onCreate(savedInstanceState: Bundle?) {  
    super.onCreate(savedInstanceState)
    Fire.init("YOUR_SERVER_KEY_HERE")
 }
```
**Step 2.** Use Below to send Push Notification.
```
Fire.create()  
    .setTitle("TITLE HERE")  
    .setBody("BODY HERE")  
    .setCallback { pushCallback, exception ->  
         //get response here
     }  
    .toIds("TOKEN ID 1","TOKEN ID 2",...)  //toTopic("FOR TOPIC") or toCondition("CONDITION HERE")
    .push()
```
There are many other functions :
```
    Fire.create()  
        .setTitle("")  
        .setBody("")  
        .setBadgeCount(2)  
        .setClickAction("")  
        .setAndroidChannelId("")  
        .setColor("")  
        .setIcon("")  
        .setSound("")  
        .setTag("")  
        .setPriority(FirePushPriority.HIGH)  
        .addData("key","value")  
        .addData(HashMap())  
        .toTopic("")// or toIds or toCondition  
        .push()
```

There is detailed Documentation regarding FCM keys are given [here.](https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages)

Please mark Star, if you find this library useful, Thanks!!

[Karandeep Atwal](https://karanatwal.github.io)
