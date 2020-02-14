// TTS UTILITIES

/**
 * Well, this is the TTS utilities class, with some very useful methods.
 * In this version of our tts_framework, this tts_Utils class only have
 * a implementation of a random numbers generator. We hope that new methods
 * will be implemented here :).
 */

public class tts_Utils
{
//***********************************************************************************************************************
// RANDOM METHODS
//***********************************************************************************************************************

    /**
     * Random seed, used to calculate the pseud-random numbers
     */
    private static long randomSeed;

    /**
     * Sets the seed of the random number generator using a single int seed.
     * This is the implementation cited at the Random.setSeed() description.
     * @param seed - the initial seed.
     */
    protected static void setSeed(long seed)
    {
        randomSeed = (seed ^ 0x5DEECE66DL) & ((1L << 48) - 1);
    }

    /**
     * Generates the next pseudorandom number. It's the implementation cited at
     * the Random.next(int bits) description.
     * @param bits - random bits
     * @return the next pseudorandom value from this random number generator's sequence.
     */
    private static int nextRandom(int bits)
    {
        randomSeed = (randomSeed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        return (int) (randomSeed >>> (48 - bits));
    }

    /**
     * This is the 'Random.nextInt(int n)' method implemented by CLCD 1.1. We
     * copy it here for compatibility with those devices that has CLCD 1.0. Note
     * that this implementation is available at the method's api.
     * @param n The bound on the random number to be returned.
     * @return a pseudorandom, uniformly distributed int value between 0
     * (inclusive) and n (exclusive).
     */
    protected static int nextRandomInt(int n)
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        if ((n & -n) == n) // i.e., n is a power of 2
        {
            return (int) ((n * (long) nextRandom(31)) >> 31);
        }

        int bits, val;
        do {
            bits = nextRandom(31);
            val = bits % n;
        } while (bits - val + (n - 1) < 0);
        return val;
    }
}
