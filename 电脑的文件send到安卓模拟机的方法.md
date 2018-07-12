## 电脑的文件send到安卓模拟机的方法 (2018.07.12)

1. `send method`
```
* windows下cmd打开命令行界面
* adb push  xxx  YYY  // 把 电脑上的文件 send 到 安卓模拟机上
C:\Users\dglong>adb push  C:/Users/dglong/Desktop/echo.txt /data/data
C:/Users/dglong/Desktop/echo.txt: 1 fi... pushed. 0.0 MB/s (18 bytes in 0.074s)

C:\Users\dglong>

* adb shell 进入到安卓模拟机中
C:\Users\dglong>adb shell
vbox86p:/ #

* vbox86p:/ # cd /data/data/
* vbox86p:/data/data # ls -lt echo.txt      
-rw-rw-rw- 1 root root 18 2018-06-29 04:31 echo.txt     // 可见已成功send echo.txt文件到安卓模拟机中
```

