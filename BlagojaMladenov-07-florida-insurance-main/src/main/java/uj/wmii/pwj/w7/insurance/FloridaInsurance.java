package uj.wmii.pwj.w7.insurance;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FloridaInsurance {
    public static void main(String[] args) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile("FL_insurance.csv.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        List<ReadFileItem> list = new ArrayList<>();
        ZipEntry entry = entries.nextElement();
        try {
            InputStream stream = zipFile.getInputStream(entry);
            InputStreamReader isr = new InputStreamReader(stream,
                    StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String country = values[2];
                BigDecimal tiv_2011 = new BigDecimal(values[7]);
                BigDecimal tiv_2012 = new BigDecimal(values[8]);
                list.add(new ReadFileItem(country, tiv_2011, tiv_2012));
            }
//            System.out.println(distinctElements);
            long count  = list.stream()
                    .collect(Collectors.groupingBy(ReadFileItem::country))
                    .values()
                    .stream()
                    .count();
            try {
                File myObj = new File("count.txt");
                if (myObj.createNewFile()) {
//                    System.out.println("File created: " + myObj.getName());
                    String str = Long.toString(count);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(myObj));
                    writer.write(str);

                    writer.close();
                } else {
//                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
//            System.out.println(count);
            BigDecimal sum2012 =
                    list.stream()
                            .map(ReadFileItem::tiv_2012)
                            .toList()
                            .stream()
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
//            System.out.println(sum2012);
            try {
                File myObj = new File("tiv2012.txt");
                if (myObj.createNewFile()) {
//                    System.out.println("File created: " + myObj.getName());

                    BufferedWriter writer = new BufferedWriter(new FileWriter(myObj));
                    writer.write(sum2012.toString());

                    writer.close();
                } else {
//                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            List<Map.Entry<String,BigDecimal>> map = list.stream().collect(Collectors.groupingBy(
                    ReadFileItem::country,
                    Collectors.reducing(
                            BigDecimal.ZERO,
                            element -> element.tiv_2012().subtract(element.tiv_2011()),
                            BigDecimal::add
                    )
            )).entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, BigDecimal>comparingByValue()
                            .reversed())
                    .limit(10).toList();
            try {
                File myObj = new File("most_valuable.txt");
                if (myObj.createNewFile()) {
//                    System.out.println("File created: " + myObj.getName());

                    BufferedWriter writer = new BufferedWriter(new FileWriter(myObj));
                    writer.write("country,value\n");
                    map.forEach(s -> {
                        try {
                            writer.write(s.getKey() + "," + s.getValue() + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.close();
                } else {
//                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

//            System.out.println(flattened);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
