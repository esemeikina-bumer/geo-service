package ru.netology.i18n;

import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalizationServiceImplTest {
    private final LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

    // Тест для России - должно вернуть русский текст
    @Test
    void locale_WithRussia_ShouldReturnRussianText() {
        // Act
        String result = localizationService.locale(Country.RUSSIA);
        // Assert
        assertEquals("Добро пожаловать", result);
    }
    @Test
    void locale_WithUsa_ShouldReturnEnglishText() {
        // Act
        String result = localizationService.locale(Country.USA);
        // Assert
        assertEquals("Welcome", result);
    }

    @Test
    void locale_WithGermany_ShouldReturnEnglishText() {
        // Act
        String result = localizationService.locale(Country.GERMANY);
        // Assert
        assertEquals("Welcome", result);
    }

    @Test
    void locale_WithBRAZIL_ShouldReturnEnglishText() {
        String result = localizationService.locale(Country.BRAZIL);
        assertEquals("Welcome", result);
    }


    @ParameterizedTest
    @EnumSource(value = Country.class, names = {"RUSSIA"}, mode = EnumSource.Mode.EXCLUDE)
    void locale_WithNonRussianCountries_ShouldReturnEnglishText(Country country) {
        String result = localizationService.locale(country);
        assertEquals("Welcome", result);
    }




}
