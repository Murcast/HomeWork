package HomeWork.FirstTask;

public class Predicate {
    /**
     * The array of predicates whose elements restrict the addition of new
     * elements to the array and the deletion of the existing ones
     */
    private transient Object[] predicateData;

    /**
     * Empty predicate array.
     */
    private static final Object[] EMPTY_PREDICATEDATA = {};

    /**
     * Construct the predicate that contains empty predicate data array
     */
    public Predicate(){
        this.predicateData = EMPTY_PREDICATEDATA;
    }

    /**
     * Construct the predicate that contains specified predicate data array
     *
     * @param predicateData predicate data array
     */
    public Predicate(Object[] predicateData){
        if (predicateData != null) {
            this.predicateData = predicateData;
        } else {
            throw new IllegalArgumentException("Illegal predicateData argument");
        }
    }

    /*
     * Private method that checks whether an element is not contained in predicate array.
     * If not, returns true.
     */
    public boolean notContains(Object element) {
        for (int i = 0; i < predicateData.length; i++) {
            if (predicateData[i].equals(element)) {
                return false;
            }
        }
        return true;
    }
}
