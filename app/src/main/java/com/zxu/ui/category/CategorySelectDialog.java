package com.zxu.ui.category;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zxu.R;
import com.zxu.application.GaiaApplication;
import com.zxu.model.JC_Category;
import com.zxu.util.CodeConstant;
import com.zxu.util.DensityUtil;
import com.zxu.util.UtilTools;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectDialog extends DialogFragment implements CategorySelectContract.View{
    /* Scroll << */
    ImageView iv_addCategory;
    ImageView iv_folding;
    private RecyclerView recLeft;
    private RecyclerView recRight;
    private TextView rightTitle;

    private List<String> left;
    private List<CategorySelectScrollBean> right;
    private CategorySelectScrollLeftAdapter leftAdapter;
    private CategorySelectScrollRightAdapter rightAdapter;
    //右侧title在数据中所对应的position集合
    private List<Integer> tPosition = new ArrayList<>();
    private Context mContext;
    //title的高度
    private int tHeight;
    //记录右侧当前可见的第一个item的position
    private int first = 0;
    private GridLayoutManager rightManager;
    /* Scroll >> */

    private CategorySelectContract.Presenter mPresenter;
    private List<JC_Category> categoryList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.record_category_scroll, null);
        mContext = getActivity();
        iv_addCategory = (ImageView) view.findViewById(R.id.record_category_add_category_id);
        iv_folding = (ImageView) view.findViewById(R.id.record_category_search_category_id);
        recLeft = (RecyclerView) view.findViewById(R.id.record_category_rec_left);
        recRight = (RecyclerView) view.findViewById(R.id.record_category_rec_right);
        rightTitle = (TextView) view.findViewById(R.id.record_category_right_title);

        initWidgets();
        initData();
        initLeft();
        initRight();
        return view;
    }

    /**
     * 初始化按钮事件
     */
    void initWidgets() {
        iv_addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, CodeConstant.DIALOGWAITTIME);
                CategoryListDialog dialog = new CategoryListDialog();
                CategoryListPresenter presenter = new CategoryListPresenter((GaiaApplication) getActivity().getApplication(), dialog);
                dialog.setPresenter(presenter);
                dialog.show(getActivity().getFragmentManager(), "android");
            }
        });
        iv_folding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 设置位置在底部
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DensityUtil.dp2px(getActivity().getApplication(), 390);// TODO 会影响record_category_left_text高度及滚动条
        window.setAttributes(params);
        // 设置背景透明
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.setBackgroundDrawableResource(R.color.vifrification);
    }

    @Override
    public void setCategorys(List<JC_Category> categorys) {
        setCategoryList(categorys);
    }

    @Override
    public void setPresenter(CategorySelectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /*****************_______________________________________*/
    private void initRight() {

        rightManager = new GridLayoutManager(mContext, 1);

        if (rightAdapter == null) {
            rightAdapter = new CategorySelectScrollRightAdapter(R.layout.record_category_scroll_right, R.layout.record_category_scroll_layout_right_title, null);
            recRight.setLayoutManager(rightManager);
            recRight.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(dpToPx(mContext, 3)
                            , 0
                            , dpToPx(mContext, 3)
                            , dpToPx(mContext, 3));
                }
            });
            recRight.setAdapter(rightAdapter);
        } else {
            rightAdapter.notifyDataSetChanged();
        }

        rightAdapter.setNewData(right);
        // 监听右侧点击事件
        rightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CategorySelectScrollBean scrollBean = right.get(position);
                if (scrollBean == null || scrollBean.t == null){
                    return;
                }
                JC_Category category = scrollBean.t.getCategory();
                onSelectSmallTypeListener.selectOn(category);
            }
        });

        //设置右侧初始title
        if (right.get(first).isHeader) {
            rightTitle.setText(right.get(first).header);
        }

        recRight.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取右侧title的高度
                tHeight = rightTitle.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //判断如果是header
                if (right.get(first).isHeader) {
                    //获取此组名item的view
                    View view = rightManager.findViewByPosition(first);
                    if (view != null) {
                        //如果此组名item顶部和父容器顶部距离大于等于title的高度,则设置偏移量
                        if (view.getTop() >= tHeight) {
                            rightTitle.setY(view.getTop() - tHeight);
                        } else {
                            //否则不设置
                            rightTitle.setY(0);
                        }
                    }
                }

                //因为每次滑动之后,右侧列表中可见的第一个item的position肯定会改变,并且右侧列表中可见的第一个item的position变换了之后,
                //才有可能改变右侧title的值,所以这个方法内的逻辑在右侧可见的第一个item的position改变之后一定会执行
                int firstPosition = rightManager.findFirstVisibleItemPosition();
                if (first != firstPosition && firstPosition >= 0) {
                    //给first赋值
                    first = firstPosition;
                    //不设置Y轴的偏移量
                    rightTitle.setY(0);

                    //判断如果右侧可见的第一个item是否是header,设置相应的值
                    if (right.get(first).isHeader) {
                        rightTitle.setText(right.get(first).header);
                    } else {
                        rightTitle.setText(right.get(first).t.getType());
                    }
                }

                //遍历左边列表,列表对应的内容等于右边的title,则设置左侧对应item高亮
                for (int i = 0; i < left.size(); i++) {
                    if (left.get(i).equals(rightTitle.getText().toString())) {
                        leftAdapter.selectItem(i);
                    }
                }

                //如果右边最后一个完全显示的item的position,等于bean中最后一条数据的position(也就是右侧列表拉到底了),
                //则设置左侧列表最后一条item高亮
                if (rightManager.findLastCompletelyVisibleItemPosition() == right.size() - 1) {
                    leftAdapter.selectItem(left.size() - 1);
                }
            }
        });
    }

    private void initLeft() {
        if (leftAdapter == null) {
            leftAdapter = new CategorySelectScrollLeftAdapter(R.layout.record_category_scroll_left, null);
            recLeft.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            recLeft.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            recLeft.setAdapter(leftAdapter);
        } else {
            leftAdapter.notifyDataSetChanged();
        }

        leftAdapter.setNewData(left);

        leftAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点击左侧列表的相应item,右侧列表相应的title置顶显示
                    //(最后一组内容若不能填充右侧整个可见页面,则显示到右侧列表的最底端)
                    case R.id.item:
                        leftAdapter.selectItem(position);
                        rightManager.scrollToPositionWithOffset(tPosition.get(position), 0);
                        break;
                }
            }
        });
    }

    //获取数据(若请求服务端数据,请求到的列表需有序排列)
    private void initData() {
        left = new ArrayList<>();
        right = new ArrayList<>();

        mPresenter.getAll();
        List<JC_Category> li = getCategoryList();
        for (JC_Category c : li){
            String cName = c.getName();
            left.add(cName);
            right.add(new CategorySelectScrollBean(true, cName));
            for (JC_Category cc : c.getChilds()){
                right.add(new CategorySelectScrollBean(new CategorySelectScrollBean.ScrollItemBean(cc.getName(), cName,cc)));
            }
        }
        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).isHeader) {
                //遍历右侧列表,判断如果是header,则将此header在右侧列表中所在的position添加到集合中
                tPosition.add(i);
            }
        }
//        if (right.size() == 0){
//            JC_Category c = new JC_Category();
//            c.setName("请添加分类");
//            c.setChilds(new ArrayList<>());
//            right.add(new CategorySelectScrollBean(true, c.getName()));
//        }
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
    }
    /***************+++++++++++++++++++++++++++++++++++++++++++*/
    public List<JC_Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<JC_Category> categoryList) {
        this.categoryList = categoryList;
    }
    // 选择类别 监听器 for CategorySelectDialog
    private onSelectSmallTypeListener onSelectSmallTypeListener;
    public interface  onSelectSmallTypeListener{
        void selectOn(JC_Category category);
    }

    public void setOnSelectSmallTypeListener(onSelectSmallTypeListener onSelectSmallTypeListener) {
        this.onSelectSmallTypeListener = onSelectSmallTypeListener;
    }
}
