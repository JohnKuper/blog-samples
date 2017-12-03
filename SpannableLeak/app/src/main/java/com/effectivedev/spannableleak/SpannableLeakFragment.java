package com.effectivedev.spannableleak;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SpannableLeakFragment extends Fragment {
    private static final String TAG = SpannableLeakFragment.class.getSimpleName();

    private OnSpanClickListener mOnSpanClickListener;
    private TextView mTvSpanLink;

    public static SpannableLeakFragment newInstance() {
        return new SpannableLeakFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnSpanClickListener = (OnSpanClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.effectivedev.spannableleak.R.layout.fragment_spannable_leak, container, false);
        mTvSpanLink = (TextView) rootView.findViewById(R.id.tvSpannableText);
        //this should be used during onStart()
        setTermsAndConditionsClickableText(mTvSpanLink);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //uncomment to fix the wrong spannable string behavior
//        setTermsAndConditionsClickableText(mTvSpanLink);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnSpanClickListener = null;
    }

    private void setTermsAndConditionsClickableText(TextView textView) {
        CharSequence sequence = Html.fromHtml(getString(com.effectivedev.spannableleak.R.string.terms_and_conditions_link));
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        UnderlineSpan[] underlines = strBuilder.getSpans(0, sequence.length(), UnderlineSpan.class);
        for (UnderlineSpan span : underlines) {
            int start = strBuilder.getSpanStart(span);
            int end = strBuilder.getSpanEnd(span);
            ClickableSpan detailsLink = new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick() ClickableSpan: " + this.hashCode());
                    mOnSpanClickListener.onSpanClick();
                }
            };
            Log.d(TAG, "current ClickableSpan: " + detailsLink.hashCode());
            strBuilder.setSpan(detailsLink, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setText(strBuilder, TextView.BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public interface OnSpanClickListener {
        void onSpanClick();
    }
}
