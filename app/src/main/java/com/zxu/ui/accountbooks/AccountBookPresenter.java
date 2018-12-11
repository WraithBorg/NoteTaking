package com.zxu.ui.accountbooks;

import com.zxu.application.GaiaApplication;
import com.zxu.dao.AccountBookDao;
import com.zxu.base.database.ServiceFactory;
import com.zxu.dao.RecordDao;
import com.zxu.model.JC_AccountBook;
import com.zxu.model.JC_Record;
import com.zxu.util.CostEnum;
import com.zxu.util.UtilTools;

import java.math.BigDecimal;
import java.util.List;

/**
 * 利用接口 通过Model层 获取保存数据，通过view更新界面
 */
public class AccountBookPresenter implements AccountBookContract.Presenter {
    private GaiaApplication application;
    private ServiceFactory serviceFactory = new ServiceFactory();

    private AccountBookContract.View cView;

    public AccountBookPresenter(GaiaApplication application, AccountBookContract.View cView) {
        this.cView = cView;
        this.application = application;
    }

    /**
     * 获取账本
     */
    @Override
    public void getAccountBooks() {
        // 1: 获取数据,传给Presenter
        // 2: 通过view进行交互
        List<JC_AccountBook> list = accountBookDao().getAll();
        cView.setAccountBooks(list);
    }

    /**
     * 新增
     *
     * @param b
     */
    @Override
    public void addAccountBook(JC_AccountBook b) {
        accountBookDao().addAccountBook(b);
    }

    /**
     * 查询
     */
    @Override
    public void getAccountBooks4EDIT() {
        List<JC_AccountBook> list = accountBookDao().getAll();
        cView.setAccountBooks4EDIT(list);
    }

    /**
     * 删除
     */
    @Override
    public void delAccountBook(JC_AccountBook item) {
        accountBookDao().delAccountBook(item);
        List<JC_AccountBook> list = accountBookDao().getAll();
        cView.setAccountBooks4EDIT(list);
    }

    @Override
    public void getMonthReport(String accountId, String period) {

        List<JC_Record> recordList = recordDao().getAll(accountId, period);
        // calculate
        BigDecimal inCome = BigDecimal.ZERO, spending = BigDecimal.ZERO, balance;
        for (JC_Record record : recordList) {
            if (CostEnum.INCOME.code().equals(record.getType())) {
                inCome = inCome.add(new BigDecimal(record.getMoney()));
            } else if (CostEnum.SPEND.code().equals(record.getType())) {
                spending = spending.add(new BigDecimal(record.getMoney()));
            }
        }
        balance = inCome.subtract(spending);

        String[] strings = new String[3];
        strings[0] = UtilTools.format(inCome);
        strings[1] = UtilTools.format(spending);
        strings[2] = UtilTools.format(balance);
        cView.setMonthReport(strings);
    }

    /**
     * @return
     */
    public AccountBookDao accountBookDao() {
        return serviceFactory.getService(application, AccountBookDao.class);
    }
    public RecordDao recordDao() {
        return serviceFactory.getService(application, RecordDao.class);
    }
}
