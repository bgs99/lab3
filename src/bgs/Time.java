package bgs;

import java.util.Date;

public class Time {
    private Date now = new Date(System.currentTimeMillis());
    public void listener(){
        now = new Date(System.currentTimeMillis());
    }
    public Date getNow(){
        return now;
    }
}
