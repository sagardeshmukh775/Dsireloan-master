package com.smartloan.smtrick.dezire_loan_admin.service;


import com.smartloan.smtrick.dezire_loan_admin.callback.CallBack;

public interface ImageCompressionService {
    void compressImage(String ImagePath, CallBack callBack);
}
