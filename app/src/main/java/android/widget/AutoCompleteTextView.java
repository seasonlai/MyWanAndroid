package android.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;

public class AutoCompleteTextView extends EditText implements Filter.FilterListener {

    public AutoCompleteTextView(Context context) {
        super(context);
    }

    public AutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFilterComplete(int count) {

    }

    public void setDropDownBackgroundResource(@DrawableRes int resId) {
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {

    }
}