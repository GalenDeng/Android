## android的SQLite数据库存储 (2018.07.06)
* SQL : Structured Query Language : 结构化查询语言
* CURD : CREATE(添加) RETRIEVE(查询) UPDATE(更新) DELETE(删除)
```
create   -> insert
retrieve -> select
update   -> update
delete   -> delete
```
1. `SQList`
```
SQList 是一个关系型数据库，占用内存少，通常只需几百KB,不用设置用户名和密码，
特别适合移动设备上使用
```
2. `数据库存储位置`
*  /data/data/<package name>/databases/目录下
```
* vbox86p:/data/data/com.example.dglong.databasetest/databases # 
```
3. `android studio自带的ADT 和 genymotion的区别`
```
* ADT启动的模拟机运行速度慢，占用内存，而genymotion恰恰相反
* ADT启动的模拟机默认是users用户，而genymotion默认是root用户
* 所以 android studio + genymotion是黄金搭配
```
4. `常规SQL命令`
```
1) 查看当前设备
E:\android-activity\DatabaseTest>adb devices            
List of devices attached
192.168.28.101:5555     device

2) 启动adb shell
* 在 android studio 的 Terminal 终端中输入： adb shell     
E:\android-activity\DatabaseTest>adb shell
vbox86p:/ #                                            // # : super用户  $ : 普通用户

3) 进入到数据库文件目录
vbox86p:/ # cd /data/data/com.example.dglong.databasetest/databases   

4)
vbox86p:/data/data/com.example.dglong.databasetest/databases # ls
BookStore.db BookStore.db-journal           // BookStore.db-journal 为临时日志文件

5) 进入到 BookStore.db数据库
vbox86p:/data/data/com.example.dglong.databasetest/databases # sqlite3 BookStore.db 

6) .table : 查看数据库中要哪些表格
sqlite> .table
Book              Category          android_metadata
//  android_metadata : 每个数据库中都会自动生成的文件

7) .schema :  查看建表语句
sqlite> .schema
CREATE TABLE android_metadata (locale TEXT);
CREATE TABLE Book (id integer primary key autoincrement, author text, price real, pages integer, name text);
CREATE TABLE Category (id integer primary key autoincrement, category_name text, category_code integer);
sqlite>

```
