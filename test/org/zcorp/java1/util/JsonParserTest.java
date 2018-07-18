package org.zcorp.java1.util;

import org.junit.Test;
import org.zcorp.java1.model.Resume;
import org.zcorp.java1.model.Section;
import org.zcorp.java1.model.TextSection;

import static org.junit.Assert.assertEquals;
import static org.zcorp.java1.TestData.R1;

public class JsonParserTest {
    @Test
    public void writeResume() {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(R1, resume);
    }

    @Test
    public void writeSection() {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        assertEquals(section1, section2);
    }
}