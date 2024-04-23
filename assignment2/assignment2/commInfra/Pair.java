package commInfra;

import java.io.Serializable;

/**
 * The Pair class is used to represent a key-value pair that can be compared to other instances by value, which makes it useful for sorting information.
 * @param <X> data type of key
 * @param <Y> data type of value
 */

public class Pair<X, Y extends Comparable<Y>> implements Comparable<Pair<X, Y>>, Serializable {
    /**
     * key of the pair
    */
    private X key;
    /**
     * value of the pair
    */
    private Y value;

    /**
     * Pair instantiation.
     * @param key key of the pair
     * @param value value of the pair
     */

    public Pair(X key, Y value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Pair key setter.
     * @param key key of the pair
     */

    public void setKey(X key) {
        this.key = key;
    }

    /**
     * Pair value setter.
     * @param value value of the pair
     */

    public void setValue(Y value) {
        this.value = value;
    }

    /**
     * Pair key getter.
     * @return key of the pair
     */

    public X getKey() {
        return this.key;
    }

    /**
     * Pair value getter.
     * @return value of the pair
     */

    public Y getValue() {
        return this.value;
    }

    /**
     * Pair compareTo function.
     * @param b the Pair to be compared.
     * @return a negative integer, zero, or a positive integer as this Pair's value is less than, equal to, or greater than the specified Pair's value.
     */

    @Override
    public int compareTo(Pair<X, Y> b) {
        return this.value.compareTo(b.getValue());
    }
}
