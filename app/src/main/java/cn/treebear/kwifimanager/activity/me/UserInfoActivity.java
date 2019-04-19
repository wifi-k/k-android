package cn.treebear.kwifimanager.activity.me;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.treebear.kwifimanager.MyApplication;
import cn.treebear.kwifimanager.R;
import cn.treebear.kwifimanager.R2;
import cn.treebear.kwifimanager.activity.account.SetPasswordActivity;
import cn.treebear.kwifimanager.base.BaseActivity;
import cn.treebear.kwifimanager.base.BaseResponse;
import cn.treebear.kwifimanager.bean.QiNiuUserBean;
import cn.treebear.kwifimanager.bean.SUserCover;
import cn.treebear.kwifimanager.bean.ServerUserInfo;
import cn.treebear.kwifimanager.config.Config;
import cn.treebear.kwifimanager.config.GlideApp;
import cn.treebear.kwifimanager.config.Keys;
import cn.treebear.kwifimanager.config.Values;
import cn.treebear.kwifimanager.http.ImageUploadManager;
import cn.treebear.kwifimanager.mvp.server.contract.ModifyUserInfoContract;
import cn.treebear.kwifimanager.mvp.server.presenter.ModifyUserInfoPresenter;
import cn.treebear.kwifimanager.util.ActivityStackUtils;
import cn.treebear.kwifimanager.util.BitmapUtils;
import cn.treebear.kwifimanager.util.Check;
import cn.treebear.kwifimanager.util.FileUtils;
import cn.treebear.kwifimanager.util.TLog;
import cn.treebear.kwifimanager.widget.pop.TChoosePicTypePop;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Administrator
 */
public class UserInfoActivity extends BaseActivity<ModifyUserInfoContract.Presenter, SUserCover> implements ModifyUserInfoContract.View {

    @BindView(R2.id.root_view)
    ConstraintLayout mRootView;
    @BindView(R2.id.civ_header_pic)
    CircleImageView civHeaderPic;
    @BindView(R2.id.tv_nick_name)
    TextView tvNickName;
    @BindView(R2.id.tv_mobile_number)
    TextView tvMobileNumber;
    private TChoosePicTypePop choosePicTypePop;
    private String picPath;
    private ServerUserInfo userInfo;
    private String mQiNiuToken;

    @Override
    public int layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public ModifyUserInfoContract.Presenter getPresenter() {
        return new ModifyUserInfoPresenter();
    }

    @Override
    protected void initView() {
        setTitleBack(R.string.personal_info);
        ActivityStackUtils.pressActivity(Config.Tags.TAG_MODIFY_USER_MOBILE, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading();
        mPresenter.getUserInfo();
//        tvNickName.setText(MyApplication.getAppContext().getUser().getName());
    }

    @OnClick(R2.id.civ_header_pic)
    public void onCivHeaderPicClicked() {
        showChoosePop();
    }

    @OnClick(R2.id.tv_nick_name)
    public void onTvNickNameClicked() {
        startActivityForResult(ModifyNickNameActivity.class, null, Values.REQUEST_MODIFY_NAME);
    }

    @OnClick(R2.id.tv_mobile_number)
    public void onTvMobileNumberClicked() {
        startActivity(ModifyMobileActivity.class);
    }

    @OnClick(R2.id.tv_modify_password)
    public void onTvModifyPasswordClicked() {
        // TODO: 2019/3/14 修改界面以兼容修改和重置
        startActivity(SetPasswordActivity.class);
    }

    @Override
    public void onLoadData(SUserCover resultData) {
        hideLoading();
        if (resultData.getUser() != null) {
            userInfo = resultData.getUser();
            userInfo.setToken(MyApplication.getAppContext().getUser().getToken());
            userInfo.setNodeSize(resultData.getNodeSize());
            MyApplication.getAppContext().savedUser(userInfo);
            tvNickName.setText(Check.hasContent(userInfo.getName()) ? userInfo.getName() : "用户" + userInfo.getMobile().substring(userInfo.getMobile().length() - 4));
            tvMobileNumber.setText(userInfo.getMobile());
            GlideApp.with(mRootView)
                    .load(userInfo.getAvatar())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_me_header)
                    .error(R.mipmap.ic_me_header)
                    .circleCrop()
                    .into(civHeaderPic);
        }
    }

