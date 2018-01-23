package com.sample.viewanimation;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.sample.viewanimation.databinding.CollapsingLayoutBinding;

public class MainActivity extends AppCompatActivity {
    
    private static final int PRE_SCROLL_OFFSET_DIFFERENCE = 100;
    private CollapsingLayoutBinding mBinding;
    private Context mContext;
    private Animation slideDownHide;
    private Animation slideUpShow;
    private MenuItem addUser, share;
    private String title = "Lorem Ipsum dummy title";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        
        initViews();
    
    
        /**
         * Adding offset change listener on appBar view
         * this listener will tell us when to hide and when to show the view
         * @see PRE_SCROLL_OFFSET_DIFFERENCE is the constant this will basically animate the view
         * before actual scrollRange of appBar layout (it is optional when you want to animate view
         * before the appbar layout is fully closed)
         */
        mBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() - PRE_SCROLL_OFFSET_DIFFERENCE) {
                  
                    animateView(true, mBinding.detailBtnLayout);
                    showMenuItems(true);
                
                } else {
                  
                    showMenuItems(false);
                    animateView(false, mBinding.detailBtnLayout);
                
                }
            }
        });
        
    }
    
    /**
     * This method will show/hide menu items and actionbar title when layout is collapsed or expanded
     * @param showItems if true=> show menu items, hide otherwise
     */
    private void showMenuItems(boolean showItems) {
        if (share != null) {
            if (showItems) {
                share.setVisible(true);
                addUser.setVisible(true);
                getSupportActionBar().setTitle(title);
                
            } else {
                share.setVisible(false);
                addUser.setVisible(false);
                getSupportActionBar().setTitle("");
                
            }
        }
    }
    
    
    /**
     * initialize all your views here when activity created
     */
    private void initViews() {
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    
    
        /**
         * getting animation from files which we have created
         */
        slideUpShow = AnimationUtils.loadAnimation(mContext, R.anim.slide_up_visible);
        slideDownHide = AnimationUtils.loadAnimation(mContext, R.anim.slide_down_hide);
        
        
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        addUser = menu.findItem(R.id.add);
        share = menu.findItem(R.id.share);
        return true;
    }
    
    /**
     * This method will animate the bottom button layout when hide/shown
     * this animation can be applied on any view.
     *
     * @param isPanelShown => boolean to show/hide view
     * @param view => a view which is to be hide/shown
     */
    public void animateView(boolean isPanelShown, final View view) {
        if (isPanelShown) {
            if (!view.isShown()) {
                view.startAnimation(slideUpShow);
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.isShown()) {
                view.startAnimation(slideDownHide);
                view.setVisibility(View.GONE);
            }
        }
    }
}
