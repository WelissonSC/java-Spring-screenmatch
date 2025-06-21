package com.ws.screenmatch.main;

import com.ws.screenmatch.model.*;
import com.ws.screenmatch.repository.SerieRepository;
import com.ws.screenmatch.service.ApiConsumer;
import com.ws.screenmatch.service.DataConvert;
import com.ws.screenmatch.model.Categoria;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private ApiConsumer consumer = new ApiConsumer();
    private DataConvert convert = new DataConvert();
    private Scanner sc = new Scanner(System.in);
    private final String ADRESS = "https://www.omdbapi.com/?t=";
    private final String APIKEY = System.getenv("OMDB-APIKEY");
    private List<SerieData> dataSerie = new ArrayList<>();
    private  List<Serie> series = new ArrayList<>();


    private SerieRepository repository;

    public Main(SerieRepository respositorio) {
        this.repository = respositorio;
    }


    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = "1 - Search for series " +
                    "\n2 - Search for episode " +
                    "\n3 - List series founds" +
                    "\n4 - Find series for title" +
                    "\n5 - Find series for actor" +
                    "\n6 - The top 5 series" +
                    "\n7 - Find for category" +
                    "\n8 - Find for seasons" +
                    "\n\n0- Out";

            System.out.println(menu);
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    SearchForSerie();
                    break;
                case 2:
                    catEpisodeForSerie();
                    break;
                case 3:
                    listSeriesSearch();
                    break;
                case 4:
                    findForTitle();
                    break;
                case 5:
                    findForActor();
                    break;
                case 6:
                    findTop5Series();
                    break;
                case 7:
                    findForCategory();
                    break;
                case 8:
                    findForTotalTemps();
                    break;
                case 0:
                    System.out.println("Out...");
                    break;
                default:
                    System.out.println("invalid option");
            }
        }
    }




    private void SearchForSerie() {
        SerieData data = getDataSerie();
        Serie serie = new Serie(data);
        repository.save(serie);
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
        listSeriesSearch();
        System.out.println("Type the serie for the name");
        var nomeSerie = sc.nextLine();

        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {

            var serieFound = serie.get();
            List<SeasonData> seasons = new ArrayList<>();

            for (int i = 1; i <= serieFound.getTotalTemporadas(); i++) {
                var json = consumer.catData(ADRESS + serieFound.getTitulo().replace(" ", "+") + "&season=" + i + APIKEY);
                SeasonData dataSeason = convert.catData(json, SeasonData.class);
                seasons.add(dataSeason);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.number(), e)))
                    .collect(Collectors.toList());
            serieFound.setEpisodes(episodes);
            repository.save(serieFound);
        } else {
            System.out.println("Serie not found!");
        }
    }

    private void listSeriesSearch() {

       series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void findForTitle() {
        System.out.println("Chose serie for the name");
        var nomeSerie = sc.nextLine();

        Optional<Serie> serieBuscada = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Data of serie: " + serieBuscada.get());
        } else {
            System.out.println("Not found");
        }
    }

    private void findForActor() {
        System.out.println("Type the name of Actor");
        var nomeAtor = sc.nextLine();

        List<Serie> serieFound = repository.findByAtoresContainingIgnoreCase(nomeAtor);
        serieFound.forEach(serie -> System.out.println(serie.getTitulo() + " " + serie.getAvaliacao()));
    }

    private void findTop5Series() {
       List<Serie> top5 = repository.findTop5ByOrderByAvaliacaoDesc();
       top5.forEach(serie -> System.out.println(serie.getTitulo() + " " + serie.getAvaliacao()));
    }

    private void findForCategory() {
        System.out.println("Do you wish get series for category/genger?");
        var genger = sc.nextLine();
        Categoria categoria = Categoria.fromStringPt(genger);
        List<Serie> category = repository.findByGenero(categoria);
        System.out.println("Series of category " + genger);
        category.forEach(System.out::println);
    }

    private void findForTotalTemps() {
        System.out.println("How many seasons do you wana watch for a serie?");
        Integer temps = sc.nextInt();
        List<Serie> serie = repository.findByTotalTemporadasAndAvaliacaoGreaterThanEqualOrderByAvaliacaoDesc(temps, 8.5);
        serie.forEach(s -> System.out.println(s.getTitulo() + " Seasons: " + s.getTotalTemporadas() + " Assessment: " + s.getAvaliacao()));
    }

}
