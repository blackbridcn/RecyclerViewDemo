package blackbird.com.recyclerviewdemo.widget;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



public class BaseDialogFragment extends DialogFragment {

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);
       // mLayoutResId = setUpLayoutId();
    }
}
