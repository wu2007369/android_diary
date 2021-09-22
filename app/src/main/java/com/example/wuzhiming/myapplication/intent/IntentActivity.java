package com.example.wuzhiming.myapplication.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.wuzhiming.myapplication.R;
import com.example.wuzhiming.myapplication.immerse.ImmerseActivity;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PDF_CAPTURE = 2;
    Uri locationForPhotos;
    private ImageView image;


    //---淘宝的商铺及商品ID
    private String TaoBaoShopId = "131259851";   //--耐凡眼镜店
    private String TaoBaoGoodsId = "525249416835";  //--时尚潮流复古学生...眼镜框
    private String XiaoBaiShopId = "399115843";   //--小白智慧打印 店铺
    private String XiaoBaiGoodsId = "605413362441";  //小白智慧打印 商品

    //---京东的商铺及商品ID
    private String JDShopId = "1000004123";     //--京东小米官方旗舰店
    private String JDGoodsId = "4099139";       //--小米6详情页
    private String JDXiaoBaiShopId = "1000337243";     //--京东小白智慧打印 官方旗舰店
    private String JDXiaoBaiGoodsId = "100024749316";       //--100024749316 商品详情页



    //--1.打开京东或淘宝的店铺
    private String taobaoAppStr_shop = "taobao://shop.m.taobao.com/shop/shop_index.htm?shop_id="+XiaoBaiShopId+"";
    private String jdAppStr_shop = "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"jshopMain\",\"shopId\":\""+JDXiaoBaiShopId+"\",\"sourceType\":\"M_sourceFrom\",\"sourceValue\":\"dp\"}";

    //--2.打开京东或淘宝的商品详情页
    private String taobaoAppStr_goods = "taobao://item.taobao.com/item.htm?id="+XiaoBaiGoodsId+"";
    private String jdAppStr_goods = "openApp.jdMobile://virtual?params={\"category\":\"jump\",\"des\":\"productDetail\",\"skuId\":\""+JDXiaoBaiGoodsId+"\",\"sourceType\":\"JSHOP_SOURCE_TYPE\",\"sourceValue\":\"JSHOP_SOURCE_VALUE\"}";


    private String PDD_GOODS_NAME="21537222970";
    private String PDD_MALL_ID="256402000";
    //--3.打开发拼多多商品详情页
    private String  PddAppStr_goods="pinduoduo://com.xunmeng.pinduoduo/goods1.html?goods_id="+PDD_GOODS_NAME;
    //--3.打开拼多多 店铺
    private String PddAppStr_mall="pinduoduo://com.xunmeng.pinduoduo/mall_page.html?mall_id="+PDD_MALL_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        image = findViewById(R.id.image);

        findViewById(R.id.btn14).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);

        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btn10).setOnClickListener(this);
        findViewById(R.id.btn11).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn14:
                Intent intent2 = new Intent();
                ComponentName cn = new ComponentName("com.jinying.mobile", "com.jinying.mobile.wxapi.WXEntryActivity");
//                ComponentName cn = new ComponentName("net.sourceforge.simcpux",  "net.sourceforge.simcpux.wxapi.WXEntryActivity");
                intent2.setComponent(cn);
                startActivity(intent2);
                break;

            case R.id.btn1:
//                startActivity(new Intent(this,AliasActivity.class));
                startActivity(new Intent(this, JumpActivity.class));
                break;

            case R.id.btn2:
//                startActivity(new Intent(this,AliasActivity.class));
                Intent intent = new Intent();
                ComponentName cn2 = new ComponentName("com.jinying_oa", "com.jinying_oa.activity.GuideActivity");
                intent.setComponent(cn2);
                startActivity(intent);
                break;

            case R.id.btn3:
//                startActivity(new Intent(this,AliasActivity.class));
                Intent intent3 = new Intent();
                intent3.setAction("android.intent.action.VIEW");
                intent3.addCategory("android.intent.category.BROWSABLE");
                intent3.setData(Uri.parse("geoaapp://"));
                startActivity(intent3);
                break;
            case R.id.btn4:
//                startActivity(new Intent(this,AliasActivity.class));
                Intent intent4 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent4.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.withAppendedPath(locationForPhotos, "targetFilename"));
                startActivityForResult(intent4, REQUEST_IMAGE_CAPTURE);
                break;
            case R.id.btn5:
//                startActivity(new Intent(this,AliasActivity.class));
                Intent intent5 = new Intent(Intent.ACTION_GET_CONTENT);
                intent5.addCategory(Intent.CATEGORY_OPENABLE);

//                                intent5.setType("*/*");

/*                intent5.setType("application/*");
                intent5.putExtra(Intent.EXTRA_MIME_TYPES,
                        new String[]{"application/msword","application/pdf","text/plain"});*/

                intent5.setType("application/pdf");
                startActivityForResult(intent5, REQUEST_PDF_CAPTURE);
                break;
            case R.id.btn6:
                gotoApp(this, "taobao://shop.m.taobao.com/shop/shop_index.htm?shop_id=109437670");
                break;
            case R.id.btn7:
                gotoApp(this, "taobao://item.taobao.com/item.htm?id=544746466475");

//                totaoBao();
                break;
            case R.id.btn8:
                gotoApp(this,jdAppStr_shop);
                break;
            case R.id.btn9:
                gotoApp(this,jdAppStr_goods);
                break;
            case R.id.btn10:
                gotoApp(this,PddAppStr_mall);
                break;
            case R.id.btn11:
                gotoApp(this,PddAppStr_goods);
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");
            Glide.with(this).load(thumbnail).into(image);
        }
    }

    /**
     * 隐式intent 跳转商品详情
     */
    void totaoBao() {
        if (!checkPackage("com.taobao.taobao")) {
//            Toast.makeText(MainActivity.this,"请安装淘宝app！",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ImmerseActivity.class);
            intent.putExtra("url", "https://item.taobao.com/item.htm?spm=a1z0d.6639537.1997196601.14.45d07484uw9hPZ&id=565080000925");
            startActivity(intent);
            return;
        }


        String tbPath = "https://item.taobao.com/item.htm?spm=a1z0d.6639537.1997196601.14.45d07484uw9hPZ&id=565080000925";

//        String tbPath="https://detail.tmall.com/item.htm?spm=a1z0d.6639537.1997196601.3.45d07484uw9hPZ&id=565570128470";
        Intent intent = new Intent();
        intent.setAction("Android.intent.action.VIEW");
        Uri uri = Uri.parse(tbPath); // 商品地址
        intent.setData(uri);
        intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
        startActivity(intent);
    }

    /**
     * 隐式intent 跳转店铺
     */
    void toshop(String tbPath) {

//        String tbPath="https://detail.tmall.com/item.htm?spm=a1z0d.6639537.1997196601.3.45d07484uw9hPZ&id=565570128470";
        Intent intent = new Intent();
        intent.setAction("Android.intent.action.VIEW");
        Uri uri = Uri.parse(tbPath); // 商品地址
        intent.setData(uri);
        intent.setClassName("com.taobao.taobao", "com.taobao.android.shop.activity.ShopHomePageActivity");
        startActivity(intent);
    }

    /**
     * 跳转至淘宝  商品+店铺
     * uri解析跳转
     */
    public static void gotoApp(Activity activity, String url) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(url));
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            this.getPackageManager().getApplicationInfo(packageName, PackageManager
                    .GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}