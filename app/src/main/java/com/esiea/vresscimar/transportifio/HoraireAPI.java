package com.esiea.vresscimar.transportifio;

/**
 * Created by vress on 14/12/2016.
 */

public class HoraireAPI {
    private String date;
    //private String mode;
    private String trainNum;
    private String trainMiss;
    private String terminus;

    public HoraireAPI()
    {
        this.date = "";
        //this.mode = "";
        this.trainNum = "";
        this.trainMiss = "";
        this.terminus = "";
    }

    public void setDate(String _date) {
        this.date = _date;
    }

    //public void setMode(String _mode) {
    //    this.mode = _mode;
    //}

    public void setTrainNum(String _trainNum) {
        this.trainNum = _trainNum;
    }

    public void setTrainMiss(String _trainMiss) {
        this.trainMiss = _trainMiss;
    }

    public void setTerminus(String _terminus) {
        this.terminus = _terminus;
    }

    public String getDate() {
        return this.date;
    }

    //public String getMode() {
    //    return this.mode;
    //}

    public String getTrainNum() {
        return this.trainNum;
    }

    public String getTrainMiss() {
        return this.trainMiss;
    }

    public String getTerminus() {
        return this.terminus;
    }

    public String getTimeAndStation() {
        return this.date + " - " + this.terminus;
    }

    public String getTrainCode() {
        return this.trainMiss + " " + this.trainNum;
    }

}
