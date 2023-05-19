package com.app.nursecalling.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserModel extends SugarRecord implements Serializable {
    public long id;
    public String channelId;
    public String Password;


    public UserModel(){}

    public UserModel(long id, String channelId, String password) {
        this.id = id;
        this.channelId = channelId;
        Password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", channelId='" + channelId + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
