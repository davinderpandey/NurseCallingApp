package com.app.nursecalling.model;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class EventsModel extends SugarRecord implements Serializable {
    public long id;
    public String eventName;
    public String systemName;

    public String username;
    public String address;
    public LocalDateTime date;

    public EventsModel(){}

    public EventsModel(long id, String eventName, String systemName, String username, LocalDateTime time) {
        this.id = id;
        this.eventName = eventName;
        this.systemName = systemName;
        this.username = username;
        this.date = time;
    }

    @Override
    public String toString() {
        return "EventsModel{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", systemName='" + systemName + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", date=" + date +
                '}';
    }
}
