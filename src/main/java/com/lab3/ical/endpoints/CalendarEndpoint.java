package com.lab3.ical.endpoints;

import com.lab3.ical.services.CalendarService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CalendarEndpoint {

    private CalendarService calendarService;

    @Autowired
    public CalendarEndpoint(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/calendar/{month}")
    public @ResponseBody ResponseEntity<?> fetchCalendarFileInICalFormat(@PathVariable int month) throws IOException {
        List<String> eventsInCalendar = fetchEventsFromSpecificCalendar(month);
        List<String> summariesForEventsInCalendar = fetchSummariesForEventsInCalendar(month);
        calendarService.createNewCalendarFile(eventsInCalendar, summariesForEventsInCalendar, month);

        return null;
    }

    private List<String> fetchSummariesForEventsInCalendar(int month) throws IOException {
        List<String> summaries = new ArrayList<>();
        Document document = Jsoup.connect("http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=2019&miesiac=" + month + "&lang=1").get();
        Elements elements = document.select("div.InnerBox");

        for (Element element : elements) {
            summaries.add(element.text());
        }

        return summaries;
    }

    private List<String> fetchEventsFromSpecificCalendar(int month) throws IOException {
        Document document = Jsoup.connect("http://www.weeia.p.lodz.pl/pliki_strony_kontroler/kalendarz.php?rok=2019&miesiac=" + month + "&lang=1").get();
        Elements elements = document.select("a.active");
        List<String> events = new ArrayList<>();

        for (Element element : elements) {
            events.add(element.text());
        }

        return events;
    }

}