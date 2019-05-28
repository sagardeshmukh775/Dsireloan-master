package com.smartloan.smtrick.dezire_loan_admin.view.fragements;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.constants.Constant;
import com.smartloan.smtrick.dezire_loan_admin.databinding.FragmentGenerateleadBinding;
import com.smartloan.smtrick.dezire_loan_admin.exception.ExceptionUtil;
import com.smartloan.smtrick.dezire_loan_admin.helper.RuntimePermissionHelper;
import com.smartloan.smtrick.dezire_loan_admin.interfaces.OnFragmentInteractionListener;
import com.smartloan.smtrick.dezire_loan_admin.models.LeedsModel;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.repository.LeedRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.LeedRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.service.ImageUploadIntentService;
import com.smartloan.smtrick.dezire_loan_admin.singleton.AppSingleton;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.adapters.ViewImageAdapter;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.util.ArrayList;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.LEEDS_TABLE_REF;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.LEED_PREFIX;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_GENERATED;

public class Fragment_Generate_doctorloan extends RuntimePermissionHelper implements AdapterView.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;
    FragmentGenerateleadBinding fragmentGenerateleadBinding;
    AppSharedPreference appSharedPreference;
    LeedRepository leedRepository;
    AppSingleton appSingleton;
    ProgressDialogClass progressDialogClass;
    ArrayList<Uri> imagesUriList;
    Context context;
    static String value;
    ViewImageAdapter viewImageAdapter;
    private Uri profileUri;
    private static final int REQUEST_PERMISSIONS = 7000;

    public Fragment_Generate_doctorloan() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentGenerateleadBinding == null) {
            fragmentGenerateleadBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_generatelead, container, false);
            if (mListener != null) {

               // String strtext = getArguments().getString("edttext");
                mListener.onFragmentInteraction("New Lead");
            }
            context = getActivity();

            progressDialogClass = new ProgressDialogClass(getActivity());
            appSingleton = AppSingleton.getInstance(getActivity());
            leedRepository = new LeedRepositoryImpl();
            appSharedPreference = new AppSharedPreference(getActivity());
            String[] loanType = appSingleton.getLoanType();
            String[] empType = appSingleton.getEmployeeType();
            //Toast.makeText(getActivity(), value+" ", Toast.LENGTH_LONG).show();//show data in tost

           /* fragmentGenerateleadBinding.rvDocumentImages.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.HORIZONTAL));*/
            onClickGenerateLead();
            //onClickCallExpart();
            //onClickAttachDocuments();
           // onClickSelectProfile();
            //onClickCancelProfile();
        }
        return fragmentGenerateleadBinding.getRoot();
    }//end of onCreateView






    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // NOTE: This is the part that usually gives you the error
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void onClickGenerateLead() {
        fragmentGenerateleadBinding.buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateLeed();
            }
        });
    }


    private void generateLeed() {
        LeedsModel leedsModel = fillUserModel();
        if (validate(leedsModel)) {
            progressDialogClass.showDialog(this.getString(R.string.leed_In_loading), this.getString(R.string.PLEASE_WAIT));
            leedRepository.createLeed(leedsModel, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    // if (imagesUriList == null || imagesUriList.isEmpty())
                    OnSuccessResult();
                }

                @Override
                public void onError(Object object) {
                    if (progressDialogClass != null)
                        progressDialogClass.dismissDialog();
                    Utility.showLongMessage(getActivity(), getString(R.string.lead_generated_fails_message));
                }
            });
            uploadImages(leedsModel.getLeedId());
        }
    }

    private void OnSuccessResult() {
        Utility.showLongMessage(getActivity(), getString(R.string.lead_generated_success_message));
        mListener.changeFragement(new InvoicesTabFragment());
        progressDialogClass.dismissDialog();
    }

    private void uploadImages(String leedId) {
        try {
            if (profileUri != null) {
                if (imagesUriList == null)
                    imagesUriList = new ArrayList<>();
                imagesUriList.add(profileUri);
            }
            if (imagesUriList != null && !imagesUriList.isEmpty()) {
                int count = 0;
                for (Uri uri : imagesUriList) {
                    count += 1;
                    Intent intentToUpload = new Intent(getActivity(), ImageUploadIntentService.class);
                    intentToUpload.putExtra(Constant.BITMAP_IMG, uri);
                    if (profileUri != null && imagesUriList.size() == count)
                        intentToUpload.putExtra(Constant.STORAGE_PATH, Constant.CUSROMER_PROFILE_PATH);
                    else
                        intentToUpload.putExtra(Constant.STORAGE_PATH, Constant.DOCUMENTS_PATH);
                    intentToUpload.putExtra(Constant.LEED_ID, leedId);
                    intentToUpload.putExtra(Constant.IMAGE_COUNT, count);
                    intentToUpload.putExtra(Constant.TOTAL_IMAGE_COUNT, imagesUriList.size());
                    context.startService(intentToUpload);
                }
            }
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    private boolean validate(LeedsModel leedsModel) {
        String validationMessage;
        boolean isValid = true;
        try {
            if (Utility.isEmptyOrNull(leedsModel.getCustomerName())) {
                validationMessage = getString(R.string.customer_name_should_be_empty);
                fragmentGenerateleadBinding.edittextname.setError(validationMessage);
                isValid = false;
            }
            if (!Utility.isValidMobileNumber(leedsModel.getMobileNumber())) {
                validationMessage = getString(R.string.INVALID_MOBILE_NUMBER);
                fragmentGenerateleadBinding.edittextmobile.setError(validationMessage);
                isValid = false;
            }


        } catch (Exception e) {
            isValid = false;
            ExceptionUtil.logException(e);
        }
        return isValid;
    }


    private LeedsModel fillUserModel() {
        LeedsModel leedsModel = new LeedsModel();
        leedsModel.setLeedId(LEEDS_TABLE_REF.push().getKey());
        leedsModel.setCustomerName(fragmentGenerateleadBinding.edittextname.getText().toString());
        leedsModel.setMobileNumber(fragmentGenerateleadBinding.edittextmobile.getText().toString());
        leedsModel.setAltmobile(fragmentGenerateleadBinding.etAlternetMobile.getText().toString());
        leedsModel.setAddress(fragmentGenerateleadBinding.edittextaddress.getText().toString());
        leedsModel.setLoanType("Doctor Loan");
        leedsModel.setLeedNumber(Utility.generateAgentId(LEED_PREFIX));
        leedsModel.setAgentId(appSharedPreference.getAgeniId());
       // leedsModel.setAgentId(appSharedPreference.getUserId());
        leedsModel.setAgentName(appSharedPreference.getUserName());
        leedsModel.setCreatedBy(appSharedPreference.getUserId());
        leedsModel.setStatus(STATUS_GENERATED);
       // History history = new History();
        //history.setStatus(STATUS_GENERATED);
       // history.setUpdatedByName(appSharedPreference.getUserName());
       // history.setUpdatedbyId(appSharedPreference.getAgeniId());
       // Map<String, History> historyMap = new HashMap<>();
      // historyMap.put(LEEDS_TABLE_REF.push().getKey(), history);
       // leedsModel.setHistory(historyMap);
        return leedsModel;
    }

    private void setReceiver() {

    }

    @Override
    public void onStart() {
        setReceiver();
        super.onStart();
    }






    @Override
    public void onPermissionsGranted(final int requestCode) {
        if (checkPermissionGranted(Manifest.permission.CAMERA, getActivity()) && requestCode == REQUEST_PERMISSIONS) {
        }
    }
}
