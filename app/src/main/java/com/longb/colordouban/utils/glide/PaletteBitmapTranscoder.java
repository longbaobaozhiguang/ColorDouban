package com.longb.colordouban.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

/**
 * see https://github.com/bumptech/glide/wiki/Custom-targets#overriding-default-behavior
 */
public class PaletteBitmapTranscoder implements ResourceTranscoder<Bitmap, PaletteBitmap> {
    private final BitmapPool bitmapPool;

    public PaletteBitmapTranscoder(@NonNull Context context) {
        this.bitmapPool = Glide.get(context).getBitmapPool();
    }

    @Override
    public Resource<PaletteBitmap> transcode(Resource<Bitmap> toTranscode) {
        Bitmap bitmap = toTranscode.get();
        Palette palette = Palette.generate(bitmap);
        PaletteBitmap result = new PaletteBitmap(bitmap, palette);
        return new PaletteBitmapResource(result, bitmapPool);
    }

    @Override
    public String getId() {
        return PaletteBitmapTranscoder.class.getName();
    }
}
