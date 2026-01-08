package app.mkt.dtos;

import app.mkt.interfaces.OrderInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for order information.
 * Implements OrderInterface for field name constants.
 */
public class OrderDTO implements OrderInterface
{

    private String associateID = "";

    private Map<String, Object> billingAddress = new HashMap<>();

    private String couponCode = "";

    private Map<String, Object> customer = new HashMap<>();

    private String customerNumber = "";

    private String dateEntered = "";

    private double discountTotal = 0.0;

    private String email = "";

    private double handlingTotal = 0.0;

    private List<Map<String, Object>> items = new ArrayList<>();

    private double itemTotal = 0.0;

    private double merchandiseDiscount = 0.0;

    private String merchandiseDiscountDescription = "";

    private String merchandiseDiscountType = "";

    private String meta1 = "";

    private String meta2 = "";

    private String meta3 = "";

    private String meta4 = "";

    private String meta5 = "";

    private double nonMerchandiseDiscount = 0.0;

    private String nonMerchandiseDiscountDescription = "";

    private String nonMerchandiseDiscountType = "";

    private String orderNumber = "";

    private double orderTotal = 0.0;

    private String shipDate = "";

    private Map<String, Object> shippingAddress = new HashMap<>();

    private String shippingMethod = "";

    private double shippingTotal = 0.0;

    private String source = "";

    private String status = "";

    private String storeNumber = "";

    private double taxTotal = 0.0;

    private String trackingNumber = "";

    /**
     * Constructs an OrderDTO from a map of data.
     *
     * @param data map containing order data
     */
    @SuppressWarnings("unchecked")
    public OrderDTO(Map<String, Object> data)
    {
        if (data == null)
        {
            return;
        }

        this.associateID = String.valueOf(data.getOrDefault(ASSOCIATE_ID, ""));
        this.billingAddress = (Map<String, Object>) data.getOrDefault(BILLING_ADDRESS, new HashMap<>());
        this.couponCode = String.valueOf(data.getOrDefault(COUPON_CODE, ""));
        this.customer = (Map<String, Object>) data.getOrDefault(CUSTOMER, new HashMap<>());
        this.customerNumber = String.valueOf(data.getOrDefault(CUSTOMER_NUMBER, ""));
        this.dateEntered = String.valueOf(data.getOrDefault(DATE_ENTERED, ""));
        this.discountTotal = roundDouble(data.getOrDefault(DISCOUNT_TOTAL, 0.0));
        this.email = String.valueOf(data.getOrDefault(EMAIL, ""));
        this.handlingTotal = roundDouble(data.getOrDefault(HANDLING_TOTAL, 0.0));
        this.items = (List<Map<String, Object>>) data.getOrDefault(ITEMS, new ArrayList<>());
        this.merchandiseDiscount = roundDouble(data.getOrDefault(MERCHANDISE_DISCOUNT, 0.0));
        this.merchandiseDiscountDescription = String.valueOf(data.getOrDefault(MERCHANDISE_DISCOUNT_DESCRIPTION, ""));
        this.merchandiseDiscountType = String.valueOf(data.getOrDefault(MERCHANDISE_DISCOUNT_TYPE, ""));
        this.meta1 = String.valueOf(data.getOrDefault(META1, ""));
        this.meta2 = String.valueOf(data.getOrDefault(META2, ""));
        this.meta3 = String.valueOf(data.getOrDefault(META3, ""));
        this.meta4 = String.valueOf(data.getOrDefault(META4, ""));
        this.meta5 = String.valueOf(data.getOrDefault(META5, ""));
        this.nonMerchandiseDiscount = roundDouble(data.getOrDefault(NON_MERCHANDISE_DISCOUNT, 0.0));
        this.nonMerchandiseDiscountDescription = String.valueOf(data.getOrDefault(NON_MERCHANDISE_DISCOUNT_DESCRIPTION, ""));
        this.nonMerchandiseDiscountType = String.valueOf(data.getOrDefault(NON_MERCHANDISE_DISCOUNT_TYPE, ""));
        this.orderNumber = String.valueOf(data.getOrDefault(ORDER_NUMBER, ""));
        this.orderTotal = roundDouble(data.getOrDefault(ORDER_TOTAL, 0.0));
        this.shipDate = String.valueOf(data.getOrDefault(SHIP_DATE, ""));
        this.shippingAddress = (Map<String, Object>) data.getOrDefault(SHIPPING_ADDRESS, new HashMap<>());
        this.shippingMethod = String.valueOf(data.getOrDefault(SHIPPING_METHOD, ""));
        this.shippingTotal = roundDouble(data.getOrDefault(SHIPPING_TOTAL, 0.0));
        this.source = String.valueOf(data.getOrDefault(SOURCE, ""));
        this.status = String.valueOf(data.getOrDefault(STATUS, ""));
        this.storeNumber = String.valueOf(data.getOrDefault(STORE_NUMBER, ""));
        this.taxTotal = roundDouble(data.getOrDefault(TAX_TOTAL, 0.0));
        this.trackingNumber = String.valueOf(data.getOrDefault(TRACKING_NUMBER, ""));

        this.itemTotal = calculateItemTotal(this.items);
    }

