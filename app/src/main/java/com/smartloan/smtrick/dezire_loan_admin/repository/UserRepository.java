package com.smartloan.smtrick.dezire_loan_admin.repository;

import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;
import com.smartloan.smtrick.dezire_loan_admin.models.User;

import java.util.Map;

public interface UserRepository {

    void signIn(final String mobileNumber, final String password, final CallBack callback);

    void readLoggedInUser(final CallBack callback);

    void readUser(final String userId, final CallBack callback);

    void readUserByMobileNumber(final String mobileNumber, final CallBack callBack);

    void notifyReadUserByMobileNumber(final String mobileNumber, final CallBack callBack);

    void changePassword(final String newPassword, final CallBack callback);

  //  void createUser(final User userModel, final CallBack callback);

    void createUserData(final User userModel, final CallBack callback);

    void updateUser(final User userModel, final CallBack callback);

    void deleteUser(final String userId, final CallBack callback);

    void updateUser(final String regId, final Map userMap, final CallBack callback);

    void updateLeed(final String leedId, final Map leedsMap, final CallBack callBack);

    void readUserByUserId(final String regId, final CallBack callBack);

    void readUserData(final String id, final CallBack callBack);

    void setDataChangeListnerOnUserLogin(final String regId, final CallBack callback);
}