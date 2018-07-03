package in.saralam.sbs.server.subscription;

import com.sapienter.jbilling.common.CommonConstants;

public final class SubscriptionConstants implements CommonConstants {
 public static final String TABLE_SERVICE_STATUS = "service_status"; //added
 public static final String TABLE_SERVICE_FEATURE_STATUS = "service_feature_status"; //added
 public static final int SERVICE_FEATURE_STATUS_PENDING = 1;
 public static final int SERVICE_FEATURE_STATUS_PROVISIONED = 2;
 public static final int SERVICE_FEATURE_STATUS_DEPROVISIONED = 3;
}
