//TTS MATH

/**
 * Well, here we have a bunch of probably useful math methods. Probably will no used on all
 * projects using this framework, but still here, are already tested methods and works fine :)
 */

public class tts_Math
{
    // Returns 1000 * sin(angle), angle in degrees
    public static int sin(int angle)
    {
        angle = angle % 360;
        if (angle < -180)
        {
            return SIN_TABLE[360 - angle];
        }
        else if (angle < 0)
        {
            return -SIN_TABLE[(-angle)];
        }
        else if (angle <= 180)
        {
            return SIN_TABLE[(angle)];
        }
        else
        {
            return -SIN_TABLE[(360 - angle)];
        }
    }

    // Returns 1000 * cos(angle), angle in degrees
    public static int cos(int angle)
    {
        angle = angle % 360;
        if (angle < 0)
        {
            angle = -angle;
        }

        if (angle <= 180)
        {
            return COS_TABLE[(angle)];
        }
        else
        {
            return COS_TABLE[(360 - angle)];
        }
    }

    // Returns 1000 * tan(angle), angle in degrees
    public static int tan(int angle)
    {
        return (1000 * sin(angle) / cos(angle));
    }

    // Returns angle = arcsin(value/100), angle in degrees, value ranging from -200 to 200
    public static int arcsin(int value)
    {
        return ARCSIN_TABLE[value + 100];
    }

    // Returns angle = arccos(value/100), angle in degrees, value ranging from -200 to 200
    public static int arccos(int value)
    {
        return ARCCOS_TABLE[value + 100];
    }

