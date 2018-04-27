/*
Student Number: 10383630
Student Name:   Baiju John
*/

package com.DBS.MobileApp;

public interface NetworkCallback {
    void processJSONString(String jsonString, String endpoint);
    void dataFetchStarted(String endpoint);
}
