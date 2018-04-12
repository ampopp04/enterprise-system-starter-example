package com.starter.runner;


import com.system.inversion.InversionContainer;

/**
 * The <class>StarterRunner</class> runs the application
 *
 * @author Andrew
 */
public class StarterRunner {
    /**
     * Run the application
     *
     * @param args
     */
    public static void main(String[] args) {
        InversionContainer.startInversion(args);
    }
}