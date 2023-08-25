/*
 * Classe similiar à classe 'pair' da STL da linguagem cpp
 */

public class Pair<T, P> { // Recebe quaisquer classes 'T' e 'P'
    private T first;
    private P second;

    public Pair(T first, P second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return this.first;
    }

    public P getSecond() {
        return this.second;
    }
}
