package com.example.lenovo.homework;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDates;

    private TextView mFriend;
    private TextView mFind;
    private TextView mChat;

    private ImageView TableLine;
    private int mScreen1_3;
    private int mCurrentPagerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initTabLine();
        initView();
    }

    public void initTabLine(){
        TableLine = (ImageView)findViewById(R.id.tableLine);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3 = outMetrics.widthPixels / 3;
        LayoutParams lp = TableLine.getLayoutParams();
        lp.width = mScreen1_3;
        TableLine.setLayoutParams(lp);
    }

    private void initView(){
        mFind = (TextView)findViewById(R.id.mFind);
        mFriend = (TextView) findViewById(R.id.mFriend);
        mChat = (TextView) findViewById(R.id.mChat);
        mChat.setTextColor(Color.parseColor("#008000"));
        mViewPager =(ViewPager) findViewById(R.id.viewpager);
        mDates = new ArrayList<Fragment>();
        FriendChat tab01 = new FriendChat();
        Friend tab02 = new Friend();
        Find tab03 = new Find();

        mDates.add(tab01);
        mDates.add(tab02);
        mDates.add(tab03);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){


            public int getCount(){
                return mDates.size();
            }

            public Fragment getItem(int arg0){
                return mDates.get(arg0);
            }

        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position){
                    case 0:
                        mChat.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        mFind.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        mFriend.setTextColor(Color.parseColor("#008000"));
                        break;
                }
                mCurrentPagerIndex = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
                LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) TableLine.getLayoutParams();

                if (mCurrentPagerIndex == 0 && position == 0){  //0-->1
                    lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPagerIndex * mScreen1_3);
                }else if (mCurrentPagerIndex == 1 && position == 0){  //1-->0
                    lp.leftMargin = (int) ( (positionOffset-1) * mScreen1_3 + mCurrentPagerIndex * mScreen1_3);
                }else if (mCurrentPagerIndex == 1 && position == 1){  //1-->2
                    lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPagerIndex * mScreen1_3);
                }else  if (mCurrentPagerIndex == 2 && position == 1){  //2-->1
                    lp.leftMargin = (int) ((positionOffset-1) * mScreen1_3 + mCurrentPagerIndex * mScreen1_3);
                }

                TableLine.setLayoutParams(lp);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public void resetTextView(){
        mChat.setTextColor(Color.BLACK);
        mFind.setTextColor(Color.BLACK);
        mFriend.setTextColor(Color.BLACK);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_update:
                Toast.makeText(SecondActivity.this,"Update!",Toast.LENGTH_SHORT).show();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
