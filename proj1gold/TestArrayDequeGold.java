import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void test() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        String msg = "";
        int s = 0;

        for (int i = 0; i < 100; ++i) {
            double rand = StdRandom.uniform();
            if (rand < 0.25) {
                sad.addFirst(i);
                ads.addFirst(i);
                msg += "addFirst(" + i + ")\n";
                s++;
                assertEquals(msg, ads.get(0), sad.get(0));
            } else if (rand < 0.5) {
                sad.addLast(i);
                ads.addLast(i);
                msg += "addLast(" + i + ")\n";
                s++;
                assertEquals(msg, ads.get(s - 1), sad.get(s - 1));
            } else if (rand < 0.75) {
                if (ads.isEmpty()) {
                    msg += "isEmpty()\n";
                    assertTrue(msg, sad.isEmpty());
                    continue;
                }
                Integer act = sad.removeFirst();
                Integer exp = ads.removeFirst();
                msg += "removeFirst()\n";
                s--;
                assertEquals(msg, exp, act);
            } else {
                if (ads.isEmpty()) {
                    msg += "isEmpty()\n";
                    assertTrue(msg, sad.isEmpty());
                    continue;
                }
                Integer act = sad.removeLast();
                Integer exp = ads.removeLast();
                msg += "removeLast()\n";
                s--;
                assertEquals(msg, exp, act);
            }
        }
    }
}
