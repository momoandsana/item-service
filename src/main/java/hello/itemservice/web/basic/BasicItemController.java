package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")// 같은 url 이지만 get, post 다르기 때문에 다르게 처리(http method 로 구분)
    public String addItemV1(@RequestParam("itemName") String itemName,
                       @RequestParam("price") int price,
                       @RequestParam("itemName") Integer quantity,
                       Model model)
    {
        Item item =new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item",item);

        return "basic/item";
    }

    @PostMapping("/add")// 같은 url 이지만 get, post 다르기 때문에 다르게 처리(http method 로 구분)
    public String addItemV2(@ModelAttribute("item") Item item,Model model)
    {
    /*(
    @ModelAttribute 는 프론트에서 객체로 받아오고, 자동으로 model.addAttribute("item",item) 의 역할을
    해주기 때문에 바로 다음 뷰에 객체를 넣어서 보낼 수가 있다
     */
        itemRepository.save(item); // 바로 객체 통째로 받아옴

        //model.addAttribute("item",item); // 자동 추가, 생략 가능

        return "basic/item";
    }

    @GetMapping("/{itemId}/edit") // 해당 상품의 수정 폼을 불러옴
    public String editForm(@PathVariable("itemId") Long itemId,Model model)
    {
        Item item=itemRepository.findById(itemId); // 해당 아이디 번호에 해당하는 상품을 꺼내옴
        model.addAttribute("item",item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")// 해당 상품의 수정 폼 제출하기
    public String edit(@PathVariable("itemId") Long itemId,@ModelAttribute Item item)
    {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
        // redirect: 없으면 무조건 forward, redirect 는 무조건 url 바뀜
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
