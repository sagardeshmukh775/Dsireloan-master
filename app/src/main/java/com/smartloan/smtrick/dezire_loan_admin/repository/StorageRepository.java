package com.smartloan.smtrick.dezire_loan_admin.repository;

import android.content.Context;
import android.widget.ImageView;

import com.google.firebase.storage.StorageReference;

public interface StorageRepository {
    void loadImage(final Context context, final StorageReference storageReference,int placeHolerId,final ImageView imageView);
}
