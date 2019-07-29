package com.onedata.sunexchange.logintask.utilities;


public class StatusCode  {


    public static String errorResponseToaster(int statusCode){
       StatusMessage statusMessage= new StatusMessage();
        switch(statusCode){
                case 400:
                return statusMessage.fieldMissing;
                case 500:
                return statusMessage.internalError;
                case 404:
                return statusMessage.resourceNotFound;
                case 401:
                return statusMessage.unauthorized;
                default :
                return statusMessage.someError;
        }
    }
    }

