package com.anningtex.commonbasedata.weight.actionbar.style;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.anningtex.commonbasedata.R;
import com.anningtex.commonbasedata.weight.actionbar.SelectorDrawable;

/**
 * @author Song
 */
public class TitleBarNightStyle extends BaseTitleBarStyle {
    public TitleBarNightStyle(Context context) {
        super(context);
    }

    @Override
    public Drawable getBackground() {
        return new ColorDrawable(0xFF000000);
    }

    @Override
    public Drawable getBackIcon() {
        return getDrawable(R.mipmap.icon_bar_back_white);
    }

    @Override
    public int getLeftColor() {
        return 0xCCFFFFFF;
    }

    @Override
    public int getTitleColor() {
        return 0xEEFFFFFF;
    }

    @Override
    public int getRightColor() {
        return 0xCCFFFFFF;
    }

    @Override
    public boolean isLineVisible() {
        return true;
    }

    @Override
    public Drawable getLineDrawable() {
        return new ColorDrawable(0xFFFFFFFF);
    }

    @Override
    public Drawable getLeftBackground() {
        return new SelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x66FFFFFF))
                .setPressed(new ColorDrawable(0x66FFFFFF))
                .builder();
    }

    @Override
    public Drawable getRightBackground() {
        return getLeftBackground();
    }
}
