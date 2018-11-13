package com.zxu.index;

import com.zxu.model.JC_AccountBook;

import java.util.ArrayList;
import java.util.List;

/**
 * 利用借口 通过Model层 获取保存数据，通过view更新界面
 */
public class IndexPagePresenter implements IndexPageContract.Presenter {

    private IndexPageContract.View cView;

    public IndexPagePresenter(IndexPageContract.View cView) {
        this.cView = cView;
    }

    /**
     * 获取账本
     *
     * @param userId 用户ID
     */
    @Override
    public void getAccountBooks(String userId) {
        // 1: 获取数据,传给Presenter
        // 2: 通过view进行交互
        List<JC_AccountBook> list = new ArrayList<>();
        JC_AccountBook b1 = new JC_AccountBook();
        b1.setId("1");
        b1.setName("Abc");
        JC_AccountBook b2 = new JC_AccountBook();
        b2.setId("2");
        b2.setName("Zxc");
        list.add(b1);
        list.add(b2);

        cView.setAccountBooks(list);

    }

}
