package com.be.two.c.apibetwoc.service;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;
import java.util.concurrent.TimeUnit;

public class Carro implements ITempoPercurso {

    @Override
    public Long calcularTempoDeslocamento(Double x, Double y, Double toX, Double toY) {
        Geodesic geod = Geodesic.WGS84;
        GeodesicData result = geod.Inverse(x, y, toX, toY);

        return TimeUnit.MINUTES.toHours(Math.round(result.s12 / 3.0));
    }
}
