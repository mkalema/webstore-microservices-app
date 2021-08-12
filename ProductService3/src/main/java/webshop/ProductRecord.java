package webshop;

public class ProductRecord {

    private String productNumber;
    private Integer quantity;

    public ProductRecord() {
    }

    public ProductRecord(String productNumber, Integer quantity) {
        this.productNumber = productNumber;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductRecord{" +
                "productNumber='" + productNumber + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
