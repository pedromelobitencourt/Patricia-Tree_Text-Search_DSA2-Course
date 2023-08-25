import java.util.Scanner;
import java.util.Formatter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TextFileHandler {
    private static Scanner input;
    private static Formatter output;
    private static ArrayList< Pair<String, Position> > fileWords_Position; // Palavra e Posição delas

    public static ArrayList<Pair<String, Position>> getFileWords_Position() {
        return TextFileHandler.fileWords_Position;
    }

    public static String removeInvalidCharacters(String string) { // Remover caracteres inválidos
        string = string.replace("(", "");
        string = string.replace(")", "");
        string = string.replace(",", "");
        string = string.replace(".", "");
        string = string.replace("?", "");
        string = string.replace(":", "");
        string = string.replace("*", "");
        string = string.replace(";", "");
        string = string.replace(" ", "");
        string = string.replace("\n", "");

        return string;
    }

    public static void openFile(String file) {
        try {
            Path path = Paths.get(file);
            if(Files.exists(path)) // Caso o arquivo exista, abrimos-o
                input = new Scanner(Paths.get(file));
            else { // Se o arquivo não existe, o criamos
                output = new Formatter(file);
                input = new Scanner(Paths.get(file));
            }
        }
        catch(FileNotFoundException e) {
            System.out.printf("O arquivo '%s' não foi encontrado\n", file);
            System.exit(1);
        }
        catch(IOException e) {
            System.out.printf("Error: %s", e);
            System.exit(1);
        }
    }

    public static void readRecords(String file) {
        openFile(file);

        int i = 0; // Número da palavra na linha
        long lines = 0;
        String str1;
        String str2;
        fileWords_Position = new ArrayList<>();

        try {
            while(input.hasNext()) {
                str2 = input.nextLine(); // Pegamos a próxima linha do arquivo 'file'
                lines++;

                str1 = ""; // Resetar a palavra
                i = 0;
                for(char c : str2.toCharArray()){ // Escanear sobre a linha lida pelo 'str2'
                    if(c == ' ' && !(removeInvalidCharacters(str1).equals(""))){ // Se achou um espaço, ou seja, vai começar uma próxima palavra
                        i++; // Número de palavras
                        str1 = removeInvalidCharacters(str1);
                        if(!str1.equals(""))
                            fileWords_Position.add(new Pair(str1, new Position((int)lines, i)));
                        str1 = "";
                    }
                    str1 += c; // Adicionando o caractere 'c' à palavra que está sendo lida
                }
            }
        }
        catch(NoSuchElementException e){
            System.err.println("Houve um erro, logo, o programa vai ser fechado\n");
            System.err.println(e);
            System.exit(1);
        }
        catch(IllegalStateException e){
            System.err.println("Houve um erro, logo, o programa vai ser fechado\n");
            System.err.println(e);
            System.exit(1);
        }

        System.out.printf("Words read:\n");

        for(int j = 0; j < fileWords_Position.size(); j++) {
            Pair<String, Position> p = fileWords_Position.get(j);
            System.out.printf("'%s':     linha: %d    Número da palavra: %d\n\n", p.getFirst(), p.getSecond().getLine(), p.getSecond().getColumn());
        }
    }
}
