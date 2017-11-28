package com.developers.bakingapp.util;

import android.content.Context;

/**
 * Created by Amanjeet Singh on 10/11/17.
 */

public interface ClickCallBack {
    void onClick(Context context, Integer id, String description, String url,String thumbnailUrl);
}
