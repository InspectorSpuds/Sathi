package com.Message;

import com.google.gson.Gson;
import com.slack.api.model.view.View;
import com.slack.api.util.json.GsonFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;

/* @Author Ishan Parikh
   Purpose: This is a collection class of global scale constant pre-designed messages
   used as basic storage for abstraction without actually cluttering
   the main class.

   Note:
   -I would recommend statically importing this, helps with abstraction and
    this class ideally should never be instantiated if you're being mindful
    of productivity

    static import: ie. you can write MODAL_VIEW instead of
    MessageCollection.MODAL_VIEW Kind of a productivity booster imo
    Views: modal message storage container for the Slack API
    Modal: Focused mini-window in Slack that functions as primarily a form
    submission method

    Notes for the future:
    Internship sites commonly used:
    -LinkedIn
    -StackOverflow
    -Indeed
* */
public class MessageCollection {
    public static final View SCHEDULER_VIEW = buildSchedulerView();
    public static final View NOTIFICATION_MANAGER_VIEW = null;
    public static final View INTERNSHIP_SCRAPER_VIEW = buildInternshipCreatorView();
    public static final View DELETE_ANNOUNCEMENT_VIEW = null;

    private static View buildSchedulerView() {

        Date myDate = new Date();
        SimpleDateFormat datePickerFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timePickerFormat = new SimpleDateFormat("HH:mm");
        return view(view -> view
                .callbackId("Scheduler")
                .type("modal")
                .title(viewTitle(title -> title.type("plain_text").text("Create Event").emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Create").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .blocks(asBlocks(
                        section(section -> section
                                .blockId("Event-Selector")
                                .text(markdownText("Select an Event"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId("Event-Type")
                                        .placeholder(plainText("Select an item"))
                                        .options(asOptions(
                                                option(plainText("Regular Event"),"Regular Event"),
                                                option(plainText("Annual Event"), "Repeating Event"),
                                                option(plainText("Internships"), "Internships")
                                        ))
                                ))
                        ),
                        section(section -> section
                                .blockId("Datepicker")
                                .text(markdownText("Pick a date for when event occurs:"))
                                .accessory(datePicker(datePicker -> datePicker
                                        .initialDate(datePickerFormat.format(myDate))
                                        .placeholder(plainText("Select a date"))
                                        .actionId("datepicker")
                                ))
                        ),
                        section(section -> section
                                .blockId("Hour_Time")
                                .text(markdownText("Select the Hour time"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId("Hour")
                                        .placeholder(plainText("Select the Hour time"))
                                        .options(asOptions(
                                                option(plainText("0"),"0"),
                                                option(plainText("1"),"1"),
                                                option(plainText("2"),"2"),
                                                option(plainText("3"),"3"),
                                                option(plainText("4"),"4"),
                                                option(plainText("5"),"5"),
                                                option(plainText("6"),"6"),
                                                option(plainText("7"),"7"),
                                                option(plainText("8"),"8"),
                                                option(plainText("9"),"9"),
                                                option(plainText("10"),"10"),
                                                option(plainText("11"),"11"),
                                                option(plainText("12"),"12"),
                                                option(plainText("13"),"13"),
                                                option(plainText("14"),"14"),
                                                option(plainText("15"),"15"),
                                                option(plainText("16"),"16"),
                                                option(plainText("17"),"17"),
                                                option(plainText("18"),"18"),
                                                option(plainText("19"),"19"),
                                                option(plainText("20"),"20"),
                                                option(plainText("21"),"21"),
                                                option(plainText("22"),"22"),
                                                option(plainText("23"),"23")
                                        ))
                                ))
                        ),
                        section(section -> section
                                .blockId("Minute-Time")
                                .text(markdownText("Select Minute Time"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId("Minute")
                                        .placeholder(plainText("Select Minute Time"))
                                        .options(asOptions(
                                                option(plainText("1"),"1"),
                                                option(plainText("2"),"2"),
                                                option(plainText("3"),"3"),
                                                option(plainText("4"),"4"),
                                                option(plainText("5"),"5"),
                                                option(plainText("6"),"6"),
                                                option(plainText("7"),"7"),
                                                option(plainText("8"),"8"),
                                                option(plainText("9"),"9"),
                                                option(plainText("10"),"10"),
                                                option(plainText("11"),"11"),
                                                option(plainText("12"),"12"),
                                                option(plainText("13"),"13"),
                                                option(plainText("14"),"14"),
                                                option(plainText("15"),"15"),
                                                option(plainText("16"),"16"),
                                                option(plainText("17"),"17"),
                                                option(plainText("18"),"18"),
                                                option(plainText("19"),"19"),
                                                option(plainText("20"),"20"),
                                                option(plainText("21"),"21"),
                                                option(plainText("22"),"22"),
                                                option(plainText("23"),"23"),
                                                option(plainText("24"),"24"),
                                                option(plainText("25"),"25"),
                                                option(plainText("26"),"26"),
                                                option(plainText("27"),"27"),
                                                option(plainText("28"),"28"),
                                                option(plainText("29"),"29"),
                                                option(plainText("30"),"30"),
                                                option(plainText("31"),"31"),
                                                option(plainText("32"),"32"),
                                                option(plainText("33"),"33"),
                                                option(plainText("34"),"34"),
                                                option(plainText("35"),"35"),
                                                option(plainText("36"),"36"),
                                                option(plainText("37"),"37"),
                                                option(plainText("38"),"38"),
                                                option(plainText("39"),"39"),
                                                option(plainText("40"),"40"),
                                                option(plainText("41"),"41"),
                                                option(plainText("42"),"42"),
                                                option(plainText("43"),"43"),
                                                option(plainText("44"),"44"),
                                                option(plainText("45"),"45"),
                                                option(plainText("46"),"46"),
                                                option(plainText("47"),"47"),
                                                option(plainText("48"),"48"),
                                                option(plainText("49"),"49"),
                                                option(plainText("50"),"50"),
                                                option(plainText("51"),"51"),
                                                option(plainText("52"),"52"),
                                                option(plainText("53"),"53"),
                                                option(plainText("54"),"54"),
                                                option(plainText("55"),"55"),
                                                option(plainText("56"),"56"),
                                                option(plainText("57"),"57"),
                                                option(plainText("58"),"58"),
                                                option(plainText("59"),"59")
                                        ))
                                ))
                        ),
                        input(input -> input
                                .blockId("Agenda_Block")
                                .element(plainTextInput(pti -> pti.actionId("Agenda").multiline(true)))
                                .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
                        )
                ))

        );
    }

    private static View buildNotificationManagerView() {
        return null;
    }

    private static View buildInternshipCreatorView() {
        return null;
    }
}