package com.mvp.mobexs.mvp_test.model;

/**
 * Created by Oleg Tarashkevich on 27/03/2017.
 */

public interface IUser {

    String getName();
    String getPassword();
    boolean validate(String name, String password);
}