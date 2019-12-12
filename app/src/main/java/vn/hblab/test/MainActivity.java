package vn.hblab.test;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.hblab.test.Model.ImagePicker;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImagePicker> allpictures;
    @BindView(R.id.tv_title)
    TextView tvtitle;
    @BindView(R.id.img_canlce)
    ImageButton imgcanlce;
    @BindView(R.id.btn_choose)
    Button btnchoose;
    @BindView(R.id.rcv_view)
    RecyclerView rcvview;
    PictureAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        allpictures = new ArrayList<>();
        imgcanlce.setImageResource(R.drawable.ic_delete_button);
        if (allpictures.isEmpty()){
            allpictures = getAllImagesByFolder("/storage/emulated/0/DCIM/Facebook/");
            adapter = new PictureAdapter(allpictures, this, this);
            rcvview.setLayoutManager(new GridLayoutManager(this, 5));
            rcvview.setAdapter(adapter);
        }

    }

    private ArrayList<ImagePicker> getAllImagesByFolder(String path){
        ArrayList<ImagePicker> images = new ArrayList<>();
        Uri allVideosuri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.ImageColumns.DATA ,MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE};
        Cursor cursor = MainActivity.this.getContentResolver().query( allVideosuri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[] {"%"+path+"%"}, null);
        try {
            cursor.moveToFirst();
            do{
                ImagePicker pic = new ImagePicker();

                pic.setPicturName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));

                pic.setPicturePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));

                pic.setPictureSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                images.add(pic);
            }while(cursor.moveToNext());
            cursor.close();
            ArrayList<ImagePicker> reSelection = new ArrayList<>();
            for(int i = images.size()-1;i > -1;i--){
                reSelection.add(images.get(i));
            }
            images = reSelection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }
}
