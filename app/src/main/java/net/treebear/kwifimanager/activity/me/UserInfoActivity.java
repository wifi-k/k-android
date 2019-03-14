package net.treebear.kwifimanager.activity.me;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;

import net.treebear.kwifimanager.R;
import net.treebear.kwifimanager.activity.account.ForgetPwdCodeActivity;
import net.treebear.kwifimanager.base.BaseActivity;
import net.treebear.kwifimanager.config.Values;
import net.treebear.kwifimanager.util.FileUtils;
import net.treebear.kwifimanager.widget.TChooseTypePop;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Administrator
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.root_view)
    ConstraintLayout mRootView;
    @BindView(R.id.civ_header_pic)
    CircleImageView civHeaderPic;
    @BindView(R.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R.id.tv_mobile_number)
    TextView tvMobileNumber;
    private TChooseTypePop choosePicTypePop;
    private Uri mImageUri;
    private String picPath;

    @Override
    public int layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.personal_info);
    }

    @OnClick(R.id.civ_header_pic)
    public void onCivHeaderPicClicked() {
        showChoosePop();
    }

    @OnClick(R.id.tv_nick_name)
    public void onTvNickNameClicked() {
    }

    @OnClick(R.id.tv_mobile_number)
    public void onTvMobileNumberClicked() {
    }

    @OnClick(R.id.tv_modify_password)
    public void onTvModifyPasswordClicked() {
        // TODO: 2019/3/14 修改界面以兼容修改和重置
        startActivity(ForgetPwdCodeActivity.class);
    }

    /**
     * 弹出获取图片方式弹窗
     */
    private void showChoosePop() {
        if (choosePicTypePop == null) {
            choosePicTypePop = new TChooseTypePop(this);
            choosePicTypePop.setOnChooseTypeList(new TChooseTypePop.OnChooseTypeListener() {
                @Override
                public void onCancelClick() {
                    choosePicTypePop.dismiss();
                }

                @Override
                public void onLeftClick() {
                    choosePicTypePop.dismiss();
                    gotoCamera();
                }

                @Override
                public void onRightClick() {
                    choosePicTypePop.dismiss();
                    gotoGallery();
                }

                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
        }
        backgroundAlpha(0.8f);
        choosePicTypePop.show(mRootView);
    }

    /**
     * 相册获取图片
     */
    private void gotoGallery() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
                        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(albumIntent, Values.REQUEST_SYSTEM_GALLERY);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort(R.string.refuse_gallery_permission_error);
                    }
                }).request();

    }

    /**
     * 牌照获取图片
     */
    private void gotoCamera() {
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        //系统常量， 启动相机的关键
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 参数常量为自定义的request code, 在取返回结果时有用
                        startActivityForResult(openCameraIntent, Values.REQUEST_SYSTEM_CAMERA);
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort(R.string.refuse_camera_permission_error);
                    }
                }).request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case Values.REQUEST_SYSTEM_CAMERA:
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap bmp = (Bitmap) bundle.get("data");
                    FileUtils.saveJpgToSdCard(bmp);
                    civHeaderPic.setImageBitmap(bmp);
                }
                break;
            case Values.REQUEST_SYSTEM_GALLERY:
                try {
                    Uri selectedImage = data.getData();
                    if (selectedImage == null) {
                        return;
                    }
                    //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    //从系统表中查询指定Uri对应的照片
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        picPath = cursor.getString(columnIndex);
                        //获取照片路径
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(picPath);
                        civHeaderPic.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

}
