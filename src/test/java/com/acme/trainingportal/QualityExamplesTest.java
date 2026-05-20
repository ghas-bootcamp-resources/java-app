package com.acme.trainingportal;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class QualityExamplesTest {
    @Test
    void detectsEmptyCourseList() {
        QualityExamples examples = new QualityExamples("admin");

        assertThat(examples.hasCourses(List.of())).isFalse();
    }

    @Test
    void normalizesTitles() {
        QualityExamples examples = new QualityExamples("admin");

        assertThat(examples.normalizeTitle("Secure Coding")).isEqualTo("Secure Coding");
        assertThat(examples.replaceSeparator("Secure-Coding")).isEqualTo("Secure Coding");
    }
}
