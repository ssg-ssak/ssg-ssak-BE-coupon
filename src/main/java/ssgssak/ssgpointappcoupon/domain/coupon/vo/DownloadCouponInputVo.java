package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.Getter;

@Getter
public class DownloadCouponInputVo {
    private Long couponId;
    private String couponNumber;
    private String uuid; //todo: 임시설정
}
