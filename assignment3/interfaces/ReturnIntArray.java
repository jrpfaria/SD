package interfaces;

import java.io.Serializable;

/**
 * Data type to return both an integer array value and an integer state value.
 *
 * Used in calls on remote objects.
 */

public class ReturnIntArray implements Serializable {
   /**
    * Serialization key.
    */

   public static final long serialVersionUID = 2021L;

   /**
    * Integer array value.
    */

   private int[] val;

   /**
    * Integer state value.
    */

   private int state;

   /**
    * ReturnInt instantiation.
    *
    * @param val   integer array value
    * @param state integer state value
    */

   public ReturnIntArray(int val[], int state) {
      this.val = val;
      this.state = state;
   }

   /**
    * Getting integer array value.
    *
    * @return integer array value
    */

   public int[] getIntVal() {
      return (val);
   }

   /**
    * Getting integer state value.
    *
    * @return integer state value
    */

   public int getIntStateVal() {
      return (state);
   }
}
