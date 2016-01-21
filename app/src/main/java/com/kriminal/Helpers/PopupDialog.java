package com.kriminal.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.kriminal.main_activity.R;

/**
 * Created by Kriminal on 21/01/2016.
 * *  * *******************************************************************************
 * Copyright (c) 2016 kriminal666.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
public class PopupDialog extends Dialog {

    private String title;
    private String message;

    /**
     * Constructor
     * @param context
     * @param themeResId
     * @param title
     * @param message
     */
    public PopupDialog(Activity activity, int layout, String title, String message ) {
        super(activity);

        this.setContentView(layout);



            Toast.makeText(activity, activity.findViewById(R.id.popup_text).getClass().getName(), Toast.LENGTH_LONG).show();




        this.setTitle(title);





    }



}
