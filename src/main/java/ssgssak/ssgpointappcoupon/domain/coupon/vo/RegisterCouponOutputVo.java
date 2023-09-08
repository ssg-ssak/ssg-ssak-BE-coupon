package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCouponOutputVo {
    private Long id;
    private Long couponId;
    private String uuid;
    private LocalDate downloadDate;
    private Boolean isUsed;
    private Boolean isExpired;
}
