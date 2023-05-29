package com.example.demo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FootballMatches {

    private static final String MATCH_URL_1 = "https://jsonmock.hackerrank.com/api/football_matches";
    private static final String MATCH_URL_2 = "https://jsonmock.hackerrank.com/api/football_competitions";

    public static int getTotalGoals(String team, int year) {
        int cnt = 0;
        int page = 1;
        int tot_pages = 1;
        try {
            team = URLEncoder.encode(team, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }
        while (page <= tot_pages) {
            for (int type = 1; type <= 2; type++) {
                try {

                    URL url = new URL(String.format(MATCH_URL_1 + "?year=%d&team%d=%s", year, type, team));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    //Getting the response code
                    int responseCode = conn.getResponseCode();

                    if (responseCode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responseCode);
                    } else {

                        String inline = "";
                        Scanner scanner = new Scanner(url.openStream());

                        //Write all the JSON data into a string using a scanner
                        while (scanner.hasNext()) {
                            inline += scanner.nextLine();
                        }

                        //Close the scanner
                        scanner.close();

                        //Using the JSON simple library parse the string into a json object
                        JSONParser parse = new JSONParser();
                        JSONObject data_obj = (JSONObject) parse.parse(inline);

                        tot_pages = Integer.parseInt(data_obj.get("total_pages").toString());

                        JSONArray data = (JSONArray) data_obj.get("data");

                        for (int i = 0; i < data.size(); i++) {

                            JSONObject new_obj = (JSONObject) data.get(i);
                            if (type == 1) {
                                cnt += Integer.parseInt(new_obj.get("team1goals").toString());
                            } else {
                                cnt += Integer.parseInt(new_obj.get("team2goals").toString());
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            page++;
        }
        return cnt;
    }


    public static String getWinner(String competition, int year) {
        String winner = "";

        try {
            competition = URLEncoder.encode(competition, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }
        try {

            URL url = new URL(String.format(MATCH_URL_2 + "?name=%s&year=%d", competition, year));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);
                JSONArray data = (JSONArray) data_obj.get("data");
                JSONObject new_obj = (JSONObject) data.get(0);

                winner = new_obj.get("winner").toString();
                try {
                    winner = URLEncoder.encode(winner, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new AssertionError("UTF-8 is unknown");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return winner;

    }

    public static int getWinnerTotalGoals(String competition, int year) {
        String team = getWinner(competition, year);
        int cnt = 0;
        int page = 1;
        int tot_pages = 1;

        try {
            competition = URLEncoder.encode(competition, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("UTF-8 is unknown");
        }

        while (page <= tot_pages) {
            for (int type = 1; type <= 2; type++) {
                try {

                    URL url = new URL(String.format(MATCH_URL_1 + "?year=%d&team%d=%s&competition=%s&page=%d", year, type, team, competition, page));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();

                    //Getting the response code
                    int responseCode = conn.getResponseCode();

                    if (responseCode != 200) {
                        throw new RuntimeException("HttpResponseCode: " + responseCode);
                    } else {

                        String inline = "";
                        Scanner scanner = new Scanner(url.openStream());

                        //Write all the JSON data into a string using a scanner
                        while (scanner.hasNext()) {
                            inline += scanner.nextLine();
                        }

                        //Close the scanner
                        scanner.close();

                        //Using the JSON simple library parse the string into a json object
                        JSONParser parse = new JSONParser();
                        JSONObject data_obj = (JSONObject) parse.parse(inline);

                        tot_pages = Integer.parseInt(data_obj.get("total_pages").toString());

                        JSONArray data = (JSONArray) data_obj.get("data");

                        for (int i = 0; i < data.size(); i++) {

                            JSONObject new_obj = (JSONObject) data.get(i);
                            if (type == 1) {
                                cnt += Integer.parseInt(new_obj.get("team1goals").toString());
                            } else {
                                cnt += Integer.parseInt(new_obj.get("team2goals").toString());
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            page++;
        }

        return cnt;

    }
}
