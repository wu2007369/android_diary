package com.example.wuzhiming.myapplication.jetpacktest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.databinding.ActivityNormalUseBinding;

public class NormalUseActivity extends AppCompatActivity {

    //TODO ATTETION
    //    这个类是跟你的xml名字关联的
    private ActivityNormalUseBinding binding;
    private MainViewModel mainViewModel;
//    private ViewDataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_normal_use);


        // 绑定工作
        binding = DataBindingUtil.setContentView(this, R.layout.activity_normal_use);

        // 旧版本
        // mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        // 最新版本  extends AndroidViewModel
        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        //  最新版本  extends ViewModel  不需要环境
        // mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        // 绑定工作
        binding.setVm(mainViewModel);

        // 建立感应
        binding.setLifecycleOwner(this);
    }
}