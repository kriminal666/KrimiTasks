package com.kriminal.sweet_alert;

import android.content.Context;

import com.kriminal.helpers.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Kriminal on 26/01/2016.
 *  * *******************************************************************************
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
public class SweetAlert  {


    /**
     * Basic message
     * @param ctx
     * @param msg
     * @return
     */
    public static SweetAlertDialog basicMessage(Context ctx ,String msg){
        return new SweetAlertDialog(ctx).setTitleText(msg);

    }

    /**
     * Title with content message
     * @param ctx
     * @param title
     * @param content
     * @return
     */
    public static SweetAlertDialog titleWithContent(Context ctx, String title, String content){
        return new SweetAlertDialog(ctx)
                .setTitleText(title)
                .setContentText(content);
    }


    /**
     * Error message
     * @param ctx
     * @param title
     * @param content
     * @return
     */
    public static SweetAlertDialog errorMessage(Context ctx, String title, String content){
        return new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(content);
    }


    /**
     * Warning message with confirm button
     * @param ctx
     * @param title
     * @param content
     * @param btnText
     * @return
     */
    public static SweetAlertDialog warningMessage(Context ctx, String title, String content,String btnText){
        return new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(btnText);

    }


    /**
     * Success message
     * @param ctx
     * @param title
     * @param content
     * @return
     */
    public static SweetAlertDialog successMessage(Context ctx, String title, String content){
        return new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content);


    }


    /**
     * Message with custom icon
     * @param ctx
     * @param title
     * @param content
     * @param icon
     * @return
     */
    public static SweetAlertDialog customIconMessage(Context ctx, String title, String content, int icon){
        return new SweetAlertDialog(ctx, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setCustomImage(icon);
    }


    /**
     * Message with click listener
     * @param ctx
     * @param title
     * @param content
     * @param btnText
     * @param listener sDialog.dismissWithAnimation();
     * @return
     */
    public static SweetAlertDialog confirmButtonListenerMessage(Context ctx, String title, String content,
                                            String btnText,SweetAlertDialog.OnSweetClickListener listener){
        return new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(btnText)
                .setConfirmClickListener(listener);
    }


    /**
     * Confirm and cancel button message dialog
     * @param ctx
     * @param title
     * @param content
     * @param btnConfirm
     * @param btnCancel
     * @param listener
     * @return
     */
    public static SweetAlertDialog confirmCancelBtnListenerMessage(Context ctx, String title,String content,
                                                                   String btnConfirm, String btnCancel,
                                                                   SweetAlertDialog.OnSweetClickListener listener){

        return new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setCancelText(btnCancel)
                .setConfirmText(btnConfirm)
                .showCancelButton(true)
                .setConfirmClickListener(listener);
    }


    /**
     * Dialog change after confirm
     * @param ctx
     * @param title
     * @param content
     * @param btnConfirm
     * @param sDialog
     */
    public static void changeDialogUponConfirm(Context ctx,String title, String content,
                                                           String btnConfirm, SweetAlertDialog sDialog,String change){

        int chg=0;
        switch(change){
            case Utils.SUCCESS :
                chg = SweetAlertDialog.SUCCESS_TYPE;
                break;
            case Utils.NOT_SUCCESS:
                chg = SweetAlertDialog.ERROR_TYPE;
                break;


        }


                sDialog
                        .setTitleText(title)
                        .setContentText(content)
                        .setConfirmText(btnConfirm)
                        .showCancelButton(false)
                        .setConfirmClickListener(null)
                        .changeAlertType(chg);
    }


}
