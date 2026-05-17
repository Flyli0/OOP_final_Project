package service;

import java.io.*;
import java.util.*;

import model.ResearchPaper;

/**
 * 
 */
public class ByDate implements ResearchPaperComparator {
    @Override
    public int compare(ResearchPaper rp1, ResearchPaper rp2) {
        // TODO Auto-generated method stub
        if (rp1.getPublicationDate() == null && rp2.getPublicationDate() == null) return 0;
        if (rp1.getPublicationDate() == null) return 1;
        if (rp2.getPublicationDate() == null) return -1;
        
        return rp1.getPublicationDate().compareTo(rp2.getPublicationDate());
    }

    @Override 
    public String toString() {
        return "By Date";
    }

}