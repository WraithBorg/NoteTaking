package com.zxu.dao;

import com.zxu.application.GaiaApplication;
import com.zxu.helpers.DatabaseHelper;

public class AbstractService {
    private ServiceFactory serviceFactory;
    private GaiaApplication context;
    private DatabaseHelper databaseHelper = null;

    public void init(GaiaApplication context) {
        this.context = context;
        databaseHelper = context.getDatabaseHelper();
    }

    public GaiaApplication getContext() {
        return context;
    }

    public void finish() {
        if (databaseHelper != null) {
            context.getDatabaseHelper().close();
            databaseHelper = null;
        }
    }

    protected DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

}
