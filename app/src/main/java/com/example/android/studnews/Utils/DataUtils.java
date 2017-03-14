package com.example.android.studnews.Utils;

import com.example.android.studnews.Utils.Data;

import java.util.ArrayList;

/**
 * Created by kedee on 28/2/17.
 */

public class DataUtils {
    public static ArrayList<Data> newsList;
    public static ArrayList<String> content;
    public static ArrayList<String> category;
    static {
        newsList = new ArrayList<>();
        content = new ArrayList<>();
        category = new ArrayList<>();

        content.add("To qualify for appearing in exam, students need to have at least 75 per cent marks or be in top 20 percentile in Class XII examination. SC, ST students should have 65 per cent marks. “All other JEE examination systems shall remain unchanged,” the official said.");
        content.add("Virat Kohli will lead India in the upcoming ODI and T20I series against England, the BCCI selection committee announced on Friday, January 6.\n" +
                "Kohli will captain a 15-member Indian side for the ODIs, while also leading a 15-member squad for the T20I series against visitors England.\n" +
                "The right-handed batting talisman was handed the captain\'s role following MS Dhoni\'s decision to step down as skipper on Wednesday.");
        content.add("Indian Idol junior 2015 is generating tremendous buzz thanks to the brilliant singers fighting it out by stretching their vocal chords. The talent on display is unbelievable. These juniors can put eve");
        content.add("Some people seem to be really good at tests and exams. Some people are not. This could be because they haven't done enough work or studied hard enough but it can also mean that they really have problems doing exams or even tests.\n" +
                "Everyone feels nervous about tests and exams but some people just become so stressed that they do badly even though they have worked really hard and know the work.\n" +
                "Stress gets in the way of them being able to do their best.");
        content.add("Chesapeake Bay Summer Internship Program\n" +
                "\n" +
                " Chesapeake Bay Summer Internship Program.\n" +
                "Each summer, NOAA Chesapeake Bay Office, in partnership with the Chesapeake Research Consortium, offers several paid summer internships primarily geared toward current undergraduate students. Internships focus on scientific field research to resource management and policy.");
        content.add("To qualify for appearing in exam, students need to have at least 75 per cent marks or be in top 20 percentile in Class XII examination. SC, ST students should have 65 per cent marks. “All other JEE examination systems shall remain unchanged,” the official said.");
        content.add("Virat Kohli will lead India in the upcoming ODI and T20I series against England, the BCCI selection committee announced on Friday, January 6.\n" +
                "Kohli will captain a 15-member Indian side for the ODIs, while also leading a 15-member squad for the T20I series against visitors England.\n" +
                "The right-handed batting talisman was handed the captain\'s role following MS Dhoni\'s decision to step down as skipper on Wednesday.");
        content.add("Indian Idol junior 2015 is generating tremendous buzz thanks to the brilliant singers fighting it out by stretching their vocal chords. The talent on display is unbelievable. These juniors can put eve");
        content.add("Some people seem to be really good at tests and exams. Some people are not. This could be because they haven't done enough work or studied hard enough but it can also mean that they really have problems doing exams or even tests.\n" +
                "Everyone feels nervous about tests and exams but some people just become so stressed that they do badly even though they have worked really hard and know the work.\n" +
                "Stress gets in the way of them being able to do their best.");
        content.add("Chesapeake Bay Summer Internship Program\n" +
                "\n" +
                " Chesapeake Bay Summer Internship Program.\n" +
                "Each summer, NOAA Chesapeake Bay Office, in partnership with the Chesapeake Research Consortium, offers several paid summer internships primarily geared toward current undergraduate students. Internships focus on scientific field research to resource management and policy.");

        newsList.add(new Data(1, "Academic", "New Format of JEE", content.get(0), ""));
        newsList.add(new Data(2, "Games", "Indian Junior team in cricket final ", content.get(1), ""));
        newsList.add(new Data(3, "Entertainment", "Indian idol juniors are rocking", content.get(2), ""));
        newsList.add(new Data(4, "Health", "Health is more important than exams", content.get(3), ""));
        newsList.add(new Data(5, "Future", "New opportunities for students", content.get(4), ""));
        newsList.add(new Data(6, "Academic", "New format of JEE", content.get(0), ""));
        newsList.add(new Data(7, "Games", "India junior team in cricket final ", content.get(1), ""));
        newsList.add(new Data(8, "Entertainment", "Indian idol juniors are rocking", content.get(2), ""));
        newsList.add(new Data(9, "Health", "Health is more important than exams", content.get(3), ""));
        newsList.add(new Data(10, "Future", "New opportunities for students", content.get(4), ""));


        category.add("Trending");
        category.add("Exams");
        category.add("Career");
        category.add("Sports");
        category.add("Entertainment");
        category.add("Health");
        category.add("Motivation");
        category.add("Colleges");
        category.add("Schools");
        category.add("Books");
        category.add("Innovations");
        category.add("Achievements");
        category.add("Others");



    }


}
