package com.lib.collections.core.classes;

import com.lib.collections.core.enums.ReadCondition;

import java.util.ArrayList;

/**
 * Created by nikzz on 29/10/17.
 */
public class ReaderCondition {
    private ReadCondition readCondition;
    private ArrayList<String> readerNames;


    /**
     * Blank constructor with default values
     */
    public ReaderCondition() {

    }


    /**
     * @param readCondition
     * @param readerNames
     */
    public ReaderCondition(ReadCondition readCondition, ArrayList<String> readerNames) {
        this.readCondition = readCondition;
        this.readerNames = readerNames;
    }

    /**
     * @return
     */
    public ReadCondition getReadCondition() {
        return readCondition;
    }

    /**
     * @param readCondition
     */
    public void setReadCondition(ReadCondition readCondition) {
        this.readCondition = readCondition;
    }

    /**
     * @return
     */
    public ArrayList<String> getReaderNames() {
        return readerNames;
    }

    /**
     * @param readerNames
     */
    public void setReaderNames(ArrayList<String> readerNames) {
        this.readerNames = readerNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReaderCondition that = (ReaderCondition) o;

        if (getReadCondition() != that.getReadCondition()) return false;
        return getReaderNames().equals(that.getReaderNames());
    }

    @Override
    public int hashCode() {
        int result = getReadCondition().hashCode();
        result = 31 * result + getReaderNames().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ReaderCondition{" +
                "readCondition=" + readCondition +
                ", readerNames=" + readerNames.toString() +
                '}';
    }
}
