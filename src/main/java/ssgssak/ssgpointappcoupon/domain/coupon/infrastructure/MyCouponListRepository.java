package ssgssak.ssgpointappcoupon.domain.coupon.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssgssak.ssgpointappcoupon.domain.coupon.entity.MyCouponListData;

@Repository
public interface MyCouponListRepository extends JpaRepository<MyCouponListData, Long> {
}
