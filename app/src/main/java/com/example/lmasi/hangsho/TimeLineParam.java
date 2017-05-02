package com.example.lmasi.hangsho;

/**
 * Created by lmasi on 2016-10-08.
 */
public class TimeLineParam extends HangShowLayoutParam {

    TimeLinePlace parent = null;


    public TimeLineParam(double w, double h) {
        super(w, h);
    }

    public TimeLineParam addRules(int verb) {
        super.addRule(verb);

        return this;
    }

    public TimeLineParam setMargin(double left, double top, double right, double bottom)
    {
        super.setMargins((int)left, (int)top, (int)right, (int)bottom);
        return this;
    }

    public TimeLineParam addRules(int verb, int subject) {
        super.addRule(verb, subject);

        return this;
    }


    public void setParent(TimeLinePlace parent) {
        this.parent = parent;
    }

    public TimeLinePlace getParent() {
        return parent;
    }

    public boolean isNear(TimeLinePlace other)
    {
        return (this.topMargin < other.getY() && other.getY() < this.topMargin + this.height);
    }



}
