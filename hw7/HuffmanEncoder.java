import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : inputSymbols ){
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        return map;
    }
    public static void main(String[] args) {
        char[] eightBit = FileUtils.readFile(args[0]);
        Map<Character, Integer> map = buildFrequencyTable(eightBit);
        BinaryTrie bt = new BinaryTrie(map);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(bt);
        ow.writeObject(eightBit.length);
        Map<Character, BitSequence> lookupmap = bt.buildLookupTable();
        List<BitSequence> list = new ArrayList<>();
        for (char c : eightBit) {
            list.add(lookupmap.get(c));
        }
        BitSequence bs = BitSequence.assemble(list);
        ow.writeObject(bs);
    }
}
