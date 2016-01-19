package com.kriminal.database;

/**
 * Created by Kriminal on 13/01/2016.
 * *******************************************************************************
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
 **/
public class Task {

    private int id;
    private String title;
    private String description;
    private String date;
    private String time;
    private String finished_date;
    private String finished_time;

    //Constructors
    public Task() {

    }

    public Task(int id, String title, String description, String date, String time, String finished_date, String finished_time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.finished_date = finished_date;
        this.finished_time = finished_time;
    }

    ////Getters & Setters//////


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setFinished_date(String finished_date) {
        this.finished_date = finished_date;
    }

    public void setFinished_time(String finished_time) {
        this.finished_time = finished_time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getFinished_date() {
        return finished_date;
    }

    public String getFinished_time() {
        return finished_time;
    }
}
