import java.util.*;

public class GrammarSolver {
    
    private SortedMap<String, String[]> grammar;
    private Random rand;



    public GrammarSolver(List<String> rules) {
        if (rules.isEmpty()) {
            throw new IllegalArgumentException();
        }
        grammar = new TreeMap<String, String[]>();
        rand = new Random();
        for (String line : rules) {
            String[] piecesOfLine = line.split("::=");
            if (grammar.containsKey(piecesOfLine[0])) {
                throw new IllegalArgumentException();
            }
            String[] piecesOfPhrase = piecesOfLine[1].split("\\|");
            grammar.put(piecesOfLine[0], piecesOfPhrase);
        }
    }

    public boolean grammarContains(String symbol) {
        if (grammar.containsKey(symbol)) {
            return true;
        } else {
            return false;
        }
    }

    public String getSymbols() {
        String symbol = "[";
        for (String grammarSymbols : grammar.keySet()) {
            symbol += grammarSymbols + ", ";
        }
        symbol = symbol.substring(0, symbol.length() - 2);
        return symbol + "]";
    }

    public String[] generate(String symbol, int times) {
        String[] generation= new String[times];
        if (times < 0 || (!grammarContains(symbol))) {
            throw new IllegalArgumentException();
        }
        for (int repeat = 0; repeat < times; repeat++) {
            String oneLine = generateOne(symbol);
            oneLine = oneLine.trim();
            generation[repeat] = oneLine;
        }
        return generation;
    }

    private String generateOne(String symbol) {
        return generateOne(symbol, "");
    }

    private String generateOne(String symbol, String finalSentence) {
        int index = rand.nextInt(grammar.get(symbol).length);
        String select = grammar.get(symbol)[index].trim();
        String[] selectedOption = select.split("\\s+");
        for (int i = 0; i < selectedOption.length; i++) {
            if (grammarContains(selectedOption[i])) { // if it is non terminal
                generateOne(selectedOption[i]);
            }
            finalSentence += selectedOption[i] + " ";
        }
        return finalSentence;
    }

    

}
