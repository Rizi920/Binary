package com.example.jobportal.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailsParceableObject implements Parcelable {


    String first_name;
    String last_name;
    String email;
    String phone_no;
    String gender;
    String dob;
    String id;
    String image;

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getId() {
        return id;
    }

    public DetailsParceableObject(String first_name, String last_name, String email, String phone_no, String dob, String gender, String id,String image) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_no = phone_no;
        this.dob = dob;
        this.gender = gender;
        this.id = id;
        this.image=image;
    }


    //parcel part
    public DetailsParceableObject(Parcel in) {
        String[] data = new String[8];

        in.readStringArray(data);
        first_name = data[0];
        last_name = data[1];
        email = data[2];
        phone_no = data[3];
        dob = data[4];
        gender = data[5];
        id = data[6];
        image = data[7];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{this.first_name, this.last_name, this.email, this.phone_no, this.dob, this.gender, this.id, this.image});
    }

    public static final Parcelable.Creator<DetailsParceableObject> CREATOR = new Parcelable.Creator<DetailsParceableObject>() {

        @Override
        public DetailsParceableObject createFromParcel(Parcel source) {
            return new DetailsParceableObject(source);  //using parcelable constructor
        }

        @Override
        public DetailsParceableObject[] newArray(int size) {
            return new DetailsParceableObject[size];
        }
    };
}
