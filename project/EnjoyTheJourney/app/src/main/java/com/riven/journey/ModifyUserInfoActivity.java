package com.riven.journey;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.riven.journey.fragment.MyPageFragment;
import com.riven.journey.util.ConfigUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 田春雨
 */
public class ModifyUserInfoActivity extends AppCompatActivity {
    private ImageView ivPhoto;//头像
    public static final int PICK_PHOTO = 102;
    private String phone;
    private EditText edName;//昵称
    private EditText edSign;//签名
    private RadioButton rbBoy;//男
    private RadioButton rbGirl;//女
    private Button btnEdit;//保存修改按钮
    private String sex;
    private String imagePath;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.obj.equals("true")) {
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2:
                    Log.e("lww", "网络连接错误");
                    break;
                case 3:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    ivPhoto.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information_modification);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //获取控件
        findViews();
        //设置监听器
        setListener();
        //获取得到的用户手机号
        SharedPreferences sp = getSharedPreferences("setting", MODE_PRIVATE);
        phone = sp.getString("tel", "1");
        Intent request = getIntent();
        String name = request.getStringExtra("name");
        String sign = request.getStringExtra("sign");
        String imgUrl = request.getStringExtra("imgUrl");
        sex = request.getStringExtra("sex");
        //将头像，昵称，签名显示在页面上
        edName.setText(name);
        edSign.setText(sign);
        Log.e("lww", sex);
        if (sex.equals("男")) {
            rbBoy.setChecked(true);
            rbGirl.setChecked(false);
        } else {
            rbGirl.setChecked(true);
            rbBoy.setChecked(false);
        }
        //显示图片控件
        showPhoto(imgUrl);

    }

    //显示头像
    private void showPhoto(String imgUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUrl);
            InputStream in1 = url.openStream();
            //将输入流解析成Bitmap对象
            bitmap = BitmapFactory.decodeStream(in1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage();
        msg.what = 3;
        msg.obj = bitmap;
        handler.sendMessage(msg);
    }

    private void setListener() {
        MyListener listener = new MyListener();
        ivPhoto.setOnClickListener(listener);
        rbBoy.setOnClickListener(listener);
        rbGirl.setOnClickListener(listener);
        btnEdit.setOnClickListener(listener);
    }

    private void findViews() {
        ivPhoto = findViewById(R.id.iv_photo);
        edName = findViewById(R.id.ed_name);
        rbBoy = findViewById(R.id.rb_boy);
        rbGirl = findViewById(R.id.rb_girl);
        edSign = findViewById(R.id.ed_sign);
        btnEdit = findViewById(R.id.btn_edit);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_photo:
                    //上传头像
                    showUploadAvaratPopuWindow();
                    break;
                case R.id.rb_boy:
                    rbBoy.setChecked(true);
                    rbGirl.setChecked(false);
                    sex = "男";
                    break;
                case R.id.rb_girl:
                    rbGirl.setChecked(true);
                    rbBoy.setChecked(false);
                    sex = "女";
                    break;
                case R.id.btn_edit:
                    //将数据传给服务器
                    upLoadData();
                    //跳转页面
                    finish();
                    break;
            }
        }
    }

    private void upLoadData() {
        //1. OkClient对象
        //2. 创建Request请求对象（提前准备好Form表单数据封装）
        //创建FormBody对象
        FormBody formBody = new FormBody.Builder()
                .add("tel", phone)
                .add("name", edName.getText().toString())
                .add("sex", sex)
                .add("intro", edSign.getText().toString())
                .build();
        Log.e("lww", phone + edName.getText().toString() + sex + edSign.getText().toString());
        String s = ConfigUtil.BASE_URL + "user/updateMy";
        String key = "?tel=" + phone;
        //创建请求对象
        Request request = new Request.Builder()
                .url(s + key)
                .post(formBody)
                .build();
        //3. 创建CALL对象
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String result = "网络连接错误";
                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = result;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取响应的字符串信息
                String result = response.body().string();
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        });
    }

    /*
    上传头像
     */
    private void showUploadAvaratPopuWindow() {
        //创建对象
        final PopupWindow popupWindow = new PopupWindow(this);
        //设置弹出窗口的宽度
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置他的视图
        View view = getLayoutInflater().inflate(R.layout.upload_avatar_popupwindow, null);
        //设置当中控件的属性和监听器
        Button btnCancel = view.findViewById(R.id.btn_cancel_tab);
        Button btnAlbum = view.findViewById(R.id.btn_album);
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传头像
                //动态申请获取访问 读写磁盘的权限
                if (ContextCompat.checkSelfPermission(ModifyUserInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ModifyUserInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                } else {
                    //打开相册
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_PHOTO); // 打开相册
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭弹出框
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        LinearLayout root = findViewById(R.id.root);
        popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) { // 判断手机系统版本号
                    handleImageOnKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    /*
    处理图片文件类型
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content: //downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        // 根据图片路径显示图片
        displayImage(imagePath);
        //将图片传给服务端
        upLoadImg(imagePath);
        Log.e("lww", imagePath);

    }

    /*
    将图片传到服务端
     */
    private void upLoadImg(String imagePath) {
        File file = new File(imagePath);
        String fileName = file.getName();
        //获取后缀名
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Log.e("lww", prefix);
        Log.e("lww", phone);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("image/jpeg");
        RequestBody requestBody = RequestBody.create(mediaType, file);
        String s = ConfigUtil.BASE_URL + "user/upLoadImg";
        String key = "?tel=" + phone + "&fileType=" + prefix;
        //创建请求对象
        final Request request = new Request.Builder()
                .url(s + key)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("lww", "失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取响应的字符串信息
                String result = response.body().string();
                Log.e("lww", result);
            }
        });

    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivPhoto.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取相册图片失败", Toast.LENGTH_SHORT).show();
        }
    }
}
