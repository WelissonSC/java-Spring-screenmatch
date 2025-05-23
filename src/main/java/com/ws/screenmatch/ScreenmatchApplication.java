package com.ws.screenmatch;

import com.ws.screenmatch.model.SerieData;
import com.ws.screenmatch.service.ApiConsumer;
import com.ws.screenmatch.service.DataConvert;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ApiConsumer apiConsumer = new ApiConsumer();
		String json = apiConsumer.catData("https://www.omdbapi.com/?t=Whiplash&apikey=be919000");
		//System.out.println(json);
		DataConvert convert = new DataConvert();
		SerieData data = convert.catData(json, SerieData.class);
		System.out.println(data);
	}
}
