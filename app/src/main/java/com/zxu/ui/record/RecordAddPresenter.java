package com.zxu.ui.record;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.RecordDao;
import com.zxu.model.JC_Record;

public class RecordAddPresenter implements RecordAddContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private RecordAddContract.View cView;

    public RecordAddPresenter(GaiaApplication application, RecordAddContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    @Override
    public void addRecord(JC_Record record) {
        recordDao().addRecord(record);
    }
    public RecordDao recordDao(){
        return serviceFactory.getService(application, RecordDao.class);
    }
}
