package hello.itemservice.domain;

import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.v2.ItemQueryRepositoryV2;
import hello.itemservice.repository.v2.ItemRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class ItemQueryRepositoryV2Test {
    @Autowired
    private ItemQueryRepositoryV2 itemQueryRepositoryV2;
    @Autowired
    private ItemRepositoryV2 itemRepositoryV2;

    @TestConfiguration
    static class ItemQueryRepositoryV2Config {
        @Autowired
        private EntityManager em;

        @Bean
        ItemQueryRepositoryV2 itemQueryRepositoryV2() {
            return new ItemQueryRepositoryV2(em);
        }
    }


    @Test
    void findItems() {
        //given
        Item item1 = new Item("itemA-1", 10000, 10);
        Item item2 = new Item("itemA-2", 20000, 20);
        Item item3 = new Item("itemB-1", 30000, 30);

        itemRepositoryV2.save(item1);
        itemRepositoryV2.save(item2);
        itemRepositoryV2.save(item3);

        //둘 다 없음 검증
        test(null, null, item1, item2, item3);
        test("", null, item1, item2, item3);

        //itemName 검증
        test("itemA", null, item1, item2);
        test("temA", null, item1, item2);
        test("itemB", null, item3);

        //maxPrice 검증
        test(null, 10000, item1);

        //둘 다 있음 검증
        test("itemA", 10000, item1);
    }

    void test(String itemName, Integer maxPrice, Item... items) {
        List<Item> result = itemQueryRepositoryV2.findAll(new ItemSearchCond(itemName, maxPrice));
        assertThat(result).containsExactly(items);
    }
}
