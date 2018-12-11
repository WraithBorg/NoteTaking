package com.zxu.ui.record;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.RecordDao;
import com.zxu.model.JC_Record;

public class RecordPresenter implements ListRecordContract.Presenter, AddRecordContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private ListRecordContract.View listView;
    private AddRecordContract.View addView;

    public RecordPresenter(GaiaApplication application, ListRecordContract.View view) {
        this.application = application;
        this.listView = view;
    }

    public RecordPresenter(GaiaApplication application, AddRecordContract.View view) {
        this.application = application;
        this.addView = view;
    }

    @Override
    public void getRecords(String accountId, String period) {
        listView.setRecords(recordDao().getAll(accountId, period));
    }

    @Override
    public void addRecord(JC_Record record) {
        recordDao().addRecord(record);
    }

    public RecordDao recordDao() {
        return serviceFactory.getService(application, RecordDao.class);
    }
}
