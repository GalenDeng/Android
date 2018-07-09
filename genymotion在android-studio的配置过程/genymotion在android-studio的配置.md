## genymotion在android-studio的配置 (2018.7.6)
1. `去genymotion的官网下载genymotion`
* 因为 genymotion要和virtualBox配置使用，所以我们下载的是 with(VirtualBox)版本
* [genymotion下载链接](https://www.genymotion.com/)
* 安装 -> 注意要设置VirtualBox以兼容模式运行
* 使用代理ip上网的，genymotion在settings中要设置好对应的 proxy ip:port ,这样才能download virtual machine

2. `在android-studio中下载genymotion相关的genymotion-plugin`
* 打开 androuid-studio, File -> Settings -> plugins 
* click Browse repositories ->  search genymotion -> install -> reply -> OK -> restart android studio
* 注意：有使用代理ip上网的要设置 https proxy才能 install success.
* click File -> Settings -> Other Settings -> Genymotion -> 设置你安装的genymotion的安装目录路径
* 有时候，android studio安装了genymotion-pluigins后在菜单栏也见不到genymotion的红色图标，我们可以 click View -> Toolbar(select) ,我们就可以在菜单栏上看到 genymotion的红色图标

3. `启动genymotion的模拟器`
* 点击红色图标，启动模拟器
* run -> 选中genymotion的模拟器 -> 启动成功

4. `本地安装plugins`
```
* 去相对应得网站下载 plugins (一般是 jar格式)
* 把它放在 android studio的安装目录的plugins的文件夹下
* 打开 android studio, click Files -> settings -> plugins -> genymotion -> install -> restart 即可
```