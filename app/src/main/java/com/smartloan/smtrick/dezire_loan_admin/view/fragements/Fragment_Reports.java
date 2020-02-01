package com.smartloan.smtrick.dezire_loan_admin.view.fragements;

import android.app.DatePickerDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartloan.smtrick.dezire_loan_admin.R;
import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.databinding.FragmentReportBinding;
import com.smartloan.smtrick.dezire_loan_admin.exception.ExceptionUtil;
import com.smartloan.smtrick.dezire_loan_admin.interfaces.OnFragmentInteractionListener;
import com.smartloan.smtrick.dezire_loan_admin.models.LeedsModel;
import com.smartloan.smtrick.dezire_loan_admin.preferences.AppSharedPreference;
import com.smartloan.smtrick.dezire_loan_admin.recyclerListener.RecyclerTouchListener;
import com.smartloan.smtrick.dezire_loan_admin.repository.LeedRepository;
import com.smartloan.smtrick.dezire_loan_admin.repository.impl.LeedRepositoryImpl;
import com.smartloan.smtrick.dezire_loan_admin.singleton.AppSingleton;
import com.smartloan.smtrick.dezire_loan_admin.utilities.Utility;
import com.smartloan.smtrick.dezire_loan_admin.view.adapters.ReportLeedsAdapter;
import com.smartloan.smtrick.dezire_loan_admin.view.dialog.ProgressDialogClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.CALANDER_DATE_FORMATE;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_APPROVED;
import static com.smartloan.smtrick.dezire_loan_admin.constants.Constant.STATUS_REJECTED;

public class Fragment_Reports extends Fragment {
    ReportLeedsAdapter reportLeedsAdapter;
    LeedRepository leedRepository;
    AppSingleton appSingleton;
    ProgressDialogClass progressDialogClass;
    AppSharedPreference appSharedPreference;
    FragmentReportBinding fragmentReportBinding;
    int fromYear, fromMonth, fromDay;
    int toYear, toMonth, toDay;
    long fromDate, toDate;
    ArrayList<LeedsModel> leedsModelArrayList;
    private OnFragmentInteractionListener mListener;

    EditText etd_search_box;
    DatabaseReference databaseReference;

