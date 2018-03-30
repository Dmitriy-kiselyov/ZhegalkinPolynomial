package com.pussy_penetrator.descretemath.zhegalkinpolinomial;

/**
 * Created by Pussy_penetrator on 08.01.2016.
 */
public class PolinomLab {

    private static Polinom mPolinom;

    private PolinomLab() {
        //do nothing
    }

    public static Polinom get() {
        if (mPolinom == null)
            mPolinom = new Polinom();
        return mPolinom;
    }
}
