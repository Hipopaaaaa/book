package ohj.pojo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ohj
 * @Date 2022-07-19 14:47
 * 购物车对象
 */
public class Cart {
    private Integer totalCount;
    private BigDecimal totalPrice;
    // key是商品编号，value是商品信息
    private Map<Integer,CartItem> items=new LinkedHashMap<>();

    /**
     * 功能； 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem){
        // 先查看购物车中是否已经添加过此商品项
        //若已添加，则该商品项的数量和总金额累加
        //若无添加，直接添加到集合中
        CartItem item = items.get(cartItem.getId());

        if(item==null){
            items.put(cartItem.getId(),cartItem);
        }else {
            item.setCount(item.getCount()+1);  //数量累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount()))); //金额累加
        }
    }

    /**
     * 功能； 删除商品项
     * @param id
     */
    public void deleteItem(Integer id){
        items.remove(id);
    }

    /**
     * 功能； 清空购物车
     */
    public void clear( ){
        items.clear();
    }


    /**
     * 功能； 修改商品数量
     * @param id
     */
    public void updateCount(Integer id,Integer count){
        //先查看购物车中是否有此商品
        //若有，则修改商品数量，更新总金额
        CartItem cartItem = items.get(id);
        if(cartItem!=null){
            cartItem.setCount(count);  //数量累加
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));  //金额累加
        }
    }


    public Integer getTotalCount() {
        totalCount=0;
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalCount+=entry.getValue().getCount();
        }
        return totalCount;
    }



    public BigDecimal getTotalPrice() {
        totalPrice=new BigDecimal(0);
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            totalPrice=totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }


    public Cart() {
    }

    public Map<Integer, CartItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }
}
