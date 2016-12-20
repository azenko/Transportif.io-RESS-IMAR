package com.esiea.vresscimar.transportifio;

/**
 * Created by vress on 13/12/2016.
 */

public class Station {
    private String id;
    private String label;

    public Station() {
    }

    public void setId(String _id)
    {
        this.id = _id;
    }

    public void setLabel(String _label)
    {
        this.label = _label;
    }

    public String toString() {
        return this.label;
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }
}
