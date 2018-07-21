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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
    private EditText editText_column;   // excel的列字段的名称
    private EditText etEditText_rows;  // excel的行字段的名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        editText_column = (EditText)findViewById(R.id.input_column);
        etEditText_rows= (EditText)findViewById(R.id.input_rows);
        /*
        User user = new User();
        user.setName("Galen");
        user.setPrice(19.89f);
        user.setCover(getResByte(R.drawable.yuyu));
        user.save();*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.excel,menu);        // import按钮
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.onimport_menu:
                onImport();
                break;
            default:
        }
        return  true;
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
    }*/


    public void onImport() {

        final String databasePath1 = this.getDatabasePath("internal.db").getAbsolutePath();

        final String databasePath2 = Environment.getExternalStorageDirectory().getPath()
                + File.separator + "external.db";
        Log.d("Path:",databasePath2);



        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else  {
            File file = new File("/data/user/0/com.liyu.demo/databases/table.xls.db");
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
               .setFilePath("/sdcard/Download/table.xls")
                .setDecryptKey("1234567")
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .start(new ExcelToSQLite.ImportListener() {
                    @Override
                    public void onStart() {
//                        tv.append(makeLog("\n" +
//                                "Import importTables--->"));
                        Toast.makeText(MainActivity.this,"开始导入",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCompleted(String result) {
//                        tv.append(makeLog("\n" +
//                                "Import completed--->" + result));
                        Toast.makeText(MainActivity.this,"导入成功",Toast.LENGTH_SHORT).show();
                        //                      tv.setText("");
  //                      Log.d("result", result);
 //                       showDbMsg(result,"盐酸","硝酸");
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
        String InputColumnValues = editText_column.getText().toString();    // 得到 excel行字段的值
        String InputRowsValues = etEditText_rows.getText().toString();      // 得到 excel列字段的值
        showDbMsg("/data/data/com.liyu.demo/databases/table.xls.db",InputColumnValues,InputRowsValues);
    }

    private void showDbMsg(String dbName,String column,String rows) {
        // 判别值种类
        // 1 ：相同种化学品
        // 2 ：可以配存
        // 3 ：可以配存，堆放时至少隔离2米
        // 4 : 不可以配存
        final String CategoryValues1 = "1";
        final String CategoryValues2 = "2";
        final String CategoryValues3 = "3";
        final String CategoryValues4 = "4";
        SQLiteDatabase database;
        try {
            database = SQLiteDatabase.openOrCreateDatabase(dbName, null);
            Cursor cursor2 = database.rawQuery("select " + column + " from  family2 where name =? "  , new String[]{rows});
                while (cursor2.moveToNext()) {
                    tv.append("\n");
                    tv.append("这种组合的配存方式结果是:");
                    tv.append("\n\n\n");
//                    int int1=cursor2.getColumnCount();
//                   String s="";
//                    Log.d("cursorColumnCount",s=String.valueOf(int1));
//                    for (int i = 0; i < cursor2.getColumnCount(); i++) {
                    switch (cursor2.getString(0)) {
                        case  CategoryValues1:
                            tv.append("相同种化学品");
                            break;
                        case  CategoryValues2:
                            tv.append("可以配存");
                            break;
                        case  CategoryValues3:
                            tv.append("可以配存，堆放时至少隔离2米");
                            break;
                        case  CategoryValues4:
                            tv.append("不可以配存");
                            break;
                        default:
                            tv.append("");
                            break;
                    }

//                    }
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