    private double roundDouble(Object value)
    {
        if (value instanceof Number)
        {
            return Math.round(((Number) value).doubleValue() * 100.0) / 100.0;
        }
        return 0.0;
    }

    private double calculateItemTotal(List<Map<String, Object>> items)
    {
        double total = 0.0;
        for (Map<String, Object> item : items)
        {
            Object itemTotal = item.get("itemTotal");
            if (itemTotal instanceof Number)
            {
                total += ((Number) itemTotal).doubleValue();
            }
        }
        return Math.round(total * 100.0) / 100.0;
    }

    /**
     * Converts the DTO to JSON string.
     *
     * @return JSON string representation
     */
    public String toJSON()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(ASSOCIATE_ID, associateID);
        map.put(BILLING_ADDRESS, billingAddress);
        map.put(COUPON_CODE, couponCode);
        map.put(CUSTOMER, customer);
        map.put(CUSTOMER_NUMBER, customerNumber);
        map.put(DATE_ENTERED, dateEntered);
        map.put(DISCOUNT_TOTAL, discountTotal);
        map.put(EMAIL, email);
        map.put(HANDLING_TOTAL, handlingTotal);
        map.put(ITEMS, items);
        map.put(ITEM_TOTAL, itemTotal);
        map.put(MERCHANDISE_DISCOUNT, merchandiseDiscount);
        map.put(MERCHANDISE_DISCOUNT_DESCRIPTION, merchandiseDiscountDescription);
        map.put(MERCHANDISE_DISCOUNT_TYPE, merchandiseDiscountType);
        map.put(META1, meta1);
        map.put(META2, meta2);
        map.put(META3, meta3);
        map.put(META4, meta4);
        map.put(META5, meta5);
        map.put(NON_MERCHANDISE_DISCOUNT, nonMerchandiseDiscount);
        map.put(NON_MERCHANDISE_DISCOUNT_DESCRIPTION, nonMerchandiseDiscountDescription);
        map.put(NON_MERCHANDISE_DISCOUNT_TYPE, nonMerchandiseDiscountType);
        map.put(ORDER_NUMBER, orderNumber);
        map.put(ORDER_TOTAL, orderTotal);
        map.put(SHIP_DATE, shipDate);
        map.put(SHIPPING_ADDRESS, shippingAddress);
        map.put(SHIPPING_METHOD, shippingMethod);
        map.put(SHIPPING_TOTAL, shippingTotal);
        map.put(SOURCE, source);
        map.put(STATUS, status);
        map.put(STORE_NUMBER, storeNumber);
        map.put(TAX_TOTAL, taxTotal);
        map.put(TRACKING_NUMBER, trackingNumber);

        return new Gson().toJson(map);
    }
}

