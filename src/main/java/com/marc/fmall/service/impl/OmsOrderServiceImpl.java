package com.marc.fmall.service.impl;

import com.marc.fmall.common.exception.Asserts;
import com.marc.fmall.domain.CartPromotionItem;
import com.marc.fmall.domain.SmsCouponHistoryDetail;
import com.marc.fmall.dto.OrderParam;
import com.marc.fmall.entity.*;
import com.marc.fmall.mapper.OmsOrderMapper;
import com.marc.fmall.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marc.fmall.vo.ConfirmOrderResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author marc
 * @since 2021-03-15
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements IOmsOrderService {


    @Autowired
    private  OmsOrderMapper omsOrderMapper;
    @Autowired
    private OmsPromotionService omsPromotionService;
    @Autowired
    private IOmsCartItemService omsCartItemService;
    @Autowired
    private IUmsMemberReceiveAddressService umsMemberReceiveAddressService;
    @Autowired
    private IUmsMemberService memberService;
    @Autowired
    private IUmsIntegrationConsumeSettingService umsIntegrationConsumeSettingService;
    @Autowired
    private UmsMemberCouponService memberCouponService;
    @Override
    public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds, String openId) {
        ConfirmOrderResult result = new ConfirmOrderResult();
        //获取当前登录用户
        UmsMember currentMember = memberService.getCurrentMember(openId);
        //获取购物车信息
        List<CartPromotionItem> cartPromotionItemList = omsCartItemService.listPromotion(openId, cartIds);
        result.setCartPromotionItemList(cartPromotionItemList);
        //获取用户收货地址列表
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressService.listDelivery(openId);
        result.setMemberReceiveAddressList(umsMemberReceiveAddresses);
        //获取用户可用优惠券列表
        List<SmsCouponHistoryDetail> couponHistoryDetailList = memberCouponService.listCart(cartPromotionItemList, 1,openId);
        result.setCouponHistoryDetailList(couponHistoryDetailList);
        //获取用户积分
        result.setMemberIntegration(currentMember.getIntegration());
        //积分的使用规则
        UmsIntegrationConsumeSetting setting = umsIntegrationConsumeSettingService.getById(1);
        result.setIntegrationConsumeSetting(setting);
        //计算总金额、活动优惠、应付金额
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(cartPromotionItemList);
        result.setCalcaAmount(calcAmount);
        return result;
    }

    @Override
    public Map<String, Object> generateOrder(OrderParam orderParam) {
        //获取当前登录用户
        UmsMember currentMember = memberService.getCurrentMember(orderParam.getOpenId());
        //获取购物车信息
        List<CartPromotionItem> cartPromotionItemList = omsCartItemService.listPromotion(orderParam.getOpenId(), orderParam.getCartIds());
        List<OmsOrderItem> orderItemList = new ArrayList<>();
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            //生成下单商品信息
            OmsOrderItem omsOrderItem = new OmsOrderItem();
            omsOrderItem.setProductId(cartPromotionItem.getProductId());
            omsOrderItem.setProductName(cartPromotionItem.getProductName());
            omsOrderItem.setProductPic(cartPromotionItem.getProductPic());
            omsOrderItem.setProductAttr(cartPromotionItem.getProductAttr());
            omsOrderItem.setProductBrand(cartPromotionItem.getProductBrand());
            omsOrderItem.setProductSn(cartPromotionItem.getProductSn());
            omsOrderItem.setProductPrice(cartPromotionItem.getPrice());
            omsOrderItem.setProductQuantity(cartPromotionItem.getQuantity());
            omsOrderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
            omsOrderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
            omsOrderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
            omsOrderItem.setPromotionAmount(cartPromotionItem.getReduceAmount());
            omsOrderItem.setPromotionName(cartPromotionItem.getPromotionMessage());
            omsOrderItem.setGiftIntegration(cartPromotionItem.getIntegration());
            omsOrderItem.setGiftGrowth(cartPromotionItem.getGrowth());
            orderItemList.add(omsOrderItem);
        }

        //判断购物车中商品是否都有库存
        if (!hasStock(cartPromotionItemList)) {
            Asserts.fail("库存不足，无法下单");
        }
        //判断使用使用了优惠券
        if (orderParam.getCouponId() == null) {
            //不用优惠券
            for (OmsOrderItem orderItem : orderItemList) {
                orderItem.setCouponAmount(new BigDecimal(0));
            }
        } else {
            //使用优惠券
            SmsCouponHistoryDetail couponHistoryDetail = getUseCoupon(cartPromotionItemList, orderParam.getCouponId(),orderParam.getOpenId());
            if (couponHistoryDetail == null) {
                Asserts.fail("该优惠券不可用");
            }
            //对下单商品的优惠券进行处理
            handleCouponAmount(orderItemList, couponHistoryDetail);
        }
        //判断是否使用积分
