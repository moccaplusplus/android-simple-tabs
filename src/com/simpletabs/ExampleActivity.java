package com.simpletabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.simpletabs.SimpleTabView.OnTabClickedListener;

public class ExampleActivity extends Activity implements OnTabClickedListener {

	private Toast t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example_activity);
		SimpleTabView myTabs = (SimpleTabView) findViewById(R.id.my_tabs);
		myTabs.setFillViewport(true);
		myTabs.addTab("Pierwszy");
		myTabs.addTab("Drugi", true);
		myTabs.addTab("Cieci");
		myTabs.addTab("Ćwiarty");
		myTabs.addTab("Pionty");
		myTabs.addTab("Shoosty");
		myTabs.setOnTabClickedListener(this);
	}

	@Override
	public boolean onTabClicked(SimpleTabView tabView, int tabIndex, CharSequence tabText) {
		if (t == null) {
			t = Toast.makeText(this, "Tab index: " + tabIndex, Toast.LENGTH_SHORT);
		} else {
			t.setText("Tab index: " + tabIndex);
		}
		t.show();
		return true;
	}
}
