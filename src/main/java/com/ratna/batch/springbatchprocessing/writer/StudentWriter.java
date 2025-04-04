package com.ratna.batch.springbatchprocessing.writer;

import com.ratna.batch.springbatchprocessing.model.StudentResponse;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentWriter implements ItemWriter<StudentResponse> {
    @Override
    public void write(List<? extends StudentResponse> list) throws Exception {
        System.out.println("Inside Item Writer");
        list.forEach(System.out::println);
    }
}
