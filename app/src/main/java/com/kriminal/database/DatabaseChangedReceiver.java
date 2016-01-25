package com.kriminal.database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Kriminal on 25/01/2016.
 *
 *   ~ *******************************************************************************
 ~   Copyright (c) 2016 kriminal666.
 ~
 ~   Licensed under the Apache License, Version 2.0 (the "License");
 ~   you may not use this file except in compliance with the License.
 ~   You may obtain a copy of the License at
 ~
 ~   http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~   Unless required by applicable law or agreed to in writing, software
 ~   distributed under the License is distributed on an "AS IS" BASIS,
 ~   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~   See the License for the specific language governing permissions and
 ~   limitations under the License.
 ~  *****************************************************************************
 */
public class DatabaseChangedReceiver extends BroadcastReceiver {

    public static String ACTION_DATABASE_CHANGED="com.kriminal.database.DatabaseChangedReceiver.DATABASE_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
