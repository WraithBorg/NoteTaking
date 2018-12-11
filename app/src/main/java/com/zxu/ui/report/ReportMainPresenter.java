package com.zxu.ui.report;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.RecordDao;

public class ReportMainPresenter implements ReportMainContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private ReportMainContract.View cView;

    public ReportMainPresenter(GaiaApplication application, ReportMainContract.View cView) {
        this.application = application;
        this.cView = cView;
    }

    @Override
    public void getTodayRecords(String accountId) {
        cView.setTodayRecords(recordDao().getAll(accountId));
    }

    public RecordDao recordDao() {
        return serviceFactory.getService(application, RecordDao.class);
    }
}
