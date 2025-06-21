package com.ws.screenmatch;

import com.ws.screenmatch.main.Main;
import com.ws.screenmatch.model.EpisodesData;
import com.ws.screenmatch.model.SeasonData;
import com.ws.screenmatch.model.SerieData;
import com.ws.screenmatch.repository.SerieRepository;
import com.ws.screenmatch.service.ApiConsumer;
import com.ws.screenmatch.service.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    @Autowired
    private SerieRepository repositorio;

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Main main = new Main(repositorio);
        main.showMenu();

    }
}
