package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetUnavailableCouponsOutputVo {
    private List<Coupon> unavailableCoupons;
}
