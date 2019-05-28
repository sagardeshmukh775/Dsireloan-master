package com.smartloan.smtrick.dezire_loan_admin.repository;

import android.content.Context;

import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.models.LeedsModel;

import java.util.Map;

public interface LeedRepository {

    void readAllLeeds(final CallBack callback);

    void readLeedsByUserIdReport(final Context context, final String userId, final CallBack callBack);

    //void readLeedsByUserIdReport(Context context, String userId, CallBack callBack);

    void createLeed(final LeedsModel leedsModel, final CallBack callback);

    void readLeedsByStatus(final String status, final CallBack callBack);

    void deleteLeed(final String leedId, final CallBack callback);

    void updateLeed(final String leedId, final Map leedMap, final CallBack callback);

    void readLeedByLeedId(final String leedId, final CallBack callBack);

    void updateLeedDocuments(final String leedId, final Map leedMap, final CallBack callback);

    void updateLeedHistory(final String leedId, final Map leedMap, final CallBack callback);
}
