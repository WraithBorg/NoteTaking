package com.zxu.ui.record;

import com.zxu.application.GaiaApplication;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.RecordDao;
import com.zxu.model.JC_Record;
import com.zxu.model.JC_RecordSum;

import java.util.List;

public class RecordPresenter implements ListRecordContract.Presenter, AddRecordContract.Presenter, EditRecordContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();
    private ListRecordContract.View listView;
    private AddRecordContract.View addView;
    private EditRecordContract.View editView;

    public RecordPresenter(GaiaApplication application, ListRecordContract.View view) {
        this.application = application;
        this.listView = view;
    }

    public RecordDao recordDao() {
        return serviceFactory.getService(application, RecordDao.class);
    }

    public RecordPresenter(GaiaApplication application, AddRecordContract.View view) {
        this.application = application;
        this.addView = view;
    }

    public RecordPresenter(GaiaApplication application, EditRecordContract.View editView) {
        this.application = application;
        this.editView = editView;
    }

    @Override
    public void getRecords(String accountId, String period) {
        listView.setRecords(recordDao().getAll(accountId, period));
    }

    @Override
    public void getRecordSumByWeek(String accountId, String period) {
        List<JC_RecordSum> recordSums = recordDao().getRecordSumByWeek(accountId);
        listView.setRecordSumByWeekOrMonth(recordSums);
    }

    @Override
    public void getRecordSumByMonth(String accountId, String period) {
        List<JC_RecordSum> recordSums = recordDao().getRecordSumByMonth(accountId);
        listView.setRecordSumByWeekOrMonth(recordSums);
    }

    @Override
    public void addRecord(JC_Record record) {
        recordDao().addRecord(record);
    }

    @Override
    public void editRecord(JC_Record record) {
        recordDao().editRecord(record);
    }

    @Override
    public void delete(JC_Record mRecord) {
        recordDao().delete(mRecord.getId());
    }
}
