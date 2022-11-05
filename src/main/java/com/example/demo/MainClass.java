package com.example.demo;

import static com.example.demo.FootballMatches.getTotalGoals;
import static com.example.demo.FootballMatches.getWinnerTotalGoals;
import static com.example.demo.MedicalReports.avgPulseRate;

public class MainClass {

    public static void main(String[] args) {
        String diagnosisName = "Pulmonary embolism";
        int doctorId = 2;
        System.out.println(avgPulseRate(diagnosisName, doctorId));

        String team = "Barcelona";
        int year = 2011;
        System.out.println(getTotalGoals(team, year));

        String competition = "UEFA Champions League";
        year = 2012;
        System.out.println(getWinnerTotalGoals(competition, year));
    }

}
