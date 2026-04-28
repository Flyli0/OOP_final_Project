package model;

import java.util.List;

public interface Researcher {

    double calculateH();

    void conductResearch(String content, String title, int pages);

    void printPapers();
}