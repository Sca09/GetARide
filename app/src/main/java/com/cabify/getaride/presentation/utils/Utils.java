package com.cabify.getaride.presentation.utils;

import android.graphics.Bitmap;
import android.text.format.DateUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by davidtorralbo on 08/10/16.
 */

public class Utils {

    public static String getDateStringFromDate(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_FOR_START_AT);

        return formatter.format(cal.getTime());
    }

    public static String getDateStringFromDateForPicker(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT_FOR_PICKER);

        long now = System.currentTimeMillis();
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH;

        String prefix = (String) DateUtils.getRelativeTimeSpanString(cal.getTime().getTime(), now, DateUtils.DAY_IN_MILLIS, flags);
        String suffix = formatter.format(cal.getTime());
        String resultDate = prefix +", "+ suffix;
        return resultDate;
    }

    public static boolean isDatePassed(Calendar cal) {
        return cal.getTime().getTime() < System.currentTimeMillis();
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];

            for(;;) {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1) {
                    break;
                }

                os.write(bytes, 0, count);
            }
        }

        catch (Exception ex){}
    }

    public static Bitmap getResizedBitmapAllowed(Bitmap image) {
        if(image != null) {
            int height = image.getHeight();
            int width = image.getWidth();
            int maxTextureSize = Utils.getMaxTextureSize();

            if (height > maxTextureSize || width > maxTextureSize) {
                return getResizedBitmap(image, maxTextureSize);
            } else {
                return image;
            }
        } else {
            return null;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static int getMaxTextureSize() {
        try {
            // Get EGL Display
            EGL10 egl = (EGL10) EGLContext.getEGL();
            EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

            // Initialise
            int[] version = new int[2];
            egl.eglInitialize(display, version);

            // Query total number of configurations
            int[] totalConfigurations = new int[1];
            egl.eglGetConfigs(display, null, 0, totalConfigurations);

            // Query actual list configurations
            EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
            egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);

            int[] textureSize = new int[1];
            int maximumTextureSize = 0;

            // Iterate through all the configurations to located the maximum texture size
            for (int i = 0; i < totalConfigurations[0]; i++) {
                // Only need to check for width since opengl textures are always squared
                egl.eglGetConfigAttrib(display, configurationsList[i], EGL10.EGL_MAX_PBUFFER_WIDTH, textureSize);

                // Keep track of the maximum texture size
                if (maximumTextureSize < textureSize[0]) {
                    maximumTextureSize = textureSize[0];
                }
            }

            // Release
            egl.eglTerminate(display);

            // Return largest texture size found, or default
            return Math.max(maximumTextureSize, Constants.MAX_IMAGE_SIZE);
        } catch (Exception e) {
            return Constants.MAX_IMAGE_SIZE;
        }
    }

}
