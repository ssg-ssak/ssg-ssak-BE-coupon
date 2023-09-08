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
    private LocalDate startDate;
    private LocalDate expirationDate;
    private String name;
    private String brand;
    private String discountImageUrl;
    private String brandImageUrl;
    private String brandLogoUrl;
    private CouponType type;
}
