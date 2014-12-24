package bc_hashtable;

/**
 *
 * @author Brett Crawford (brett.crawford@temple.edu)
 */
public class BC_HashTable {

    int maxSize;
    double[] table;

    public BC_HashTable(int maxSize) {
            this.maxSize = maxSize;
            this.table = new double[maxSize];
    }

    // Returns result[0] - position in table
    //         result[1] - number of collisions
    public int[] insert(double element) {
        int result[] = new int[2];
        int collisions = 0;
        int pos = hashFunction(element);
        while(table[pos] != 0 && collisions < maxSize) {
            collisions++;
            pos = (pos + 1) % maxSize;
        }
        if(table[pos] == 0) {
            table[pos] = element;
        }
        else {
            pos = -1;
        }

        result[0] = pos;
        result[1] = collisions;

        return result;
    }

    private int hashFunction(double value) {

            return (int) (Math.round(value) % maxSize);
    }
}
