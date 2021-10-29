/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dresses.library.loadingpage.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dresses.library.R;
import com.dylanc.loadinghelper.LoadingHelper;

/**
 * @author Dylan Cai
 */
public class ErrorAdapter extends LoadingHelper.Adapter<ErrorAdapter.ViewHolder> {
    private ViewHolder holder;

    private View.OnClickListener listener = null;

    public ErrorAdapter(View.OnClickListener listener) {
        this.listener = listener;
    }

    private String msg;
    private int resId;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.layout_error, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder) {
        this.holder = holder;
        holder.btnReload.setOnClickListener(listener);
        if (!TextUtils.isEmpty(msg)) {
            setErrorText(msg);
        }
        if (resId != 0) {
            setErrorRes(resId);
        }
    }

    public void setErrorRes(int resId) {
        this.resId = resId;
        if (holder == null) return;
        holder.errorImageView.setImageResource(resId);
    }

    public void setErrorText(String text) {
        this.msg = text;
        if (holder == null) return;
        holder.tvErrorText.setText(text);
    }

    public static class ViewHolder extends LoadingHelper.ViewHolder {

        private final View btnReload;
        private final TextView tvErrorText;
        private final ImageView errorImageView;

        ViewHolder(@NonNull View rootView) {
            super(rootView);
            btnReload = rootView.findViewById(R.id.vCLError);
            tvErrorText = rootView.findViewById(R.id.tv_error_text);
            errorImageView = rootView.findViewById(R.id.errorImageView);
        }
    }
}
