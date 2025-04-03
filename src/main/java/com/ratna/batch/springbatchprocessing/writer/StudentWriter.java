package com.ratna.batch.springbatchprocessing.writer;

import com.ratna.batch.springbatchprocessing.model.StudentCsv;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentWriter implements ItemWriter<StudentCsv> {
    @Override
    public void write(List<? extends StudentCsv> list) throws Exception {
        System.out.println("CSV File Writer");
        list.forEach(System.out::println);
    }
}
