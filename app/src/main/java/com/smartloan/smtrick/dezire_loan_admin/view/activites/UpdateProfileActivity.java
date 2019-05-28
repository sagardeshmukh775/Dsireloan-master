package com.smartloan.smtrick.dezire_loan_admin.view.activites;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.databinding.ActivityUpdateProfileBinding;
import com.smartloan.smtrick.dezire_loan_admin.exception.ExceptionUtil;
import com.smartloan.smtrick.dezire_loan_admin.firebasestorage.StorageService;
import com.smartloan.smtrick.dezire_loan_admin.models.User;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.repository.UserRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.UserRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.service.ImageCompressionService;
import com.smartloan.smtrick.dezire_loan_admin.service.impl.ImageCompressionServiceImp;
import com.smartloan.smtrick.dezire_loan_admin.singleton.AppSingleton;
import com.smartloan.smtrick.dezire_loan_admin.utilities.FileUtils;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;
import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;

import java.io.InputStream;
import java.util.HashMap;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.RESULT_CODE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.USER_PROFILE_PATH;

public class UpdateProfileActivity extends AppCompatActivity {
    ActivityUpdateProfileBinding activityUpdateProfileBinding;
    AppSharedPreference appSharedPreference;
    private ProgressDialogClass progressDialogClass;
    private Uri profileUri;
    Bitmap bitmap;
    UserRepository userRepository;
    private String profileImage = "";
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile);
        appSharedPreference = new AppSharedPreference(this);
        progressDialogClass = new ProgressDialogClass(this);
        userRepository = new UserRepositoryImpl(this);
        setToolBar();
        readUserData(appSharedPreference.getUserId());
