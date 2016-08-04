package com.tanap.retrofit2rxandroid.model;

import org.parceler.Parcel;

/**
 * Created by trusttanapruk on 7/27/2016.
 */
@Parcel
public class ProfileDao extends StatusDao {
    public ProfileDao() {
    }

    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
