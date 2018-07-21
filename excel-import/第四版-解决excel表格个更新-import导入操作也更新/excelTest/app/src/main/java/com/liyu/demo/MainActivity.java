package com.liyu.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liyu.sqlitetoexcel.ExcelToSQLite;
import com.liyu.sqlitetoexcel.SQLiteToExcel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    private String outputFile;

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
        user.save();*/

    }




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


    public void onImport(View v) {

        final String databasePath1 = this.getDatabasePath("internal.db").getAbsolutePath();

        final String databasePath2 = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "external.db";
        Log.d("Path:",databasePath2);



        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else  {
            File file = new File("/data/user/0/com.liyu.demo/databases/user22.xls.db");
            if (file.exists()) {
                file.delete();
            }
            ImportAction();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImportAction();
                } else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private  void  ImportAction() {
        new ExcelToSQLite
                .Builder(this)
                //        .setDataBase("/data/data/com.liyu.demo/databases/galen")
          //             .setAssetFileName("user22.xls")
               .setFilePath("/sdcard/Download/user22.xls")
                .setDecryptKey("1234567")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .start(new ExcelToSQLite.ImportListener() {
                    @Override
                    public void onStart() {
//                        tv.append(makeLog("\n" +
//                                "Import importTables--->"));
                        Toast.makeText(MainActivity.this,"importTables",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCompleted(String result) {
//                        tv.append(makeLog("\n" +
//                                "Import completed--->" + result));
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

    private void showDbMsg(String dbName,String column,String rows) {
        SQLiteDatabase database;
        try {
            database = SQLiteDatabase.openOrCreateDatabase(dbName, null);
 //           Cursor cursor = database.rawQuery("select name from sqlite_master where type='table' order by name", null);
  //          while (cursor.moveToNext()) {
  //              tv.append("\nNew tables is : " + cursor.getString(0) + "  ");
            Cursor cursor2 = database.rawQuery("select " + column + " from  family2 where name =? "  , new String[]{rows});
                while (cursor2.moveToNext()) {
                    tv.append("\n");
                    for (int i = 0; i < cursor2.getColumnCount(); i++) {
                        tv.append(cursor2.getString(i) + "  ");
                    }
                }
                cursor2.close();
 //           }
 //           cursor.close();
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
