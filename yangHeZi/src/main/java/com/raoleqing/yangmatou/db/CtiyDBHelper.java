package com.raoleqing.yangmatou.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.raoleqing.yangmatou.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * 老黄历数据库
 * **/
public class CtiyDBHelper {
	
	public static final String DATABASE_FILENAME = "china_province_city_zone.db"; // 这个是DB文件名字  
    public static final String PACKAGE_NAME = "com.raoleqing.yanghezi"; // 这个是自己项目包路径
    public static final String DATABASE_PATH = "/data"+  
             Environment.getDataDirectory().getAbsolutePath() + "/"  
            + PACKAGE_NAME+"/"; // 获取存储位置地址  
    private static SQLiteDatabase database; 

	/**
	 * 打开一个数据库
	 * */
	public static SQLiteDatabase openDatabase(Context context) {
		try {
			String databaseFilename = DATABASE_PATH + DATABASE_FILENAME;
			File dir = new File(DATABASE_PATH);

			if (!dir.exists()) {
				dir.mkdirs();
			}

			File file = new File("/data/data/com.raoleqing.yanghezi/xiongshen.db");

			if (!file.exists()) {
				InputStream is = context.getResources().openRawResource(R.raw.china_province_city_zone);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}

				fos.close();
				is.close();
			}

			database = SQLiteDatabase.openOrCreateDatabase(databaseFilename,
					null);
			return database;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
