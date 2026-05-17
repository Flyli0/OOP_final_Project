package service;



import java.io.*;
import java.util.*;


import model.ResearchPaper;


public interface ResearchPaperComparator extends Comparator<ResearchPaper>{

    public int compare(ResearchPaper o1, ResearchPaper o2);
}