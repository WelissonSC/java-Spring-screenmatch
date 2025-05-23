package com.ws.screenmatch.service;

public interface IDataConverter {
    //isso é um generics, vai me retornar um dado que eu n sei ainda que tipo é
    <T> T catData(String json, Class<T> classe);
}
