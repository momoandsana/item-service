package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

// dto 로 사용하는 경우에만 @Data 를 쓴다. 포함하는 어노테이션이 많기 때문에 신중하게 사용해야 한다
@Getter
@Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price;// price=0 이면 이상하니까 초기값을 null 로 설정
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