//        setProfileData();
        setUpdateClickListner();
        onClickSelectProfile();
        onClickCancelProfile();
    }//end of activity

    public void setToolBar() {
        Toolbar tb = activityUpdateProfileBinding.toolbar;
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            ab.setTitle(appSharedPreference.getAgeniId());
            ab.setDisplayHomeAsUpEnabled(true);
        }
        tb.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setProfileData() {


            try {

                if (appSharedPreference.getUserName() != null) {
                    activityUpdateProfileBinding.edittextname.setText(appSharedPreference.getUserName());
                }
                if (appSharedPreference.getAddress() != null) {
                    activityUpdateProfileBinding.edittextaddress.setText(appSharedPreference.getAddress());
                }
                if (appSharedPreference.getMobileNo() != null) {
                    activityUpdateProfileBinding.edittextmobile.setText(appSharedPreference.getMobileNo());
                }
                if (appSharedPreference.getEmaiId() != null) {
                    activityUpdateProfileBinding.edittextemailid.setText(appSharedPreference.getEmaiId());
                }
                if (user.getAltmobileno() != null) {
                    activityUpdateProfileBinding.edittextaltmobilenumber.setText(user.getAltmobileno());
                }
                if (user.getFather() != null) {
                    activityUpdateProfileBinding.edittextfathersname.setText(user.getFather());
                }
                if (user.getCity() != null) {
                    activityUpdateProfileBinding.edittexcity.setText(user.getCity());
                }
                if (user.getState() != null) {
                    activityUpdateProfileBinding.edittextstate.setText(user.getState());
                }
                if (user.getState() != null) {
                    activityUpdateProfileBinding.edittextbirthdate.setText(user.getBirthdate());
                }
                if (user.getPincode() != null) {
                    activityUpdateProfileBinding.edittextpincode.setText(user.getPincode());
                }
                if (user.getAccountname() != null) {
                    activityUpdateProfileBinding.edittextaccountname.setText(user.getAccountname());
                }
                if (user.getBank() != null) {
                    activityUpdateProfileBinding.edittextbank.setText(user.getBank());
                }
                if (user.getAccounttype() != null) {
                    activityUpdateProfileBinding.edittextaccounttype.setText(user.getAccounttype());
                }
                if (user.getAccountno() != null) {
                    activityUpdateProfileBinding.edittextaccountnumber.setText(user.getAccountno());
                }
                if (user.getPan() != null) {
                    activityUpdateProfileBinding.edittextpan.setText(user.getPan());
                }
                if (user.getBranch() != null) {
                    activityUpdateProfileBinding.edittextbranch.setText(user.getBranch());
                }
                if (user.getIfsc() != null) {
                    activityUpdateProfileBinding.edittextifsc.setText(user.getIfsc());

                    // profileImage = appSharedPreference.getProfileLargeImage();
                }

                if (!Utility.isEmptyOrNull(appSharedPreference.getProfileLargeImage())) {
                    Picasso.with(this).load(appSharedPreference.getProfileLargeImage()).resize(200, 200).centerCrop().placeholder(R.drawable.dummy_user_profile).into(activityUpdateProfileBinding.ivProfile);
                    activityUpdateProfileBinding.ivCancelProfile.setVisibility(View.VISIBLE);
                } else
                    activityUpdateProfileBinding.ivProfile.setImageResource(R.drawable.dummy_user_profile);
            } catch (Exception e) {
            }
    }

    private void setUpdateClickListner() {
        activityUpdateProfileBinding.buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndCreateUser();
            }
        });
    }

    private void validateAndCreateUser() {
        User user = fillUserModel();
        if (validate(user))
            updateUser(user);
    }

    private User fillUserModel() {
        User user = new User();
        user.setUserName(activityUpdateProfileBinding.edittextname.getText().toString());
        user.setMobileNumber(activityUpdateProfileBinding.edittextmobile.getText().toString());
        user.setAddress(activityUpdateProfileBinding.edittextaddress.getText().toString());
        user.setEmail(activityUpdateProfileBinding.edittextemailid.getText().toString());
        user.setAltmobileno(activityUpdateProfileBinding.edittextaltmobilenumber.getText().toString());
        user.setFather(activityUpdateProfileBinding.edittextfathersname.getText().toString());
        user.setCity(activityUpdateProfileBinding.edittexcity.getText().toString());
        user.setState(activityUpdateProfileBinding.edittextstate.getText().toString());
        user.setBirthdate(activityUpdateProfileBinding.edittextbirthdate.getText().toString());
        user.setPincode(activityUpdateProfileBinding.edittextpincode.getText().toString());
        user.setAccountname(activityUpdateProfileBinding.edittextaccountname.getText().toString());
        user.setBank(activityUpdateProfileBinding.edittextbank.getText().toString());
        user.setAccounttype(activityUpdateProfileBinding.edittextaccounttype.getText().toString());
        user.setAccountno(activityUpdateProfileBinding.edittextaccountnumber.getText().toString());
        user.setPan(activityUpdateProfileBinding.edittextpan.getText().toString());
        user.setBranch(activityUpdateProfileBinding.edittextbranch.getText().toString());
        user.setIfsc(activityUpdateProfileBinding.edittextifsc.getText().toString());

        // user.setUserProfileImageLarge(profileImage);
        //user.setUserProfileImageSmall(profileImage);

        return user;
    }

    private boolean validate(User user) {
        String validationMessage;
        boolean isValid = true;
        try {
            if (Utility.isEmptyOrNull(user.getUserName())) {
                validationMessage = getString(R.string.PLEASE_ENTER_NAME);
                activityUpdateProfileBinding.edittextname.setError(validationMessage);
                isValid = false;
            }
            if (Utility.isEmptyOrNull(user.getMobileNumber())) {
                validationMessage = getString(R.string.MOBILE_NUMBER_SHOULD_NOT_BE_EMPTY);
                activityUpdateProfileBinding.edittextmobile.setError(validationMessage);
                isValid = false;
            } else if (!Utility.isValidMobileNumber(user.getMobileNumber())) {
                validationMessage = getMessage(R.string.INVALID_MOBILE_NUMBER);
                activityUpdateProfileBinding.edittextmobile.setError(validationMessage);
                isValid = false;
            }
        } catch (Exception e) {
            isValid = false;
            ExceptionUtil.logException(e);
        }
        return isValid;
    }

    private String getMessage(int id) {
        return getString(id);
    }

    private void updateUser(final User user) {
        if (profileUri != null && bitmap != null) {
            uploadImage(bitmap, USER_PROFILE_PATH);
        }
        progressDialogClass.showDialog(getMessage(R.string.loading), getMessage(R.string.please_wait));
        userRepository.updateUser(appSharedPreference.getUserId(), user.getUpdateUserMap(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                addUserDataToPreferences(user);
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
                Utility.showMessage(getApplicationContext(), getMessage(R.string.data_updation_fails_message));
            }
        });
    }

    private void addUserDataToPreferences(User user) {
        appSharedPreference.addUserDetails(user);
        setResult(RESULT_CODE);
        finish();
    }

    private void onClickSelectProfile() {
        activityUpdateProfileBinding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startCropImageActivity();
            }
        });
    }

    private void onClickCancelProfile() {
        activityUpdateProfileBinding.ivCancelProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityUpdateProfileBinding.ivProfile.setImageResource(R.drawable.dummy_user_profile);
                profileUri = null;
                bitmap = null;
                profileImage = "";
                activityUpdateProfileBinding.ivCancelProfile.setVisibility(View.GONE);
            }
        });
    }

    //Start crop image activity for the given image.
