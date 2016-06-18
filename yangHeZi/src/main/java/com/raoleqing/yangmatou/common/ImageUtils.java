package com.raoleqing.yangmatou.common;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.raoleqing.yangmatou.uitls.AbBase64;


public class ImageUtils
{
  private static final String TAG = "BitmapCommonUtils";
  
  private static final String JPEG_FILE_PREFIX = "IMG_";
  private static final String JPEG_FILE_SUFFIX = ".jpg";

  public static Bitmap getUnErrorBitmap(InputStream is, BitmapFactory.Options options)
  {
     Bitmap bitmap = null;
    try {
       bitmap = BitmapFactory.decodeStream(is, null, options);
    } catch (OutOfMemoryError e) {
       options.inSampleSize += 1;
       return getUnErrorBitmap(is, options);
    }
     return bitmap;
  }

  public static byte[] Bitmap2Bytes(Bitmap bm) {
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
     return baos.toByteArray();
  }

  public static String getBitmapWH(Bitmap bitmap)
  {
     byte[] datas = Bitmap2Bytes(bitmap);
     BitmapFactory.Options options = new BitmapFactory.Options();
     options.inJustDecodeBounds = true;
     BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
     return options.outWidth + "X" + options.outHeight;
  }

  public static File getDiskCacheDir(Context context, String uniqueName)
  {
     String cachePath = "mounted".equals(
       Environment.getExternalStorageState()) ? getExternalCacheDir(context)
       .getPath() : context.getCacheDir().getPath();

     return new File(cachePath + File.separator + uniqueName);
  }

  public static int getBitmapSize(Bitmap bitmap)
  {
     return bitmap.getRowBytes() * bitmap.getHeight();
  }

  public static File getExternalCacheDir(Context context)
  {
     String cacheDir = "/Android/data/" + context.getPackageName() + 
       "/cache/";
     return new File(Environment.getExternalStorageDirectory().getPath() + 
      cacheDir);
  }
  
  /**
   * 保存图片
 * @param context
 * @param bm
 * @param fileName
 * @return
 */
  public static String saveBitmap(Context context,Bitmap bm){
	  try {
		  File  f = createImageFile(context);
		  
		  if (f.exists()) {
		   f.delete();
		  }
		   FileOutputStream out = new FileOutputStream(f);
		   bm.compress(Bitmap.CompressFormat.PNG, 90, out);
		   out.flush();
		   out.close();
		   return f.getAbsolutePath();
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	  
	  return null;
  }
  
  /**
 * @param context
 * @param fileName
 * @param bm
 * @return
 */
public static String saveBitmap(Context context,String fileName,Bitmap bm){
	  try {
		  File  f = new File(context.getExternalCacheDir() + "/" + "img/" + fileName);
		  
		  if (f.exists()) {
		   f.delete();
		  }
		   FileOutputStream out = new FileOutputStream(f);
		   bm.compress(Bitmap.CompressFormat.PNG, 90, out);
		   out.flush();
		   out.close();
		   return f.getAbsolutePath();
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	  
	  return null;
  }
  
  /**
   *获取图片路径 
	 * @return
	 */
	public static File getAlbumDir(Context context) {
		File storageDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			storageDir = new File(context.getExternalCacheDir() + "/" + "img");
			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						return null;
					}
				}
			}
		}
		return storageDir;
	}
	
	/**
	 * 创建图片
	 * @return
	 * @throws IOException
	 */
	public static File createImageFile(Context context) throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getAlbumDir(context);
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}
	
	/**
	 * 创建图片
	 * @return
	 * @throws IOException
	 */
	public static File createImageFile(Context context,String fileName) throws IOException {
		// Create an image file name
		File albumF = getAlbumDir(context);
		File imageF = File.createTempFile(fileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}
	
	/**
	 * 显示图片
	 * @param mImageView
	 * @param picPath
	 */
	public static void showPic(ImageView mImageView,String picPath) {

		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = mImageView.getWidth();
		int targetH = mImageView.getHeight();

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(picPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW / targetW, photoH / targetH);
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		Bitmap bitmap = BitmapFactory.decodeFile(picPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
		mImageView.setImageBitmap(bitmap);

	}
	
	public static String getPath(Activity context, Uri uri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.managedQuery(uri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	
	/**
	 * 
	 * @param image
	 * @param maxSize
	 *            按Kb
	 * @return
	 */
	public static String compressImageInBase64(Bitmap image, long maxSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > maxSize && options > 0) {
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 20;
		}
		return AbBase64.encode(baos.toByteArray());
	}
	
	public static byte[] compressImageToByte(Bitmap image, long maxSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > maxSize && options > 0) {
			baos.reset();
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 20;
		}
		return baos.toByteArray();
	}

  public static long getUsableSpace(File path)
  {
    try
    {
       StatFs stats = new StatFs(path.getPath());
       return stats.getBlockSize() * 
         stats.getAvailableBlocks();
    } catch (Exception e) {
      Log.e("BitmapCommonUtils", 
         "获取 sdcard 缓存大小 出错，请查看AndroidManifest.xml 是否添加了sdcard的访问权限");
       e.printStackTrace();
     }return -1L;
  }
}
