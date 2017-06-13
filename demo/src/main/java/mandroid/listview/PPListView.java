package mandroid.listview;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import mandroid.utils.R;

/**
 * Created by KalinaRain on 2015/5/21.
 */
public class PPListView extends ListView implements AbsListView.OnScrollListener{

    public static final String TAG = PPListView.class.getSimpleName();
    private IPPListViewListener mIPPListViewListener;
    View header;
    View footer;

    /**
     * 下拉状态
     */
    public static final int STATUS_PULL_TO_REFRESH = 0;

    /**
     * 释放立即刷新状态
     */
    public static final int STATUS_RELEASE_TO_REFRESH = 1;

    /**
     * 正在刷新状态
     */
    public static final int STATUS_REFRESHING = 2;

    /**
     * 刷新完成或未刷新状态
     */
    public static final int STATUS_REFRESH_FINISHED = 3;

    /**
     * 下拉头部回滚的速度
     */
    public static final int SCROLL_SPEED = -20;

    /**
     * 一分钟的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MINUTE = 60 * 1000;

    /**
     * 一小时的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * 一天的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_DAY = 24 * ONE_HOUR;

    /**
     * 一月的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_MONTH = 30 * ONE_DAY;

    /**
     * 一年的毫秒值，用于判断上次的更新时间
     */
    public static final long ONE_YEAR = 12 * ONE_MONTH;

    /**
     * 上次更新时间的字符串常量，用于作为SharedPreferences的键值
     */
    private static final String UPDATED_AT = "updated_at";

    /**
     * 下拉刷新、上拉加载更多的回调接口
     */
    private IPPListViewListener mListener;

    // 用于存储上次更新时间
    private SharedPreferences preferences;

    /**
     * 需要去下拉刷新的ListView
     */
    private ListView listView;

    /**
     * 刷新时显示的进度条
     */
    private ProgressBar progressBar;

    /**
     * 指示下拉和释放的箭头
     */
    private ImageView pp_arrow;

    /**
     * 指示下拉和释放的文字描述
     */
    private TextView description;

    /**
     * 上次更新时间的文字描述
     */
    private TextView updateAt;

    /**
     * 下拉头的布局参数
     */
    private MarginLayoutParams headerLayoutParams;

    /**
     * 上次更新时间的毫秒值
     */
    private long lastUpdateTime;

    /**
     * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
     */
    private int mId = -1;

    /**
     * 下拉头的高度
     */
    private int hideHeaderHeight;

    /**
     * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
     * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
     */
    private int currentStatus = STATUS_REFRESH_FINISHED;;

    /**
     * 记录上一次的状态是什么，避免进行重复操作
     */
    private int lastStatus = currentStatus;

    /**
     * 手指按下时的屏幕纵坐标
     */
    private float yDown;

    /**
     * 在被判定为滚动之前用户手指可以移动的最大值。
     */
    private int touchSlop;

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;

    /**
     * 当前是否可以下拉，只有ListView滚动到头的时候才允许下拉
     */
    private boolean ableToPull;

    public PPListView(Context context) {
        super(context);
        initView(context);
    }

    public PPListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PPListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.pplist_header, null);
        this.addHeaderView(header);
//        this.addFooterView(footer);
    }

    /**
     * 停止下拉刷新，并充值header
     */
    public void stopRefresh() {
        /*if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }*/
    }
    /**
     * 停止上拉加载更多，并重置底布局
     */
    public void stopLoadMore() {
        /*if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(PPListView.STATE_NORMAL);
        }*/
    }

    private void resetHeaderHeight() {

    }

    private void updateHeaderView() {

    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     *
     * @param ippListViewListener
     */
    public void setPPListViewListener(IPPListViewListener ippListViewListener) {
        mIPPListViewListener = ippListViewListener;
    }

    /**
     * 实现该接口——获取下拉刷新和上拉加载更多的回调事件
     */
    public interface IPPListViewListener{


        /**
         * 刷新时会回调此方法，在该方法内编写具体的刷新处理事件。
         * 注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。
         */
        public void onDownRefresh();//下拉刷新

        /**
         * 上拉加载时会回调此方法，在该方法内编写具体的加载处理事件。
         * 注意此方法是在子线程中调用的， 你可以不必另开线程来进行耗时操作。
         */
        public void onUpLoadMore();//上拉加载更多
    }
}
