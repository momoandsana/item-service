package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basic/items")// 가장 상위 url. 클래스 안의 함수들은 이 url + 추가적인 url 이 들어간다
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }// 생성자 주입. 생성자가 하나만 있으므로 Autowired 생략 가능->@RequiredArgsConstructor 로 생성가능
    @GetMapping
    public String items(Model model)
    {
        List<Item> items=itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")// 아이템 상세
    public String item(@PathVariable("itemId") Long itemId,Model model){
        Item item=itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    /*
    테스트용 데이터 추가
     */
    @PostConstruct// 의존성 주입이 완료된 후에 자동으로 호출
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }
}
