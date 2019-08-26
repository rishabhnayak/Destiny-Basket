package nmdl.express.ecomm.response;

public class ShopResponse {
    String shop_id,shop_name,shop_image,shop_timing,shop_rating,success,phone;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public void setShop_image(String shop_image) {
        this.shop_image = shop_image;
    }

    public void setShop_timing(String shop_timing) {
        this.shop_timing = shop_timing;
    }

    public void setShop_rating(String shop_rating) {
        this.shop_rating = shop_rating;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_image() {
        return shop_image;
    }

    public String getShop_timing() {
        return shop_timing;
    }

    public String getShop_rating() {
        return shop_rating;
    }

    public String getPhone() {
        return phone;
    }
}

