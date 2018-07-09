## android开发环境搭建过程 (2018.6.22)

1. `去官网下载JDK8，配置好环境`
```

* 新建环境变量 JAVA_HOME , 设置值为JDK8的安装路径
* 在 path环境路径中添加: %JAVA_HOME%\bin;
```
* ![jdk8环境变量设置1]()
* ![jdk8环境变量设置2]()
*  [下载链接](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

2. `去官网下载android-studio的非安装版`
*  android-studio-ide-173.4819257-windows.zip       // 该版本不匹配 gradle4.8 ,可以使用 gradle4.4
*  [下载链接](http://www.androiddevtools.cn/#)

3. `下载gradle4.4`
*  下载  gradle-4.4-all.zip ， 把它放在 C:\Users\dglong\.gradle\wrapper\dists
*  [下载链接](http://services.gradle.org/distributions/)

4. `下载你需要的SDK,设置SDK和jdk的本地路径设置`
*  ![SDK和jdk的本地路径设置]()

5. `首次去掉 offline,即要上网解决依赖问题`
*  ![首次去掉offline]()
* 点 file -> Sync Project with Gradle Files
* 即可看到依赖解决了，而任务建立成功了

6. `安装安卓虚拟机`
* ![安卓虚拟机]()

7. `下次断网建立新的成功即可成功`

8. `设置ADB`
* adb.exe的位置在 F:\MSHDA\android-sdk\android-sdk-cli\platform-tools ： 即SDK的 platform-tools目录下
* 在windows下设置adb的环境变量 ： 控制面板 -> 系统
* 系统变量中新建 ADB_HOME , values为 adb在 sdk的目录路径
* 编辑 path 环境变量，添加 %ADB_HOME%
* 进入cmd命令行界面,输入adb,判断adb设置环境变量是否 success
* 打开 android studio,设置adb