    /**
     * 弹出获取图片方式弹窗
     */
    private void showChoosePop() {
        if (choosePicTypePop == null) {
            choosePicTypePop = new TChoosePicTypePop(this);
            choosePicTypePop.setOnChooseTypeList(new TChoosePicTypePop.OnChooseTypeListener() {
                @Override
                public void onCancelClick() {
                    dismiss(choosePicTypePop);
                }

                @Override
                public void onLeftClick() {
                    dismiss(choosePicTypePop);
                    gotoCamera();
                }

                @Override
                public void onRightClick() {
                    dismiss(choosePicTypePop);
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
                        picPath = FileUtils.createNewFileName();
                        TLog.i(picPath);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.createUriFromPath(picPath));
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
                TLog.i("result -->>> " + picPath);
                civHeaderPic.setImageBitmap(BitmapUtils.readBitmapAutoSize(picPath, 512, 512));
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
                        TLog.i("result -->>> " + picPath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        dealImage();
    }

    private void dealImage() {
        TLog.i("dealImage -->>> " + picPath);
        PermissionUtils.permission(PermissionConstants.STORAGE)
                .callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        preUploadImage();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtils.showShort(R.string.refuse_storage_permission_error);
                    }
                }).request();
    }

    private void preUploadImage() {
        showLoading(R.string.upload_ing);
        if (!Check.hasContent(mQiNiuToken = CacheDiskUtils.getInstance(MyApplication.getAppContext().getUser().getMobile())
                .getString(Keys.QI_NIU_TOKEN, ""))) {
            TLog.i("dealImage2 -->>> " + picPath);
            mPresenter.getQiNiuToken();
        } else {
            TLog.i("dealImage3 -->>> " + picPath);
            uploadImage();
        }
    }

    @Override
    public void onQiNiuTokenResponse(QiNiuUserBean result) {
        mQiNiuToken = result.getToken();
        CacheDiskUtils.getInstance(MyApplication.getAppContext().getUser().getMobile())
                .put(Keys.QI_NIU_TOKEN, mQiNiuToken, 60 * 3);
        uploadImage();
    }

    @Override
    public void onModifyUserInfo() {
        hideLoading();
        MyApplication.getAppContext().setNeedUpdateUserInfo(true);
        civHeaderPic.setImageBitmap(BitmapUtils.readBitmapAutoSize(picPath, 720, 960));
        ToastUtils.showShort(R.string.user_info_update_success);
    }

    private void uploadImage() {
        TLog.i(picPath);
        if (Check.hasContent(picPath)) {
            File file = new File(picPath);
            String name = file.getName();
            File newFile = new CompressHelper.Builder(this)
                    .setMaxWidth(720)  // 默认最大宽度为720
                    .setMaxHeight(960) // 默认最大高度为960
                    .setQuality(80)    // 默认压缩质量为80
                    .setFileName(name) // 设置你需要修改的文件名
                    .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                    .setDestinationDirectoryPath(FileUtils.getPublicDiskSafe())
                    .build()
                    .compressToFile(file);
            ImageUploadManager.getInstance().put(newFile, newFile.getName(), mQiNiuToken, (key, info, response) -> {
                if (!info.isOK()) {
                    hideLoading();
                    ToastUtils.showShort(R.string.upload_error);
                } else {
                    mPresenter.modifyUserInfo(null, newFile.getName());
                }
            }, null);
        }
    }

    @Override
    public void onLoadFail(BaseResponse resultData, String resultMsg, int resultCode) {
        super.onLoadFail(resultData, resultMsg, resultCode);
    }

    @Override
    protected void onDestroy() {
        dismiss(choosePicTypePop);
        ActivityStackUtils.popActivity(Config.Tags.TAG_MODIFY_USER_MOBILE, this);
        super.onDestroy();
    }
}
