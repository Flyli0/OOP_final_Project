package service;
import java.io.*;
import java.util.*;

import model.ResearchPaper;

/**
 * 
 */
public class ByPages implements ResearchPaperComparator {


    @Override
    public int compare(ResearchPaper rp1, ResearchPaper rp2) {
        // TODO Auto-generated method stub
        return Integer.compare(rp2.getPages(), rp1.getPages());
    }

    @Override 
    public String toString() {
        return "By Pages";
    }

}