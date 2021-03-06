package com.moon.lang;

/**
 * @author benshaoye
 */
public enum NumberComparator {
    GT {
        @Override
        public boolean compare(Number value1, Number value2) {
            return nonNull(value1, value2) && doCompare(value1, value2) > 0;
        }
    },
    LT {
        @Override
        public boolean compare(Number value1, Number value2) {
            return nonNull(value1, value2) && doCompare(value1, value2) < 0;
        }
    },
    EQ {
        @Override
        public boolean compare(Number value1, Number value2) {
            return (value1 == null && value2 == null)
                || (nonNull(value1, value2) && doCompare(value1, value2) == 0);
        }
    },
    GE {
        @Override
        public boolean compare(Number value1, Number value2) {
            return !LT.compare(value1, value2);
        }
    },
    LE {
        @Override
        public boolean compare(Number value1, Number value2) {
            return !GT.compare(value1, value2);
        }
    },
    NE {
        @Override
        public boolean compare(Number value1, Number value2) {
            return !EQ.compare(value1, value2);
        }
    };

    private static boolean nonNull(Number value1, Number value2) {
        return value1 != null && value2 != null;
    }

    private static int doCompare(Number value1, Number value2) {
        if (value1 instanceof Comparable) {
            return ((Comparable) value1).compareTo(value2);
        }
        throw new IllegalArgumentException();
    }

    public abstract boolean compare(Number value1, Number value2);
}
