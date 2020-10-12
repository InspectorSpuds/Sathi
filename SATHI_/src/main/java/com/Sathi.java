package com;

/**
 * @Authors: Ishan Parikh, Bhagyashree Aras, Alex Wallen
 *
 *Notes on things you should avoid during development:
 *-avoid static field variables or methods, they persist through the entirety
 * of a program's life and can cause memory leaks (this program will be running 24/7)
 *
 **/
public class Sathi {
    public static void main(String[] args) throws Exception {
        StateController mainController = new StateController();
        mainController.start();
        //App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
    }
}
