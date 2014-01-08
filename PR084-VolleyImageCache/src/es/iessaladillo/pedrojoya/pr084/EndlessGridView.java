package es.iessaladillo.pedrojoya.pr084;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

public class EndlessGridView extends GridView implements OnScrollListener {

    private LoadAgent listener;
    private boolean isLoading;

    public interface LoadAgent {
        public void loadData();
    }

    public EndlessGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public EndlessGridView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public EndlessGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        if (getAdapter() == null)
            return;

        if (getAdapter().getCount() == 0)
            return;

        int l = visibleItemCount + firstVisibleItem;
        if (l >= totalItemCount && !isLoading) {
            // It is time to add new data. We call the listener
            isLoading = true;
            listener.loadData();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

    public void setLoadAgent(LoadAgent listener) {
        this.listener = listener;
    }

    public void setLoaded() {
        isLoading = false;
    }
}
