package com.dresses.library.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSpanUtils {

    // 由一多个
    private static final String START = "start";
    private static final String END = "end";
    private static final String REGEX = "regex";

    public static List<HashMap<String, String>> getStartAndEnd(Pattern pattern,
                                                               CharSequence input) {
        Matcher matcher = pattern.matcher(input);
        List<HashMap<String, String>> list = new ArrayList<>();
        while (matcher.find()) {
            HashMap<String, String> map = new HashMap<>();
            map.put(REGEX, matcher.group());
            map.put(START, matcher.start() + "");
            map.put(END, matcher.end() + "");
            list.add(map);
        }
        return list;
    }

    /**
     * 将传入的String数组包装成Pattern数组
     *
     * @param strs
     * @return
     */
    public static Pattern[] addPattern(String... strs) {
        Pattern[] patterns = new Pattern[strs.length];
        for (int i = 0; i < strs.length; i++) {
            patterns[i] = Pattern.compile(strs[i]);
        }
        return patterns;
    }

    public static SpannableString setForegroundColorSpan(
            SpannableString spannableString, int color, String... strs) {
        setForegroundColorSpan(spannableString, color, addPattern(strs));
        return spannableString;
    }

    public static  SpannableString setUserNameColorSpan(SpannableString spannableString,int color,int start,int end){

        return spannableString;
    }


    public static SpannableString setForegroundColorSpan(
            SpannableString spannableString, int color, Pattern... patterns) {
        for (Pattern pattern : patterns) {
            List<HashMap<String, String>> list = getStartAndEnd(pattern,
                    spannableString);
            for (HashMap<String, String> hashMap : list) {
                int start = Integer.parseInt(hashMap.get(START));
                int end = Integer.parseInt(hashMap.get(END));
                int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
                spannableString.setSpan(new ForegroundColorSpan(color), start,
                        end, flags);
            }
        }
        return spannableString;
    }

    public static SpannableString setBackgroundColorSpan(
            SpannableString spannableString, int color, String... strs) {
        setForegroundColorSpan(spannableString, color, addPattern(strs));
        return spannableString;
    }

    public static SpannableString setBackgroundColorSpan(
            SpannableString spannableString, int color, Pattern... patterns) {
        for (Pattern pattern : patterns) {
            List<HashMap<String, String>> list = getStartAndEnd(pattern,
                    spannableString);
            for (HashMap<String, String> hashMap : list) {
                int start = Integer.parseInt(hashMap.get(START));
                int end = Integer.parseInt(hashMap.get(END));
                int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
                spannableString.setSpan(new BackgroundColorSpan(color), start,
                        end, flags);
            }
        }
        return spannableString;
    }


    public static SpannableString setImageSpan(Context context,
                                               SpannableString spannableString, int resId, Pattern... patterns) {
        Drawable emoji = context.getResources().getDrawable(resId);
        int w = (int) (emoji.getIntrinsicWidth());
        int h = (int) (emoji.getIntrinsicHeight());
        return setImageSpan(context, spannableString, resId, w, h, patterns);
    }

    public static SpannableString setImageSpan(Context context,
                                               SpannableString spannableString, int resId, int w, int h, Pattern... patterns) {
        for (Pattern pattern : patterns) {
            List<HashMap<String, String>> list = getStartAndEnd(pattern,
                    spannableString);
            for (HashMap<String, String> hashMap : list) {
                int start = Integer.parseInt(hashMap.get(START));
                int end = Integer.parseInt(hashMap.get(END));
                int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
                Drawable emoji = context.getResources().getDrawable(resId);
                emoji.setBounds(0, 0, w, h);
                spannableString.setSpan(new ImageSpan(emoji), start,
                        end, flags);
            }
        }
        return spannableString;
    }

    public static SpannableString setClickSpan(
            SpannableString spannableString, final SpanOnClickListener listener, Pattern... patterns) {
        for (Pattern pattern : patterns) {
            List<HashMap<String, String>> list = getStartAndEnd(pattern,
                    spannableString);
            for (int i = 0; i < list.size(); i++) {
                final int index = i;
                final HashMap<String, String> hashMap = list.get(i);
                int start = Integer.parseInt(hashMap.get(START));
                int end = Integer.parseInt(hashMap.get(END));
                int flags = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
                spannableString.setSpan(new ClickableSpan() {
                                            @Override
                                            public void onClick(View widget) {
                                                String regex = hashMap.get(REGEX);
                                                if (listener != null) {
                                                    listener.onClick(widget, regex, index);
                                                }
                                            }

                                            @Override
                                            public void updateDrawState(TextPaint ds) {
                                                if (listener != null) {
                                                    listener.setClickDrawState(ds);
                                                }
                                                //  super.updateDrawState(ds);
                                            }
                                        }, start,
                        end, flags);
            }
        }
        return spannableString;
    }

    public interface SpanOnClickListener {

        void onClick(View v, String regex, int index);

        void setClickDrawState(TextPaint ds);
    }
}
