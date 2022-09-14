package com.example.wuzhiming.myapplication.dialog;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.dialog.base.BaseDialog;


/**
 * Created by Administrator on 2018/8/6.
 */

public class VerificationScanCodeOrderDialog extends BaseDialog {
    private String content;
    Activity mContext = null;
    DialogType mDialogType;

    private ImageView top_logo;
    private TextView tip1;
    private ImageView barcode;
    private TextView tip2;

    public void setContent(String content) {
        this.content = content;
    }

    public void setDialogType(DialogType dialogType) {
        this.mDialogType = dialogType;
    }

    public VerificationScanCodeOrderDialog(Activity context, DialogType dialogType, String content) {
        super(context, R.style.dialog);
        mContext = context;
        mDialogType = dialogType;
        this.content = content;
    }

    @Override
    protected void init() {
        setContentView(R.layout.dialog_verification_scan_code_order);

        top_logo = findViewById(R.id.top_logo);
        tip1 = findViewById(R.id.tip1);
        barcode = findViewById(R.id.barcode);
        tip2 = findViewById(R.id.tip2);

        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }


    @Override
    protected void setListener() {
        super.setListener();
    }


    @Override
    public void show() {
        super.show();
        setDialogFullScreen();
        updateUI();
    }

    private void updateUI() {

    }

    public void setDialogFullScreen() {
        Window window = getWindow();
        window.setWindowAnimations(R.style.dialog);
        //设置alterdialog 全屏，缺点是头部 底部有空隙
/*        WindowManager windowManager = ((Activity) (mContext)).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = (int) (display.getHeight()); //设置宽度
        lp.width = (int) (display.getWidth()); //设置宽度
        window.setAttributes(lp);*/

        //设置alterdialog 真全屏
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
    }


    @Override
    public void onClick(View v) {

    }


    public enum DialogType {
        //购物扫码购，711扫码购
        MARKET("4"), SEVEN11("5");
        private String type;

        DialogType(String type) {
            this.type = type;
        }
    }
}
