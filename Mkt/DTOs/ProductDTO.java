package app.mkt.dtos;

import app.mkt.interfaces.ProductInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for product information.
 * Implements ProductInterface for field name constants.
 */
public class ProductDTO implements ProductInterface
{

    private List<String> alternateImagesType1 = new ArrayList<>();

    private List<String> alternateImagesType2 = new ArrayList<>();

    private List<String> alternateImagesType3 = new ArrayList<>();

    private String brand = "";

    private String category = "";

    private String color = "";

    private String description = "";

    private boolean discontinued = false;

    private String gender = "";

    private String imageUrl = "";

    private boolean inStock = false;

    private boolean isClearance = false;

    private boolean isOutlet = false;

    private boolean isPurchasable = true;

    private boolean isViewable = true;

    private String linkUrl = "";

    private String masterSku = "";

    private String meta1 = "";

    private String meta2 = "";

    private String meta3 = "";

    private String meta4 = "";

    private String meta5 = "";

    private double msrp = 0.0;

    private boolean onSale = false;

    private double price = 0.0;

    private int qoh = 0;

    private List<String> relatedProducts = new ArrayList<>();

    private String reviewProductID = "";

    private String reviewURL = "";

    private String saleEndDate = "";

    private double salePrice = 0.0;

    private String saleStartDate = "";

    private String size = "";

    private String sku = "";

    private String style = "";

    private String subCategory = "";

    private String title = "";

    private double unitCost = 0.0;

    /**
     * Constructs a ProductDTO from a map of data.
     *
     * @param data map containing product data
     */
    @SuppressWarnings("unchecked")
    public ProductDTO(Map<String, Object> data)
    {
        if (data == null)
        {
            return;
        }

        this.alternateImagesType1 = (List<String>) data.getOrDefault(ALTERNATE_IMAGES_TYPE1, new ArrayList<>());
        this.alternateImagesType2 = (List<String>) data.getOrDefault(ALTERNATE_IMAGES_TYPE2, new ArrayList<>());
        this.alternateImagesType3 = (List<String>) data.getOrDefault(ALTERNATE_IMAGES_TYPE3, new ArrayList<>());
        this.brand = (String) data.getOrDefault(BRAND, "");
        this.category = (String) data.getOrDefault(CATEGORY, "");
        this.color = (String) data.getOrDefault(COLOR, "");
        this.description = (String) data.getOrDefault(DESCRIPTION, "");
        this.discontinued = (Boolean) data.getOrDefault(DISCONTINUED, false);
        this.gender = (String) data.getOrDefault(GENDER, "");
        this.imageUrl = (String) data.getOrDefault(IMAGE_URL, "");
        this.inStock = (Boolean) data.getOrDefault(IN_STOCK, false);
        this.isClearance = (Boolean) data.getOrDefault(IS_CLEARANCE, false);
        this.isOutlet = (Boolean) data.getOrDefault(IS_OUTLET, false);
        this.isPurchasable = (Boolean) data.getOrDefault(IS_PURCHASABLE, true);
        this.isViewable = (Boolean) data.getOrDefault(IS_VIEWABLE, true);
        this.linkUrl = (String) data.getOrDefault(LINK_URL, "");
        this.masterSku = (String) data.getOrDefault(MASTER_SKU, "");
        this.meta1 = (String) data.getOrDefault(META1, "");
        this.meta2 = (String) data.getOrDefault(META2, "");
        this.meta3 = (String) data.getOrDefault(META3, "");
        this.meta4 = (String) data.getOrDefault(META4, "");
        this.meta5 = (String) data.getOrDefault(META5, "");
        this.msrp = roundDouble(data.getOrDefault(MSRP, 0.0));
        this.onSale = (Boolean) data.getOrDefault(ON_SALE, false);
        this.price = roundDouble(data.getOrDefault(PRICE, 0.0));
        this.qoh = (Integer) data.getOrDefault(QOH, 0);
        this.relatedProducts = (List<String>) data.getOrDefault(RELATED_PRODUCTS, new ArrayList<>());
        this.reviewProductID = (String) data.getOrDefault(REVIEW_PRODUCT_ID, "");
        this.reviewURL = (String) data.getOrDefault(REVIEW_URL, "");
        this.saleEndDate = (String) data.getOrDefault(SALE_END_DATE, "");
        this.salePrice = roundDouble(data.getOrDefault(SALE_PRICE, 0.0));
        this.saleStartDate = (String) data.getOrDefault(SALE_START_DATE, "");
        this.size = (String) data.getOrDefault(SIZE, "");
        this.sku = (String) data.getOrDefault(SKU, "");
        this.style = (String) data.getOrDefault(STYLE, "");
        this.subCategory = (String) data.getOrDefault(SUB_CATEGORY, "");
        this.title = (String) data.getOrDefault(TITLE, "");
        this.unitCost = roundDouble(data.getOrDefault(UNIT_COST, 0.0));
    }

    private double roundDouble(Object value)
    {
        if (value instanceof Number)
        {
            return Math.round(((Number) value).doubleValue() * 100.0) / 100.0;
        }
        return 0.0;
    }

    public String getSku()
    {
        return sku;
    }

    public String getTitle()
    {
        return title;
    }

    public double getPrice()
    {
        return price;
    }

    public boolean isInStock()
    {
        return inStock;
    }

    /**
     * Converts the DTO to JSON string.
     *
     * @return JSON string representation
     */
    public String toJSON()
    {
        Map<String, Object> map = new HashMap<>();
        map.put(ALTERNATE_IMAGES_TYPE1, alternateImagesType1);
        map.put(ALTERNATE_IMAGES_TYPE2, alternateImagesType2);
        map.put(ALTERNATE_IMAGES_TYPE3, alternateImagesType3);
        map.put(BRAND, brand);
        map.put(CATEGORY, category);
        map.put(COLOR, color);
        map.put(DESCRIPTION, description);
        map.put(DISCONTINUED, discontinued);
        map.put(GENDER, gender);
        map.put(IMAGE_URL, imageUrl);
        map.put(IN_STOCK, inStock);
        map.put(IS_CLEARANCE, isClearance);
        map.put(IS_OUTLET, isOutlet);
        map.put(IS_PURCHASABLE, isPurchasable);
        map.put(IS_VIEWABLE, isViewable);
        map.put(LINK_URL, linkUrl);
        map.put(MASTER_SKU, masterSku);
        map.put(META1, meta1);
        map.put(META2, meta2);
        map.put(META3, meta3);
        map.put(META4, meta4);
        map.put(META5, meta5);
        map.put(MSRP, msrp);
        map.put(ON_SALE, onSale);
        map.put(PRICE, price);
        map.put(QOH, qoh);
        map.put(RELATED_PRODUCTS, relatedProducts);
        map.put(REVIEW_PRODUCT_ID, reviewProductID);
        map.put(REVIEW_URL, reviewURL);
        map.put(SALE_END_DATE, saleEndDate);
        map.put(SALE_PRICE, salePrice);
        map.put(SALE_START_DATE, saleStartDate);
        map.put(SIZE, size);
        map.put(SKU, sku);
        map.put(STYLE, style);
        map.put(SUB_CATEGORY, subCategory);
        map.put(TITLE, title);
        map.put(UNIT_COST, unitCost);

        return new Gson().toJson(map);
    }
}