//    private void startCropImageActivity() {
//        try {
//            CropImage.activity(null)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setMultiTouchEnabled(true)
//                    .start(this);
//        } catch (Exception e) {
//            ExceptionUtil.logException(e);
//        }
//    }
//
//    public void onActivityResult(int requestCode, int resultCode,
//                                 Intent imageData) {
//        super.onActivityResult(requestCode, resultCode, imageData);
//        try {
//            switch (requestCode) {
//                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
//                    CropImage.ActivityResult result = CropImage.getActivityResult(imageData);
//                    if (resultCode == RESULT_OK) {
//                        if (imageData != null) {
//                            Bundle extras = imageData.getExtras();
//                            if (extras != null) {
//                                Bitmap bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
//                                profileUri = result.getUri();
//                                activityUpdateProfileBinding.ivCancelProfile.setVisibility(View.VISIBLE);
//                                if (bitmapImg != null)
//                                    activityUpdateProfileBinding.ivProfile.setImageBitmap(bitmapImg);
//                                compressBitmap(profileUri);
//                            }
//                        }
//                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                        Utility.showMessage(this, "Cropping failed: " + result.getError());
//                    }
//                    break;
//            }
//        } catch (Exception e) {
//            ExceptionUtil.logException(e);
//        }
//    }

    private void compressBitmap(Uri uri) {
        String path = FileUtils.getPath(this, uri);
        ImageCompressionService imageCompressionService = new ImageCompressionServiceImp();
        imageCompressionService.compressImage(path, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    bitmap = (Bitmap) object;
                }
            }

            @Override
            public void onError(Object object) {
            }
        });
    }

    void uploadImage(Bitmap bitmap, String storagePath) {
        try {
            AppSingleton.getInstance(this).setNotificationManager();
            InputStream imageInputStream = Utility.returnInputStreamFromBitmap(bitmap);
            StorageService.uploadImageStreamToFirebaseStorage(imageInputStream, storagePath, new CallBack() {
                public void onSuccess(Object object) {
                    if (object != null) {
                        String downloadUrlLarge = (String) object;
                        try {
                            appSharedPreference.setUserProfileImages(downloadUrlLarge);
                            Intent broadcastIntent = new Intent();
                            broadcastIntent.setAction(MainActivity.ImageUploadReceiver.PROCESS_RESPONSE);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } catch (Exception e) {
                            ExceptionUtil.logException(e);
                        }
                        updateUserData(downloadUrlLarge);
                    }
                }

                public void onError(Object object) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUserData(final String downloadUrlLarge) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userProfileImageLarge", downloadUrlLarge);
        map.put("userProfileImageSmall", downloadUrlLarge);
        userRepository.updateUser(appSharedPreference.getUserId(), map, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                AppSingleton.getInstance(UpdateProfileActivity.this).updateProgress(1, 1, 100);
            }

            @Override
            public void onError(Object object) {
                Utility.showMessage(getApplicationContext(), getMessage(R.string.data_updation_fails_message));
            }
        });
    }

    private void readUserData(final String userId) {
        userRepository.readUserData(userId, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                     user = (User) object;
                    setProfileData();

                } else {

                }

            }

            @Override
            public void onError(Object object) {

            }
        });
    }


}//rnd of activity
