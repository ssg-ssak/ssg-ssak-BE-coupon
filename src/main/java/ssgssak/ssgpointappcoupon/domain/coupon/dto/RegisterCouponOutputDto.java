package ssgssak.ssgpointappcoupon.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterCouponOutputDto {
    private Long id;
    private Long couponId;
    private LocalDate downloadDate;
    private Boolean isUsed;
    private Boolean isExpired;
}
