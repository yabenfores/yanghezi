package com.raoleqing.yangmatou.db;

import java.util.ArrayList;
import java.util.List;

import com.raoleqing.yangmatou.ben.City;
import com.raoleqing.yangmatou.ben.Province;
import com.raoleqing.yangmatou.ben.Zone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 老黄历,凶神说明数据库
 **/
public class CtiyDBManage {

	private SQLiteDatabase db;

	public CtiyDBManage(Context context) {
		db = CtiyDBHelper.openDatabase(context);
	}

	/*
	 * 询出一条数据
	 */
	public List<Province> queryProvince() {
		
		List<Province> provinceList = new ArrayList<Province>();

		Cursor c = db.query("T_Province", null, null, null, null, null, null);

	
		while (c.moveToNext()) {
			Province mProvince = new Province();
			mProvince.setProName(c.getString(c.getColumnIndex("ProName")));
			mProvince.setProSort(c.getInt(c.getColumnIndex("ProSort")));
			mProvince.setArea_id(c.getInt(c.getColumnIndex("area_id")));
			provinceList.add(mProvince);
		}
		c.close();
		return provinceList;
	}
	
	
	/*
	 * 询出一条数据
	 */
	public List<City> queryCity(String ProSort) {
		
		List<City> list = new ArrayList<City>();

		Cursor c = db.query("T_City", null, "ProID = ?", new String[] { ProSort }, null, null, null);

		while (c.moveToNext()) {
			City mCity = new City();
			mCity.setCityName(c.getString(c.getColumnIndex("CityName")));
			mCity.setCitySort(c.getInt(c.getColumnIndex("CitySort")));
			mCity.setProID(c.getInt(c.getColumnIndex("ProID")));
			mCity.setArea_id(c.getInt(c.getColumnIndex("area_id")));
			
			list.add(mCity);
		}
		c.close();
		return list;
	}
	
	/*
	 * 询出一条数据
	 */
	public List<Zone> queryZone(String CityID) {
		
		List<Zone> list = new ArrayList<Zone>();

		Cursor c = db.query("T_Zone", null, "CityID = ?", new String[] { CityID }, null, null, null);

		while (c.moveToNext()) {
			Zone mZone = new Zone();
			mZone.setZoneName(c.getString(c.getColumnIndex("ZoneName")));
			mZone.setCityID(c.getInt(c.getColumnIndex("CityID")));
			mZone.setArea_id(c.getInt(c.getColumnIndex("area_id")));
		
			list.add(mZone);
		}
		c.close();
		return list;
	}
	
	

	/**
	 * 关闭数据库库
	 */
	public void closeDb() {
		db.close();
	}
}
