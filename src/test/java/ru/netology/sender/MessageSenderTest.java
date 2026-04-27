import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import javax.annotation.processing.Messager;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MessageSenderTest {

    //Поверить, что MessageSenderImpl всегда отправляет только русский текст, если ip относится к российскому сегменту адресов.
    @Test
    void test_get_russia_message_if_ip_172() {
        ;
        //создаем моки (заглушки) для зависимостей
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);

        // Создаем РЕАЛЬНЫЙ объект MessageSenderImpl с моками
        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);

        // Подготавливаем тестовые данные
        String russianIp = "172.123.45.67";
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, russianIp);


        // Создаем тестовый Location для России
        Location russianLocation = new Location("Moscow", Country.RUSSIA, "Tverskaya", 10);

        // НАСТРАИВАЕМ ЗАГЛУШКИ - говорим мокам, что возвращать
        Mockito.when(geoServiceMock.byIp(russianIp)).thenReturn(russianLocation);
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать в Россию");

        // Вызываем тестируемый метод
        String result = messageSender.send(headers);

        // Проверяем результат
        assertEquals("Добро пожаловать в Россию", result);


    }

    @Test
    void test_get_usa_message_if_ip_96() {
        //создаем моки (заглушки) для зависимостей
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);

        // Создаем РЕАЛЬНЫЙ объект MessageSenderImpl с моками
        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);

        // Подготавливаем тестовые данные
        String usaIp = "96.100.11.69";
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, usaIp);
        // Создаем тестовый Location для США
        Location usaLocation = new Location("New York", Country.USA, "5th Avenue", 20);

        // НАСТРАИВАЕМ ЗАГЛУШКИ
        Mockito.when(geoServiceMock.byIp(usaIp)).thenReturn(usaLocation);
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");

        // Вызываем тестируемый метод
        String result = messageSender.send(headers);

        // Проверяем результат
        assertEquals("Welcome", result);


    }



    @Test
    void test_get_usa_message_if_ip_16() {
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);
        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        String anyIp = "16.100.11.69";

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, anyIp);
        // Создаем тестовый Location
        Location usaLocation = new Location("Berlin", Country.GERMANY, "Strasse", 20);
        Mockito.when(geoServiceMock.byIp(anyIp)).thenReturn(usaLocation);

        Mockito.when(localizationServiceMock.locale(Country.GERMANY)).thenReturn("Welcome");

        String result = messageSender.send(headers);

        assertEquals("Welcome", result);

    }


}
