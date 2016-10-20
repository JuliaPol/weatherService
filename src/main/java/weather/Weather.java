package weather;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by Julia on 20.10.2016.
 */
public class Weather {
    public static String weather(String idCity) throws JAXBException, IOException {
        YahooWeatherService service = new YahooWeatherService();
        Channel result = service.getForecast(idCity, DegreeUnit.CELSIUS);
        System.out.println(result.getDescription());
        System.out.println(result.getTitle());
        System.out.println(result.getWind());
        System.out.println(result.getAtmosphere());
        System.out.println(result.getLink());
        System.out.println(result.getItem().getForecasts());
        System.out.println(result.getItem().getCondition());
        return result.getItem().getCondition().toString();
    }
}
