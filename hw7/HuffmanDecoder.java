import java.util.ArrayList;
import java.util.List;

public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie bt = (BinaryTrie) or.readObject();
        int len = (int) or.readObject(), idx = 0;
        BitSequence bs = (BitSequence) or.readObject();
        char[] ans = new char[len];
        while (len > 0) {
            Match m = bt.longestPrefixMatch(bs);
            ans[idx++] = m.getSymbol();
            bs = bs.allButFirstNBits(m.getSequence().length());
            len--;
        }
//        FileUtils.writeCharArray("original" + args[0].substring(0, args[0].length() - 4), ans);
        FileUtils.writeCharArray(args[1], ans);
    }
}
