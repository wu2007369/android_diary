package com.example.wuzhiming.myapplication.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


/**
 * 
 * @author Richard.Ma
 * 
 */
public abstract class BaseDialog extends Dialog implements OnClickListener
{
    protected Button mBtnBack;
//    protected LinearLayout mLytProgressBar;

    protected TextView mHeaderView = null;
    protected Button mHeaderLeft = null;
    protected TextView mHeaderRight = null;
    
    public BaseDialog(Context context, int theme)
    {
        super(context, theme);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
        setListener();
        loadData();
    }

    protected abstract void init();


    protected void setListener()
    {
        if (mBtnBack != null)
        {
            mBtnBack.setOnClickListener(this);
        }
    };

    protected void loadData()
    {
    };

    public void show(Bundle bundle)
    {
        super.show();
    }





    protected void stopProgressDialog()
    {
//        mLytProgressBar.setVisibility(View.GONE);
    }

    protected void cancelProgressDialog()
    {

    }



    @Override
    public void dismiss()
    {
        super.dismiss();
    }

}
