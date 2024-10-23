package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store=new HashMap<>();// 그냥 해시맵은 동시에 여러 스레드가 접근가능. concurrent hashmap 을 써야 한다
    private static long sequence=0L; // 여기도 동시에 접근한다면 atomic long 을 사용해야 한다

    public Item save(Item item)
    {
        item.setId(++sequence);
        store.put(item.getId(),item);
        return item;
    }

    public Item findById(Long id)
    {
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam)
    {
        Item findItem=findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        //원래는 Item 의 id 를 사용하지 않기 때문에 별도의 update 객체를 만드는게 맞아, 중복보다는 명확성이 더 중요함. 기능이 겹쳐도 dto 를 더 만드는게 맞다
    }

    public void clearStore(){
        store.clear();
    }

}
