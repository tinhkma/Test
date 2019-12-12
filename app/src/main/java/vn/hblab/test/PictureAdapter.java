package vn.hblab.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.hblab.test.Model.ImagePicker;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHoler> {

    private static final int NUMBERS_OF_ITEM_TO_DISPLAY = 5;
    private ArrayList<ImagePicker> pickerArrayList = new ArrayList<>();
    private MainActivity activity;
    private Context context;
    private OnClick onClick;

    public PictureAdapter(ArrayList<ImagePicker> pickerArrayList, MainActivity activity, Context context) {
        this.pickerArrayList = pickerArrayList;
        this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_image, parent, false);
        view.getLayoutParams().width = getScreenWidth() / NUMBERS_OF_ITEM_TO_DISPLAY;
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {

//        Glide.with(activity)
//                .load(pickerArrayList.get(position).getPicturePath())
//                .apply(new RequestOptions().centerCrop())
//                .into(holder.imgImage);

        File file = new File(pickerArrayList.get(position).getPicturePath());
        if (file.exists()){
            Uri uri = Utils.getImageContentUri(context, file);
            Log.i(TAG, "onBindViewHolder: " + file.length());
            Log.i(TAG, "onBindViewHolder: " + uri.toString());
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            holder.imgImage.setImageURI(uri);
        }



//        holder.imgImage.setOnClickListener(v -> {
//            Intent intent = new Intent(activity, ImageViewActivity.class);
//            intent.putExtra("EXTRA_PicturePath", pickerArrayList.get(position).getPicturePath());
//            intent.putExtra("EXTRA_PicturePosition", position);
//            activity.startActivity(intent);
//            Toast.makeText(activity, "Uri: "+ position + "  " + pickerArrayList.get(position).getPicturePath(), Toast.LENGTH_SHORT).show();
//        });

        holder.imgImage.setOnClickListener(v ->{
            Intent intent = new Intent(activity, ImageViewActivity.class);
            intent.putExtra("PATH",  pickerArrayList.get(position).getPicturePath());
            Log.i("abc", "onBindViewHolder: " + position);
            intent.putExtra("POSITION", (position+1) + "/" + pickerArrayList.size());
            activity.startActivity(intent);
        });

    }



    public int getScreenWidth() {
        WindowManager wm = (WindowManager) activity.getSystemService(activity.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    public int getItemCount() {
        return pickerArrayList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        @BindView(R.id.img_image)
        ImageView imgImage;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClick {
        void onDataPass(int position, ArrayList<ImagePicker> imagePickers);
    }
}
