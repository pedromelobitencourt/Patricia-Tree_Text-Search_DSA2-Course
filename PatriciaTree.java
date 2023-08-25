/*
 * Referência: Ziviani
 */

import java.util.ArrayList;

public class PatriciaTree {
    private PatNo root;
    private int keyNBits;

    public PatriciaTree() {
        this.root = null;
        this.keyNBits = 128;
    }

    public PatriciaTree(int i) {
        this.root = null;
        this.keyNBits = i;
    }

    private static abstract class PatNo {}
    
    private static class PatNoInt extends PatNo { // Nó Interno: Posição do bit que difere palavras da esquerda das palavras da direita
        int index; // Posição que difere
        PatNo left, right;

        public PatNoInt(int index, PatNo left, PatNo right) {
            this.index = index;
            this.left = left;
            this.right = right;
        }
    }

    private static class PatNoExt extends PatNo { // Nó Externo: Palavra resultante ou palavra com o mesmo prefixo da palavra resultada
        String key; // Palavra
        ArrayList <Position> position;

        public PatNoExt() {
            this.key = "";
            this.position = new ArrayList<>();
        }
    }

    /*private int getIthBit(int i, char k) { // Método que retorna o bit na posição 'i' (O(1))
        int mask = (1 << i), kk = (int) k;
        return (kk & mask) > 0 ? 1 : 0;
    }*/

    private int getIthBit(int i, String str) {
        return (str.charAt(i) == '1') ? 1 : 0;
    }

    private boolean isExternalNode(PatNo p) { // Método que retorna se a class 'p' é da class PatNoExt (Nó externo)
        Class theClass = p.getClass();
        return theClass.getName().equals(PatNoExt.class.getName());
    }

    private PatNo createInternalNode(int i, PatNo left, PatNo right) { // Criar nó interno
        PatNoInt p = new PatNoInt(i, left, right);
        return p;
    }

    private PatNo createExternalNode(String k, int line, int column) { // Criar nó externo
        PatNoExt p = new PatNoExt();
        p.key = k;
        p.position.add(new Position(line, column));
        return p;
    }

    public void procurar(String str) {
        this.search(str, this.root);
    }

    private void search(String k, PatNo t) { //t: nó atual; k: Palavra que está sendo procurada
        if(this.isExternalNode(t)) {
            PatNoExt aux = (PatNoExt) t; // Type casting, pois pode ser um nó interno, e, sendo um nó interno, não possui o atributo 'key'
            if(aux.key.equals(k)){
                System.out.println("\nElemento encontrado");
                System.out.printf("Esse elemento foi encontrado %d vezes\nEle está nas seguintes posições:\n", ((PatNoExt) t).position.size());

                for(Position p : ((PatNoExt) t).position){
                    System.out.printf("Linha: %d          Número da palavra: %d\n", p.getLine(), p.getColumn());
                }
                
                System.out.println("\n\n");
            }
            else
                System.out.println("Elemento não encontrado");
        }
        else { // É um nó interno
            PatNoInt aux = (PatNoInt) t;
            if(this.getIthBit(aux.index, k) == 0)
                search(k, aux.left);
            else
                search(k, aux.right);
        }
    }

    public void inserir(String k, int line, int column) {
        this.root = this.insert(k, this.root, line, column);
    }

    private PatNo insert(String k, PatNo t, int line, int column) {
        if(t == null){
            return this.createExternalNode(k, line, column);
        }
        else {
            PatNo p = t;

            while(!isExternalNode(p)) {
                if(this.getIthBit(((PatNoInt)p).index, k) == 1) 
                    p = ((PatNoInt)p).right;
                else
                    p = ((PatNoInt)p).left;
            }

            int i = 1;
            while((i < this.keyNBits) && getIthBit(i, k) == getIthBit(i, ((PatNoExt)p).key))
                i++;
            if(i >= this.keyNBits) {
                System.out.printf("A chave '%s' já está na árvore\n", ((PatNoExt)p).key);
                ((PatNoExt)p).position.add(new Position(line, column));
                return t;
            }
            else
                return this.insertBetween(k, t, i, line, column);

        }
        
    }

    private PatNo insertBetween(String k, PatNo t, int i, int line, int column) { //i: Posição que difere duas palavras
        PatNoInt aux = null;
        if(!this.isExternalNode(t)) // Criar um novo nó interno com a posição 'i' se duas palavras colidiram
            aux = (PatNoInt) t;
        if(this.isExternalNode(t) || (i < aux.index)){ // 2 opção
            PatNo p = this.createExternalNode(k, line, column);

            if(this.getIthBit(i, k) == 1)
                return this.createInternalNode(i, t, p); // Novo nó vai a direita
            else
                return this.createInternalNode(i, p, t);
        }
        else {
            if(this.getIthBit(aux.index, k) == 1)
                aux.right = this.insertBetween(k, aux.right, i, line, column); // Criar nó interno e colocar a palavra certa à direita
            else
                aux.left = this.insertBetween(k, aux.left, i, line, column);
            return aux;
        }
    }
}
