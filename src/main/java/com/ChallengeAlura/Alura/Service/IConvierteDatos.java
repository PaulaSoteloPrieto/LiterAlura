package com.ChallengeAlura.Alura.Service;

public interface IConvierteDatos{
    <T> T obtenerDatos (String json, Class<T> clase);

}
