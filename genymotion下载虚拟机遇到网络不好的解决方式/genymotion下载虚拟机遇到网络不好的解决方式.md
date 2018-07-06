## genymotion下载虚拟机遇到网络不好的解决方式 (2018.07.06)
* [参考解决链接](https://blog.csdn.net/xulei881/article/details/38821685)

1. `主要解决方式`
* 在C:\Users\xxx\AppData\Local\Genymobile下找到：genymotion.log文件
* 记事本打开 genymotion.log文件
* 找到要下载的虚拟机的下载路径，如：
```
* https://dl.genymotion.com/dists/7.0.0/ova/genymotion_vbox86p_7.0_170321_002642.ova
```
* 复制粘贴在浏览器里面,以浏览器的形式下载
* 将下载好的ova文件剪贴到C:\Users\xxx\AppData\Local\Genymobile\Genymotion\ova里面；重启
* 打开后一般要重新到下载页面进行下载（系统会自动搞一些文件的处理），OK了