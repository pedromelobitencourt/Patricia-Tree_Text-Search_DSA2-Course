import java.util.Scanner;

public class Main {

    public static String stringReverse(String str) { // Inverter uma string
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    public static String decimalToBinary(int decimal) { // Retornar uma string que define a representação binária do número recebido
        return Integer.toBinaryString(decimal);
    }

    public static String sequenciaBits(int characterInASCIITable) { // Converter um caractere para binário
        String returnString = Main.decimalToBinary(characterInASCIITable);
        int length = returnString.length();

        while(length < 8) { // Cada caractere deve ter 8 bits. Para isso, para extender os bits à esquerda, inverto a string e adiciono um 0 no final e inverto novamente
            returnString = stringReverse(returnString);
            returnString += "0";
            length++;
            returnString = stringReverse(returnString);
        }
        return returnString;
    }

    public static String extensorDeBits0(String str) { // Deve-se ter 128 bits as palavras, ou seja, cada palavra tem 16 caracters, em que cada um tem 8 bits
        String returnString = str;
        int length = str.length();

        while(length < 128) {
            returnString = stringReverse(returnString); // Inverter a string
            returnString += "0"; // Colocar um bit '0' no início da string para completar os 128 bits
            length++;
            returnString = stringReverse(returnString);
        }

        return returnString;
    }

    public static String converterBinario(String str) {
        int length = str.length();
        String returnString = "";

        for(int i = 0; i < length; i++) {
            returnString += sequenciaBits((int)(str.charAt(i))); // Concatenar os caracteres de 'str' convertidos em binário
        }

        returnString = Main.extensorDeBits0(returnString); // Extender os bits, pois deve ter 128 bits

        return returnString;
    }

    public static void main(String[] args) {
        String fileName = "", searchWord = "";
        PatriciaTree patriciaTree = new PatriciaTree(128);
        Scanner input = new Scanner(System.in);

        while(!(fileName.contains(".txt"))) {
            System.out.println("Digite o nome do arquivo 'txt' para leitura: ");
            fileName = input.next();
        }

        System.out.println("Digite um termo para procurar no texto do arquivo: ");
        searchWord = input.next();


        TextFileHandler.readRecords(fileName);

        for(int i = 0; i < TextFileHandler.getFileWords_Position().size(); i++) {
            Pair<String, Position> p = TextFileHandler.getFileWords_Position().get(i);
            patriciaTree.inserir(converterBinario(p.getFirst()), p.getSecond().getLine(), p.getSecond().getColumn());
        }
        System.out.println();

        patriciaTree.procurar(converterBinario(searchWord));

    }
}
