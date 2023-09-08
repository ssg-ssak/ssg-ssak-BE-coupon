package ssgssak.ssgpointappcoupon.domain.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.Coupon;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetMyCouponsOutputVo {
    private List<Coupon> myCoupons;
}
