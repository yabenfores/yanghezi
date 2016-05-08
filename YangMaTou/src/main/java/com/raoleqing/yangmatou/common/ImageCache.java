package com.raoleqing.yangmatou.common;

import java.util.HashMap;

import android.graphics.Bitmap;

@SuppressWarnings("serial")
public class ImageCache extends HashMap<String, Bitmap>
{
	public boolean isCached(String url)
	{
		return containsKey(url) && get(url) != null;
	}
}
