package com.longb.colordouban.databinding;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.longb.colordouban.R;
import com.longb.colordouban.adapter.RvPageAdapter;
import com.longb.colordouban.adapter.common.DataBindingAdapter;
import com.longb.colordouban.bean.Cast;
import com.longb.colordouban.bean.Director;
import com.longb.colordouban.bean.Subject;

import java.util.List;

/**
 * Created by longb on 2017/2/23.
 */

public class AppBindAdapters {

    @BindingAdapter("casts")
    public static void setCastString(TextView textView, Subject subject) {
        Object extra = subject.getExtra(R.id.extra_casts);
        if (extra == null) {
            extra = formatCasts(subject);
            subject.putExtra(R.id.extra_casts, extra);
        }
        textView.setText(extra.toString());
    }

    @NonNull
    private static String formatCasts(Subject subject) {
        StringBuilder sb = new StringBuilder(20);
        if (subject.getDirectors() != null) {
            for (Director d : subject.getDirectors()) {
                sb.append(d.getName() + " ");
            }
        }
        sb.append(" | ");
        if (subject.getCasts() != null) {
            for (Cast c : subject.getCasts()) {
                sb.append(c.getName() + " ");
            }
        }
        return sb.toString();
    }

    @BindingAdapter("movies")
    public static void setMovies(RecyclerView recyclerView, List<Subject> movies) {
        ((RvPageAdapter<DataBindingAdapter<Subject>>) recyclerView.getAdapter()).getWrapperAdapter().setDatas(movies);
    }
 }
