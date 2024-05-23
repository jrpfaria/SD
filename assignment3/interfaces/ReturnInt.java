package interfaces;

import java.io.Serializable;

/**
 * Data type to return both an integer value and an integer state value.
 *
 * Used in calls on remote objects.
 */

public class ReturnInt implements Serializable {
   /**
    * Serialization key.
    */

   public static final long serialVersionUID = 2021L;

   /**
    * Integer value.
    */

   private int val;

   /**
    * Integer state value.
    */

   private int state;

   /**
    * ReturnInt instantiation.
    *
    * @param val   integer value
    * @param state integer state value
    */

   public ReturnInt(int val, int state) {
      this.val = val;
      this.state = state;
   }

   /**
    * Getting integer value.
    *
    * @return integer value
    */

   public int getIntVal() {
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
