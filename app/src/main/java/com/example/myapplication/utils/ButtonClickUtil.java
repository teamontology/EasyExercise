package com.example.myapplication.utils;

import android.util.Log;
import android.widget.Button;

/**
 * This utility class is to control {@link Button} click frequency to efficiently avoid multiple repeated clicks. <br/>
 * If a {@link android.widget.Button} is triggered multiple times in a short time, clickListener regards the clicks invalid.<br/>
 * <p>
 * An example to illustrate its usage:<br/>
 * <pre class="prettyprint">
 *
 * mButton.setOnItemClickListener(new OnItemClickListener() {
 *   public void onItemClick(AdapterView<?> arg0, View arg1, int arg2) {
 *     if (!ButtonClickUtils.isFastDoubleClick(R.id.button_mButton)) {
 *          // Your codes here
 *     }
 *   }
 * });
 * </pre>
 */
public class ButtonClickUtil {
    private static long lastClickTime = 0;
    private static long DIFF = 1000;
    private static int lastButtonId = -1;

    /**
     * Judge time interval of two click events. If less than 1000, it is thought invalid.
     *
     * @param buttonId {@link com.example.myapplication.R.id} corresponds to {@link Button} object
     * @return isFastDoubleClick(buttonId, DIFF)
     * @see #isFastDoubleClick()
     * @see #isFastDoubleClick(int, long)
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * Judge time interval of two click events. If less than DIFF, it is thought invalid.
     *
     * @param buttonId {@link com.example.myapplication.R.id} corresponds to {@link Button} object
     * @param diff     predefined time interval DIFF
     * @return false or true
     */
    private static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            Log.e("isFastDoubleClick", "The button is triggered multiple times in a short time");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}