package vn.hblab.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewActivity extends AppCompatActivity {

    private static final String TAG = "abc";
    @BindView(R.id.tv_title)
    TextView tvtitle;
    @BindView(R.id.img_canlce)
    ImageButton imgcanlce;
    @BindView(R.id.btn_choose)
    Button btnchoose;
    @BindView(R.id.ln_toolbar)
    LinearLayout lntoolbar;
    @BindView(R.id.btn_upload)
    ImageButton btnupload;
    @BindView(R.id.btn_delete)
    ImageButton btndelete;
    @BindView(R.id.btn_crop)
    ImageButton btncrop;
    @BindView(R.id.img_view)
    ImageView img_view;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        String path = getIntent().getStringExtra("PATH");
        String position = getIntent().getStringExtra("POSITION");
        imgcanlce.setImageResource(R.drawable.ic_back);
        Log.i(TAG, "onCreate: " + path + position);

        File file = new File(path);
        if (file.exists()) {
            uri = Utils.getImageContentUri(this, file);
            Log.i(TAG, "onBindViewHolder: " + file.length());
            Log.i(TAG, "onBindViewHolder: " + uri.toString());
            img_view.setImageURI(uri);
        }

        tvtitle.setText(position);

        btnupload.setOnClickListener(v->{
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("image/jpg");
            myIntent.putExtra(Intent.EXTRA_STREAM,uri);
            startActivity(Intent.createChooser(myIntent, "Share Using"));
        });

        btndelete.setOnClickListener(v->{
            File file1 = new File(path);
            file1.delete();
            finish();
        });

        imgcanlce.setOnClickListener(v -> finish());
    }
}
