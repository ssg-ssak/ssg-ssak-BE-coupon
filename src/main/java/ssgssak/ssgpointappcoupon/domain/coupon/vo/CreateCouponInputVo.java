package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.Getter;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.CouponType;

import java.time.LocalDate;

@Getter
public class CreateCouponInputVo {
    private LocalDate startDate;
    private LocalDate expirationDate;
    private String name;
    private String brand;
    private String discountImageUrl;
    private String brandImageUrl;
    private String brandLogoUrl;
    private CouponType type;
}
