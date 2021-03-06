package pt.it.av.atnog.ml.tm;

import pt.it.av.atnog.ml.tm.lexicalPattern.EIsAC;
import pt.it.av.atnog.ml.tm.lexicalPattern.LexicalPattern;
import pt.it.av.atnog.ml.tm.ngrams.NGram;
import pt.it.av.atnog.ml.tm.stemmer.PorterStemmer;
import pt.it.av.atnog.ml.tm.stemmer.Stemmer;
import pt.it.av.atnog.utils.ws.search.SearchEngine;
import pt.it.av.atnog.utils.ws.search.Yacy;

import java.util.ArrayList;
import java.util.List;

/**
 * Hypernym evaluation.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class HypernymEvaluation {
    public static void main(String[] args) {
        Stemmer stemmer = new PorterStemmer();
        SearchEngine se = new Yacy();
        List<LexicalPattern> patterns = new ArrayList<LexicalPattern>();
        patterns.add(new EIsAC(stemmer));

        Hypernyms h = new Hypernyms(se, patterns);
        System.err.print(h.hypernym(NGram.Unigram("Rome")));
    }
}
