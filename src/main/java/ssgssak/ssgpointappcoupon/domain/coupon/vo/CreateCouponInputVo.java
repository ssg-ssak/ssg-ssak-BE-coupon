package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.Getter;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.CouponType;

import java.time.LocalDate;

@Getter
public class CreateCouponInputVo {
    private String couponName;
    private CouponType couponType;
    private String couponNumber;
    private String barcodeUrl;
    private LocalDate couponIssueDate;
    private LocalDate couponExpirationDate;
    private String availableShop;
    private String couponImageUrl;
    private Boolean isRegistered;
}
