package service;


import java.io.*;
import java.util.*;


public interface Comparator<T> {
    int compare(T o1, T o2);
}