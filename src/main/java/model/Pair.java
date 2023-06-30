package model;

public class Pair<T1, T2> {
    public T1 first;
    public T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {

    }

    public static boolean notNull(Pair<Integer, Integer> pair) {
        if (pair == null)
            return false;
        return pair.first != null && pair.second != null;
    }

    public boolean isEqualTo(Pair<T1, T2> secondPair) {
        return this.first.equals(secondPair.first) && this.second.equals(secondPair.second);
    }
}
