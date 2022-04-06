// HuffmanNode stores a character's ASCII value, the number of
// times it occurs and references to the next bit in a binary
// value.
public class HuffmanNode implements Comparable<HuffmanNode> {
   public int character;     // stores the character's ASCII value
   public int count;         // number of times that character occurs
   public HuffmanNode left;  // points to a "0" bit in a binary value
   public HuffmanNode right; // points to a "1" bit in a binary value

   // Param: character:
   //            count:
   //  Post: Builds a Huffman node that stores a character's ASCII
   //        value and number of occurences.
   public HuffmanNode(int character, int count) {
      this(character, count, null, null);
   }

   // Param: character: a character's ASCII value
   //            count: number of times character occurs
   //             left: points to a "0" bit in a binary value
   //            right: points to a "1" bit in a binary value
   //  Post: Builds a Huffman node that stores a character's ASCII value, number of
   //        occurences, and references to the next bit in a binary value.
   public HuffmanNode(int character, int count, HuffmanNode left, HuffmanNode right) {
      this.character = character;
      this.count = count;
      this.left = left;
      this.right = right;
   }

   // Param: other: HuffmanNode to compare to this one
   //  Post: returns a negative integer, zero, or a positive integer to
   //        designate this object as less than, equal to, or greater
   //        than the given object for the purpose of ordering.
   //        Compares by number of occurences.
   public int compareTo(HuffmanNode other) {
      return this.count - other.count;
   }

   //  Post: returns a String representation of the character this HuffmanNode
   //        stores and its frequency (in parentheses)
   public String toString() {
      return (char) this.character + ", (" + this.count + ")";
   }
}