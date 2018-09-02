package ie.sugrue.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public static Boolean isNotNull(Object val) {
		return !isNull(val);
	}

	public static Boolean isNull(Object val) {
		return (null == val);
	}

	// TODO Probably should cater for other numerics here. Also, should consider if this is required. Probably, the best solution is to use
	// Boxed datatypes in domain model definitions rather than primitives. Otherwise, we are not sure if a value is zero because it was
	// empty/missing or if the user/logic wants to set it to zero.
	public static Boolean isNullOrZero(Object val) {
		if (null == val) {
			return true;
		} else if (val instanceof Integer) {
			if (0 == (Integer) val) {
				return true;
			}
		}

		return false;
	}
}
