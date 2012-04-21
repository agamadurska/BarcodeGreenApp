package com.greenapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.Gallery;

public class PatchedGallery extends Gallery
{
    public PatchedGallery(Context context)
    {
        super(context);
    }

    public PatchedGallery(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PatchedGallery(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        boolean handled = false;

        if (getFocusedChild() != null)
        {
            handled = getFocusedChild().dispatchKeyEvent(event);
        }

        if (!handled)
        {
            handled = event.dispatch(this, null, null);
        }

        return handled;
    }
}