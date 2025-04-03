package com.ratna.batch.springbatchprocessing.config;

import com.ratna.batch.springbatchprocessing.model.StudentCsv;
import com.ratna.batch.springbatchprocessing.writer.StudentWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class JobConfig {

    @Value("${inputfile}")
    private String inputValue;

    @Value("${inputjsonfile}")
    private String inputjsonFile;

    @Value("${inputxmlfile}")
    private String inputxmlfile;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private StudentWriter studentWriter;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job chunkJob() {
        return jobBuilderFactory.get("Chunk Job")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();
    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First chunk Step")
                .<StudentCsv, StudentCsv>chunk(2)
                .reader(jdbcCursonItemReader())
                .writer(studentWriter)
                .build();
    }


    // fieldsetmapper - line defaultlinemapper - linetokenizer
    @StepScope
    @Bean
    public FlatFileItemReader<StudentCsv> flatFileItemReader() {
        FlatFileItemReader<StudentCsv> flatFileItemReader =
                new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource(inputValue));
        DefaultLineMapper<StudentCsv> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames("id", "firstName", "lastName", "email");
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSet -> {
            StudentCsv studentCsv = new StudentCsv();
            studentCsv.setId(fieldSet.readLong("id"));
            studentCsv.setFirstName(fieldSet.readString("firstName"));
            studentCsv.setLastName(fieldSet.readString("lastName"));
            studentCsv.setEmail(fieldSet.readString("email"));
            return studentCsv;
        });
        flatFileItemReader.setLineMapper(defaultLineMapper);
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    @StepScope
    @Bean
    public JsonItemReader<StudentCsv> jsonFileItemReader() {
        JsonItemReader<StudentCsv> jsonItemReader = new JsonItemReader<>();
        jsonItemReader.setResource(new ClassPathResource(inputjsonFile));
        jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentCsv.class));
        return jsonItemReader;
    }

    public JdbcCursorItemReader<StudentCsv> jdbcCursonItemReader() {
        JdbcCursorItemReader<StudentCsv> jdbcCursorItemReader = new JdbcCursorItemReader<>();
        jdbcCursorItemReader.setDataSource(dataSource);
        jdbcCursorItemReader.setSql("select * from student");
        jdbcCursorItemReader.setRowMapper((resultSet, i) -> {
            StudentCsv studentCsv = new StudentCsv();
            studentCsv.setId(resultSet.getLong("id"));
            studentCsv.setFirstName(resultSet.getString("first_name"));
            studentCsv.setLastName(resultSet.getString("last_name"));
            studentCsv.setEmail(resultSet.getString("email"));
            return studentCsv;
        });
        return jdbcCursorItemReader;
    }

}
