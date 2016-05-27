package br.com.hole19.marvel.comm.model.comm_data;

import java.io.Serializable;
import java.util.List;

import br.com.hole19.marvel.comm.model.marvel.Character;

/**
 * Created by edgar on 29-Apr-16.
 */
public class Payload implements Serializable {

    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<Character> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Character> getResults() {
        return results;
    }

    public void setResults(List<Character> results) {
        this.results = results;
    }
}
