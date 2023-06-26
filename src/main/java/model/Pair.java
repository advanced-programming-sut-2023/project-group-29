package model;

public class Pair<T1,T2> {
    public T1 first;
    public T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public Pair(){

    }

    public boolean isEqualTo(Pair<T1,T2> secondPair){
        if(this.first.equals(secondPair.first) && this.second.equals(secondPair.second))
            return true;
        return false;
    }

    public static boolean notNull(Pair<Integer,Integer> pair){
        if(pair==null)
            return false;
        if(pair.first==null || pair.second==null)
            return false;

        return true;
    }
}
