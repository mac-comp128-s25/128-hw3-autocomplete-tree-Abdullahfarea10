package autoComplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A prefix tree used for autocompletion. The root of the tree just stores links to child nodes (up to 26, one per letter).
 * Each child node represents a letter. A path from a root's child node down to a node where isWord is true represents the sequence
 * of characters in a word.
 */
public class PrefixTree {
    private TreeNode root; 

    // Number of words contained in the tree
    private int size;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     * Adds the word to the tree where each letter in sequence is added as a node
     * If the word, is already in the tree, then this has no effect.
     * @param word
     */
    public void add(String word){
        TreeNode current = root; 
        for (int i = 0; i < word.length(); i++) {
            char currentLetter = word.charAt(i); 
            if (!current.children.containsKey(currentLetter)) {
                TreeNode letterNode = new TreeNode(); 
                letterNode.letter = word.charAt(i); 
                current.children.put(currentLetter, letterNode);
                current = letterNode; 
            } else {
                current = current.children.get(currentLetter);
            }
        }
        if (!current.isWord) {
            current.isWord = true;
            size++;
        }
    }

    /**
     * Checks whether the word has been added to the tree
     * @param word
     * @return true if contained in the tree.
     */
    public boolean contains(String word){
        TreeNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char currentLetter = word.charAt(i);
            if (!current.children.containsKey(currentLetter)) {
                return false;
            }
            current = current.children.get(currentLetter);
        }
        return current.isWord;
    }

    /**
     * Finds the words in the tree that start with prefix (including prefix if it is a word itself).
     * The order of the list can be arbitrary.
     * @param prefix
     * @return list of words with prefix
     */
    public ArrayList<String> getWordsForPrefix(String prefix){
        ArrayList<String> listOfWords = new ArrayList<>(); 
        TreeNode current = root;
        
        for (char letter: prefix.toCharArray()) {
            current = current.children.get(letter);
            if (current == null) {
                return listOfWords; 
            }
        }
        traversalHelper(current, prefix, listOfWords);
        return listOfWords; 
    }
 /**
     * Traverses recursively the prefix tree from a given node to find and collect all words that can be formed 
     */
    public void traversalHelper(TreeNode node, String currentWord, List<String> words) {
        if (node.isWord) {
            words.add(currentWord); 
        }
        for (Map.Entry<Character, TreeNode> entry : node.children.entrySet()) {
            String nextWord = currentWord + entry.getKey();
            traversalHelper(entry.getValue(), nextWord, words);
        }
    }

    /**
     * @return the number of words in the tree
     */
    public int size(){
        return size;
    }
    
}
