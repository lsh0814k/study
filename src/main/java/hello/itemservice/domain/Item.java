package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column 생략 가능 자동으로 카멜 케이스를 언더스코어로 자동으로 변환해준다.
    @Column(name = "item_name", length = 100)
    private String itemName;
    private Integer price;
    private Integer quantity;

    // JPA는 기본 생성자는 필수
    public Item() {}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
