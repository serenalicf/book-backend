package com.inventory.management.book.util;

import bean.CsvData;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVUtil {

    public static byte[] generateCSVOutputStream(List<String> headers, List<CsvData> csvDataList){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            byteArrayOutputStream.writeBytes(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            PrintWriter pw = new PrintWriter(byteArrayOutputStream, false, StandardCharsets.UTF_8);

            pw.println(String.join(",", headers));
            for(CsvData csvData : csvDataList){
                pw.println(String.join(",", csvData.getData()));
            }
            pw.flush();
            pw.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e){
            throw new RuntimeException("Failed to generate csv");
        }


    }
}
