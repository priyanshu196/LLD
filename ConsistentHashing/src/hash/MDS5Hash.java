package hash;

public class MDS5Hash implements HashFunction {



    public Integer hash(Object key) {
        if (key == null)
            return 0;

        int result = 1;

            result = 31 * result + (key == null ? 0 : key).hashCode();

        return result;
    }


}
