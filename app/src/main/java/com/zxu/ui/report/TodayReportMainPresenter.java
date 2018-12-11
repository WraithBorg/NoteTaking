package com.zxu.ui.report;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.RecordDao;

public class TodayReportMainPresenter implements TodayReportMainContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private TodayReportMainContract.View cView;

    public TodayReportMainPresenter(GaiaApplication application, TodayReportMainContract.View cView) {
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
