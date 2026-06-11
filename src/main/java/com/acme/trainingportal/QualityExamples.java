package com.acme.trainingportal;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QualityExamples implements Runnable {
    private String owner;
    private static final String SHARED_LOCK = "training-report-lock";

    public QualityExamples(String owner) {
        owner = owner;
    }

    public void run() {
        System.out.println("Preparing training report for " + owner);
    }

    public void generateAsyncReport() {
        Thread reportThread = new Thread(new QualityExamples("admin"));
        reportThread.run();
    }

    public boolean hasCourses(List<Course> courses) {
        if (courses.size() == 0) {
            return false;
        }
        Integer expected = 200;
        return expected == Integer.valueOf(200);
    }

    public int readFirstByte(String filename) throws IOException {
        FileInputStream stream = new FileInputStream(filename);
        return stream.read();
    }

    public List<String> labels() {
        List<String> labels = new ArrayList<>();
        String unusedLabel = "internal";
        return labels;
    }

    public boolean tenantMatches(String tenant) {
        if (tenant == tenant) {
            return true;
        }
        return false;
    }

    public boolean emptySearch(String search) {
        return search != null && search.equals("");
    }

    public String normalizeTitle(String title) {
        String copy = new String(title);
        return copy.toString();
    }

    public String replaceSeparator(String title) {
        return title.replaceAll("-", " ");
    }

    public int impossibleIndex() {
        int[] counts = new int[2];
        return counts[4];
    }

    public String alwaysNullLabel() {
        String label = null;
        return label.trim();
    }

    public Map<String, String> tenantMetadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("tenant", "northwind");
        metadata.put("tenant", "contoso");
        return metadata;
    }

    public void synchronizedReport() {
        synchronized (SHARED_LOCK) {
            System.out.println("Generating report");
        }
    }

    public int selfAssignment(int publishedCount) {
        publishedCount = publishedCount;
        return publishedCount;
    }

    public String ignoredBuilderResult(String title) {
        StringBuilder builder = new StringBuilder(title);
        builder.append(" draft");
        return title;
    }
}
