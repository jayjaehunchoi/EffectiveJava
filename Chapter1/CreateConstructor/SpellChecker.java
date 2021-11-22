package CreateConstructor;

import static CreateConstructor.Dictionary.*;

public class SpellChecker {
    private final Dictionary dictionary;
    public SpellChecker(Dictionary dictionary){
        this.dictionary = dictionary;
    }

    public void findSpell(){
        dictionary.findWord();
    }

    public static void main(String[] args) {
        System.out.println("한국어 사전 사용자 전용");
        SpellChecker spellChecker = new SpellChecker(new KoreanDictionary());
        spellChecker.findSpell();

        System.out.println("영어 사전 사용자 전용");
        SpellChecker spellChecker1 = new SpellChecker(new EnglishDictionary());
        spellChecker1.findSpell();
    }

}