//        if (orderParam.getUseIntegration() == null||orderParam.getUseIntegration().equals(0)) {
//            //不使用积分
//            for (OmsOrderItem orderItem : orderItemList) {
//                orderItem.setIntegrationAmount(new BigDecimal(0));
//            }
//        } else {
//            //使用积分
//            BigDecimal totalAmount = calcTotalAmount(orderItemList);
//            BigDecimal integrationAmount = getUseIntegrationAmount(orderParam.getUseIntegration(), totalAmount, currentMember, orderParam.getCouponId() != null);
//            if (integrationAmount.compareTo(new BigDecimal(0)) == 0) {
//                Asserts.fail("积分不可用");
//            } else {
//                //可用情况下分摊到可用商品中
//                for (OmsOrderItem orderItem : orderItemList) {
//                    BigDecimal perAmount = orderItem.getProductPrice().divide(totalAmount, 3, RoundingMode.HALF_EVEN).multiply(integrationAmount);
//                    orderItem.setIntegrationAmount(perAmount);
//                }
//            }
//        }
        //TODO
        return null;
    }

    /**
     * 查询购物车中用到的优惠券
     * @param cartPromotionItemList
     * @param couponId
     * @param openid
     * @return
     */
    public SmsCouponHistoryDetail getUseCoupon(List<CartPromotionItem> cartPromotionItemList,Long couponId,String openid){
        List<SmsCouponHistoryDetail> smsCouponHistoryDetails = memberCouponService.listCart(cartPromotionItemList, 1, openid);
        for (SmsCouponHistoryDetail smsCouponHistoryDetail : smsCouponHistoryDetails) {
            if(smsCouponHistoryDetail.getCouponId().equals(couponId)){
                return smsCouponHistoryDetail;
            }

        }
        return null;
    }

    /**
     * 处理优惠券优惠
     * @param orderItemList
     * @param couponHistoryDetail
     */
    public  void handleCouponAmount(List<OmsOrderItem> orderItemList,SmsCouponHistoryDetail couponHistoryDetail){
        SmsCoupon coupon = couponHistoryDetail.getCoupon();
        if(coupon.getUseType().equals(0)){
            //全场通用优惠券
            calcPerCouponAmount(orderItemList, coupon);
        }else if(coupon.getUseType().equals(1)){
            //指定某一类商品优惠

            calcPerCouponAmount(orderItemList, coupon);
        }else if(coupon.getUseType().equals(2)){
            //指定商品优惠
            calcPerCouponAmount(orderItemList, coupon);

        }

    }

    public   List<OmsOrderItem> getCouponOrderItemByRelation(){
            return  null;
    }

    /**
     *
     * @param orderItemList
     * @param coupon
     */
    public void  calcPerCouponAmount(List<OmsOrderItem> orderItemList, SmsCoupon coupon){
        BigDecimal bigDecimal = calcTotalAmount(orderItemList);
        for (OmsOrderItem omsOrderItem : orderItemList) {
            //（商品的价格/商品的总价）*优惠金额
            BigDecimal couponAmount = omsOrderItem.getProductPrice().divide(bigDecimal).multiply(coupon.getAmount());
            omsOrderItem.setCouponAmount(couponAmount);
        }
    }
    /**
     * 判断下单商品是否都有库存
     * @return
     */
    public boolean hasStock(List<CartPromotionItem> cartPromotionItemList){
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            if(cartPromotionItem.getRealStock()==null||cartPromotionItem.getRealStock() <=0){
                return false;
            }
        }
        return true;
    }
    /**
     * 计算总金额
     */
    private BigDecimal calcTotalAmount(List<OmsOrderItem> orderItemList) {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OmsOrderItem item : orderItemList) {
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        return totalAmount;
    }


    /**
     * 计算购物车中商品的价格
     */
    private ConfirmOrderResult.CalcAmount calcCartAmount(List<CartPromotionItem> cartPromotionItemList) {
        ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
        //TODO 运费有待商榷
        calcAmount.setFreightAmount(new BigDecimal(0));

        BigDecimal totalAmount = new BigDecimal("0");
        BigDecimal promotionAmount = new BigDecimal("0");
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            totalAmount = totalAmount.add(cartPromotionItem.getPrice().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
            promotionAmount = promotionAmount.add(cartPromotionItem.getReduceAmount().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
        }

        calcAmount.setTotalAmount(totalAmount);
        calcAmount.setPromotionAmount(promotionAmount);
        calcAmount.setPayAmount(totalAmount.subtract(promotionAmount));
        return calcAmount;
    }
}
