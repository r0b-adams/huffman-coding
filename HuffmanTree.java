// HuffmanTree stores characters' binary value and their Huffman binary values
// for the purpose of compressing a text file, to decompressing a compressed
// file. It can be constructed from an array of ASCII values and the number of
// occurences, or from a formatted .code file of ASCII values and their Huffman
// binary values. When compressing, it writes all characters and their Huffman
// binary values to a given output. When decompressing, writes characters
// (as a char) to given output.

import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallRoot; // Stores reference to beginning of a
                                    // character's Huffman binary value

   // Param: counts: Stores character's ASCII values and # times it occurs
   // Post: Constructs a new HuffmanTree according to characters that
   // occur and their frequencies
   public HuffmanTree(int[] counts) {
      this.overallRoot = buildfromCounts(counts);
   }

   // Pre: counts: Stores character's ASCII values and # times it occurs
   // Post: Returns a reference to the beginning of any character's Huffman
   // binary value
   private HuffmanNode buildfromCounts(int[] counts) {
      Queue<HuffmanNode> charFrequency = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < counts.length; i++) {
         if (counts[i] > 0) { // ensure the character actually occurs

            // store the character's ASCII value and how often it occurs
            HuffmanNode charAndCount = new HuffmanNode(i, counts[i]);
            charFrequency.add(charAndCount);
         }
      }
      charFrequency.add(new HuffmanNode(counts.length, 1)); // end of file char

      while (charFrequency.size() > 1) {
         HuffmanNode left = charFrequency.remove(); // 1st char and frequency
         HuffmanNode right = charFrequency.remove(); // 2nd char and frequency

         // store the sum of each character's frequencies
         HuffmanNode root = new HuffmanNode(0, left.count + right.count,
               left, right);
         charFrequency.add(root);
      }
      return charFrequency.remove();
   }

   // Param: input: Scans a formatted file of ASCII and Huffman binary values
   // Post: Constructs a new HuffmanTree according to Huffman binary values
   public HuffmanTree(Scanner input) {
      this.overallRoot = buildFromCode(this.overallRoot, input);
   }

   // Param: root: Stores a character's ASCII value, or a reference to the
   // next bit in a character's Huffman binary value
   // input: Scans a formatted file of ASCII and Huffman binary values
   // Post: Returns a reference to the beginning of any character's Huffman
   // binary value
   private HuffmanNode buildFromCode(HuffmanNode root, Scanner input) {
      if (input.hasNextLine()) { // if there is something to read
         int charValue = Integer.parseInt(input.nextLine()); // get ASCII value
         String code = input.nextLine(); // get Huffman binary
         root = add(root, charValue, code); // add value
         root = buildFromCode(root, input); // repeat
      }
      return root;
   }

   // Param: root: Stores a character's ASCII value, or a reference to the
   // next bit in a character's Huffman binary value
   // charValue: ASCII value of character
   // code: represents character's Huffman binary value
   // Post: Adds the character according to its Huffman binary value
   private HuffmanNode add(HuffmanNode root, int charValue, String code) {
      if (code.equals("")) { // if the end of code has been reached
         root = new HuffmanNode(charValue, 0); // add the character
      } else {
         if (root == null) { // keep building until end of code is reached
            root = new HuffmanNode(0, 0);
         }
         if (code.substring(0, 1).equals("0")) { // next bit is 0
            root.left = add(root.left, charValue, code.substring(1));
         } else { // next bit is 1
            root.right = add(root.right, charValue, code.substring(1));
         }
      }
      return root;
   }

   // Param: output: PrintStream to write to
   // Post: Prints all character's ASCII values and their Huffman binary
   // values to output according in inorder format
   public void write(PrintStream output) {
      write(overallRoot, "", output);
   }

   // Param: root: Stores a character's ASCII value, or a reference to the
   // next bit in a character's Huffman binary value
   // path: represents a character's Huffman binary value
   // output: PrintStream to write to
   // Post: Prints all character's ASCII values and their Huffman binary
   // values to output in inorder format
   private void write(HuffmanNode root, String path, PrintStream output) {
      if (root.left == null && root.right == null) { // reached a character
         output.println(root.character); // print character's ASCII value
         output.println(path); // print character's Huffman binary
      } else {
         write(root.left, path + "0", output); // next bit is 0
         write(root.right, path + "1", output); // next bit is 1
      }
   }

   // Param: input: InputStream of bits to decode a character from
   // output: PrintStream to write to
   // eof: represents the end of file
   // Post: Writes decoded characters to output (as char)
   public void decode(BitInputStream input, PrintStream output, int eof) {
      int charValue = getChar(this.overallRoot, input); // get first char

      while (charValue != eof) { // check if end of file has been reached
         output.write(charValue); // write to output
         charValue = getChar(this.overallRoot, input); // decode next char
      }
   }

   // Param: root: Stores a character's ASCII value, or a reference to the
   // next bit in a character's Huffman binary value
   // input: InputStream of bits to decode a character from
   // Post: Returns an integer representing a character's ASCII value
   private int getChar(HuffmanNode root, BitInputStream input) {
      int charValue; // initialize, value assigned once decoded

      if (root.left == null && root.right == null) { // found a character
         return root.character; // return its ASCII value
      } else { // else keep looking
         int bit = input.readBit(); // get next 0 or 1

         if (bit == 0) {
            charValue = getChar(root.left, input);
         } else { // else bit == 1
            charValue = getChar(root.right, input);
         }
      }
      return charValue;
   }
}