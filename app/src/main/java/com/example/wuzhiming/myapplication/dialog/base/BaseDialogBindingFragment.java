package com.example.wuzhiming.myapplication.dialog.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author: mango
 * Time: 2019/9/6 22:46
 * Version:
 * Desc:
 */
public abstract class BaseDialogBindingFragment<T extends ViewDataBinding> extends DialogFragment {

    public boolean isSaveInState;

    protected T mDataBind;

    public abstract int setDialogStyle();
    public abstract int setLayoutId();
    public abstract void initView(View root);
    public abstract void setParams();

    public void setSaveInState(boolean saveInState) {
        isSaveInState = saveInState;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, setDialogStyle());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root ;
        mDataBind = DataBindingUtil.inflate(inflater, setLayoutId(), container, false);
        root = mDataBind.getRoot();
        initView(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        setParams();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Dialog dialog = getDialog();
//        if (dialog == null) {
//            return;
//        }
//        DisplayUtil.fullScreen(dialog);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (isSaveInState) {
            return;
        }
        try {
            manager.executePendingTransactions();
            if (!isAdded()) {
                super.show(manager, tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        if (isSaveInState) {
            return;
        }
        if (getFragmentManager() == null) {
            return;
        }
        super.dismiss();
    }
}
