package hello.itemservice.domain;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach// 각각의 테스트 메서드가 실행된 후에 수행되어야 할 작업들을 정의할 때 사용
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item=new Item("itemA",10000,10);

        //when
        Item savedItem=itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
        // 아이템을 저장하고 그 저장된 아이템과 아이템의 아이디로 찾은 아이템이 같은지 판단

    }

    @Test
    void findAll(){
        //given
        //리스트 조회는 아이템 2개 이상 만들어서 테스트하기
        Item item1=new Item("item1",10000,10);
        Item item2=new Item("item2",20000,20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result=itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1,item2);

    }

    @Test
    void updateItem(){
        //given
        Item item=new Item("item1",10000,10);

        Item savedItem=itemRepository.save(item);
        Long itemId=savedItem.getId();

        //when
        Item updateParam=new Item("item2",20000,30);
        itemRepository.update(itemId,updateParam);

        //then
        Item findItem=itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());

    }
}
