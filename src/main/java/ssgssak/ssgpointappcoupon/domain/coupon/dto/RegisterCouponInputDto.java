package ssgssak.ssgpointappcoupon.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCouponInputDto {
    private Long couponId;
    private String couponNumber;
    private String uuid; //todo: 임시설정
}
