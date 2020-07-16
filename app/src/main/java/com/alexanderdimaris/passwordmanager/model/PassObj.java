package com.alexanderdimaris.passwordmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PassObj implements Parcelable {

    private int id;
    private String title;
    private String username;
    private String password;
    private String comments;

    public PassObj (int id, String title, String username, String password, String comments) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String prettyPrint() {
        return "Title=" + this.getTitle() + " Username=" + this.getUsername() + " Password=" + this.getPassword() + " Comments=" + this.getComments();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //write all properties to the parcle
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(comments);
    }

    //constructor used for parcel
    public PassObj(Parcel parcel) {
        id = parcel.readInt();
        title = parcel.readString();
        username = parcel.readString();
        password = parcel.readString();
        comments = parcel.readString();
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<PassObj> CREATOR = new Parcelable.Creator<PassObj>() {

        @Override
        public PassObj createFromParcel(Parcel parcel) {
            return new PassObj(parcel);
        }

        @Override
        public PassObj[] newArray(int size) {
            return new PassObj[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}
