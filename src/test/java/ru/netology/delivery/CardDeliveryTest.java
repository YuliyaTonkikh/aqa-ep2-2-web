package ru.netology.delivery;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardDeliveryTest {

    @Test
    @DisplayName("Успешная отправка формы доставки карты")
    public void shouldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://127.0.0.1:9999/");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id=city] input").setValue("Абакан");

        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Тонких Юлия");
        $("[data-test-id=phone] input").setValue("+79999990000");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(Condition.text
                ("Успешно! Встреча успешно забронирована на " + deliveryDate), Duration.ofSeconds(15));

    }

    @Test
    @DisplayName("Ошибка при отправке формы доставки карты с пустым городом")
    public void shouldNotSendCity() {
        Configuration.holdBrowserOpen = true;
        open("http://127.0.0.1:9999/");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id=name] input").setValue("Тонких Юлия");
        $("[data-test-id=phone] input").setValue("+79999990000");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        String expectedText = "Поле обязательно для заполнения";
        String actualText = $("[data-test-id=city] .input__sub").getText().trim();
        assertEquals(expectedText, actualText);
    }

}