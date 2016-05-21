package com.raoleqing.yangmatou.uitls;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class WindowManagerUtils {

	private Context context;
	private int width;
	private int height;

	public WindowManagerUtils(Activity context) {
		this.context = context;

		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);

		width = metric.widthPixels; // 屏幕宽度（像素）
		height = metric.heightPixels; // 屏幕高度（像素）

		// float density = metric.density; // 屏幕密度�?0.75 / 1.0 / 1.5�?
		// int densityDpi = metric.densityDpi; // 屏幕密度DPI�?120 / 160 / 240�?

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
