package com.weison.io.csv;

import com.opencsv.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.weison.io.csv.filter.UserCsvToBeanFilter;
import com.weison.io.model.UserCsvFiler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

@Slf4j
@DisplayName("CSV文件")
public class CsvTest {

    @Test
    @DisplayName("create CSV file")
    @Order(1)
    public void createCsv() throws IOException {
        File file = new File("./user.csv");
        Writer writer = new FileWriter(file);
        //分隔符默认为逗号
        CSVWriter csvWriter = new CSVWriter(writer);
        String[] strings = {"abc", "abc", "abc"};
        csvWriter.writeNext(strings);
        csvWriter.close();
    }

    @Test
    @DisplayName("create CSV file1")
    @Order(2)
    public void createCsvOriginal() throws IOException {
        File file = new File("./user1.csv");
        Writer writer = new FileWriter(file);
        //分隔符默认为逗号
        for (int i = 0; i < 3; i++) {
            String strings = "abc, abc, abc\n";
            writer.write(strings);
        }
        writer.flush();
        writer.close();
    }

    @Test
    @DisplayName("create CSV file2")
    @Order(3)
    public void createCsvOriginal1() throws IOException {
        File file = new File("./user21.csv");
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        //分隔符默认为逗号
        for (int i = 0; i < 3; i++) {
            String strings = "abc, abc, abc";
            bufferedWriter.write(strings);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStreamWriter.close();
        outputStream.close();
    }

    @Test
    @DisplayName("read CSV file")
    @Order(3)
    public void readCsv() throws IOException, CsvValidationException {
        File file = new File("./user21.csv");
        //List<UserCsv> csvData = readCsv(UserCsv.class, file);
        List<UserCsvFiler> csvData4 = readCsv(UserCsvFiler.class, file);
        Set<String> csvData1 = readCsv1(2, file);
        List<String[]> csvData2 = readCsv2(file);
        List<String> csvData3 = readCsv3(file);
        //log.info("-1->" + csvData);
        log.info("-2->" + csvData1);
        log.info("-3->" + csvData2);
        log.info("-4->" + csvData3);
        log.info("-5->" + csvData4);
    }

    public static <T> List<T> readCsv(Class<T> clazz, File file)
            throws FileNotFoundException, UnsupportedEncodingException {

        InputStreamReader in = new InputStreamReader(new FileInputStream(file), "gbk");
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);
        UserCsvToBeanFilter userCsvToBeanFilter = new UserCsvToBeanFilter();

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withFilter(userCsvToBeanFilter)
                .withMappingStrategy(strategy)
                .build();
        List<T> list = csvToBean.parse();
        return list;
    }

    private static Set<String> readCsv1(int minLine, File file) throws IOException, CsvValidationException {
        Integer lineNum = minLine;
        FileReader fReader = new FileReader(file);
        CSVReader csvReader = new CSVReader(fReader);
        String[] values;
        Set<String> set = new HashSet<>();
        //跳过前方minLine 条数据
        csvReader.skip(minLine);
        while ((values = csvReader.readNext()) != null) {
            // values 是一个数组，存储了csv当前行的所有元素，在此你可以将数组中的元素取出来放入你的对象中
            set.add(StringUtils.join(values, CSVWriter.DEFAULT_SEPARATOR));
            lineNum++;
        }
        return set;
    }

    private static List<String[]> readCsv2(File file) throws IOException, CsvValidationException {
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), "utf-8");
            CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').build();
            CSVReader reader = new CSVReaderBuilder(is).withCSVParser(csvParser).build();
            reader.readAll()
                    .stream()
                    .forEach(strings -> Arrays.stream(strings).forEach(System.out::println));
            return null;
        } catch (UnsupportedEncodingException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> readCsv3(File file) throws IOException, CsvValidationException {
        //第一步：先获取csv文件的路径，通过BufferedReader类去读该路径中的文件
        List<String> list = new ArrayList<>();
        //第二步：从字符输入流读取文本，缓冲各个字符，从而实现字符、数组和行（文本的行数通过回车符来进行判定）的高效读取。
        BufferedReader textFile = new BufferedReader(new FileReader(file));
        String lineDta = "";

        //第三步：将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
        while ((lineDta = textFile.readLine()) != null) {
            list.add(lineDta);
            System.out.println(lineDta);
        }

        textFile.close();
        return list;
    }

    private static List<String> readCsvToBean(File file) throws IOException, CsvValidationException {
        //第一步：先获取csv文件的路径，通过BufferedReader类去读该路径中的文件
        List<String> list = new ArrayList<>();
        //第二步：从字符输入流读取文本，缓冲各个字符，从而实现字符、数组和行（文本的行数通过回车符来进行判定）的高效读取。
        BufferedReader textFile = new BufferedReader(new FileReader(file));
        String lineDta = "";

        //第三步：将文档的下一行数据赋值给lineData，并判断是否为空，若不为空则输出
        while ((lineDta = textFile.readLine()) != null) {
            list.add(lineDta);
            System.out.println(lineDta);
        }

        textFile.close();
        return list;
    }
}
