package com.longb.colordouban.common.base;

import android.databinding.ViewDataBinding;

/**
 * Base View
 *
 * @param <VBD>
 * @param <VM>
 */
public class BaseFragmentView<VBD extends ViewDataBinding, VM extends ViewModel> extends BaseFragment<VBD>  {

    protected VM mViewModel;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.onDestroy();
        }
    }

    protected void setViewModel(VM viewModel) {
        mViewModel = viewModel;
    }

}
