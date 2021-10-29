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

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.dresses.library.R;
import com.dylanc.loadinghelper.LoadingHelper;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dylan Cai
 */
public class CustomHeaderAdapter extends LoadingHelper.Adapter<CustomHeaderAdapter.ViewHolder> {

    private View.OnClickListener listener;
    private int firstDrawableId;
    private int secondDrawableId;
    private int backId;
    private String title;
    private String leftText;
    private String firstText;
    private String secondText;

    private ViewHolder holder;

    private CustomHeaderAdapter(View.OnClickListener listener, String title
            , int backId, int firstDrawableId
            , int secondDrawableId
            , String leftText
            , String firstText
            , String secondText
    ) {
        this.firstDrawableId = firstDrawableId;
        this.listener = listener;
        this.secondDrawableId = secondDrawableId;
        this.backId = backId;
        this.title = title;
        this.leftText = leftText;
        this.firstText = firstText;
        this.secondText = secondText;

    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull LayoutInflater inflater, @NotNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.layout_custom_header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder) {
        this.holder = holder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (firstDrawableId == 0 && TextUtils.isEmpty(firstText)) {
            holder.btnFirst.setVisibility(View.GONE);
        } else {
            holder.btnFirst.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, firstDrawableId, 0);
            holder.btnFirst.setOnClickListener(listener);
        }
        if (secondDrawableId == 0 && TextUtils.isEmpty(secondText)) {
            holder.btnSecond.setVisibility(View.GONE);
        } else {
            holder.btnSecond.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, secondDrawableId, 0);
            holder.btnSecond.setOnClickListener(listener);
        }

        if (backId == 0 && TextUtils.isEmpty(leftText)) {
            holder.btnBack.setVisibility(View.INVISIBLE);
        } else {
            holder.btnBack.setCompoundDrawablesRelativeWithIntrinsicBounds(backId, 0, 0, 0);
            holder.btnBack.setOnClickListener(listener);
        }

        if (!TextUtils.isEmpty(title)) {
            holder.tvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(leftText)) {
            holder.btnBack.setText(leftText);
        }

        if (!TextUtils.isEmpty(firstText)) {
            holder.btnFirst.setText(firstText);
        }

        if (!TextUtils.isEmpty(secondText)) {
            holder.btnSecond.setText(secondText);
        }
    }

    private void updateTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            holder.tvTitle.setText(title);
        }
    }

    private void updateLeftText(String leftText) {
        if (!TextUtils.isEmpty(leftText)) {
            holder.btnBack.setText(leftText);
        }
    }

    private void updateFirstText(String firstText) {
        if (!TextUtils.isEmpty(leftText)) {
            holder.btnFirst.setText(leftText);
        }
    }

    private void updateSecondText(String secondText) {
        if (!TextUtils.isEmpty(leftText)) {
            holder.btnSecond.setText(leftText);
        }
    }

    static class ViewHolder extends LoadingHelper.ViewHolder {
        private final TextView btnFirst;
        private final TextView btnSecond;
        private final TextView btnBack;
        private final TextView tvTitle;


        ViewHolder(@NonNull View rootView) {
            super(rootView);
            btnFirst = rootView.findViewById(R.id.btn_first);
            btnSecond = rootView.findViewById(R.id.btn_second);
            btnBack = rootView.findViewById(R.id.ivBaseBack);
            tvTitle = rootView.findViewById(R.id.tvBaseTitle);
        }

        private Activity getActivity() {
            return (Activity) getRootView().getContext();
        }
    }


    public static class Builder {
        private View.OnClickListener listener;
        private int firstDrawableId;
        private int secondDrawableId;
        private int backId;
        private String title;

        private String leftText;
        private String firstText;
        private String secondText;

        private CustomHeaderAdapter adapter;

        public Builder setListener(View.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setFirstResId(int firstDrawableId) {
            this.firstDrawableId = firstDrawableId;
            return this;

        }

        public Builder setSecondResId(int secondDrawableId) {
            this.secondDrawableId = secondDrawableId;
            return this;

        }

        public Builder setLeftResId(int backId) {
            this.backId = backId;
            return this;

        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setLeftText(String left) {
            this.leftText = left;
            return this;
        }

        public Builder setFirstText(String first) {
            this.firstText = first;
            return this;
        }

        public Builder setSecondText(String second) {
            this.secondText = second;
            return this;
        }

        public void updateTitle(String title) {
            if (adapter == null) return;
            this.title = title;
            adapter.updateTitle(title);
        }

        public void updateLeftText(String leftText) {
            if (adapter == null) return;
            this.leftText = leftText;
            adapter.updateLeftText(leftText);
        }

        public void updateFirstText(String firstText) {
            if (adapter == null) return;
            this.firstText = firstText;
            adapter.updateFirstText(firstText);
        }

        public void updateSecondText(String secondText) {
            if (adapter == null) return;
            this.secondText = secondText;
            adapter.updateSecondText(secondText);
        }

        public CustomHeaderAdapter build() {

            if (adapter == null) {
                adapter = new CustomHeaderAdapter(this.listener,
                        this.title,
                        this.backId,
                        this.firstDrawableId,
                        this.secondDrawableId
                        , this.leftText
                        , this.firstText
                        , this.secondText);
            }
            return adapter;
        }
    }
}
