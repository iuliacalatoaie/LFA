import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class Main {
    static DFA dfa;
    public static void main(String [] argz) {
        if(argz.length != 1) {
            System.err.println("Argument error");
            System.exit(1);
        }
        int mode = -1;
        if(argz[0].equals("-e")) {
            mode = 1;
        } else if(argz[0].equals("-a")) {
            mode = 2;
        } else if(argz[0].equals("-u")) {
            mode = 3;
        } else if(argz[0].equals("-v")) {
            mode = 4;
        } else if(argz[0].equals("-f")) {
            mode = 5;
        } else {
            System.err.println("Argument error");
            System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("dfa"));
            Flexer scanner = new Flexer(br);
            scanner.yylex();
            br.close();

            dfa = Flexer.dfa;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //TODO: Done parsing
        Iterator<String> iter;

        switch(mode) {
            case 1:
                //TODO: Has e
                dfa.acceptsE();
                break;
            case 2:
                //TODO: Accessible states
                HashSet<String> aStates = dfa.accesibleStates();
                iter = aStates.iterator();
                while (iter.hasNext()) {
                    System.out.println(iter.next());
                }
                break;
            case 3:
                //TODO: Useful states
                HashSet<String> uStates = dfa.usefulStates();
                iter = uStates.iterator();
                while (iter.hasNext()) {
                    System.out.println(iter.next());
                }
                break;
            case 4:
                //TODO: Empty language
                dfa.voidLanguage();
                break;
            case 5:
                //TODO: Finite language
                break;
            default:
                System.err.println("Argument error");
                System.exit(1);
        }

    }
}
