package com.kikiplus.app.main.popup;

import android.content.Context;
import android.view.View;

import com.kikiplus.app.frame.custom.KDialog;
import com.kikiplus.app.frame.impl.OnPopupEvent;

/**
 * Created by mihyeKim on 2017-02-12.
 */

public class BasicPopup extends KDialog {

    public BasicPopup(Context context, String title, int contentView, OnPopupEvent popupEventListener, boolean isCancle, int popId) {
        super(context, title, contentView, popupEventListener,
                isCancle, popId);
    }

    @Override
    protected void initDialog() {
    }

    @Override
    protected void destroyDialog() {
    }

}
