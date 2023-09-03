package ssgssak.ssgpointappcoupon.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.CouponType;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCouponInputDto {
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
