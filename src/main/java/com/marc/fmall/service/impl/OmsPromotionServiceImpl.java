package com.marc.fmall.service.impl;

import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.domain.PromotionProduct;
import com.marc.fmall.entity.*;
import com.marc.fmall.mapper.PortalProductMapper;
import com.marc.fmall.service.OmsPromotionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @Author: marc
 * @Date: 2021/3/18 17:57
 */
@Service
public class OmsPromotionServiceImpl implements OmsPromotionService {
    @Autowired
    private PortalProductMapper portalProductMapper;
    @Override
    public List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList) {
        //1.先根据商品的productId进行对购物车里面商品进行分组，以spu为单位进行计算优惠
        Map<Long, List<OmsCartItem>> listMap = groupCartItemBySpu(cartItemList);

        //2.查询所有商品的优惠相关信息
        List<PromotionProduct> promotionProductList = getPromotionProductList(cartItemList);
        //3.根据商品促销类型计算商品促销优惠价格
        ArrayList<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        for (Map.Entry<Long, List<OmsCartItem>> entry : listMap.entrySet()) {
            Long productId = entry.getKey();
            PromotionProduct promotionProduct = getPromotionProductById(productId, promotionProductList);
            List<OmsCartItem> itemList = entry.getValue();
            Integer promotionType = promotionProduct.getPromotionType();
            if(promotionType==1){
                //单品促销
                for (OmsCartItem omsCartItem : itemList) {

                    CartPromotionItem cartPromotionItem = new CartPromotionItem();
                    BeanUtils.copyProperties(omsCartItem,cartPromotionItem);
                    cartPromotionItem.setPromotionMessage("单品促销");
                    //商品原价-促销价
                    PmsSkuStock pmsSkuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
                    BigDecimal orignPrice = pmsSkuStock.getPrice();
                    //单品促销使用原价
                    cartPromotionItem.setPrice(orignPrice);
                    cartPromotionItem.setReduceAmount(orignPrice.subtract(pmsSkuStock.getPromotionPrice()));
                    cartPromotionItem.setRealStock(pmsSkuStock.getStock()-pmsSkuStock.getLockStock());
                    cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                    cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                    cartPromotionItemList.add(cartPromotionItem);
                }
            }else if(promotionType==3){
                //打折优惠
                int cartItemCount = getCartItemCount(itemList);
                PmsProductLadder productLadder = getProductLadder(cartItemCount, promotionProduct.getProductLadderList());
                if(productLadder!=null){
                    for (OmsCartItem omsCartItem : itemList) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        BeanUtils.copyProperties(omsCartItem,cartPromotionItem);
                        String message = getLadderPromotionMessage(productLadder);
                        cartPromotionItem.setPromotionMessage(message);
                        //商品原价-折扣*原价
                        PmsSkuStock pmsSkuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
                        BigDecimal orignPrice = pmsSkuStock.getPrice();
                        BigDecimal reduceAmount = orignPrice.subtract(productLadder.getDiscount().multiply(orignPrice));
                        cartPromotionItem.setReduceAmount(reduceAmount);
                        cartPromotionItem.setRealStock(pmsSkuStock.getStock()-pmsSkuStock.getLockStock());
                        cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                }else {
                    handleNoReduce(cartPromotionItemList,itemList,promotionProduct);
                }
            }else if(promotionType==4){
                //满减
                BigDecimal totalAmount = getCartItemAmount(itemList, promotionProductList);
                PmsProductFullReduction productFullReduction = getProductFullReduction(totalAmount, promotionProduct.getProductFullReductionList());
                if(productFullReduction!=null){
                    for (OmsCartItem omsCartItem : itemList) {
                        CartPromotionItem cartPromotionItem = new CartPromotionItem();
                        BeanUtils.copyProperties(omsCartItem,cartPromotionItem);
                        String message = getFullReductionPromotionMessage(productFullReduction);
                        cartPromotionItem.setPromotionMessage(message);
                        //（商品原价/总价）*（满减金额）
                        PmsSkuStock pmsSkuStock = getOriginalPrice(promotionProduct, omsCartItem.getProductSkuId());
                        BigDecimal reduceAmount = pmsSkuStock.getPrice().divide(totalAmount, RoundingMode.HALF_EVEN).multiply(productFullReduction.getReducePrice());
                        cartPromotionItem.setReduceAmount(reduceAmount);
                        cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
                        cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
                        cartPromotionItemList.add(cartPromotionItem);
                    }
                }else {
                    handleNoReduce(cartPromotionItemList,itemList,promotionProduct);
                }

            }else {
                //无优惠
                handleNoReduce(cartPromotionItemList,itemList,promotionProduct);
            }

        }
        return cartPromotionItemList;
    }

    /**
     * 以SPU为单位进行对购物车商品分组
     */

    public Map<Long,List<OmsCartItem>> groupCartItemBySpu(List<OmsCartItem> cartItemList) {
        Map<Long, List<OmsCartItem>> listMap = new TreeMap<>();
        for (OmsCartItem omsCartItem : cartItemList) {
            List<OmsCartItem> omsCartItems = listMap.get(omsCartItem.getProductId());
            if (omsCartItems == null) {
                omsCartItems = new ArrayList<>();
                omsCartItems.add(omsCartItem);
                listMap.put(omsCartItem.getProductId(), omsCartItems);

            } else {
                omsCartItems.add(omsCartItem);
            }
        }
        return listMap;
    }

    /**
     *获取购物车中商品的数量
     * @return
     */
    public int getCartItemCount(List<OmsCartItem> cartItems){
        int count=0;
        for (OmsCartItem cartItem : cartItems) {
            count+=cartItem.getQuantity();

        }
        return count;

    }

    /**
     * 购物车中商品总价格
     * @return
     */
    private BigDecimal getCartItemAmount(List<OmsCartItem> itemList,List<PromotionProduct> promotionProductList){
        BigDecimal amount = new BigDecimal(0);
        for (OmsCartItem omsCartItem : itemList) {
            PromotionProduct promotionProductById = getPromotionProductById(omsCartItem.getProductId(), promotionProductList);
            PmsSkuStock pmsSkuStock = getOriginalPrice(promotionProductById, omsCartItem.getProductSkuId());
            amount=amount.add(pmsSkuStock.getPrice().multiply(new BigDecimal(omsCartItem.getQuantity())));
        }
        return amount;

    }

    /**
     * 获取商品对应的折扣信息
     * @param count
     * @param productLadderList
     * @return
     */
   public PmsProductLadder getProductLadder(int count,List<PmsProductLadder> productLadderList){
        //按照数量从大到小排序
       productLadderList.sort(new Comparator<PmsProductLadder>() {
           @Override
           public int compare(PmsProductLadder o1, PmsProductLadder o2) {
               return o2.getCount()-o1.getCount();
           }
       });
       for (PmsProductLadder pmsProductLadder : productLadderList) {
           if(count>=pmsProductLadder.getCount()){
               return pmsProductLadder;
           }
       }
       return null;
    }

    /**
     * 获取满减信息
     * @param amount
     * @param pmsProductFullReductionList
     * @return
     */
   private PmsProductFullReduction getProductFullReduction(BigDecimal amount,List<PmsProductFullReduction> pmsProductFullReductionList){
       //按条件从高到底排序
       pmsProductFullReductionList.sort(new Comparator<PmsProductFullReduction>() {
           @Override
           public int compare(PmsProductFullReduction o1, PmsProductFullReduction o2) {
               return o2.getFullPrice().subtract(o1.getFullPrice()).intValue();
           }
       });
       for (PmsProductFullReduction pmsProductFullReduction : pmsProductFullReductionList) {
           if(amount.subtract(pmsProductFullReduction.getFullPrice()).intValue()>=0){
               return pmsProductFullReduction;
           }

       }
       return null;

   }

    /**
     * 获取满减促销消息
     * @param fullReduction
     * @return
     */
    private String getFullReductionPromotionMessage(PmsProductFullReduction fullReduction) {
        StringBuilder sb = new StringBuilder();
        sb.append("满减优惠：");
        sb.append("满");
        sb.append(fullReduction.getFullPrice());
        sb.append("元，");
        sb.append("减");
        sb.append(fullReduction.getReducePrice());
        sb.append("元");
        return sb.toString();
    }


    /**
     * 对没有满足优惠条件的商品进行处理
     */
    private void handleNoReduce(List<CartPromotionItem> cartPromotionItemList,List<OmsCartItem> itemList,PromotionProduct promotionProduct){
        for (OmsCartItem omsCartItem : itemList) {
            CartPromotionItem cartPromotionItem = new CartPromotionItem();
            BeanUtils.copyProperties(omsCartItem,cartPromotionItem);
            cartPromotionItem.setPromotionMessage("无优惠");
            cartPromotionItem.setReduceAmount(new BigDecimal(0));
            PmsSkuStock skuStock = getOriginalPrice(promotionProduct,omsCartItem.getProductSkuId());
            if(skuStock!=null){
                cartPromotionItem.setRealStock(skuStock.getStock()-skuStock.getLockStock());
            }
            cartPromotionItem.setIntegration(promotionProduct.getGiftPoint());
            cartPromotionItem.setGrowth(promotionProduct.getGiftGrowth());
            cartPromotionItemList.add(cartPromotionItem);
        }

    }

    /**
     * 获取打折优惠的促销信息
     */
    private String getLadderPromotionMessage(PmsProductLadder ladder) {
        StringBuilder sb = new StringBuilder();
        sb.append("打折优惠：");
        sb.append("满");
        sb.append(ladder.getCount());
        sb.append("件，");
        sb.append("打");
        sb.append(ladder.getDiscount().multiply(new BigDecimal(10)));
        sb.append("折");
        return sb.toString();
    }
    /**
     * 查询所有商品的优惠相关信息
     */

    public List<PromotionProduct> getPromotionProductList(List<OmsCartItem> cartItemList){
        ArrayList<Long> productIds = new ArrayList<>();
        for (OmsCartItem omsCartItem : cartItemList) {
            productIds.add(omsCartItem.getProductId());
        }
        return portalProductMapper.getPromotionProductList(productIds);
    }

    /**
     * 获取购车中商品在sku-store中对应的原价
     * @param promotionProduct
     * @param productSkuId
     * @return
     */

   public PmsSkuStock getOriginalPrice(PromotionProduct promotionProduct,Long productSkuId){
       for (PmsSkuStock skuStock :promotionProduct.getSkuStockList()) {
           if(productSkuId.equals(skuStock.getId())){
               return skuStock;
           }
       }
       return  null;
   }

    /**
     * 根据商品id获取商品的促销信息
     */
    public PromotionProduct getPromotionProductById(Long productId,List<PromotionProduct> list){
        for (PromotionProduct promotionProduct : list) {
            if (productId.equals(promotionProduct.getId())){
                return promotionProduct;
            }
        }
        return null;
    }
}
