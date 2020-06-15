package com.faceunity.pta_art.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.faceunity.pta_art.R;
import com.faceunity.pta_art.constant.FUPTAClient;
import com.faceunity.pta_art.entity.AvatarPTA;
import com.faceunity.pta_art.entity.DBHelper;
import com.faceunity.pta_art.evergrande.module.avatar.AvatarManager;
import com.faceunity.pta_art.evergrande.utils.transformers.Transformers;
import com.faceunity.pta_art.fragment.drive.BodyDriveFragment;
import com.faceunity.pta_art.hd.ui.MyFragment;
import com.faceunity.pta_art.utils.ToastUtil;
import com.rxjava.rxlife.RxLife;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by tujh on 2018/8/22.
 */
public class HomeFragment extends BaseFragment
        implements View.OnClickListener {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private CheckBox mTrackBtn;

    private View mGuideView;
    private TextView mVersionText;
    private String text;
    private TextView mTvUpload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        mActivity.initDebug(mVersionText);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mFUP2ARenderer = mActivity.getFUP2ARenderer();
            mP2ACore = mActivity.getP2ACore();
            mAvatarHandle = mActivity.getAvatarHandle();
            mCameraRenderer = mActivity.getCameraRenderer();
            mAvatarP2AS = mActivity.getAvatarP2As();
            mTrackBtn.setChecked(mCameraRenderer.isShowCamera());
        }
    }

    private void initView(View view) {
        mGuideView = view.findViewById(R.id.main_guide_img);
        mGuideView.bringToFront();
        mGuideView.setVisibility(View.VISIBLE);
        mVersionText = view.findViewById(R.id.main_version_text);

//        text = String.format("DigiMe Art v%s\nSDK v%s", BuildConfig.VERSION_NAME, FUPTAClient.getVersion());
//        mVersionText.setText(text);
        Log.e(TAG, "FUPTAClient.Version " + FUPTAClient.getVersion());

        mTvUpload = view.findViewById(R.id.tvUpload);
        mTvUpload.setOnClickListener(view1 -> {
            if (mActivity.getShowAvatarP2A().isSystemDefaultAvatar()) {
                ToastUtil.showCenterToast(getContext(), "预置模型无法上传，请先自定义一个");
                return;
            }
            AvatarManager.INSTANCE.createOrUpdateAvatar(mActivity.getShowAvatarP2A())
                    .compose(Transformers.INSTANCE.asyncAndWaiting())
                    .as(RxLife.asOnMain(getViewLifecycleOwner(), Lifecycle.Event.ON_STOP))
                    .subscribe(new SingleObserver<AvatarPTA>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(AvatarPTA avatarPTA) {
                            updateAvatar(avatarPTA);
                            ToastUtil.showCenterToast(getContext(), "上传成功");
                            Log.d(TAG, "createOrUpdateAvatar onSuccess");
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showCenterToast(getContext(), "上传失败" + e.toString());
                            Log.e(TAG, "createOrUpdateAvatar error", e);
                        }
                    });
        });

        mTrackBtn = view.findViewById(R.id.avatar_track_image_btn);
        mTrackBtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mP2ACore != null && mAvatarHandle != null && mCameraRenderer != null) {
                mP2ACore.setNeedTrackFace(isChecked);
                mAvatarHandle.setAvatar(mActivity.getShowAvatarP2A());
                mCameraRenderer.setShowCamera(isChecked);
                mCameraRenderer.setShowLandmarks(isChecked);
            }
        });

        view.findViewById(R.id.main_avatar_image_btn).setOnClickListener(this);
        view.findViewById(R.id.main_edit_image_btn).setOnClickListener(this);
        view.findViewById(R.id.main_avatar_body_btn).setOnClickListener(this);
        view.findViewById(R.id.main_group_photo_image_btn).setOnClickListener(this);
        view.findViewById(R.id.main_my_btn).setOnClickListener(this);
    }

    private void updateAvatar(AvatarPTA mAvatarP2A) {
        DBHelper dbHelper = new DBHelper(getContext());
        dbHelper.updateHistory(mAvatarP2A);
        mActivity.updateAvatarP2As();
        mActivity.setShowAvatarP2A(mAvatarP2A);
    }

    public void checkGuide() {
        if (mGuideView.getVisibility() == View.VISIBLE) {
            mGuideView.post(new Runnable() {
                @Override
                public void run() {
                    mGuideView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_avatar_image_btn:
                mActivity.showBaseFragment(AvatarFragment.TAG);
                break;
            case R.id.main_edit_image_btn:
                if (mActivity.getShowAvatarP2A().isSystemDefaultAvatar()) {
                    mActivity.showBaseFragment(TakePhotoFragment.TAG);
                } else {
                    mActivity.showBaseFragment(EditFaceFragment.TAG);
                    mTrackBtn.setChecked(false);
                }
                break;
            case R.id.main_avatar_body_btn:
                mActivity.showBaseFragment(BodyDriveFragment.TAG);
                mTrackBtn.setChecked(false);
                break;
            case R.id.main_group_photo_image_btn:
                mActivity.showBaseFragment(GroupPhotoFragment.TAG);
                mTrackBtn.setChecked(false);
                break;
            case R.id.main_my_btn:
                mActivity.showBaseFragment(MyFragment.Companion.getTAG());
                mTrackBtn.setChecked(false);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        mP2ACore.setNextHomeAnimationPosition();
        return super.onSingleTapUp(e);
    }
}
