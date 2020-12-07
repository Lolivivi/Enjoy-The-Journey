package com.example.mybaidupanorma.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybaidupanorma.R;
import com.example.mybaidupanorma.util.ConfigUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadVideoActivity extends AppCompatActivity {
    private String phone;//手机号
    private GridView gridView1;              //网格显示缩略图
    private Button buttonPublish;            //发布按钮
    private final int IMAGE_OPEN = 1;        //打开图片标记
    //选择图片路径
    private String pathImage;
    private String videoPath;
    private ArrayList<String> path = new ArrayList<>();
    private List<String> path1;
    //导入临时图片
    private Bitmap bmp;
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;
    //设置控件
    private EditText editText1;
    private EditText title;
    private TextView tvDate;
    private EditText edAddress;
    //笔记id
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenery_template);
        //获取手机号
        SharedPreferences sp = getSharedPreferences("setting", MODE_PRIVATE);
        phone = sp.getString("tel", "1");

        //锁定屏幕
        getWindow().setSoftInputMode(WindowManager.LayoutParams.
                SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.scenery_template);
        //获取控件对象
        gridView1 = findViewById(R.id.gridView1);
        editText1 = findViewById(R.id.editText1);
        title = findViewById(R.id.editText0);
        tvDate = findViewById(R.id.tv_date);
        edAddress = findViewById(R.id.ed_address);

        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.like3);
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.griditem_addpic,
                new String[]{"itemImage"}, new int[]{R.id.imageView1});

        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView1.setAdapter(simpleAdapter);

        /*
         * 监听GridView点击事件
         */
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               if (position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(getApplicationContext(), "添加图片", Toast.LENGTH_SHORT).show();
                   Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                   startActivityForResult(i,66);
                } else {
                    //删除图片
                    dialog(position);
                }
            }
        });

        //获取当前时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
        String sim = dateFormat.format(date);
        Log.e("lww", sim);
        tvDate.setText(sim);

        //发布按钮的点击事件
        buttonPublish = findViewById(R.id.button1);
        buttonPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("lww", path1 + "");
                upLoadText();
                Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_LONG).show();
                finish();
            }
        });


    }

    private void upLoadText() {
        FormBody formBody = new FormBody.Builder()
                .add("tel", phone)
                .add("content", editText1.getText().toString())
                .add("titlle", title.getText().toString())
                .add("upload_time", tvDate.getText().toString())
                .add("upload_position", edAddress.getText().toString())
                .build();
        Log.e("lww", phone);
        Log.e("lww", editText1.getText().toString());
        Log.e("lww", title.getText().toString());
        Log.e("lww", tvDate.getText().toString());
        Log.e("lww", edAddress.getText().toString());
        //创建请求对象
        Request request = new Request.Builder()
                .url(ConfigUtil.BASE_URL + "note/postNote")
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
                Log.e("lww", result);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取响应的字符串信息
                String result = response.body().string();
                id = result;
                upLoadPictures(videoPath);
                Log.e("lww", result);

            }
        });
    }

    private void upLoadPictures(String path1) {
        //将视频上传给服务端
        File file = new File(path1);
        String fileName = file.getName();
        Log.e("names", fileName);
        //获取后缀名
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        Log.e("lww", prefix);
        Log.e("lww", id);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("image/jpeg");
        RequestBody requestBody = RequestBody.create(mediaType, file);
        String s = ConfigUtil.BASE_URL + "note/upLoadCoverImg";
        String key = "?note_id=" + id + "&fileType=" + prefix+"&res_type=video";
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

    //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 66 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String[] filePathColumn = {MediaStore.Video.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedVideo,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            videoPath = cursor.getString(columnIndex);
            cursor.close();
            Log.e("videoPath", videoPath);
            pathImage = videoPath;
            Log.e("path", pathImage);
            if (!TextUtils.isEmpty(pathImage)) {
                Bitmap addbmp =  ThumbnailUtils.createVideoThumbnail(pathImage, MediaStore.Images.Thumbnails.MICRO_KIND);
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("itemImage", addbmp);
                imageItem.add(map);
                Log.e("lww", imageItem + "");
                simpleAdapter = new SimpleAdapter(this,
                        imageItem, R.layout.griditem_addpic,
                        new String[]{"itemImage"}, new int[]{R.id.imageView1});
                simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {
                        // TODO Auto-generated method stub
                        if (view instanceof ImageView && data instanceof Bitmap) {
                            ImageView i = (ImageView) view;
                            i.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });
                gridView1.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();
                //刷新后释放防止手机休眠后自动添加
                pathImage = null;
            }
        }
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadVideoActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();

    }

}

