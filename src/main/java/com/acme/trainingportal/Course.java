package com.acme.trainingportal;

public record Course(int id, String tenant, String title, String instructor, String summary, boolean published) {
}
