package com.onedata.sunexchange.logintask.utilities;

import android.app.Activity;
import android.content.Context;

import com.onedata.sunexchange.logintask.R;

public class StatusMessage {
    private Context context = GlobalApplication.getAppContext();
    final  String fieldMissing =(String) context.getResources().getString(R.string.field_missing);
    final  String internalError =(String) context.getResources().getString(R.string.internal_error);
    final  String unauthorized =(String) context.getResources().getString(R.string.unauthorised);
    final  String resourceNotFound =(String) context.getResources().getString(R.string.resource_not_found);
    final  String someError =(String) context.getResources().getString(R.string.something_went_wrong);
}
