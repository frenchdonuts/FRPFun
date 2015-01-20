package com.example.frenchdonuts.frpfun.com.example.frenchdonuts.frpfun.twittermodels;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frenchdonuts.frpfun.FRPFunActivity;
import com.example.frenchdonuts.frpfun.R;

/**
 * Created by frenchdonuts on 7/25/14.
 */
public class StatusItem extends RelativeLayout {
    TextView tvName;
    TextView tvScreenName;
    TextView tvTweet;

    public StatusItem(LayoutInflater inflater, Context context) {
        super(context);
        if(inflater != null) {
            inflater.inflate(R.layout.status_item_view, this);
            int padding = FRPFunActivity.scale(7);
            this.setPadding(padding, padding, padding, padding);
            tvName = (TextView) this.findViewById(R.id.tvName);
            tvScreenName = (TextView) this.findViewById(R.id.tvScreenName);
            tvTweet = (TextView) this.findViewById(R.id.tvTweet);

        }
    }

    public void bindDataToView(Status status) {
        tvName.setText(status.user.name);
        tvScreenName.setText("@" + status.user.screenName);
        tvTweet.setText(status.text);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
