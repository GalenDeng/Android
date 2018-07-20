package com.liyu.demo;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liyu.sqlitetoexcel.ExcelToSQLite;
import com.liyu.sqlitetoexcel.SQLiteToExcel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    private String outputFile;

    public MainActivity() throws FileNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
/*
        User user = new User();
        user.setName("Galen");
        user.setPrice(19.89f);
        user.setCover(getResByte(R.drawable.yuyu));
        user.save();
 */
    }
/*
    public void onExport(View v) {

        String databasePath1 = this.getDatabasePath("ste_db.db").getAbsolutePath();

        String databasePath2 = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "citychina.db";

        new SQLiteToExcel
                .Builder(this)
                .setDataBase(databasePath1)
//                .setTables("user")
//                .setPath(Environment.getExternalStorageDirectory().getPath())
//                .setFileName("test.xls")
                .setEncryptKey("1234567")
                .setProtectKey("9876543")
                .start(new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                        tv.append(makeLog("\n" +
                                "Export importTables--->"));
                    }

                    @Override
                    public void onCompleted(String filePath) {
                        tv.append(makeLog("\n" +
                                "Export completed--->" + filePath));
                        outputFile = filePath;
                    }

                    @Override
                    public void onError(Exception e) {
                        tv.append(makeLog("\n" +
                                "Export error--->" + e.toString()));

                    }
                });
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.excel,menu);        // import按钮
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.onimport_menu:
                Toast.makeText(MainActivity.this,"cautious",Toast.LENGTH_SHORT).show();
                onImport_excel();
                break;
            default:
        }
        return  true;
    }

    private void onImport_excel() {

        final String databasePath1 = this.getDatabasePath("internal.db").getAbsolutePath();

        final String databasePath2 = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "external.db";


       // SQLiteDatabase database;

        // 防止导入重复数据
        //database = SQLiteDatabase.openOrCreateDatabase("/data/user/0/com.liyu.demo/databases/user22.xls.db", null);
        //database = SQLiteDatabase.openOrCreateDatabase("/data/user/0/com.liyu.demo/databases/user22.xls.db", null);
        File file = new File("/data/user/0/com.liyu.demo/databases/user22.xls.db");
        if (file.exists()) {
            file.delete();
        }

   /*     File file2 = new File("/data/user/0/com.liyu.demo/databases");
        if (file2.exists()) {
            file2.delete();
        }*/
        //database.execSQL("drop table if exists family2");
        //database.execSQL("drop table  if exists family1");

        // 导入 Excel 表格
        new ExcelToSQLite
                .Builder(this)
        //        .setDataBase("/data/data/com.liyu.demo/databases/galen")
                .setAssetFileName("user22.xls")
        //      .setFilePath("/sdcard/Download/user22.xls")
                .setDecryptKey("1234567")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .start(new ExcelToSQLite.ImportListener() {


                    @Override
                    public void onStart() {
//                        tv.append(makeLog("\n" +
//                                "Import importTables--->"));
//                        Toast.makeText(MainActivity.this,"importTables",Toast.LENGTH_SHORT).show();

                        Log.d("index","gaga");
                    }

                    @Override
                    public void onCompleted(String result) {
 //                      tv.append(makeLog("\n" +
 //                               "Import completed--->" + result));
                       Toast.makeText(MainActivity.this,"completed",Toast.LENGTH_SHORT).show();
 //                      tv.setText("");
                        Log.d("result", result);
                       showDbMsg(result,"盐酸","硝酸");
                    }

                    @Override
                    public void onError(Exception e) {
                        tv.append(makeLog("\n" +
                                "Import error--->" + e.toString()));
                    }
                });
    }


    public   void  onQuery(View v) {
        tv.setText("");
        showDbMsg("/data/user/0/com.liyu.demo/databases/user22.xls.db","盐酸","硝酸");
    }



    private void showDbMsg(String dbName,String column,String rows) {
        SQLiteDatabase database;
        try {
            database = SQLiteDatabase.openOrCreateDatabase(dbName, null);
                Cursor cursor2 = database.rawQuery("select " + column + " from  family2 where name =? "  , new String[]{rows});
                while (cursor2.moveToNext()) {
                    tv.append("\n");
                    for (int i = 0; i < cursor2.getColumnCount(); i++) {
                        tv.append(cursor2.getString(i) + "  ");
                    }
                }
                cursor2.close();
                database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String makeLog(String log) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return log + " " + sdf.format(now);
    }

    private byte[] getResByte(@DrawableRes int id) {
        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
