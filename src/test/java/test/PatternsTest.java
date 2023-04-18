package test;
import data.DataGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class PatternsTest {
    @BeforeEach
    void setup() {open("http://localhost:9999/");
    }

    @Test
    void test1() {

        DataGenerator.UserInfo validUser = DataGenerator.Registation.generateUser("ru");
        int daysToAddFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddFirstMeeting);
        int daysToAddSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddSecondMeeting);
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'].notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на" + firstMeetingDate))
                .shouldBe(visible);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(secondMeetingDate);
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'].notification__content")
                .shouldHave(exactText("У вас уже запланирована встреча на другую датую. Перепланировать?"))
                .shouldBe(visible);
        $("[data-test-id='replan-notification'] buttton").click();
        $("[data-test-id='success-notification'].notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на" + secondMeetingDate))
                .shouldBe(visible);

    }
}
