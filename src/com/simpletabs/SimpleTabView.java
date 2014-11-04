package com.simpletabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleTabView extends HorizontalScrollView implements View.OnClickListener {

	public interface OnTabClickedListener {

		boolean onTabClicked(SimpleTabView tabView, int tabIndex, CharSequence tabText);
	}

	private final LinearLayout mInsertRoot;
	private int mSelectedTabIndex = -1;
	private OnTabClickedListener mOnTabClickedListener;

	public SimpleTabView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.actionBarStyle);
	}

	public SimpleTabView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		inflate(context, R.layout.simple_tab_view, this);
		mInsertRoot = (LinearLayout) getChildAt(0);
	}

	public int addTab(CharSequence tabLabel) {
		int tabIndex = mInsertRoot.getChildCount();
		TextView tabView = (TextView) LayoutInflater.from(
				getContext()).inflate(
				R.layout.simple_tab_view_tab, mInsertRoot, false);
		tabView.setTag(tabIndex);
		tabView.setText(tabLabel);
		tabView.setOnClickListener(this);
		mInsertRoot.addView(tabView, tabIndex);
		return tabIndex;
	}

	public int addTab(CharSequence tabLabel, boolean selected) {
		int tabIndex = addTab(tabLabel);
		if (selected) {
			selectTabAt(tabIndex);
		}
		return tabIndex;
	}

	public CharSequence getTabTextAt(int tabIndex) {
		return ((TextView) getChildAt(tabIndex)).getText();
	}

	public void selectTabAt(int tabIndex) {
		if (mSelectedTabIndex != tabIndex) {
			View tabView;
			int previousIndex = mSelectedTabIndex;
			mSelectedTabIndex = tabIndex;
			if (previousIndex > -1) {
				tabView = mInsertRoot.getChildAt(previousIndex);
				tabView.setOnClickListener(this);
				tabView.setSelected(false);
			}
			if (mSelectedTabIndex > -1) {
				tabView = mInsertRoot.getChildAt(mSelectedTabIndex);
				tabView.setSelected(true);
				tabView.setOnClickListener(null);
				tabView.setClickable(false);
				centerTabIfNeeded(tabView);
			}
		}
	}

	@Override
	public void onClick(View v) {
		int tabIndex = (Integer) v.getTag();
		if (mOnTabClickedListener == null || mOnTabClickedListener.onTabClicked(
				this, tabIndex, ((TextView) v).getText())) {
			selectTabAt(tabIndex);
		}
	}

	public void setOnTabClickedListener(OnTabClickedListener listener) {
		mOnTabClickedListener = listener;
	}

	private void centerTabIfNeeded(View tabView) {
		if (getWidth() < mInsertRoot.getWidth()) {
			smoothScrollTo(getXScrollToCenterTab(tabView), getScrollY());
		}
	}

	private int getXScrollToCenterTab(View tabView) {
		int scrollX = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
		return scrollX < 0 ? 0 : scrollX > mInsertRoot.getWidth() - tabView.getWidth() ? 0 : scrollX;
	}
}
