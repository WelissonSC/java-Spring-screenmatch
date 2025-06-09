package com.ws.screenmatch.main;

import com.ws.screenmatch.model.*;
import com.ws.screenmatch.service.ApiConsumer;
import com.ws.screenmatch.service.DataConvert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private ApiConsumer consumer = new ApiConsumer();
    private DataConvert convert = new DataConvert();
    private Scanner sc = new Scanner(System.in);
    private final String ADRESS = "https://www.omdbapi.com/?t=";
    private final String APIKEY = "&apikey=be919000";
    private List<SerieData> dataSerie = new ArrayList<>();

    public void showMenu() {
        var option = -1;
        var menu = "1 - Search for series " +
                "\n2 - Search for episode " +
                "\n3 - List series founds" +
                "\n\n0- Out";

        System.out.println(menu);
        option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                SearchForSerie();
                break;
            case 3:
                listSeriesSearch();
                break;
            case 2:
                catEpisodeForSerie();
                break;
            case 0:
                System.out.println("Out...");
                break;
            default:
                System.out.println("invalid option");
        }

    }

    private void SearchForSerie() {
        SerieData data = getDataSerie();
        System.out.println(data);
    }

    private SerieData getDataSerie() {
        System.out.println("Type the name serie for search");
        var nameSerie = sc.nextLine();
        var json = consumer.catData(ADRESS + nameSerie.replace(" ", "+") + APIKEY);
        SerieData data = convert.catData(json, SerieData.class);
        return data;
    }

    private void catEpisodeForSerie() {
        SerieData dataSerie = getDataSerie();
        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1 ; i <= dataSerie.totalTemps() ; i++) {
            var json = consumer.catData(ADRESS + dataSerie.title().replace(" ", "+" + "&season=" + i + APIKEY));
            SeasonData dataSeason = convert.catData(json, SeasonData.class);
            seasons.add(dataSeason);
        }

        seasons.forEach(System.out::println);
    }

    private void listSeriesSearch() {

        List<Serie> series = new ArrayList<>();
        series = dataSerie.stream()
                .map(d -> new Serie(d))
                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

}