    private static final int[] SIN_TABLE =
    {
        //                  5                       10                      15                       20                       25                       30                       35                       40                       45                       50                       55                       60                       65                       70                       75                       80                       85                       90                        95                       100                      105                      110                      115                      120                      125                      130                      135                      140                      145                      150                      155                      160                      165                      170                      175                 180
        0, 17, 34, 52, 69, 87, 104, 121, 139, 156, 173, 190, 207, 224, 241, 258, 275, 292, 309, 325, 342, 358, 374, 390, 406, 422, 438, 453, 469, 484, 499, 515, 529, 544, 559, 573, 587, 601, 615, 629, 642, 656, 669, 681, 694, 707, 719, 731, 743, 754, 766, 777, 788, 798, 809, 819, 829, 838, 848, 857, 866, 874, 882, 891, 898, 906, 913, 920, 927, 933, 939, 945, 951, 956, 961, 965, 970, 974, 978, 981, 984, 987, 990, 992, 994, 996, 997, 998, 999, 999, 1000, 999, 999, 998, 997, 996, 994, 992, 990, 987, 984, 981, 978, 974, 970, 965, 961, 956, 951, 945, 939, 933, 927, 920, 913, 906, 898, 891, 882, 874, 866, 857, 848, 838, 829, 819, 809, 798, 788, 777, 766, 754, 743, 731, 719, 707, 694, 681, 669, 656, 642, 629, 615, 601, 587, 573, 559, 544, 529, 515, 499, 484, 469, 453, 438, 422, 406, 390, 374, 358, 342, 325, 309, 292, 275, 258, 241, 224, 207, 190, 173, 156, 139, 121, 104, 87, 69, 52, 34, 17, 0
    };
    private static final int[] COS_TABLE =
    {
        //                         5                       10                       15                       20                       25                       30                       35                       40                       45                       50                       55                       60                       65                       70                       75                       80                       85                       90                        95                       100                      105                      110                      115                      120                      125                      130                      135                      140                      145                      150                      155                      160                      165                      170                      175                 180
        1000, 999, 999, 998, 997, 996, 994, 992, 990, 987, 984, 981, 978, 974, 970, 965, 961, 956, 951, 945, 939, 933, 927, 920, 913, 906, 898, 891, 882, 874, 866, 857, 848, 838, 829, 819, 809, 798, 788, 777, 766, 754, 743, 731, 719, 707, 694, 681, 669, 656, 642, 629, 615, 601, 587, 573, 559, 544, 529, 515, 500, 484, 469, 453, 438, 422, 406, 390, 374, 358, 342, 325, 309, 292, 275, 258, 241, 224, 207, 190, 173, 156, 139, 121, 104, 87, 69, 52, 34, 17, 0, -17, -34, -52, -69, -87, -104, -121, -139, -156, -173, -190, -207, -224, -241, -258, -275, -292, -309, -325, -342, -358, -374, -390, -406, -422, -438, -453, -469, -484, -499, -515, -529, -544, -559, -573, -587, -601, -615, -629, -642, -656, -669, -681, -694, -707, -719, -731, -743, -754, -766, -777, -788, -798, -809, -819, -829, -838, -848, -857, -866, -874, -882, -891, -898, -906, -913, -920, -927, -933, -939, -945, -951, -956, -961, -965, -970, -974, -978, -981, -984, -987, -990, -992, -994, -996, -997, -998, -999, -999, -1000
    };
    private static final int[] ARCSIN_TABLE =
    {
        -90, -81, -78, -75, -73, -71, -70, -68, -66, -65, -64, -62, -61, -60, -59, -58, -57, -56, -55, -54, -53, -52, -51, -50, -49, -48, -47, -46, -46, -45, -44, -43, -42, -42, -41, -40, -39, -39, -38, -37, -36, -36, -35, -34, -34, -33, -32, -32, -31, -30, -30, -29, -28, -28, -27, -26, -26, -25, -24, -24, -23, -22, -22, -21, -21, -20, -19, -19, -18, -18, -17, -16, -16, -15, -15, -14, -13, -13, -12, -12, -11, -10, -10, -9, -9, -8, -8, -7, -6, -6, -5, -5, -4, -4, -3, -2, -2, -1, -1, 0, 0, 0, 1, 1, 2, 2, 3, 4, 4, 5, 5, 6, 6, 7, 8, 8, 9, 9, 10, 10, 11, 12, 12, 13, 13, 14, 15, 15, 16, 16, 17, 18, 18, 19, 19, 20, 21, 21, 22, 22, 23, 24, 24, 25, 26, 26, 27, 28, 28, 29, 30, 30, 31, 32, 32, 33, 34, 34, 35, 36, 36, 37, 38, 39, 39, 40, 41, 42, 42, 43, 44, 45, 46, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 64, 65, 66, 68, 70, 71, 73, 75, 78, 81, 90
    };
    private static final int[] ARCCOS_TABLE =
    {
        180, 171, 168, 165, 163, 161, 160, 158, 156, 155, 154, 152, 151, 150, 149, 148, 147, 146, 145, 144, 143, 142, 141, 140, 139, 138, 137, 136, 136, 135, 134, 133, 132, 132, 131, 130, 129, 129, 128, 127, 126, 126, 125, 124, 124, 123, 122, 122, 121, 120, 120, 119, 118, 118, 117, 116, 116, 115, 114, 114, 113, 112, 112, 111, 111, 110, 109, 109, 108, 108, 107, 106, 106, 105, 105, 104, 103, 103, 102, 102, 101, 100, 100, 99, 99, 98, 98, 97, 96, 96, 95, 95, 94, 94, 93, 92, 92, 91, 91, 90, 90, 89, 88, 88, 87, 87, 86, 85, 85, 84, 84, 83, 83, 82, 81, 81, 80, 80, 79, 79, 78, 77, 77, 76, 76, 75, 74, 74, 73, 73, 72, 71, 71, 70, 70, 69, 68, 68, 67, 67, 66, 65, 65, 64, 63, 63, 62, 61, 61, 60, 60, 59, 58, 57, 57, 56, 55, 55, 54, 53, 53, 52, 51, 50, 50, 49, 48, 47, 47, 46, 45, 44, 43, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 25, 24, 23, 21, 19, 18, 16, 14, 11, 8, 0
    };

    private static int nextSqrt(int n, int i)
    {
        return (n + i / n) >> 1;
    }

    public static int sqrt(int number)
    {
        int n = 1;
        int n1 = nextSqrt(n, number);

        while (abs(n1 - n) > 1)
        {
            n = n1;
            n1 = nextSqrt(n, number);
        }
        while ((n1 * n1) > number)
        {
            n1 -= 1;
        }
        return n1;
    }

    public static int abs(int x)
    {
        if (x < 0)
        {
            return -x;
        }
        else
        {
            return x;
        }
    }

    public static int dist2points(int[] pointOne, int[] pointTwo)
    {
        //if points with different dimensions, return -1
        if ((pointOne.length != pointTwo.length) || (pointOne.length <= 0) || (pointTwo.length <= 0))
        {
            return -1;
        }

        int length = pointOne.length;
        int distance = 0;

        for (int i = 0; i < length; i++)
        {
            int delta2 = abs(pointTwo[i] - pointOne[i]);
            distance += delta2 * delta2;
        }

        return sqrt(distance);

    }

    public static int boundedInt(int value, int min, int max)
    {
        if (value < min)
        {
            return min;
        }
        else if (value > max)
        {
            return max;
        }
        else
        {
            return value;
        }
    }

    public static int min(int a, int b)
    {
        return a < b ? a : b;
    }

    public static int max(int a, int b)
    {
        return a > b ? a : b;
    }
}
