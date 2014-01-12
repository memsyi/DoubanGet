package com.example.doubanget;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyPagerAdapter extends PagerAdapter {

	private ArrayList<View> views;      
	public MyPagerAdapter(ArrayList<View> views){   
	this.views = views;      
	}   

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	public void destroyItem(View container, int position, Object object) {   
		((ViewPager)container).removeView(views.get(position));
		}
	public Object instantiateItem(View container, int position) {   
		((ViewPager)container).addView(views.get(position));   
		return views.get(position);
		}

}
