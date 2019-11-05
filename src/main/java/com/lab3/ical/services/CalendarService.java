package com.lab3.ical.services;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class CalendarService {

    public void createNewCalendarFile(List<String> dates, List<String> summaries, int month) throws IOException {
        File file = createNewFile();
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(CalendarUtils.CAL_BEGIN);
        bufferedWriter.write(CalendarUtils.VERSION);
        bufferedWriter.write(CalendarUtils.PROD_ID);
        bufferedWriter.write(CalendarUtils.CAL_SCALE);
        bufferedWriter.write(CalendarUtils.METHOD);

        writeFile(dates, summaries, month, bufferedWriter);

        bufferedWriter.write(CalendarUtils.CAL_END);
        bufferedWriter.close();
    }

    private void writeFile(List<String> dates, List<String> summaries, int month, BufferedWriter bufferedWriter) throws IOException {
        for (int i = 0; i < dates.size(); i++) {
            bufferedWriter.write(CalendarUtils.EVENT_BEGIN);
            bufferedWriter.write(CalendarUtils.SUMMARY + summaries.get(i) + "\n");
            if (dates.get(i).length() == 1) {
                bufferedWriter.write(CalendarUtils.DATE_START + month + "0" + dates.get(i) + "\n");
                bufferedWriter.write(CalendarUtils.DATE_END + month + "0" +dates.get(i) + "\n");
            }
            else {
                bufferedWriter.write(CalendarUtils.DATE_START + month + dates.get(i) + "\n");
                bufferedWriter.write(CalendarUtils.DATE_END + month +dates.get(i) + "\n");
            }
            bufferedWriter.write(CalendarUtils.EVENT_END);
        }
    }

    private File createNewFile() throws IOException {
        File file = new File(CalendarUtils.FILE_NAME);

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

}