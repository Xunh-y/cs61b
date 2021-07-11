import synthesizer.GuitarString;

public class GuitarHero {
    private static final double HZ = 440.0;
    private static GuitarString[] strings = new GuitarString[37];
    private static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        init();
        int idx = 0;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                for (int i = 0; i < 37; ++i) {
                    if (key == keyboard.charAt(i)) {
                        strings[i].pluck();
                        idx = i;
                    }
                }
            }

            double sample = strings[idx].sample();

            StdAudio.play(sample);

            strings[idx].tic();
        }
    }

    private static void init() {
        for (int i = 0; i < 37; ++i) {
            int k = keyboard.indexOf(i);
            int con = (int) (HZ * Math.pow(2, (k - 12) / 12.0));
            strings[i] = new GuitarString(con);
        }
    }
}
