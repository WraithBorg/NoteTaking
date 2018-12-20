package com.zxu.ui.recordsearch;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.RecordDao;
import com.zxu.model.JC_RecordSearchQuery;

public class SearchRecordPresenter implements SearchRecordContract.Persenter {

    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private SearchRecordContract.View cView;

    public SearchRecordPresenter(GaiaApplication application, SearchRecordContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    public RecordDao recordDao() {
        return serviceFactory.getService(application, RecordDao.class);
    }


    @Override
    public void getRecords(JC_RecordSearchQuery recordQuery) {
        cView.setRecords(recordDao().getSearchResult(recordQuery));
    }
}