    public Fragment_Reports() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentReportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false);
        if (mListener != null) {
            mListener.onFragmentInteraction("Reports");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();


        progressDialogClass = new ProgressDialogClass(getActivity());
        appSingleton = AppSingleton.getInstance(getActivity());
        leedRepository = new LeedRepositoryImpl();
        appSharedPreference = new AppSharedPreference(getActivity());
        fragmentReportBinding.recyclerViewLeeds.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fragmentReportBinding.recyclerViewLeeds.setLayoutManager(layoutManager);
        fragmentReportBinding.recyclerViewLeeds.setItemAnimator(new DefaultItemAnimator());
        fragmentReportBinding.recyclerViewLeeds.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        getteLeed();
        setFromDateClickListner();
        setToDateClickListner();

        fragmentReportBinding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                     * Clear the list when editText is empty
                     * */
                    leedsModelArrayList.clear();
                    fragmentReportBinding.recyclerViewLeeds.removeAllViews();
                }

            }
        });
        return fragmentReportBinding.getRoot();
    }

    private void setAdapter(final String toString) {

        databaseReference.child("leeds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                leedsModelArrayList.clear();
                fragmentReportBinding.recyclerViewLeeds.removeAllViews();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    LeedsModel leedsModel = snapshot.getValue(LeedsModel.class);

                    if (leedsModel.getBankName() != null && leedsModel.getAgentName() != null) {
                        if (leedsModel.getBankName().toLowerCase().contains(toString)) {

//                        LeedsModel leedsModel = snapshot.getValue(LeedsModel.class);
                            leedsModelArrayList.add(leedsModel);
                        } else if (leedsModel.getAgentName().toLowerCase().contains(toString)) {
//                        LeedsModel leedsModel = snapshot.getValue(LeedsModel.class);
                            leedsModelArrayList.add(leedsModel);

                        }
                    }

                }

                serAdapter(leedsModelArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void onClickListner() {
        fragmentReportBinding.recyclerViewLeeds.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), fragmentReportBinding.recyclerViewLeeds, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //startActivity(new Intent(getActivity(), AccountantUpdateLeedsActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    private void getteLeed() {
        progressDialogClass.showDialog(this.getString(R.string.loading), this.getString(R.string.PLEASE_WAIT));
        leedRepository.readLeedsByUserIdReport(getActivity(), appSharedPreference.getUserId(), new CallBack() {
            @Override
            public void onSuccess(Object object) {
                if (object != null) {
                    leedsModelArrayList = (ArrayList<LeedsModel>) object;
                    filterByDate(leedsModelArrayList);
                    filterbyStatus(leedsModelArrayList);
                }
                progressDialogClass.dismissDialog();
            }

            @Override
            public void onError(Object object) {
                progressDialogClass.dismissDialog();
                Utility.showLongMessage(getActivity(), getString(R.string.server_error));
            }
        });
    }

    private void serAdapter(ArrayList<LeedsModel> leedsModels) {
        setReports(leedsModels);
        if (leedsModels != null) {
            if (reportLeedsAdapter == null) {
                reportLeedsAdapter = new ReportLeedsAdapter(getActivity(), leedsModels);
                fragmentReportBinding.recyclerViewLeeds.setAdapter(reportLeedsAdapter);
                onClickListner();
            } else {
                ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
                leedsModelArrayList.addAll(leedsModels);
                reportLeedsAdapter.reload(leedsModelArrayList);
            }
        }
    }

    private void setReports(ArrayList<LeedsModel> leedsModelArrayList) {
        int approvedCount = 0, rejectedCount = 0;
        double totalPayout = 0;
        if (leedsModelArrayList != null) {
            for (LeedsModel leedsModel :
                    leedsModelArrayList) {
                if (!Utility.isEmptyOrNull(leedsModel.getStatus())) {
                    if (leedsModel.getStatus().equalsIgnoreCase(STATUS_APPROVED)) {
                        approvedCount++;
                        if (!Utility.isEmptyOrNull(leedsModel.getPayout()))
                            totalPayout += Double.valueOf(leedsModel.getPayout());
                    } else if (leedsModel.getStatus().equalsIgnoreCase(STATUS_REJECTED)) {
                        rejectedCount++;
                    }
                }
            }//end of for
            fragmentReportBinding.textViewTotalLeadsCount.setText(String.valueOf(leedsModelArrayList.size()));
            fragmentReportBinding.textViewApprovedLeadsCount.setText(String.valueOf(approvedCount));
            fragmentReportBinding.textViewRejectedLeadsCount.setText(String.valueOf(rejectedCount));
            // fragmentReportBinding.textViewPayoutAmount.setText(String.valueOf(totalPayout));
        } else {
            fragmentReportBinding.textViewTotalLeadsCount.setText("0");
            fragmentReportBinding.textViewApprovedLeadsCount.setText("0");
            fragmentReportBinding.textViewRejectedLeadsCount.setText("0");
            //fragmentReportBinding.textViewPayoutAmount.setText("0.0");
        }
    }

    private void filterbyStatus(ArrayList<LeedsModel> leedsModelArrayList) {
        try {
            ArrayList<LeedsModel> filterArrayList = new ArrayList<>();
            if (leedsModelArrayList != null) {
                for (LeedsModel leedsModel : leedsModelArrayList) {
                    if (leedsModel.getStatus().equalsIgnoreCase(STATUS_APPROVED)) {
                        filterArrayList.add(leedsModel);
                    }
                }
                int approved_amount = 0;
                for (int i = 0; i < filterArrayList.size(); i++) {
                    approved_amount = approved_amount + Integer.parseInt(filterArrayList.get(i).getDissbussloan());
                }
                fragmentReportBinding.textViewTotalAmountValue.setText(Integer.toString(approved_amount));
            }

        } catch (Exception e) {

        }
    }

    private void filterByDate(ArrayList<LeedsModel> leedsModelArrayList) {
        try {
            ArrayList<LeedsModel> filterArrayList = new ArrayList<>();
            if (leedsModelArrayList != null) {
                if (fromDate > 0) {
                    for (LeedsModel leedsModel : leedsModelArrayList) {
                        if (leedsModel.getCreatedDateTimeLong() >= fromDate && leedsModel.getCreatedDateTimeLong() <= toDate) {
                            filterArrayList.add(leedsModel);
                        }
                    }
                } else {
                    for (LeedsModel leedsModel : leedsModelArrayList) {
                        if (leedsModel.getCreatedDateTimeLong() <= toDate) {
                            filterArrayList.add(leedsModel);
                        }
                    }
                }
            }
            serAdapter(filterArrayList);
        } catch (Exception e) {
            ExceptionUtil.logException(e);
        }
    }

    private void setFromCurrentDate() {
        Calendar mcurrentDate = Calendar.getInstance();
        fromYear = mcurrentDate.get(Calendar.YEAR);
        fromMonth = mcurrentDate.get(Calendar.MONTH);
        fromDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
    }

    private void setFromDateClickListner() {
        setFromCurrentDate();
        fragmentReportBinding.edittextfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat(CALANDER_DATE_FORMATE, Locale.FRANCE);
                        String formatedDate = sdf.format(myCalendar.getTime());
                        fragmentReportBinding.edittextfromdate.setText(formatedDate);
                        fromDay = selectedday;
                        fromMonth = selectedmonth;
                        fromYear = selectedyear;
                        fromDate = Utility.convertFormatedDateToMilliSeconds(formatedDate, CALANDER_DATE_FORMATE);
                        filterByDate(leedsModelArrayList);
                    }
                }, fromYear, fromMonth, fromDay);
                mDatePicker.show();
            }
        });
    }

    private void setToCurrentDate() {
        toDate = System.currentTimeMillis();
        Calendar mcurrentDate = Calendar.getInstance();
        toYear = mcurrentDate.get(Calendar.YEAR);
        toMonth = mcurrentDate.get(Calendar.MONTH);
        toDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
    }

    private void setToDateClickListner() {
        setToCurrentDate();
        fragmentReportBinding.edittexttodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        SimpleDateFormat sdf = new SimpleDateFormat(CALANDER_DATE_FORMATE, Locale.FRANCE);
                        fragmentReportBinding.edittexttodate.setText(sdf.format(myCalendar.getTime()));
                        toDay = selectedday;
                        toMonth = selectedmonth;
                        toYear = selectedyear;
                        toDate = myCalendar.getTimeInMillis();
                        filterByDate(leedsModelArrayList);
                    }
                }, toYear, toMonth, toDay);
                mDatePicker.show();
            }
        });
    }

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
}
